package de.unidue.langtech.teaching.ba.results;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.unidue.langtech.teaching.ba.pipeline.Pipeline.CorpusConfiguration;

/**
 * Result store interface, which defines all methods for the result store.
 * @author Onur
 *
 */
public interface IResultStore {
	
    /**
     * Saves the results for a tagger.
     * At first unpacks information from posFile 
     * Then stores all data in two tables for fine & coarse-grained POS
     * Finally writes accuracies to the bottom of the tables and to an extra result file 
     * which includes tagger accuracies for each corpus
     * @param posFile
     * @param corpusName
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	public void saveResults(List<File> posFile, String corpusName) throws IOException;
    
    
    /**
     * combines all result files to one text file which contains the accuracy for every tagger and corpora
     * @param corpora
     * @throws IOException
     */
    public void combineResults(CorpusConfiguration[] corpusConfigurations) throws IOException;
    
    
    /**
     * deletes text files written by Writer class
     * with tag information because they only should exist temporary
     * @param tagger
     */
	public void deleteAllTagger(List<File> posFile) throws IOException;
		

	/**
	 * deletes all result files with corpora information because they only should exist temporary
	 * @param corpora
	 */
	public void deleteAllCorpora(CorpusConfiguration[] corpusConfigurations) throws IOException;


}
