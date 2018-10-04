package de.unidue.ltl.tagger.reader;

/*******************************************************************************
 * Copyright 2014
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universit??t Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.Type;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;

import de.tudarmstadt.ukp.dkpro.core.api.io.JCasResourceCollectionReader_ImplBase;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.dkpro.core.api.parameter.ComponentParameters;
import de.tudarmstadt.ukp.dkpro.core.api.resources.MappingProvider;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

@TypeCapability(outputs = { "de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData",
		"de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence",
		"de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token",
		"de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS" })
public class LineTokenTagReader extends JCasResourceCollectionReader_ImplBase {

	public static final String PARAM_POS_MAPPING_LOCATION = ComponentParameters.PARAM_POS_MAPPING_LOCATION;
	@ConfigurationParameter(name = PARAM_POS_MAPPING_LOCATION, mandatory = false)
	protected String mappingPosLocation;

	public static final String PARAM_POS_TAGSET = ComponentParameters.PARAM_POS_TAG_SET;
	@ConfigurationParameter(name = PARAM_POS_TAGSET, mandatory = false)
	protected String posTagset;

	public static final String PARAM_SOURCE_ENCODING = ComponentParameters.PARAM_SOURCE_ENCODING;
	@ConfigurationParameter(name = PARAM_SOURCE_ENCODING, mandatory = true, defaultValue = "UTF-8")
	protected String encoding;

	public static final String PARAM_LANGUAGE = ComponentParameters.PARAM_LANGUAGE;
	@ConfigurationParameter(name = PARAM_LANGUAGE, mandatory = false)
	private String language;

	public static final String PARAM_LOWER_CASE = "PARAM_LOWER_CASE";
	@ConfigurationParameter(name = PARAM_LOWER_CASE, mandatory = false, defaultValue = "false")
	private boolean lowerCase;

	public static final String PARAM_SEPARATOR = "PARAM_SEPARATOR";
	@ConfigurationParameter(name = PARAM_SEPARATOR, mandatory = true, defaultValue = " ")
	private String separator;

	public static final String PARAM_SET_POS = "PARAM_SET_POS";
	@ConfigurationParameter(name = PARAM_SET_POS, mandatory = true, defaultValue = "true")
	private boolean setPos;

	public static final String PARAM_SEQUENCES_PER_CAS = "PARAM_SEQUENCES_PER_CAS";
	@ConfigurationParameter(name = PARAM_SEQUENCES_PER_CAS, mandatory = true, defaultValue = "1000")
	private String seqLimitString;

	int seqLimit = -1;

	public static final String ENCODING_AUTO = "auto";

	private MappingProvider posMappingProvider;

	private BufferedReader reader;

	private List<List<String>> sequences = new ArrayList<List<String>>();

	private List<Resource> resourcePool = new ArrayList<Resource>();
	private int pointer = -1;

	private int instanceId = 1;

	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);

		seqLimit = Integer.valueOf(seqLimitString);

		posMappingProvider = new MappingProvider();
		posMappingProvider.setDefault(MappingProvider.LOCATION, "classpath:/de/tudarmstadt/ukp/dkpro/"
				+ "core/api/lexmorph/tagset/${language}-${tagger.tagset}-pos.map");
		posMappingProvider.setDefault(MappingProvider.BASE_TYPE, POS.class.getName());
		posMappingProvider.setDefault("tagger.tagset", "default");
		posMappingProvider.setOverride(MappingProvider.LOCATION, mappingPosLocation);
		posMappingProvider.setOverride(MappingProvider.LANGUAGE, language);
		posMappingProvider.setOverride("tagger.tagset", posTagset);

		List<Resource> resourcess = new ArrayList<Resource>(getResources());
		for (int i = 1; i < resourcess.size(); i++) {
			resourcePool.add(resourcess.get(i));
		}
		try {
			reader = new BufferedReader(openStream(resourcess.get(0)));
		} catch (IOException e) {
			throw new ResourceInitializationException(e);
		}
	}

	public void getNext(JCas aJCas) throws IOException, CollectionException {

		DocumentMetaData md = new DocumentMetaData(aJCas);
		md.setDocumentTitle("");
		md.setDocumentId("" + (instanceId++));
		md.setLanguage(language);
		md.addToIndexes();

		try {
			posMappingProvider.configure(aJCas.getCas());
		} catch (AnalysisEngineProcessException e1) {
			throw new CollectionException(e1);
		}

		StringBuilder documentText = new StringBuilder();

		int seqStart = 0;
		for (int k = 0; k < sequences.size(); k++) {

			List<String> sequence = sequences.get(k);

			for (int i = 0; i < sequence.size(); i++) {
				String pairs = sequence.get(i).replaceAll(" +", " ");

				int idxLastSpace = pairs.lastIndexOf(separator);
				String token = pairs.substring(0, idxLastSpace);
				String tag = pairs.substring(idxLastSpace + 1);

				int tokenLen = token.length();
				if (lowerCase) {
					token = token.toLowerCase();
				}
				documentText.append(token);

				int tokStart = documentText.length() - tokenLen;
				int tokEnd = documentText.length();
				Token t = new Token(aJCas, tokStart, tokEnd);
				t.addToIndexes();

				if (i + 1 < sequence.size()) {
					documentText.append(" ");
				}

				if (setPos) {
					Type posTag = posMappingProvider.getTagType(tag);
					POS pos = (POS) aJCas.getCas().createAnnotation(posTag, t.getBegin(), t.getEnd());
					pos.setPosValue(tag);
					pos.addToIndexes();
					t.setPos(pos);
				}
			}
			Sentence sentence = new Sentence(aJCas, seqStart, documentText.length());
			sentence.addToIndexes();

			if (k + 1 < sequences.size()) {
				documentText.append(" ");
			}
			seqStart = documentText.length();

		}
		aJCas.setDocumentText(documentText.toString());
	}

	public boolean hasNext() throws IOException, CollectionException {
		BufferedReader br = getBufferedReader();

		int readSeq = 0;
		sequences = new ArrayList<List<String>>();
		List<String> sequence = new ArrayList<>();
		String readLine = null;
		while ((readLine = br.readLine()) != null) {
			if (readLine.isEmpty()) {
				readSeq++;
				if (readSeq == seqLimit) {
					break;
				}
				sequences.add(sequence);
				sequence = new ArrayList<>();
				continue;
			}
			sequence.add(readLine);
		}
		if (!sequence.isEmpty()) {
			sequences.add(sequence);
		}
		if (!sequences.isEmpty()) {
			return true;
		}

		return closeReaderOpenNext();

	}

	private boolean closeReaderOpenNext() throws CollectionException, IOException {
		reader.close();
		reader = null;

		if (pointer + 1 < resourcePool.size()) {
			pointer++;
			Resource resource = resourcePool.get(pointer);
			reader = new BufferedReader(openStream(resource));
			return hasNext();
		}
		return false;
	}

	private InputStreamReader openStream(Resource resource) throws IOException {
		String name = resource.getResource().getFile().getName();
		InputStreamReader is = null;
		if (name.endsWith(".gz")) {
			is = new InputStreamReader(new GZIPInputStream(resource.getInputStream()), encoding);
		} else {
			is = new InputStreamReader(resource.getInputStream(), encoding);
		}
		return is;
	}

	private BufferedReader getBufferedReader() {
		return reader;
	}

	public Progress[] getProgress() {
		return null;
	}
}
