package de.unidue.langtech.tagger;

import java.util.ArrayList;
import java.util.List;

import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetPosTagger;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.gate.HepplePosTagger;
import de.tudarmstadt.ukp.dkpro.core.hunpos.HunPosTagger;
import de.tudarmstadt.ukp.dkpro.core.lbj.LbjPosTagger;
import de.tudarmstadt.ukp.dkpro.core.matetools.MatePosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosTagger;

public class EnglishTaggers {

	static String MAPPING_FOLDER_PREFIX="";
	static String posMappingFile = "en-ptb-pos.map";

	static List<TaggerConfiguration> getOpenNlpTagger() {
		List<TaggerConfiguration> taggersEn = new ArrayList<TaggerConfiguration>();
		taggersEn.add(new TaggerConfiguration(OpenNlpPosTagger.class,
				posMappingFile, "en", "maxent"));
		taggersEn.add(new TaggerConfiguration(OpenNlpPosTagger.class,
				posMappingFile, "en", "perceptron"));
		return taggersEn;
	}
	
	static List<TaggerConfiguration> getStandfordTagger() {
		List<TaggerConfiguration> taggersEn = new ArrayList<TaggerConfiguration>();
		taggersEn.add(new TaggerConfiguration(StanfordPosTagger.class,
				posMappingFile, "en", "fast.41"));
		taggersEn.add(new TaggerConfiguration(StanfordPosTagger.class,
				posMappingFile, "en", "twitter"));
		taggersEn.add(new TaggerConfiguration(StanfordPosTagger.class,
				posMappingFile, "en", "twitter-fast"));
		taggersEn.add(new TaggerConfiguration(StanfordPosTagger.class,
				posMappingFile, "en", "caseless-left3words-distsim"));
		taggersEn.add(new TaggerConfiguration(StanfordPosTagger.class,
				posMappingFile, "en", "bidirectional-distsim"));
		taggersEn.add(new TaggerConfiguration(StanfordPosTagger.class,
				posMappingFile, "en", "wsj-0-18-caseless-left3words-distsim"));
		return taggersEn;
	}
	
	static List<TaggerConfiguration> getMateTagger() {
		List<TaggerConfiguration> taggersEn = new ArrayList<TaggerConfiguration>();
		taggersEn.add(new TaggerConfiguration(MatePosTagger.class,
				posMappingFile, "en", "conll2009"));
		return taggersEn;
	}

	static List<TaggerConfiguration> getClearNlpTagger() {
		List<TaggerConfiguration> taggersEn = new ArrayList<TaggerConfiguration>();
		taggersEn.add(new TaggerConfiguration(ClearNlpPosTagger.class,
				posMappingFile, "en", "mayo"));
		taggersEn.add(new TaggerConfiguration(ClearNlpPosTagger.class,
				posMappingFile, "en", "ontonotes"));
		return taggersEn;
	}
	
	static List<TaggerConfiguration> getHepplePosTagger() {
		List<TaggerConfiguration> taggersEn = new ArrayList<TaggerConfiguration>();
		taggersEn.add(new TaggerConfiguration(HepplePosTagger.class,
				posMappingFile, "en", null));
		return taggersEn;
	}
	
	static List<TaggerConfiguration> getHunPosTagger() {
		List<TaggerConfiguration> taggersEn = new ArrayList<TaggerConfiguration>();
		taggersEn.add(new TaggerConfiguration(HunPosTagger.class,
				posMappingFile, "en", "wsj"));
		return taggersEn;
	}
	
	static List<TaggerConfiguration> getLbjPosTagger() {
	    String specificMapping= MAPPING_FOLDER_PREFIX +"/en-lbj-pos.map";
		List<TaggerConfiguration> taggersEn = new ArrayList<TaggerConfiguration>();
		taggersEn.add(new TaggerConfiguration(LbjPosTagger.class,
		        specificMapping, "en", null));
		return taggersEn;
	}
	
	static List<TaggerConfiguration> ArktweetPosTagger() {
	    String specificMapping= MAPPING_FOLDER_PREFIX +"/en-arktweet-pos.map";
		List<TaggerConfiguration> taggersEn = new ArrayList<TaggerConfiguration>();
		taggersEn.add(new TaggerConfiguration(ArktweetPosTagger.class,
		        specificMapping, "en", "default"));
		taggersEn.add(new TaggerConfiguration(ArktweetPosTagger.class,
		        EnglishTaggers.posMappingFile, "en", "irc"));
		taggersEn.add(new TaggerConfiguration(ArktweetPosTagger.class,
				EnglishTaggers.posMappingFile, "en", "ritter"));
		return taggersEn;
	}
	
	static List<TaggerConfiguration> getTreeTagger() {
	    String specificMapping= MAPPING_FOLDER_PREFIX +"/en-ptb-tt-pos.map";
		List<TaggerConfiguration> taggersEn = new ArrayList<TaggerConfiguration>();
		taggersEn.add(new TaggerConfiguration(TreeTaggerPosTagger.class,
		        specificMapping, "en", "le"));
		return taggersEn;
	}
}
