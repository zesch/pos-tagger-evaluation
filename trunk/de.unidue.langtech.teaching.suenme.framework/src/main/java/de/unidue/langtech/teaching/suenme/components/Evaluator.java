package de.unidue.langtech.teaching.suenme.components;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;


public class Evaluator {
    
	/*
	 * used to store all necessary information into one list in the following order
	 * Token, GoldPOS, detectedPOS, nrOfDocuments, correctlyTagged
	 */
    public static List<Object> evaluate(File file) throws IOException {
    	
    	List<Object> posInformation = new ArrayList<Object>();
   
    	List<String> results = FileUtils.readLines(file);
    	List<String> tokens = new ArrayList<String>();
    	List<String> goldPos = new ArrayList<String>();
    	List<String> detectedPos = new ArrayList<String>();
    	
    	int correctlyTagged = 0;
    	
 	   for (int i = 0; i<results.size(); i++) { 
		   String[] parts = results.get(i).split("\t");
		   tokens.add(parts[0]);
		   goldPos.add(parts[1]);
		   detectedPos.add(parts[2]);
		   
		   if (parts[1].equals(parts[2])) {
			   correctlyTagged++;
		     }
	 	  }
 	   
     	int nrOfDocuments = tokens.size();
 	   
 	    posInformation.add(tokens);
 	    posInformation.add(goldPos);
 	    posInformation.add(detectedPos);
 	    posInformation.add(nrOfDocuments);
 	    posInformation.add(correctlyTagged);
 	   
		return posInformation;
    	
    }
}


