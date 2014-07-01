package de.unidue.langtech.teaching.ba.results;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.collection.CollectionReaderDescription;

import de.unidue.langtech.teaching.ba.components.EvaluatorModels;
import dnl.utils.text.table.TextTable;

public class ResultStore implements IResultStore {
	
	public static final String dkproHome = System.getenv("PROJECT_HOME");

	@Override
	public void saveResults(List<File> posFile,
			CollectionReaderDescription corpus) throws IOException {
		
		//only used once to determine row length of "posTags"
   	 List<Object> posInformationSingle = EvaluatorModels.evaluateSingle(posFile.get(0));
   	 int rowLength = (Integer) posInformationSingle.get(5);
   	
   	//important for Coarse POS tags
   	String[] columnNamesCPos = new String[posFile.size()+3];
   	String [][] cPosTags = new String[rowLength][posFile.size()+3]; 
   	
   	//important for fine POS tags
   	String[] columnNamesPos = new String[posFile.size()+3];
   	String [][] posTags = new String[rowLength][posFile.size()+3]; 
   	
   	int[] correctCposTags = new int [posFile.size()];
   	int[] correctPosTags = new int [posFile.size()];
   	int nrOfDocuments = 0;

   	for (int i = 0; i<posFile.size(); i++) {
		
   	    //unpack file with all necessary information
   	    List<Object> posInformation = EvaluatorModels.evaluateSingle(posFile.get(i));

           
           List<String> tokens = (List<String>) posInformation.get(0);
           List<String> goldCPos = (List<String>) posInformation.get(1);
           List<String> cPosAnnos = (List<String>) posInformation.get(2);
           List<String> goldPos = (List<String>) posInformation.get(3);
           List<String> posAnnos = (List<String>) posInformation.get(4);

       	nrOfDocuments = (Integer) posInformation.get(5);
       	correctCposTags[i] = (Integer) posInformation.get(6);
       	correctPosTags[i] = (Integer) posInformation.get(7);
       	
       	String taggerName = posFile.get(i).getName().replaceAll(".txt", "");
 
       	//first three columns always have the same name
           columnNamesCPos[0] = "Token";
           columnNamesCPos[1] = "False?";
           columnNamesCPos[2] = "GoldCPOS";
           columnNamesCPos[i+3] = taggerName;
           
           columnNamesPos[0] = "Token";
           columnNamesPos[1] = "False?";
           columnNamesPos[2] = "GoldPOS";
           columnNamesPos[i+3] = taggerName;  
           
           for(int cPosRow=0;cPosRow<cPosAnnos.size();cPosRow++){
               for (int cPosCol=0;cPosCol<posFile.size()+1;cPosCol++){
               cPosTags[cPosRow][0] = tokens.get(cPosRow);
               cPosTags[cPosRow][2] = goldCPos.get(cPosRow);
               cPosTags[cPosRow][i+3] = cPosAnnos.get(cPosRow);
               }
           }
                       
           for(int posRow=0;posRow<posAnnos.size();posRow++){
               for (int posCol=0;posCol<posFile.size()+1;posCol++){
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
   	        if (!posTags[i][2].equals(posTags[i][j]) && posTags[i][j] != null) {
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
   	        if (!cPosTags[i][2].equals(cPosTags[i][j]) && cPosTags[i][j] != null) {
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
           File posFile1 = new File(dkproHome + "\\" + corpus.getImplementationName() + "-result-fine.txt");
           
           cPosPrintStream = new PrintStream(cPosFile);
           posPrintStream = new PrintStream(posFile1);
           
           //create tables for universal and normal POS and write them to a text file
      	    TextTable cPosTable = new TextTable(columnNamesCPos, cPosTags); 
           cPosTable.setAddRowNumbering(true);
           cPosTable.printTable(cPosPrintStream, 0);
           
      	    TextTable posTable = new TextTable(columnNamesPos, posTags); 
      	    posTable.setAddRowNumbering(true);
        	posTable.printTable(posPrintStream, 0);
        	
        	//write corpus name at the top of result file
        	File resultFile = new File(dkproHome + "\\" + corpus.getImplementationName() + "-results.txt");
        	FileUtils.writeStringToFile(resultFile, "\n" + "\n" + corpus.getImplementationName() + "\n" + "\n", true);
           
           	for (int i=0; i<posFile.size(); i++) {	
           		

           		
           		String taggerName = posFile.get(i).getName().replaceAll(".txt", "");
           		
               	String correctCPos = String.format( "%.2f", ((double)correctCposTags[i]/(double)nrOfDocuments)*100);
               	String correctPos = String.format( "%.2f", ((double)correctPosTags[i]/(double)nrOfDocuments)*100);
               	

           	//append score results at bottom of tables file
            	cPosPrintStream.append(taggerName + " scored an accuracy of " + correctCPos + "% !" + "\n" );
            	posPrintStream.append(taggerName + " scored an accuracy of " + correctPos + "% !" + "\n" );
            	
            	//write score to result file
            	FileUtils.writeStringToFile(resultFile, taggerName + "\t" 
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

	@Override
	public void combineResults(CollectionReaderDescription[] corpora)
			throws IOException {
		
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
	
	@Override
	public void deleteAllTagger(List<File> posFile) throws IOException {
		
		for (int i=0; i<posFile.size(); i++) {
    		posFile.get(i).delete();
    		}
		
	}

	@Override
	public void deleteAllCorpora(CollectionReaderDescription[] corpora)
			throws IOException {
		
		for (int i=0; i<corpora.length; i++) {
    		File file = new File(dkproHome + "\\" + corpora[i].getImplementationName() + "-results.txt");
    		file.delete();
    	}
		
	}



}
