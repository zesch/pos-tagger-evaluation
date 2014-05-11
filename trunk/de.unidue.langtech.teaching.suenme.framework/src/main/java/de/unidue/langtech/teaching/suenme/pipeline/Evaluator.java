package de.unidue.langtech.teaching.suenme.pipeline;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import dnl.utils.text.table.TextTable;

public class Evaluator {
	
    private static List<String> openNlpPosAnnos = new ArrayList<String>();
    private static List<String> matePosAnnos = new ArrayList<String>();
    private static List<String> stanfordPosAnnos = new ArrayList<String>();
    private static List<String> treeTaggerPosAnnos = new ArrayList<String>();
	
	public static void main(String[] args) throws IOException {
		
	   List<String> openNlp = FileUtils.readLines(new File("src\\test\\resources\\test\\OpenNlp.txt"));
	   List<String> matePos = FileUtils.readLines(new File("src\\test\\resources\\test\\Mate.txt"));
	   List<String> stanford = FileUtils.readLines(new File("src\\test\\resources\\test\\Stanford.txt"));
	   List<String> treeTagger = FileUtils.readLines(new File("src\\test\\resources\\test\\TreeTagger.txt"));
	   List<String> posTexts = new ArrayList<String>();
	   List<String> goldPosAnnos = new ArrayList<String>();
	   
	    int correctOpenNlp = 0;
	    int correctMatePos = 0;
	    int correctStanford = 0;
	    int correctTreeTagger = 0;
	      
	   //now count for every tagger their correct amount of pos tags      
	    
	   for (int i = 0; i<openNlp.size(); i++) { 
		   String[] parts = openNlp.get(i).split("\t");
		   posTexts.add(parts[0]);
		   goldPosAnnos.add(parts[1]);
		   openNlpPosAnnos.add(parts[2]);
		   
		   if (parts[1].equals(parts[2])) {
			   correctOpenNlp++;
		     }
	 	  }
	   
	   for (int i = 0; i<matePos.size(); i++) {
		   String[] parts = matePos.get(i).split("\t");
		   matePosAnnos.add(parts[2]);
		   
		   if (parts[1].equals(parts[2])) {
			   correctMatePos++;
		     }
	 	  }
	   
	   
	   for (int i = 0; i<stanford.size(); i++) { 
		   String[] parts = stanford.get(i).split("\t");
		   stanfordPosAnnos.add(parts[2]);
		   
		   if (parts[1].equals(parts[2])) {
			   correctStanford++;
		     }
	 	  }
	   
	   
	   for (int i = 0; i<treeTagger.size(); i++) {
		   String[] parts = treeTagger.get(i).split("\t");	  
		   treeTaggerPosAnnos.add(parts[2]);
		   
		   if (parts[1].equals(parts[2])) {
			   correctTreeTagger++;
		     }
	 	  }
	   
	    int nrOfDocuments = posTexts.size();
	   
	   String[] columnNames = {   
		        "Token",		
		        "Gold",                                          
		        "OpenNLP",                                           
		        "MatePos",                                               
		        "Stanford",                                          
		        "TreeTagger"};   
		        
		        String [][] posTags = new String[openNlpPosAnnos.size()][6];
		       
		                for(int row=0;row<openNlpPosAnnos.size();row++){
		                    for (int col=0;col<5;col++){
		                    posTags[row][0] = posTexts.get(row);
		                    posTags[row][1] = goldPosAnnos.get(row);
		                    posTags[row][2] = openNlpPosAnnos.get(row);
		                    posTags[row][3] = matePosAnnos.get(row);
		                    posTags[row][4] = stanfordPosAnnos.get(row);
		                    posTags[row][5] = treeTaggerPosAnnos.get(row);
		                    }
		                }
		                
		                TextTable tt = new TextTable(columnNames, posTags); 
		                tt.setAddRowNumbering(true);
		                tt.printTable(); 
		                
		                System.out.println();
		                System.out.println("OpenNLP scored an accuracy of " + ((double)correctOpenNlp/(double)nrOfDocuments)*100 + "% !");
		                System.out.println("MatePos scored an accuracy of " + ((double)correctMatePos/(double)nrOfDocuments)*100 + "% !");
		                System.out.println("StanfordPos scored an accuracy of " + ((double)correctStanford/(double)nrOfDocuments)*100 + "% !");
		                System.out.println("TreeTagger scored an accuracy of " + ((double)correctTreeTagger/(double)nrOfDocuments)*100 + "% !");
	   
	}

}
