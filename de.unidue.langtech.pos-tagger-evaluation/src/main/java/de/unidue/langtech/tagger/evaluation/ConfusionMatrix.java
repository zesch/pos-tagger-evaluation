package de.unidue.langtech.tagger.evaluation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.ConditionalFrequencyDistribution;

public class ConfusionMatrix
{
	private List<String> goldValues;
	private List<String> predictedValues;
	
	private ConditionalFrequencyDistribution<String, String> cfd;
	
	public ConfusionMatrix()
	{	
		this.goldValues = new ArrayList<String>();
		this.predictedValues = new ArrayList<String>();
        this.cfd = new ConditionalFrequencyDistribution<String, String>();

	}
	
	public ConfusionMatrix(List<String> goldValues, List<String> predictedValues)
	{	
        if (goldValues.size() != predictedValues.size()) {
            throw new IllegalArgumentException("Gold and predicted need to be of equal size.");
        }
              
		this.goldValues = new ArrayList<String>(goldValues);
		this.predictedValues = new ArrayList<String>(predictedValues);
        this.cfd = new ConditionalFrequencyDistribution<String, String>();

	}
	
	public void registerOutcome(String goldValue, String predictedValue)
	{
        cfd.inc(goldValue, predictedValue);

		goldValues.add(goldValue);
		predictedValues.add(predictedValue);
	}
	
	public List<String> getTopConfusions(int n) {
		Map<String, Long> confusions = new HashMap<String, Long>();
		
		for (String outerKey : cfd.getConditions()) {
			for (String innerKey : cfd.getFrequencyDistribution(outerKey).getKeys()) {
				if (!outerKey.equals(innerKey)) {
					confusions.put(
							outerKey + "-" + innerKey,
							cfd.getFrequencyDistribution(outerKey).getCount(innerKey)
					);							
				}
			}
		}
		
        Map<String, Long> sortedConfusions = new TreeMap<String, Long>(new ValueComparator(confusions));
        sortedConfusions.putAll(confusions);
        
        int i=0;
        List<String> topConfusions = new ArrayList<String>();
        for (String key : sortedConfusions.keySet()) {
        	topConfusions.add(key + " - " + confusions.get(key));
        	i++;
        	if (i>n) {
        		break;
        	}

        }
        return topConfusions;
	}
   
	public double getAccuracy()
	{
		int correct = 0;
		
		for (int i=0; i<goldValues.size(); i++) {
			if (goldValues.get(i).equals(predictedValues.get(i))) {
				correct++;
			}
		}
		
		return (double) correct / goldValues.size();
	}
	
	
    public String getFormattedMatrix() {
    	
        Set<String> categories = new HashSet<String>();
        categories.addAll(goldValues);
        categories.addAll(predictedValues);
        
        StringBuilder sb = new StringBuilder();
        sb.append("\t");
        for (String category : categories) {
            sb.append(category + "\t");
        }
        sb.append("\n");

        for (String outerCategory : categories) {
            sb.append(outerCategory + "\t");
            for (String innerCategory : categories) {
                sb.append(cfd.getCount(innerCategory, outerCategory) + "\t");
            }
            sb.append("\n");
        }
        sb.append("\n");
        
        return sb.toString();
    }
    
    class ValueComparator
	    implements Comparator<String>
	{
	
	    Map<String, Long> base;
	
	    public ValueComparator(Map<String, Long> base)
	    {
	        this.base = base;
	    }
	
	    public int compare(String a, String b)
	    {
	
	        if (base.get(a) < base.get(b)) {
	            return 1;
	        }
	        else {
	            return -1;
	        }
	    }
	}
}