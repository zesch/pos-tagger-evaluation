package de.unidue.langtech.teaching.ba;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;

import de.unidue.langtech.teaching.ba.components.EvaluatorModels;

/**
 * Test class for the evaluator.
 * Tests in 3 cases if accuracies are correctly calculated.
 * @author Onur
 *
 */
public class EvaluatorModelsTest {
    
	//test best case, 100% correctly tagged
	@Test
	public void testEvaluatorCorrect() throws Exception
    {
	    int correctCposTags = 8;
	    int correctPosTags = 8;
	    int nrOfDocuments = 8;
	    
	    String cPosAccuracy = String.format( "%.2f", ((double)correctCposTags/(double)nrOfDocuments)*100);
	    String posAccuracy = String.format( "%.2f", ((double)correctPosTags/(double)nrOfDocuments)*100);
 	
	    List<Object> posInformation = EvaluatorModels.evaluateSingle(new File("src/test/resources/correct.txt"));

       	int calculatedCorrectCposTags = (Integer) posInformation.get(6);
       	int calculatedCorrectPosTags = (Integer) posInformation.get(7);
       	int calculatedNrOfDocuments = (Integer) posInformation.get(5);
       	
       	String calculatedCposAccuracy = String.format( "%.2f", ((double)calculatedCorrectCposTags/(double)calculatedNrOfDocuments)*100);
	    String calculatedPosAccuracy = String.format( "%.2f", ((double)calculatedCorrectPosTags/(double)calculatedNrOfDocuments)*100);
       	
       	assertEquals(cPosAccuracy, calculatedCposAccuracy);
       	assertEquals(posAccuracy, calculatedPosAccuracy);
		
    }
	
	//test worst case, 0% correctly tagged
	@Test
	public void testEvaluatorFalse() throws Exception
    {

	    int correctCposTags = 0;
	    int correctPosTags = 0;
	    int nrOfDocuments = 8;
	    
	    String cPosAccuracy = String.format( "%.2f", ((double)correctCposTags/(double)nrOfDocuments)*100);
	    String posAccuracy = String.format( "%.2f", ((double)correctPosTags/(double)nrOfDocuments)*100);
 	
	    List<Object> posInformation = EvaluatorModels.evaluateSingle(new File("src/test/resources/false.txt"));

       	int calculatedCorrectCposTags = (Integer) posInformation.get(6);
       	int calculatedCorrectPosTags = (Integer) posInformation.get(7);
       	int calculatedNrOfDocuments = (Integer) posInformation.get(5);
       	
       	String calculatedCposAccuracy = String.format( "%.2f", ((double)calculatedCorrectCposTags/(double)calculatedNrOfDocuments)*100);
	    String calculatedPosAccuracy = String.format( "%.2f", ((double)calculatedCorrectPosTags/(double)calculatedNrOfDocuments)*100);
       	
       	assertEquals(cPosAccuracy, calculatedCposAccuracy);
       	assertEquals(posAccuracy, calculatedPosAccuracy);
		
    }
	
	//test normal case
	@Test
	public void testEvaluator() throws Exception
    {

	    int correctCposTags = 3;
	    int correctPosTags = 1;
	    int nrOfDocuments = 8;
	    
	    String cPosAccuracy = String.format( "%.2f", ((double)correctCposTags/(double)nrOfDocuments)*100);
	    String posAccuracy = String.format( "%.2f", ((double)correctPosTags/(double)nrOfDocuments)*100);
 	
	    List<Object> posInformation = EvaluatorModels.evaluateSingle(new File("src/test/resources/normal.txt"));

       	int calculatedCorrectCposTags = (Integer) posInformation.get(6);
       	int calculatedCorrectPosTags = (Integer) posInformation.get(7);
       	int calculatedNrOfDocuments = (Integer) posInformation.get(5);
       	
       	String calculatedCposAccuracy = String.format( "%.2f", ((double)calculatedCorrectCposTags/(double)calculatedNrOfDocuments)*100);
	    String calculatedPosAccuracy = String.format( "%.2f", ((double)calculatedCorrectPosTags/(double)calculatedNrOfDocuments)*100);
       	
       	assertEquals(cPosAccuracy, calculatedCposAccuracy);
       	assertEquals(posAccuracy, calculatedPosAccuracy);
		
    }
        
}
