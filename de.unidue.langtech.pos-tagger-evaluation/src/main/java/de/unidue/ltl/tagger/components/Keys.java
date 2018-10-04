package de.unidue.ltl.tagger.components;

public interface Keys {
    public static final String FILE_TIMER = "time.txt";
    public static final String FILE_ACC_FINE = "accFine.txt";
    public static final String FILE_ACC_COARSE = "accCoarse.txt";
    public static final String FILE_CORPUS = "corpus.txt";
    public static final String FILE_TAGGER = "tagger.txt";
    public static final String FILE_TOKEN_COUNT="tokenCount.txt";
    public static final String FILE_NORMALIZED_TOKEN_TIME="normalizedTokenTime.txt";
    
    public static final String PROP_ACC_FINE = "accFine";
    public static final String PROP_ACC_COARSE = "accCoarse";
    public static final String PROP_CORPUS_NAME = "corpusName";
    public static final String PROP_CORPUS_DOMAIN = "corpusDomain";
    public static final String PROP_TAGGER_NAME = "taggerName";
    public static final String PROP_TAGGER_MODEL_NAME = "taggerModelName";
    public static final String PROP_TOKEN_COUNT = "tokenCount";
    public static final String PROP_TOKEN_COUNT_1K = "tokenCount1k";
    public static final String PROP_NORMALIZED_MIL_TOK_PER_SEC="millionTokenPerSecond";
    
    public static final String DOMAIN_SOCIAL = "social";
    public static final String DOMAIN_FORMAL = "formal";
    public static final String DOMAIN_SPOKEN = "spoken";
    
    public static final String DISCRIMINATOR_READER="reader";
    public static final String DISCRIMINATOR_TAGGER="tagger";
}
