package de.unidue.ltl.tagger.eng;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;

import de.unidue.ltl.tagger.Keys;
import de.unidue.ltl.tagger.NamedCorpusReaderDescription;
import de.unidue.ltl.tagger.reader.LinewiseTokenTagReader;

public class EnglishCorpora
    implements Keys
{
    public static final String LANG = "en";
    public static String corporaFolderPrefix;
    public static String mappingFolderPrefix;
    public static String defaultMap;

    public static NamedCorpusReaderDescription getWrittenGumNews()
        throws Exception
    {
        CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                LinewiseTokenTagReader.class, LinewiseTokenTagReader.PARAM_SOURCE_LOCATION,
                corporaFolderPrefix + "/english/formal/gum-news/",
                LinewiseTokenTagReader.PARAM_LANGUAGE, LANG, LinewiseTokenTagReader.PARAM_PATTERNS,
                "*.txt", LinewiseTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                        + "/en-ptb-tt-pos.map");
        
        NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("gum-news", DOMAIN_FORMAL, desc);
        return nrd;
    }

    public static NamedCorpusReaderDescription getWrittenGumVoyage()
        throws Exception
    {
        CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                LinewiseTokenTagReader.class, LinewiseTokenTagReader.PARAM_SOURCE_LOCATION,
                corporaFolderPrefix + "/english/formal/gum-voyage/",
                LinewiseTokenTagReader.PARAM_LANGUAGE, LANG, LinewiseTokenTagReader.PARAM_PATTERNS,
                "*.txt", LinewiseTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                + "/en-ptb-tt-pos.map");
        
        NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("gum-voyage", DOMAIN_FORMAL, desc);
        return nrd;
    }

    public static NamedCorpusReaderDescription getWrittenGumHowto()
        throws Exception
    {
        CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                LinewiseTokenTagReader.class, LinewiseTokenTagReader.PARAM_SOURCE_LOCATION,
                corporaFolderPrefix + "/english/formal/gum-howto/",
                LinewiseTokenTagReader.PARAM_LANGUAGE, LANG, LinewiseTokenTagReader.PARAM_PATTERNS,
                "*.txt", LinewiseTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                        + "/en-ptb-tt-pos.map");
        
        NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("gum-howto", DOMAIN_FORMAL, desc);
        return nrd;
    }

    
    public static NamedCorpusReaderDescription getWrittenBnc100k()
        throws Exception
    {
        CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                LinewiseTokenTagReader.class, LinewiseTokenTagReader.PARAM_SOURCE_LOCATION,
                corporaFolderPrefix + "/english/formal/bnc-written-news/",
                LinewiseTokenTagReader.PARAM_LANGUAGE, LANG, LinewiseTokenTagReader.PARAM_PATTERNS,
                "*.txt", LinewiseTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                        + "/" + "en-c5-pos.map");
        
        NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("bnc-100k-newspaper", DOMAIN_FORMAL, desc);
        return nrd;
    }

    public static NamedCorpusReaderDescription getWrittenBrown()
        throws Exception
    {
        CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                LinewiseTokenTagReader.class, LinewiseTokenTagReader.PARAM_SOURCE_LOCATION,
                corporaFolderPrefix + "/english/formal/brown/",
                LinewiseTokenTagReader.PARAM_LANGUAGE, LANG, LinewiseTokenTagReader.PARAM_PATTERNS,
                "*.txt", LinewiseTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                        + "/" + "en-browntei-pos.map");
        
        
        NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("brown", DOMAIN_FORMAL, desc);
        return nrd;
    }

   
    public static NamedCorpusReaderDescription getSocialGimpel()
        throws Exception
    {
        CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                LinewiseTokenTagReader.class, LinewiseTokenTagReader.PARAM_SOURCE_LOCATION,
                corporaFolderPrefix + "/english/social/gimpel/",
                LinewiseTokenTagReader.PARAM_LANGUAGE, LANG, LinewiseTokenTagReader.PARAM_PATTERNS,
                "*.txt", LinewiseTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                        + "/" + "en-arktweet-pos.map");
        
        
        NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("gimpel", DOMAIN_SOCIAL, desc);
        return nrd;
    }
    
    public static NamedCorpusReaderDescription getSocialNpsIrcChat() throws Exception
    {
        
        CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                LinewiseTokenTagReader.class, LinewiseTokenTagReader.PARAM_SOURCE_LOCATION,
                corporaFolderPrefix + "/english/social/nps-irc/",
                LinewiseTokenTagReader.PARAM_LANGUAGE, LANG, LinewiseTokenTagReader.PARAM_PATTERNS,
                "*.txt", LinewiseTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                        + "/" + defaultMap);
        
        NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("nps-irc", DOMAIN_SOCIAL, desc);
        return nrd;
    }

    public static NamedCorpusReaderDescription getSocialRitterTwitter() throws Exception
    {
        CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                LinewiseTokenTagReader.class, LinewiseTokenTagReader.PARAM_SOURCE_LOCATION,
                corporaFolderPrefix + "/english/social/ritter/",
                LinewiseTokenTagReader.PARAM_LANGUAGE, LANG, LinewiseTokenTagReader.PARAM_PATTERNS,
                "*.txt", LinewiseTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                        + "/" + defaultMap);
        
        NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("ritter", DOMAIN_SOCIAL, desc);
        return nrd;
    }

    public static NamedCorpusReaderDescription getSpokenSwitchboard()
        throws Exception
    {
        CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                LinewiseTokenTagReader.class, LinewiseTokenTagReader.PARAM_SOURCE_LOCATION,
                corporaFolderPrefix + "/english/spoken/switchboard/",
                LinewiseTokenTagReader.PARAM_LANGUAGE, LANG, LinewiseTokenTagReader.PARAM_PATTERNS,
                "*.txt", LinewiseTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                        + "/" + defaultMap);
        
        NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("switchboard", DOMAIN_SPOKEN, desc);
        return nrd;
    }

    public static NamedCorpusReaderDescription getSpokenBnc()
        throws Exception
    {
        CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                LinewiseTokenTagReader.class, LinewiseTokenTagReader.PARAM_SOURCE_LOCATION,
                corporaFolderPrefix + "/english/spoken/bnc-conversations/",
                LinewiseTokenTagReader.PARAM_LANGUAGE, LANG, LinewiseTokenTagReader.PARAM_PATTERNS,
                "*.txt", LinewiseTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                        + "/" + "en-c5-pos.map");
        
        NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("bnc-100k-conversation", DOMAIN_SPOKEN, desc);
        return nrd;
    }

   
    public static NamedCorpusReaderDescription getSpokenGumInverview()
        throws Exception
    {
        CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(
                LinewiseTokenTagReader.class, LinewiseTokenTagReader.PARAM_SOURCE_LOCATION,
                corporaFolderPrefix + "/english/spoken/gum-inverview/",
                LinewiseTokenTagReader.PARAM_LANGUAGE, LANG, LinewiseTokenTagReader.PARAM_PATTERNS,
                "*.txt", LinewiseTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix
                        + "/en-ptb-tt-pos.map");
        
        NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("gum-inverview", DOMAIN_SPOKEN, desc);
        return nrd;
    }


}
