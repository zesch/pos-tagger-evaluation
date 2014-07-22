package de.unidue.langtech.teaching.ba.components;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * Used to store all necessary information into one list in the following order
 * Token, GoldPOS, detectedPOS, nrOfDocuments, correctlyTagged
 * 
 * @param file our file with contains the tokens and gold/detected tags
 * @return return the list with all information
 * @throws IOException
 */
public class EvaluatorModels {
	
	   
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
    
}


