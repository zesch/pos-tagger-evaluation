package de.unidue.langtech.tagger.evaluation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class CSVPrinter {
	public static void createCSV(String OUTPUT_FOLDER, String experimentName, HashMap<String, List<String[]>> map)
			throws IOException {
		StringBuilder sb = new StringBuilder();
		List<String> keySet = new ArrayList<String>(map.keySet());
		Collections.sort(keySet, new Comparator<String>(){

			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}});
		int max = map.get(keySet.get(0)).size();
		
		sb.append(";");
		for (String key : keySet){
			sb.append(key+";;");
		}
		sb.append("\n");
		
		for (int i=0; i < max; i++){
		for (int j=0; j < keySet.size();j++){
			String key = keySet.get(j);
			List<String[]> list = map.get(key);
			String[] v = list.get(i);
			if (j==0){
				sb.append(v[0]+";");
			}
			sb.append(v[1]+";"+v[2]+";");
		}
		sb.append("\n");
		}
		
		FileUtils.writeStringToFile(new File(OUTPUT_FOLDER + "/result"+experimentName+System.currentTimeMillis()+".csv"),
				sb.toString());
	}
}
