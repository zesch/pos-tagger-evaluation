package de.unidue.ltl.tagger;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;

import de.unidue.ltl.tagger.components.Keys;
import de.unidue.ltl.tagger.components.NamedCorpusReaderDescription;
import de.unidue.ltl.toobee.readers.LineTokenTagReader;

public class EnglishCorpora implements Keys {
	public static final String LANG = "en";
	public static String corporaFolderPrefix;
	public static String mappingFolderPrefix;
	public static String defaultMap;
	
	static String seqLimit="1000";

	public static NamedCorpusReaderDescription getWrittenGumNews() throws Exception {
		CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(LineTokenTagReader.class,
				LineTokenTagReader.PARAM_SOURCE_LOCATION, corporaFolderPrefix + "/english/formal/gum-news/",
				LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS, "*.txt",
				LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
				LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix + "/en-ptb-tt-pos.map");

		NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("gum-news", DOMAIN_FORMAL, desc);
		return nrd;
	}

	public static NamedCorpusReaderDescription getWrittenGumVoyage() throws Exception {
		CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(LineTokenTagReader.class,
				LineTokenTagReader.PARAM_SOURCE_LOCATION, corporaFolderPrefix + "/english/formal/gum-voyage/",
				LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS, "*.txt",
				LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
				LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix + "/en-ptb-tt-pos.map");

		NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("gum-voyage", DOMAIN_FORMAL, desc);
		return nrd;
	}

	public static NamedCorpusReaderDescription getWrittenGumHowto() throws Exception {
		CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(LineTokenTagReader.class,
				LineTokenTagReader.PARAM_SOURCE_LOCATION, corporaFolderPrefix + "/english/formal/gum-howto/",
				LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS, "*.txt",
				LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
				LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix + "/en-ptb-tt-pos.map");

		NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("gum-howto", DOMAIN_FORMAL, desc);
		return nrd;
	}

	public static NamedCorpusReaderDescription getWrittenBnc100k() throws Exception {
		CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(LineTokenTagReader.class,
				LineTokenTagReader.PARAM_SOURCE_LOCATION, corporaFolderPrefix + "/english/formal/bnc-written-news/",
				LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS, "*.txt",
				LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
				LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix + "/" + "en-c5-pos.map");

		NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("bnc-100k-newspaper", DOMAIN_FORMAL, desc);
		return nrd;
	}

	public static NamedCorpusReaderDescription getWrittenBrown() throws Exception {
		CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(LineTokenTagReader.class,
				LineTokenTagReader.PARAM_SOURCE_LOCATION, corporaFolderPrefix + "/english/formal/brown/",
				LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS, "*.txt",
				LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
				LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix + "/" + "en-browntei-pos.map");

		NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("brown", DOMAIN_FORMAL, desc);
		return nrd;
	}

	public static NamedCorpusReaderDescription getSocialGimpel() throws Exception {
		CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(LineTokenTagReader.class,
				LineTokenTagReader.PARAM_SOURCE_LOCATION, corporaFolderPrefix + "/english/social/gimpel/",
				LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS, "*.txt",
				LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
				LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix + "/" + "en-arktweet-pos.map");

		NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("gimpel", DOMAIN_SOCIAL, desc);
		return nrd;
	}

	public static NamedCorpusReaderDescription getSocialNpsIrcChat() throws Exception {

		CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(LineTokenTagReader.class,
				LineTokenTagReader.PARAM_SOURCE_LOCATION, corporaFolderPrefix + "/english/social/nps-irc/",
				LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS, "*.txt",
				LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
				LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix + "/" + defaultMap);

		NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("nps-irc", DOMAIN_SOCIAL, desc);
		return nrd;
	}

	public static NamedCorpusReaderDescription getSocialRitterTwitter() throws Exception {
		CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(LineTokenTagReader.class,
				LineTokenTagReader.PARAM_SOURCE_LOCATION, corporaFolderPrefix + "/english/social/ritter/",
				LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS, "*.txt",
				LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
				LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix + "/" + defaultMap);

		NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("ritter", DOMAIN_SOCIAL, desc);
		return nrd;
	}

	public static NamedCorpusReaderDescription getSocialAaveTwitter() throws Exception {
		CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(LineTokenTagReader.class,
				LineTokenTagReader.PARAM_SOURCE_LOCATION, corporaFolderPrefix + "/english/social/aaveTweets/",
				LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS, "*.txt",
				LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
				LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix + "/" + "en-aavee-pos.map");

		NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("aaveTweet", DOMAIN_SOCIAL, desc);
		return nrd;
	}

	public static NamedCorpusReaderDescription getSpokenSwitchboard() throws Exception {
		CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(LineTokenTagReader.class,
				LineTokenTagReader.PARAM_SOURCE_LOCATION, corporaFolderPrefix + "/english/spoken/switchboard/",
				LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS, "*.txt",
				LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
				LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix + "/" + defaultMap);

		NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("switchboard", DOMAIN_SPOKEN, desc);
		return nrd;
	}

	public static NamedCorpusReaderDescription getSpokenTedTalk() throws Exception {
		CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(LineTokenTagReader.class,
				LineTokenTagReader.PARAM_SOURCE_LOCATION, corporaFolderPrefix + "/english/spoken/tedTalk/",
				LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS, "*.txt",
				LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
				LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix + "/" + defaultMap);

		NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("tedTalks", DOMAIN_SPOKEN, desc);
		return nrd;
	}

	public static NamedCorpusReaderDescription getSpokenBnc() throws Exception {
		CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(LineTokenTagReader.class,
				LineTokenTagReader.PARAM_SOURCE_LOCATION,
				corporaFolderPrefix + "/english/spoken/bnc-conversations/", LineTokenTagReader.PARAM_LANGUAGE, LANG,
				LineTokenTagReader.PARAM_PATTERNS, "*.txt",
				LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
				LineTokenTagReader.PARAM_POS_MAPPING_LOCATION,
				mappingFolderPrefix + "/" + "en-c5-pos.map");

		NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("bnc-100k-conversation", DOMAIN_SPOKEN,
				desc);
		return nrd;
	}

	public static NamedCorpusReaderDescription getSpokenGumInverview() throws Exception {
		CollectionReaderDescription desc = CollectionReaderFactory.createReaderDescription(LineTokenTagReader.class,
				LineTokenTagReader.PARAM_SOURCE_LOCATION, corporaFolderPrefix + "/english/spoken/gum-inverview/",
				LineTokenTagReader.PARAM_LANGUAGE, LANG, LineTokenTagReader.PARAM_PATTERNS, "*.txt",
				LineTokenTagReader.PARAM_SEQUENCES_PER_CAS, seqLimit,
				LineTokenTagReader.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix + "/en-ptb-tt-pos.map");

		NamedCorpusReaderDescription nrd = new NamedCorpusReaderDescription("gum-inverview", DOMAIN_SPOKEN, desc);
		return nrd;
	}

}
