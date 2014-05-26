package de.unidue.langtech.tagger;

import de.unidue.langtech.tagger.reader.LinewiseTokenTagReader;

public class GermanReader {
	
	static String CORPORA_FOLDER_PREFIX="";
	
	static ReaderConfiguration getRehbeinReader() {
		return new ReaderConfiguration(LinewiseTokenTagReader.class, "rehbein",
				"de", GermanTaggers.posMappingFile, CORPORA_FOLDER_PREFIX + "/german/rehbein/",new String[] {"*.txt"});
	}

	static ReaderConfiguration getTuebadzReader() {
		return new ReaderConfiguration(LinewiseTokenTagReader.class, "tuebadz",
				"de", GermanTaggers.posMappingFile, CORPORA_FOLDER_PREFIX + "/german/tuebadz/",new String[] {"*.txt"});
	}

}
