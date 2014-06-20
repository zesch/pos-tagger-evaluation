package de.unidue.langtech.teaching.ba.components;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;

import dnl.utils.text.table.TextTable;

public class Evaluator {
	
	public static final String dkproHome = System.getenv("PROJECT_HOME");
	
    
    /**
     * used to store all necessary information into one list in the following order
	 * Token, GoldPOS, detectedPOS, nrOfDocuments, correctlyTagged
	 * 
     * @param file our file with contains the tokens and gold/detected tags
     * @return return the list with all information
     * @throws IOException
     */
    public static List<Object> evaluateSingle(File file) throws IOException {
    	
    	List<Object> posInformation = new ArrayList<Object>();
   
    	List<String> results = FileUtils.readLines(file);
    	List<String> tokens = new ArrayList<String>();
    	List<String> goldCPos = new ArrayList<String>();
    	List<String> goldPos = new ArrayList<String>();
    	List<String> detectedCPos = new ArrayList<String>();
    	List<String> detectedPos = new ArrayList<String>();
    	
    	int correctlyTaggedCPos = 0;
    	int correctlyTaggedPos = 0;
    	
 	   for (int i = 0; i<results.size(); i++) { 
 		   
		   String[] parts = results.get(i).split("\t");
		   tokens.add(parts[0]);
		   goldCPos.add(parts[1]);
		   detectedCPos.add(parts[2]);
		   goldPos.add(parts[3]);
		   detectedPos.add(parts[4]);
		   

		   if (parts[1].equals(parts[2])) {
			   correctlyTaggedCPos++;
		   }

		   if (parts[3].equals(parts[4])) {
			   correctlyTaggedPos++;
		   }			   
	 	  }
 	   
     	int nrOfDocuments = tokens.size();
 	   
 	    posInformation.add(tokens);
 	    posInformation.add(goldCPos);
 	    posInformation.add(detectedCPos);
 	    posInformation.add(goldPos);
 	    posInformation.add(detectedPos);
 	    posInformation.add(nrOfDocuments);
 	    posInformation.add(correctlyTaggedCPos);
 	    posInformation.add(correctlyTaggedPos);

		return posInformation;
    	
    }
    
    /**
     * saves results for an array of tagger and corpora
     * first unpacks the file which contain all important information
     * prints token/goldPOS/detectedPOS to two tables for universal and normal POS
     * also creates a text file which contains the accuracy for every tagger
     * @param tagger 
     * @param corpus 
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	public static void saveResults(AnalysisEngineDescription[] tagger, CollectionReaderDescription corpus) throws IOException {
    	
    	//only used once to determine row length of "posTags"
    	 List<Object> posInformationSingle = evaluateSingle(new File(dkproHome + "\\" + tagger[0].getImplementationName() + ".txt"));
    	 int rowLength = (Integer) posInformationSingle.get(5);
    	
    	//important for Coarse POS tags
    	String[] columnNamesCPos = new String[tagger.length+3];
    	String [][] cPosTags = new String[rowLength][tagger.length+3]; 
    	
    	//important for fine POS tags
    	String[] columnNamesPos = new String[tagger.length+3];
    	String [][] posTags = new String[rowLength][tagger.length+3]; 
    	
    	int[] correctCposTags = new int [tagger.length];
    	int[] correctPosTags = new int [tagger.length];
    	int nrOfDocuments = 0;
    	
    	for (int i=0; i<tagger.length; i++) {
    		
    		//unpack file with all necessary information
            List<Object> posInformation = evaluateSingle(new File(dkproHome + "\\" + tagger[i].getImplementationName() + ".txt"));
            
            List<String> tokens = (List<String>) posInformation.get(0);
            List<String> goldCPos = (List<String>) posInformation.get(1);
            List<String> cPosAnnos = (List<String>) posInformation.get(2);
            List<String> goldPos = (List<String>) posInformation.get(3);
            List<String> posAnnos = (List<String>) posInformation.get(4);

        	nrOfDocuments = (Integer) posInformation.get(5);
        	correctCposTags[i] = (Integer) posInformation.get(6);
        	correctPosTags[i] = (Integer) posInformation.get(7);
            
        	//first three columns always have the same name
            columnNamesCPos[0] = "Token";
            columnNamesCPos[1] = "False?";
            columnNamesCPos[2] = "GoldCPOS?";
            columnNamesCPos[i+3] = tagger[i].getImplementationName();  
            
            columnNamesPos[0] = "Token";
            columnNamesPos[1] = "False?";
            columnNamesPos[2] = "GoldPOS";
            columnNamesPos[i+3] = tagger[i].getImplementationName();  
            
            for(int cPosRow=0;cPosRow<cPosAnnos.size();cPosRow++){
                for (int cPosCol=0;cPosCol<tagger.length+1;cPosCol++){
                cPosTags[cPosRow][0] = tokens.get(cPosRow);
                cPosTags[cPosRow][2] = goldCPos.get(cPosRow);
                cPosTags[cPosRow][i+3] = cPosAnnos.get(cPosRow);
                }
            }
                        
            for(int posRow=0;posRow<posAnnos.size();posRow++){
                for (int posCol=0;posCol<tagger.length+1;posCol++){
                posTags[posRow][0] = tokens.get(posRow);
                posTags[posRow][2] = goldPos.get(posRow);
                posTags[posRow][i+3] = posAnnos.get(posRow);
                }
            }
                                  
    	}
    	
    	//mark a cross in column 2 for every false tagged POS
    	for (int i=0;i<posTags.length;i++) {
    	    boolean equal = true;
    	    for (int j=3;j<posTags[i].length;j++) {
    	        if (!posTags[i][2].equals(posTags[i][j])) {
    	            equal = false;
    	            break;
    	        }
    	    }
    	    
    	    if (!equal) {
    	        posTags[i][1] = "x";
    	    }
    	}
    	
    	for (int i=0;i<cPosTags.length;i++) {
    	    boolean equal = true;
    	    for (int j=3;j<cPosTags[i].length;j++) {
    	        if (!cPosTags[i][2].equals(cPosTags[i][j])) {
    	            equal = false;
    	            break;
    	        }
    	    }
    	    
    	    if (!equal) {
    	    	cPosTags[i][1] = "x";
    	    }
    	}
    	
    	PrintStream cPosPrintStream = null;
    	PrintStream posPrintStream = null;
        try {
        	
            File cPosFile = new File(dkproHome + "\\" + corpus.getImplementationName() + "-result-coarse.txt");
            File posFile = new File(dkproHome + "\\" + corpus.getImplementationName() + "-result-fine.txt");
            
            cPosPrintStream = new PrintStream(cPosFile);
            posPrintStream = new PrintStream(posFile);
            
            //create tables for universal and normal POS and write them to a text file
       	    TextTable cPosTable = new TextTable(columnNamesCPos, cPosTags); 
            cPosTable.setAddRowNumbering(true);
            cPosTable.printTable(cPosPrintStream, 0);
            
       	    TextTable posTable = new TextTable(columnNamesPos, posTags); 
       	    posTable.setAddRowNumbering(true);
         	posTable.printTable(posPrintStream, 0);
         	
         	//write corpus name at the top of result file
         	File resultFile = new File(dkproHome + "\\" + corpus.getImplementationName() + "-results.txt");
         	FileUtils.writeStringToFile(resultFile, corpus.getImplementationName() + "\n" + "\n", true);
            
            for (int i = 0; i<correctCposTags.length; i++) {
            	
            	String correctCPos = String.format( "%.2f", ((double)correctCposTags[i]/(double)nrOfDocuments)*100);
            	String correctPos = String.format( "%.2f", ((double)correctPosTags[i]/(double)nrOfDocuments)*100);
            	
            	//append score results at bottom of tables file
             	cPosPrintStream.append(tagger[i].getImplementationName() + " scored an accuracy of " + correctCPos + "% !" + "\n" );
             	posPrintStream.append(tagger[i].getImplementationName() + " scored an accuracy of " + correctPos + "% !" + "\n" );
             	
             	//write score to result file
             	FileUtils.writeStringToFile(resultFile, tagger[i].getImplementationName() + "\t" 
             	+ correctCPos + "\t" 
             	+ correctPos + "\n", true);
             }
            
            //leave a space after every corpus
            FileUtils.writeStringToFile(resultFile, "-----------------------------------------------------------------" 
            + "\n", true);
        }
        catch (Exception e) {
            throw new IOException(e);
        } finally {
            cPosPrintStream.close();
            posPrintStream.close();
        }
    }
    
    /**
     * combines all result files to one text file which contains the accuracy for every tagger and corpora
     * @param corpora
     * @throws IOException
     */
    public static void combineResults(CollectionReaderDescription[] corpora) throws IOException {    	
    	File[] files = new File[corpora.length];
    	
    	for (int i = 0; i<corpora.length; i++) {
    		files[i] = new File(dkproHome + "\\" + corpora[i].getImplementationName() + "-results.txt");
    	}
    	
    	File resultFile = new File(dkproHome + "\\" + "OverAllResults.txt");
    	FileUtils.write(resultFile, "FORMAT:" + "\n" + "<CORPUS_NAME>" 
    	+ "\n" + "<TAGGER_NAME><TAB><CPOS_ACC><TAB><POS_ACC>" + "\n\n");
    	
    	for (int i = 0; i<files.length; i++) {
    		String fileStr = FileUtils.readFileToString(files[i]);
    		FileUtils.write(resultFile, fileStr, true); // true for append
    	}
    }
    
    
    /**
     * deletes text files with tag information because they only should exist temporary
     * @param tagger
     */
	public static void deleteAllTagger(AnalysisEngineDescription[] tagger) {
    	
    	for (int i=0; i<tagger.length; i++) {
    		File file = new File(dkproHome + "\\" + tagger[i].getImplementationName() + ".txt");
    		file.delete();
    	}
		
	}
	
	/**
	 * deletes text files with corpora information because they only should exist temporary
	 * @param corpora
	 */
	public static void deleteAllCorpora(CollectionReaderDescription[] corpora) {
    	
    	for (int i=0; i<corpora.length; i++) {
    		File file = new File(dkproHome + "\\" + corpora[i].getImplementationName() + "-results.txt");
    		file.delete();
    	}
		
	}
	

}


