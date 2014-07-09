package de.unidue.langtech.teaching.ba;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;
import org.junit.Test;

import de.unidue.langtech.teaching.ba.components.EvaluatorModels;

public class EvaluatorModelsTest {

	@Test
	public void testEvaluatorCorrect() throws Exception
    {
	    int correctCposTags = 8;
	    int correctPosTags = 8;
 	
	    List<Object> posInformation = EvaluatorModels.evaluateSingle(new File("src/test/resources/correct.txt"));

       	int calculatedCorrectCposTags = (Integer) posInformation.get(6);
       	int calculatedCorrectPosTags = (Integer) posInformation.get(7);
       	
       	assertEquals(correctCposTags, calculatedCorrectCposTags);
       	assertEquals(correctPosTags, calculatedCorrectPosTags);
		
    }
	
	@Test
	public void testEvaluatorFalse() throws Exception
    {

	    int correctCposTags = 0;
	    int correctPosTags = 0;
 	
	    List<Object> posInformation = EvaluatorModels.evaluateSingle(new File("src/test/resources/false.txt"));
       
       	int calculatedCorrectCposTags = (Integer) posInformation.get(6);
       	int calculatedCorrectPosTags = (Integer) posInformation.get(7);
       	
       	assertEquals(correctCposTags, calculatedCorrectCposTags);
       	assertEquals(correctPosTags, calculatedCorrectPosTags);
		
    }
	
	@Test
	public void testEvaluator() throws Exception
    {

	    int correctCposTags = 3;
	    int correctPosTags = 1;
 	
	    List<Object> posInformation = EvaluatorModels.evaluateSingle(new File("src/test/resources/normal.txt"));
       
       	int calculatedCorrectCposTags = (Integer) posInformation.get(6);
       	int calculatedCorrectPosTags = (Integer) posInformation.get(7);
       	
       	assertEquals(correctCposTags, calculatedCorrectCposTags);
       	assertEquals(correctPosTags, calculatedCorrectPosTags);
		
    }
        
}
