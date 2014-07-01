package de.unidue.langtech.teaching.ba.results;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.uima.collection.CollectionReaderDescription;


public interface IResultStore {
	
    /**
     * saves results for an array of tagger and corpora
     * first unpacks the file which contain all important information
     * prints token/goldPOS/detectedPOS to two tables for universal and normal POS
     * also creates a text file which contains the accuracy for every tagger
     * @param posFile File which contains token/goldPOS/detectedPOS
     * @param corpus 
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	public void saveResults(List<File> posFile, CollectionReaderDescription corpus) throws IOException;
    
    
    /**
     * combines all result files to one text file which contains the accuracy for every tagger and corpora
     * @param corpora
     * @throws IOException
     */
    public void combineResults(CollectionReaderDescription[] corpora) throws IOException;
    
    
    /**
     * deletes text files with tag information because they only should exist temporary
     * @param tagger
     */
	public void deleteAllTagger(List<File> posFile) throws IOException;
		

	/**
	 * deletes text files with corpora information because they only should exist temporary
	 * @param corpora
	 */
	public void deleteAllCorpora(CollectionReaderDescription[] corpora) throws IOException;


}
