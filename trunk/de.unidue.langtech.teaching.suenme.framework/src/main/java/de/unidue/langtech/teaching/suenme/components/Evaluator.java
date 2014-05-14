package de.unidue.langtech.teaching.suenme.components;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import dnl.utils.text.table.TextTable;

public class Evaluator {
	
    
	/*
	 * used to store all necessary information into one list in the following order
	 * Token, GoldPOS, detectedPOS, nrOfDocuments, correctlyTagged
	 */
    public static List<Object> evaluateSingle(File file) throws IOException {
    	
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
    
    public static void evaluateAll(Class[] tagger ) throws IOException {
    	System.setProperty("PROJECT_HOME", "src\test\resources\test");
    	final String dkproHome = System.getenv("PROJECT_HOME");
    	
    	//important for text table
    	String[] columnNames = new String[tagger.length+2];
    	String [][] posTags = new String[488][tagger.length+2]; //get rid of "488"
    	
    	int[] correctTags = new int [tagger.length];
    	int nrOfDocuments = 0;
    	
    	for (int i=0; i<tagger.length; i++) {
            List<Object> posInformation = evaluateSingle(new File(dkproHome + "\\" + tagger[i].getSimpleName() + ".txt"));
            
            List<String> tokens = (List<String>) posInformation.get(0);
            List<String> goldPos = (List<String>) posInformation.get(1);
            List<String> posAnnos = (List<String>) posInformation.get(2);
            
        	nrOfDocuments = (Integer) posInformation.get(3);
        	correctTags[i] = (Integer) posInformation.get(4);
            
        	//first two columns always have the same name
            columnNames[0] = "Token";
            columnNames[1] = "GoldPos";
            columnNames[i+2] = tagger[i].getSimpleName();  
            
            for(int row=0;row<posAnnos.size();row++){
                for (int col=0;col<tagger.length+1;col++){
                posTags[row][0] = tokens.get(row);
                posTags[row][1] = goldPos.get(row);
                posTags[row][i+2] = posAnnos.get(row);
                }
            }
    	}
    	
    	 TextTable tt = new TextTable(columnNames, posTags); 
         tt.setAddRowNumbering(true);
         tt.printTable(); 
         
         for (int i = 0; i<correctTags.length; i++) {
         	System.out.println(tagger[i].getSimpleName() + " scored an accuracy of " + String.format( "%.2f", ((double)correctTags[i]/(double)nrOfDocuments)*100) + "% !" );
         }
    	
    }
	


}


