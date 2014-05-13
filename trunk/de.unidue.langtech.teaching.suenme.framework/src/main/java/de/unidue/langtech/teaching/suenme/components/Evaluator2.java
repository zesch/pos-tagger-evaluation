package de.unidue.langtech.teaching.suenme.components;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import dnl.utils.text.table.TextTable;

public class Evaluator2 {
    
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
	
	public static void main(String[] args) throws IOException {
		
       //PrintStream out = new PrintStream(new FileOutputStream("src\\test\\resources\\test\\Results.txt"));
       //System.setOut(out);
		
	   List<Object> openNlp = evaluate(new File("src\\test\\resources\\test\\OpenNlp.txt"));
	   List<Object> mate = evaluate(new File("src\\test\\resources\\test\\Mate.txt"));
	   List<Object> stanford = evaluate(new File("src\\test\\resources\\test\\Stanford.txt"));
	   List<Object> treeTagger = evaluate(new File("src\\test\\resources\\test\\TreeTagger.txt"));
	   List<Object> clearNlp = evaluate(new File("src\\test\\resources\\test\\ClearNlp.txt"));
	   
	   List<String> tokens = (List<String>) openNlp.get(0);
	   List<String> goldPos = (List<String>) openNlp.get(1);
	   
	   List<String> openNlpPosAnnos = (List<String>) openNlp.get(2);
	   List<String> matePosAnnos = (List<String>) mate.get(2);
	   List<String> stanfordPosAnnos = (List<String>) stanford.get(2);
	   List<String> treeTaggerPosAnnos = (List<String>) treeTagger.get(2);
	   List<String> clearNlpPosAnnos = (List<String>) clearNlp.get(2);
	   
	   int nrOfDocuments = (Integer) openNlp.get(3);
	   int correctOpenNlp = (Integer) openNlp.get(4);
	   int correctMatePos = (Integer) mate.get(4);
	   int correctStanford = (Integer) stanford.get(4);
	   int correctTreeTagger = (Integer) treeTagger.get(4);
	   int correctClearNlp = (Integer) clearNlp.get(4);
	   
	   String[] columnNames = {   
		        "Token",		
		        "Gold",                                          
		        "OpenNLP",                                           
		        "MatePos",                                               
		        "Stanford",                                          
		        "TreeTagger",
		        "ClearNLP"};   
		        
		        String [][] posTags = new String[openNlpPosAnnos.size()][7];
		       
		                for(int row=0;row<openNlpPosAnnos.size();row++){
		                    for (int col=0;col<6;col++){
		                    posTags[row][0] = tokens.get(row);
		                    posTags[row][1] = goldPos.get(row);
		                    posTags[row][2] = openNlpPosAnnos.get(row);
		                    posTags[row][3] = matePosAnnos.get(row);
		                    posTags[row][4] = stanfordPosAnnos.get(row);
		                    posTags[row][5] = treeTaggerPosAnnos.get(row);
		                    posTags[row][6] = clearNlpPosAnnos.get(row);
		                    }
		                }
		                
		                TextTable tt = new TextTable(columnNames, posTags); 
		                tt.setAddRowNumbering(true);
		                tt.printTable(); 
		                
		                System.out.println();
		                System.out.println("OpenNLP scored an accuracy of " + String.format( "%.2f", ((double)correctOpenNlp/(double)nrOfDocuments)*100) + "% !");
		                System.out.println("MatePos scored an accuracy of " + String.format( "%.2f", ((double)correctMatePos/(double)nrOfDocuments)*100) + "% !");
		                System.out.println("StanfordPos scored an accuracy of " + String.format( "%.2f", ((double)correctStanford/(double)nrOfDocuments)*100) + "% !");
		                System.out.println("TreeTagger scored an accuracy of " + String.format( "%.2f", ((double)correctTreeTagger/(double)nrOfDocuments)*100) + "% !");
		                System.out.println("ClearNLP scored an accuracy of " + String.format( "%.2f", ((double)correctClearNlp/(double)nrOfDocuments)*100) + "% !");
		                

	   
	}

}


