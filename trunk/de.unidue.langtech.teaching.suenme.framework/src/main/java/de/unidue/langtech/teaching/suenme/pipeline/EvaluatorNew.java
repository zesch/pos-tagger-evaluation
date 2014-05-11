package de.unidue.langtech.teaching.suenme.pipeline;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class EvaluatorNew {
	
    private int correctOpenNlp;
    private int correctMatePos;
    private int correctStanford;
    private int correctTreeTagger;
    private int nrOfDocuments;
    private List<String> openNlpPosAnnos = new ArrayList<String>();
    private List<String> matePosAnnos = new ArrayList<String>();
    private List<String> stanfordPosAnnos = new ArrayList<String>();
    private List<String> treeTaggerPosAnnos = new ArrayList<String>();
	private List<String> goldPosAnnos = new ArrayList<String>();
	private List<String> posTexts = new ArrayList<String>();

	public static void main(String[] args) throws IOException {
		
	   List<String> openNlpPosAnnos = FileUtils.readLines(new File("src\\test\\resources\\test\\OpenNlp.txt"));
	   
	   for (int i = 0; i<openNlpPosAnnos.size(); i++) {
		   String[] parts = openNlpPosAnnos.get(i).split("\t");
		   System.out.println("Token: " + parts[0]);
		   if (parts[1].equals(parts[2])) {
			   System.out.println("Correctly tagged!");
		   } else {
			   System.out.println("Wrongly tagged!");
		   }
	   }

	}

}
