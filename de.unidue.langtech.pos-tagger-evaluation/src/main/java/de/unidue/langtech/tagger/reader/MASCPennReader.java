package de.unidue.langtech.tagger.reader;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.Type;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.io.JCasResourceCollectionReader_ImplBase;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.parameter.ComponentParameters;
import de.tudarmstadt.ukp.dkpro.core.api.resources.MappingProvider;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class MASCPennReader
    extends JCasResourceCollectionReader_ImplBase
{

    public static final String PARAM_POS_MAPPING_LOCATION = ComponentParameters.PARAM_POS_MAPPING_LOCATION;
    @ConfigurationParameter(name = PARAM_POS_MAPPING_LOCATION, mandatory = false)
    protected String mappingPosLocation;

    public static final String PARAM_POS_TAGSET = ComponentParameters.PARAM_POS_TAG_SET;
    @ConfigurationParameter(name = PARAM_POS_TAGSET, mandatory = false)
    protected String posTagset;

    public static final String PARAM_LANGUAGE = ComponentParameters.PARAM_LANGUAGE;
    @ConfigurationParameter(name = PARAM_LANGUAGE, mandatory = false)
    private String language;

    private static final String WHITESPACE = " ";

    private MappingProvider posMappingProvider;

    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);

        posMappingProvider = new MappingProvider();
        posMappingProvider.setDefault(MappingProvider.LOCATION,
                "classpath:/de/tudarmstadt/ukp/dkpro/"
                        + "core/api/lexmorph/tagset/${language}-${tagger.tagset}-pos.map");
        posMappingProvider.setDefault(MappingProvider.BASE_TYPE, POS.class.getName());
        posMappingProvider.setDefault("tagger.tagset", "default");
        posMappingProvider.setOverride(MappingProvider.LOCATION, mappingPosLocation);
        posMappingProvider.setOverride(MappingProvider.LANGUAGE, language);
        posMappingProvider.setOverride("tagger.tagset", posTagset);

    }

    @Override
    public void getNext(JCas aJCas)
        throws IOException, CollectionException
    {
        aJCas.setDocumentLanguage("en");
        try {
            posMappingProvider.configure(aJCas.getCas());
        }
        catch (AnalysisEngineProcessException e) {
            throw new IOException(e);
        }
        Resource nextFile = nextFile();
        List<String> xmlDocument = FileUtils.readLines(nextFile.getResource().getFile());

        int i = 0;
        TreeMap<Integer, String[]> treeMap = new TreeMap<Integer, String[]>();
        for (; i < xmlDocument.size(); i++) {
            String currentLine = xmlDocument.get(i);
            if (!isTokenEntry(currentLine)) {
                continue;
            }
            int position = getPositionOfToken(currentLine);
            String[] tokInf = new String[2];
            i = readTokenData(i, xmlDocument, tokInf);
            treeMap.put(position, tokInf);
        }

        StringBuilder documentBuilder = new StringBuilder();
        int lastAnnotatedSentenceIndex = 0;
        for (Integer k : treeMap.keySet()) {
            String[] strings = treeMap.get(k);

            int idxTokenStart = documentBuilder.length();
            documentBuilder.append(strings[0]);
            int idxTokenEnd = documentBuilder.length();

            Token t = new Token(aJCas, idxTokenStart, idxTokenEnd);
            Type posTag = posMappingProvider.getTagType(strings[1]);
            POS pos = (POS) aJCas.getCas().createAnnotation(posTag, t.getBegin(), t.getEnd());
            pos.setPosValue(strings[1]);
            pos.addToIndexes();
            t.setPos(pos);
            t.addToIndexes();

            documentBuilder.append(WHITESPACE);

            if (endOfSequence(strings)) {
                int begin = lastAnnotatedSentenceIndex;
                int end = documentBuilder.toString().trim().length();
                Sentence s = new Sentence(aJCas, begin, end);
                s.addToIndexes();
                lastAnnotatedSentenceIndex = end + 1;
            }

        }

        if (lastAnnotatedSentenceIndex < documentBuilder.toString().trim().length()) {
            int begin = lastAnnotatedSentenceIndex;
            int end = documentBuilder.toString().trim().length();
            Sentence s = new Sentence(aJCas, begin, end);
            s.addToIndexes();
        }

        aJCas.setDocumentText(documentBuilder.toString().trim());

    }

    private boolean endOfSequence(String[] aStrings)
    {
        return aStrings[1].equals(".");
    }

    private int readTokenData(int idx, List<String> document, String[] aTokInf)
    {
        int j = idx + 1;
        for (; j < document.size(); j++) {
            String line = document.get(j);
            if (line.contains("<fs>")) {
                continue;
            }
            if (line.contains("</fs>")) {
                break;
            }

            // string field might not exist take the base in these cases (if string fiel occurs
            // later it is overwritten)
            if (aTokInf[0] == null && line.contains("<f name=\"base\"")) {
                aTokInf[0] = extractSubstring(line, "<f name=\"base\" value=\"", "\"").trim();
            }
            if (line.contains("<f name=\"string\"")) {
                aTokInf[0] = extractSubstring(line, "<f name=\"string\" value=\"", "\"").trim();
            }
            if (line.contains("<f name=\"msd\"")) {
                aTokInf[1] = extractSubstring(line, "<f name=\"msd\" value=\"", "\"").trim();
            }
        }

        return j;
    }

    private int getPositionOfToken(String aCurrentLine)
    {
        String BEG = "ref=\"penn-n";
        String END = "\"";
        return Integer.valueOf(extractSubstring(aCurrentLine, BEG, END));
    }

    private String extractSubstring(String input, String patternBegin, String patternEnd)
    {
        int idxStart = input.indexOf(patternBegin);
        int idxEnd = input.indexOf("\"", idxStart + patternBegin.length());
        String extract = input.substring(idxStart + patternBegin.length(), idxEnd);
        return extract;
    }

    private boolean isTokenEntry(String aCurrentLine)
    {
        return aCurrentLine.contains("<a xml:id=\"penn-");
    }

}
