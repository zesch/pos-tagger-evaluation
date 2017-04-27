package de.unidue.ltl.tagger;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;

import de.unidue.ltl.tagger.components.Keys;
import de.unidue.ltl.tagger.components.NamedCorpusReaderDescription;
import de.unidue.ltl.toobee.readers.LineTokenTagReader;

public class GermanCorpora
    implements Keys
{
    public static final String LANG = "de";
    public static String corporaFolderPrefix;
    public static String mappingFolderPrefix;
    public static String defaultMap;
    
    static String seqLimit="1000";

    public static NamedCorpusReaderDescription getFormalTuebadz()
        throws Exception
    {
        CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                LineTokenTagReader.class, LineTokenTagReader.PARAM_SOURCE_LOCATION,
                corporaFolderPrefix + "/german/formal/tuebadz",
                LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
                LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS,
                "*.txt", LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                        + defaultMap);

        NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("Tuebadz",
                DOMAIN_FORMAL, desc);
        return nrd;
    }
    
    public static NamedCorpusReaderDescription getFormalHamburgTreebank()
        throws Exception
    {
        CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                LineTokenTagReader.class, LineTokenTagReader.PARAM_SOURCE_LOCATION,
                corporaFolderPrefix + "/german/formal/hamburgTreebank",
                LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
                LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS,
                "*.txt", LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                        + defaultMap);

        NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("HamburgTree",
                DOMAIN_FORMAL, desc);
        return nrd;
    }
    
    public static NamedCorpusReaderDescription getSocialRehbein()
            throws Exception
        {
            CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                    LineTokenTagReader.class, LineTokenTagReader.PARAM_SOURCE_LOCATION,
                    corporaFolderPrefix + "/german/social/rehbein/",
                    LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
                    LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS,
                    "*.txt", LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                            + "de-stts-rehbein-pos.map");

            NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("Rehbein",
                    DOMAIN_SOCIAL, desc);
            return nrd;
        }

    public static NamedCorpusReaderDescription getSocialEmpiriCmc()
            throws Exception
        {
            CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                    LineTokenTagReader.class, LineTokenTagReader.PARAM_SOURCE_LOCATION,
                    corporaFolderPrefix + "/german/social/empiriCmc/",
                    LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
                    LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS,
                    "*.txt", LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                            + "de-stts-empiri-pos.map");

            NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("EmpiriCmc",
                    DOMAIN_SOCIAL, desc);
            return nrd;
        }
    
    public static NamedCorpusReaderDescription getSocialEmpiriWeb()
            throws Exception
        {
            CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                    LineTokenTagReader.class, LineTokenTagReader.PARAM_SOURCE_LOCATION,
                    corporaFolderPrefix + "/german/social/empiriWeb/",
                    LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
                    LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS,
                    "*.txt", LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                            + "de-stts-empiri-pos.map");

            NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("EmpiriWeb",
                    DOMAIN_SOCIAL, desc);
            return nrd;
        }    
    
    public static NamedCorpusReaderDescription getSocialNeunerdt()
            throws Exception
        {
            CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                    LineTokenTagReader.class, LineTokenTagReader.PARAM_SOURCE_LOCATION,
                    corporaFolderPrefix + "/german/social/neunerdt/",
                    LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
                    LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS,
                    "*.txt", LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                            + defaultMap);

            NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("Neunerdt",
                    DOMAIN_SOCIAL, desc);
            return nrd;
        }
    
    public static NamedCorpusReaderDescription getSpokenFolk()
            throws Exception
        {
            CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                    LineTokenTagReader.class, LineTokenTagReader.PARAM_SOURCE_LOCATION,
                    corporaFolderPrefix + "/german/spoken/folk/",
                    LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
                    LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS,
                    "*.txt", LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                            + "de-stts-folk.map");

            NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("Folk",
                    DOMAIN_SPOKEN, desc);
            return nrd;
        }
}
