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

@TypeCapability(outputs = {
		"de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData",
		"de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence",
		"de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token",
		"de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS" })
public class LinewiseTokenTagSingleCasReader extends
		JCasResourceCollectionReader_ImplBase  {

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

	/**
	 * Whether coarse-grained or fine-grained POS tags should be used.
	 */
	public static final String PARAM_USE_COARSE_GRAINED = "useCoarseGrained";
	@ConfigurationParameter(name = PARAM_USE_COARSE_GRAINED, mandatory = true, defaultValue = "false")
	protected boolean useCoarseGrained;

	public static final String ENCODING_AUTO = "auto";

	private MappingProvider posMappingProvider;

	private BufferedReader br;

	private List<BufferedReader> bfs = new ArrayList<BufferedReader>();
	private int currentReader = 0;

	private int instanceId = 1;

	boolean hasNext = true;
	
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);

		posMappingProvider = new MappingProvider();
		posMappingProvider
				.setDefault(
						MappingProvider.LOCATION,
						"classpath:/de/tudarmstadt/ukp/dkpro/"
								+ "core/api/lexmorph/tagset/${language}-${tagger.tagset}-pos.map");
		posMappingProvider.setDefault(MappingProvider.BASE_TYPE,
				POS.class.getName());
		posMappingProvider.setDefault("tagger.tagset", "default");
		posMappingProvider.setOverride(MappingProvider.LOCATION,
				mappingPosLocation);
		posMappingProvider.setOverride(MappingProvider.LANGUAGE, language);
		posMappingProvider.setOverride("tagger.tagset", posTagset);

		try {
			for (Resource r : getResources()) {
				String name = r.getResource().getFile().getName();
				InputStreamReader is = null;
				if (name.endsWith(".gz")) {
					is = new InputStreamReader(new GZIPInputStream(
							r.getInputStream()), encoding);
				} else {
					is = new InputStreamReader(r.getInputStream(), encoding);
				}
				br = new BufferedReader(is);
				bfs.add(br);
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	int sentBeg = 0;
	public void getNext(JCas aJCas) throws IOException, CollectionException {
		DocumentMetaData md = new DocumentMetaData(aJCas);
		md.setDocumentId("" + (instanceId++));
		md.setLanguage(language);
		md.addToIndexes();

		try {
			posMappingProvider.configure(aJCas.getCas());
		} catch (AnalysisEngineProcessException e1) {
			throw new IOException(e1);
		}

		StringBuilder documentText = new StringBuilder();

		while(currentReader < bfs.size()){
			BufferedReader br = getBufferedReader();
			documentText = readAndAnnotate(br, aJCas,documentText);
			br.close();
			currentReader++;
		}
		
		aJCas.setDocumentText(documentText.toString());
		hasNext=false;

		// for (Sentence s : JCasUtil.select(aJCas, Sentence.class)){
		// List<Token> selectCovered = JCasUtil.selectCovered(aJCas,
		// Token.class, s.getBegin(), s.getEnd());
		// for (Token t : selectCovered){
		// System.out.print("["+t.getCoveredText()+"]");
		// }
		// System.out.println();
		// }
		
	}

	private StringBuilder readAndAnnotate(BufferedReader br, JCas aJCas, StringBuilder documentText) throws IOException {
String readLine = null;
		
		while ((readLine = br.readLine()) != null) {
			if (readLine.isEmpty()) {
				Sentence s = new Sentence(aJCas, sentBeg, documentText.length());
				s.addToIndexes();
				sentBeg = documentText.length();
				continue;
			}

			// There might a case where trailing spaces exist - make sure
			// that
			// we find only one blank!
			String pairs = readLine.replaceAll(" +", " ");

			String[] tokenTag = pairs.split(" ");

			int tokenLen = tokenTag[0].length();
			documentText.append(tokenTag[0]);

			int tokStart = documentText.length() - tokenLen;
			int tokEnd = documentText.length();
			Token token = new Token(aJCas, tokStart, tokEnd);
			token.addToIndexes();

			Type posTag = posMappingProvider.getTagType(tokenTag[1]);
			POS pos = (POS) aJCas.getCas().createAnnotation(posTag,
					token.getBegin(), token.getEnd());
			pos.setPosValue(tokenTag[1]);
			pos.addToIndexes();
			token.setPos(pos);
			
			documentText.append(" ");
		}
		 
		return documentText;
	}


	public boolean hasNext() throws IOException, CollectionException {
		return hasNext;
	}

	private BufferedReader getBufferedReader() {
		return bfs.get(currentReader);
	}

	public Progress[] getProgress() {
		return null;
	}
}
