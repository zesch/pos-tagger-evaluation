package de.unidue.langtech.teaching.suenme.pipeline;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.suenme.type.GoldPOS;
import dnl.utils.text.table.TextTable;


/**
 * 
 * @author suenme
 *
 */
public class EvaluatorAll
    extends JCasAnnotator_ImplBase
{

    private int correctOpenNlp;
    private int correctMatePos;
    private int correctStanford;
    private int correctTreeTagger;
    private int nrOfDocuments;
    private List<String> detectedPosAnnos = new ArrayList<String>();
    private List<String> openNlpPosAnnos = new ArrayList<String>();
    private List<String> matePosAnnos = new ArrayList<String>();
    private List<String> stanfordPosAnnos = new ArrayList<String>();
    private List<String> treeTaggerPosAnnos = new ArrayList<String>();
	private List<String> goldPosAnnos = new ArrayList<String>();
	private List<String> posTexts = new ArrayList<String>();
	private List<String> posTokens = new ArrayList<String>();
    
    /* 
     * This is called BEFORE any documents are processed.
     */
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        correctOpenNlp = 0;
        correctMatePos = 0;
        correctStanford = 0;
        correctTreeTagger= 0;
        nrOfDocuments = 0;
    }
    
    
    /* 
     * This is called ONCE for each document
     */
    @Override
    public void process(JCas jcas)
        throws AnalysisEngineProcessException
    {
        //add all goldPos to the List
        for (GoldPOS goldPos : JCasUtil.select(jcas, GoldPOS.class)) {		
			goldPosAnnos.add(goldPos.getPosTag().getPosValue());
		}
  
 //add all postags of every tagger into a list
  for (Token tokenAnno : JCasUtil.select(jcas, Token.class)) {
	  for (POS pos : JCasUtil.selectCovered(POS.class, tokenAnno)) {
			detectedPosAnnos.add(pos.getPosValue());
			posTexts.add(tokenAnno.getCoveredText());
	  }
  }
  
  //posTexts has every token copied four times consecutively, get rid of it
  for (int i = 0; i<posTexts.size(); i+=4) {
	  posTokens.add(posTexts.get(i));
  }
   
  //assign pos tags to their pos taggers, TODO: pos tags sometime are out of order!!
  for (int i = 0; i<detectedPosAnnos.size(); i+=4) {
	  openNlpPosAnnos.add(detectedPosAnnos.get(i));
	  matePosAnnos.add(detectedPosAnnos.get(i+1));
	  stanfordPosAnnos.add(detectedPosAnnos.get(i+2));
	  treeTaggerPosAnnos.add(detectedPosAnnos.get(i+3));  
  }
    
 
      nrOfDocuments = posTokens.size();
      
  //now count for every tagger their correct amount of pos tags      

  for (int i = 0; i<posTokens.size(); i++) {
	  String gold = goldPosAnnos.get(i);
	  String detectedOpenNlp = openNlpPosAnnos.get(i);
			  
		  if (gold.equals(detectedOpenNlp)) {
			  correctOpenNlp++;
		  } 
		  	  
	  }
  
  for (int i = 0; i<posTokens.size(); i++) {
	  String gold = goldPosAnnos.get(i);
	  String detectedMatePos =  matePosAnnos.get(i);
	  
		  if (gold.equals(detectedMatePos)) {
			  correctMatePos++;
		  } 	  	  
	  }
  
  
  for (int i = 0; i<posTokens.size(); i++) {
	  String gold = goldPosAnnos.get(i);
	  String detectedStanford = stanfordPosAnnos.get(i);
			  
		  if (gold.equals(detectedStanford)) {
			  correctStanford++;
		  } 
		  	  
	  }
  
  
  for (int i = 0; i<posTokens.size(); i++) {
	  String gold = goldPosAnnos.get(i);
	  String detectedTreeTagger = treeTaggerPosAnnos.get(i);
			  
		  if (gold.equals(detectedTreeTagger)) {
			  correctTreeTagger++;
		  } 
		  	  
	  }

    }


    /* 
     * This is called AFTER all documents have been processed.
     */
    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();
        
        String[] columnNames = {   
        "Token",		
        "Gold",                                          
        "OpenNLP",                                           
        "MatePos",                                               
        "Stanford",                                          
        "TreeTagger"};   
        
        String [][] posTags = new String[goldPosAnnos.size()][6];
       
                for(int row=0;row<goldPosAnnos.size();row++){
                    for (int col=0;col<5;col++){
                    posTags[row][0] = posTokens.get(row);
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
                
                System.out.println("OpenNLP scored an accuracy of " + ((double)correctOpenNlp/(double)nrOfDocuments)*100 + "% !");
                System.out.println("MatePos scored an accuracy of " + ((double)correctMatePos/(double)nrOfDocuments)*100 + "% !");
                System.out.println("StanfordPos scored an accuracy of " + ((double)correctStanford/(double)nrOfDocuments)*100 + "% !");
                System.out.println("TreeTagger scored an accuracy of " + ((double)correctTreeTagger/(double)nrOfDocuments)*100 + "% !");
                
    }
}