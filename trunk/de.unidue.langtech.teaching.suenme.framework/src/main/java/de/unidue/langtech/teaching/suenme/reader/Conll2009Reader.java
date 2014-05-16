
package de.unidue.langtech.teaching.suenme.reader;

import static org.apache.commons.io.IOUtils.closeQuietly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.JCasBuilder;
import org.apache.uima.jcas.JCas;
import de.tudarmstadt.ukp.dkpro.core.api.io.JCasResourceCollectionReader_ImplBase;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.parameter.ComponentParameters;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Lemma;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

/**
 * Reads a specific Conll File (14-15 TAB separated) annotation and change it to CAS object. Format:
 * 
 * <pre>
 * Heutzutage heutzutage ADV _ _ ADV _ _
 * </pre>
 * <ol>
 * <li>ID - token number in sentence</li>
 * <li>FORM - token</li>
 * <li>LEMMA - lemma</li>
 * <li>PLEMMA - unused</li>
 * <li>POSTAG - part-of-speech tag</li>
 * <li>PPOSTAG - unused</li>
 * <li>FEATS - unused</li>
 * <li>PFEATS - unused</li>
 * <li>HEAD - target token for a dependency parsing</li>
 * <li>PHEAD - unused</li>
 * <li>DEPREL - function of the dependency parsing</li>
 * <li>PDEPREL - unused</li>
 * <li>FILLPRED - unused</li>
 * <li>PRED - unused</li>
 * <li>APREDS - unused</li>
 * </ol>
 * 
 * Sentences are separated by a blank new line
 * 
 * @author Onur
 * 
 * @see http://ufal.mff.cuni.cz/conll2009-st/task-description.html
 * inspired by Conll2006Reader @author Seid Muhie Yimam @author Richard Eckart de Castilho
 */
public class Conll2009Reader
    extends JCasResourceCollectionReader_ImplBase
{
    private static final String UNUSED = "_";

    private static final int ID = 0;
    private static final int FORM = 1;
    private static final int LEMMA = 2;
    // private static final int PLEMMA = 3;
    private static final int POSTAG = 4;
    //private static final int PPOSTAG = 5;
    // private static final int FEATS = 6;
    // private static final int PFEATS = 7;
    private static final int HEAD = 8;
    //private static final int PHEAD = 9;
    private static final int DEPREL = 10;
    //private static final int PDEPREL = 11;
    // private static final int FILLPRED = 12;
    // private static final int PRED = 13;
    // private static final int APREDS = 13;

    public static final String PARAM_ENCODING = ComponentParameters.PARAM_SOURCE_ENCODING;
    @ConfigurationParameter(name = PARAM_ENCODING, mandatory = true, defaultValue = "UTF-8")
    private String encoding;

    @Override
    public void getNext(JCas aJCas)
        throws IOException, CollectionException
    {
        Resource res = nextFile();
        initCas(aJCas, res);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(res.getInputStream(), encoding));
            convert(aJCas, reader);
        }
        finally {
            closeQuietly(reader);
        }
    }

    public void convert(JCas aJCas, BufferedReader aReader)
        throws IOException
    {
        JCasBuilder doc = new JCasBuilder(aJCas);

        List<String[]> words;
        while ((words = readSentence(aReader)) != null) {
            if (words.isEmpty()) {
                continue;
            }

            int sentenceBegin = doc.getPosition();
            int sentenceEnd = sentenceBegin;

            // Tokens, Lemma, POS
            Map<Integer, Token> tokens = new HashMap<Integer, Token>();
            for (String[] word : words) {
                // Read token
                Token token = doc.add(word[FORM], Token.class);
                tokens.put(Integer.valueOf(word[ID]), token);
                doc.add(" ");

                // Read lemma
                if (!UNUSED.equals(word[LEMMA])) {
                    Lemma lemma = new Lemma(aJCas, token.getBegin(), token.getEnd());
                    lemma.setValue(word[LEMMA]);
                    lemma.addToIndexes();
                    token.setLemma(lemma);
                }

                // Read part-of-speech tag
                if (!UNUSED.equals(word[POSTAG])) {
                    POS pos = new POS(aJCas, token.getBegin(), token.getEnd());
                    pos.setPosValue(word[POSTAG]);
                    pos.addToIndexes();
                    token.setPos(pos);
                }

                sentenceEnd = token.getEnd();
            }

            // Dependencies
            for (String[] word : words) {
                if (!UNUSED.equals(word[DEPREL])) {
                    int depId = Integer.valueOf(word[ID]);
                    int govId = Integer.valueOf(word[HEAD]);

                    // Model the root as a loop onto itself
                    if (govId == 0) {
                        govId = depId;
                    }

                    Dependency rel = new Dependency(aJCas);
                    rel.setGovernor(tokens.get(govId));
                    rel.setDependent(tokens.get(depId));
                    rel.setDependencyType(word[DEPREL]);
                    rel.setBegin(rel.getDependent().getBegin());
                    rel.setEnd(rel.getDependent().getEnd());
                    rel.addToIndexes();
                }
            }

            // Sentence
            Sentence sentence = new Sentence(aJCas, sentenceBegin, sentenceEnd);
            sentence.addToIndexes();

            // Once sentence per line.
            doc.add("\n");
        }

        doc.close();
    }

    /**
     * Read a single sentence.
     * no sanity check because length of fields varies
     */
    private static List<String[]> readSentence(BufferedReader aReader)
        throws IOException
    {
        List<String[]> words = new ArrayList<String[]>();
        String line;
        while ((line = aReader.readLine()) != null) {
            if (StringUtils.isBlank(line)) {
                break; // End of sentence
            }
            String[] fields = line.split("\t");
            words.add(fields);
        }

        if (line == null && words.isEmpty()) {
            return null;
        }
        else {
            return words;
        }
    }
}
