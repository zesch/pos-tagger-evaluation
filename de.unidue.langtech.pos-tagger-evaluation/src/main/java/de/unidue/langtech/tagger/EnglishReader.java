package de.unidue.langtech.tagger;

import de.unidue.langtech.tagger.reader.LinewiseTokenTagSingleCasReader;

public class EnglishReader {

	static String CORPORA_FOLDER_PREFIX = "";
	static String MAPPING_FOLDER_PREFIX = "";
	static String posMappingDefault = "";

	static ReaderConfiguration getSwitchboardReader() {
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class,
				"spoken-switchboard", "en", posMappingDefault,
				CORPORA_FOLDER_PREFIX + "/english/spoken/switchboard/",
				new String[] { "*.txt" });
	}

	static ReaderConfiguration getMASCSpokenTelephone() {
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class,
				"spoken-masc-telephone", "en", posMappingDefault,
				CORPORA_FOLDER_PREFIX
						+ "/english/spoken/masc-spoken-telephone/",
				new String[] { "*.txt" });
	}

	static ReaderConfiguration getMASCSpokenDebate() {
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class,
				"spoken-masc-debate", "en", posMappingDefault,
				CORPORA_FOLDER_PREFIX + "/english/spoken/masc-spoken-debate/",
				new String[] { "*.txt" });
	}

	static ReaderConfiguration getMASCSpokenCourt() {
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class,
				"spoken-masc-court", "en", posMappingDefault,
				CORPORA_FOLDER_PREFIX + "/english/spoken/masc-spoken-court/",
				new String[] { "*.txt" });
	}

	static ReaderConfiguration getMASCSpokenFace2Face() {
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class,
				"spoken-masc-face2face", "en", posMappingDefault,
				CORPORA_FOLDER_PREFIX
						+ "/english/spoken/masc-spoken-face2face/",
				new String[] { "*.txt" });
	}

	static ReaderConfiguration getBNCSpokenConversations100kToken() {
		String specificMapping = MAPPING_FOLDER_PREFIX + "/en-c5-pos.map";
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class,
				"spoken-bnc-conversations-100k-token", "en", specificMapping,
				CORPORA_FOLDER_PREFIX + "/english/spoken/bnc-conversations/",
				new String[] { "*.txt" });
	}

	// ///// SOCIAL NEW //////

	static ReaderConfiguration getMASCSocialTwitter() {
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class,
				"social-masc-twitter", "en", posMappingDefault,
				CORPORA_FOLDER_PREFIX + "/english/social/masc-social-twitter/",
				new String[] { "*.txt" });
	}

	static ReaderConfiguration getMASCSocialEmail() {
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class,
				"social-masc-email", "en", posMappingDefault,
				CORPORA_FOLDER_PREFIX + "/english/social/masc-social-email/",
				new String[] { "*.txt" });
	}

	static ReaderConfiguration getMASCSocialBlog() {
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class, "social-masc-blog",
				"en", posMappingDefault, CORPORA_FOLDER_PREFIX
						+ "/english/social/masc-social-blog/",
				new String[] { "*.txt" });
	}

	public static ReaderConfiguration getGimpel() {
		String specificMapping = MAPPING_FOLDER_PREFIX + "/en-arktweet-pos.map";
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class, "social-gimpel",
				"en", specificMapping, CORPORA_FOLDER_PREFIX
						+ "/english/social/gimpel/", new String[] { "*.txt" });
	}

	// /////// FORMAL NEW //////

	static ReaderConfiguration getBNCFormalNewspaper100kToken() {
		String specificMapping = MAPPING_FOLDER_PREFIX + "/en-c5-pos.map";
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class,
				"formal-bnc-news-100k-token", "en", specificMapping,
				CORPORA_FOLDER_PREFIX + "/english/formal/bnc-written-news/",
				new String[] { "*.txt" });
	}

	static ReaderConfiguration getBrownReader() {
		String specificMapping = MAPPING_FOLDER_PREFIX + "/en-browntei-pos.map";
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class, "formal-brown",
				"en", specificMapping, CORPORA_FOLDER_PREFIX
						+ "/english/formal/brown/", new String[] { "*.txt" });
	}

	static ReaderConfiguration getMASCFormalNonFiction() {
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class,
				"formal-masc-nonfiction", "en", posMappingDefault,
				CORPORA_FOLDER_PREFIX
						+ "/english/formal/masc-written-non-fiction/",
				new String[] { "*.txt" });
	}

	static ReaderConfiguration getMASCFormalFiction() {
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class,
				"formal-masc-fiction",
				"en",
				posMappingDefault,
				CORPORA_FOLDER_PREFIX + "/english/formal/masc-written-fiction/",
				new String[] { "*.txt" });
	}

	static ReaderConfiguration getMASCFormalEssay() {
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class,
				"formal-masc-essay", "en", posMappingDefault,
				CORPORA_FOLDER_PREFIX + "/english/formal/masc-written-essay/",
				new String[] { "*.txt" });
	}

	static ReaderConfiguration getMASCFormalGovDocs() {
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class,
				"formal-masc-gov-docs", "en", posMappingDefault,
				CORPORA_FOLDER_PREFIX
						+ "/english/formal/masc-written-govt-docs/",
				new String[] { "*.txt" });
	}

	static ReaderConfiguration getMASCFormalTechnical() {
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class,
				"formal-masc-technical", "en", posMappingDefault,
				CORPORA_FOLDER_PREFIX
						+ "/english/formal/masc-written-technical/",
				new String[] { "*.txt" });
	}

	static ReaderConfiguration getMASCFormalTravelGuide() {
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class,
				"formal-masc-travel-guide", "en", posMappingDefault,
				CORPORA_FOLDER_PREFIX
						+ "/english/formal/masc-written-travel-guides",
				new String[] { "*.txt" });
	}

	static ReaderConfiguration getMASCFormalJournal() {
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class,
				"formal-masc-journal", "en", posMappingDefault,
				CORPORA_FOLDER_PREFIX + "/english/formal/masc-written-journal",
				new String[] { "*.txt" });
	}

	static ReaderConfiguration getMASCFormalNewspaper() {
		return new ReaderConfiguration(
				LinewiseTokenTagSingleCasReader.class,
				"formal-masc-newspaper", "en", posMappingDefault,
				CORPORA_FOLDER_PREFIX
						+ "/english/formal/masc-written-newspaper",
				new String[] { "*.txt" });
	}

}
