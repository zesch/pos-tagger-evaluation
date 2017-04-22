package de.unidue.ltl.tagger.ger;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;

import de.unidue.ltl.tagger.Keys;
import de.unidue.ltl.tagger.NamedCorpusReaderDescription;
import de.unidue.ltl.tagger.reader.LinewiseTokenTagReader;

public class GermanCorpora
    implements Keys
{
    public static final String LANG = "de";
    public static String corporaFolderPrefix;
    public static String mappingFolderPrefix;
    public static String defaultMap;

    public static NamedCorpusReaderDescription getFormalTuebadz()
        throws Exception
    {
        CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                LinewiseTokenTagReader.class, LinewiseTokenTagReader.PARAM_SOURCE_LOCATION,
                corporaFolderPrefix + "/german/formal/tuebadz",
                LinewiseTokenTagReader.PARAM_LANGUAGE, LANG, LinewiseTokenTagReader.PARAM_PATTERNS,
                "*.txt", LinewiseTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                        + defaultMap);

        NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("Tuebadz",
                DOMAIN_FORMAL, desc);
        return nrd;
    }
    
    public static NamedCorpusReaderDescription getFormalHamburgTreebank()
        throws Exception
    {
        CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                LinewiseTokenTagReader.class, LinewiseTokenTagReader.PARAM_SOURCE_LOCATION,
                corporaFolderPrefix + "/german/formal/hamburgTreebank",
                LinewiseTokenTagReader.PARAM_LANGUAGE, LANG, LinewiseTokenTagReader.PARAM_PATTERNS,
                "*.txt", LinewiseTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                        + defaultMap);

        NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("HamburgTree",
                DOMAIN_FORMAL, desc);
        return nrd;
    }
    
    public static NamedCorpusReaderDescription getSocialRehbein()
            throws Exception
        {
            CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                    LinewiseTokenTagReader.class, LinewiseTokenTagReader.PARAM_SOURCE_LOCATION,
                    corporaFolderPrefix + "/german/social/rehbein/",
                    LinewiseTokenTagReader.PARAM_LANGUAGE, LANG, LinewiseTokenTagReader.PARAM_PATTERNS,
                    "*.txt", LinewiseTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                            + "de-stts-rehbein-pos.map");

            NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("Rehbein",
                    DOMAIN_SOCIAL, desc);
            return nrd;
        }
    
    public static NamedCorpusReaderDescription getSocialNeunerdt()
            throws Exception
        {
            CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                    LinewiseTokenTagReader.class, LinewiseTokenTagReader.PARAM_SOURCE_LOCATION,
                    corporaFolderPrefix + "/german/social/neunerdt/",
                    LinewiseTokenTagReader.PARAM_LANGUAGE, LANG, LinewiseTokenTagReader.PARAM_PATTERNS,
                    "*.txt", LinewiseTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                            + defaultMap);

            NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("Neunerdt",
                    DOMAIN_SOCIAL, desc);
            return nrd;
        }
    
    public static NamedCorpusReaderDescription getSpokenFolk()
            throws Exception
        {
            CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                    LinewiseTokenTagReader.class, LinewiseTokenTagReader.PARAM_SOURCE_LOCATION,
                    corporaFolderPrefix + "/german/social/folk/",
                    LinewiseTokenTagReader.PARAM_LANGUAGE, LANG, LinewiseTokenTagReader.PARAM_PATTERNS,
                    "*.txt", LinewiseTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                            + "de-stts-folk.map");

            NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("Folk",
                    DOMAIN_SPOKEN, desc);
            return nrd;
        }
}
