import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;


public class TermPipeline {
	
	static String query;
	Double count=1.0;
	public TermPipeline(String containt)
	{
		this.query=containt;
		
		
	}
	// method for geting terms removing Stopwords
	public HashMap<String, Double> getterms() throws IOException
	{
		
		HashMap<String, Integer> stopword =new HashMap<String, Integer>();
		HashMap<String, Double> words =new HashMap<String, Double>();
		String[] word=query.split("\\s+");
		//path to stopwords
		File dir1 = new File("/home/pawan/Sem_6/IR_Lab/lab1/stopword/stopwords_hi.txt");
		
		
		
			   String str=FileUtils.readFileToString(dir1);
			   String[] stpword2=str.split("\\s+");
			  // int i=0;
			   for(String stp:stpword2)
			   {
				     stopword.put(stp,1);
			   }
			   for(String wrd:word)
			   {
				  if(!stopword.containsKey(wrd))
				  {
					  if(words.containsKey(wrd))
					  {
						  Double fr=words.get(wrd)+1;
						  words.put(wrd, fr);
					  }else
					  {
						  words.put(wrd,count);
				  
					  }
				  }
			   }
			 
		
		return words;
	}
	
		
}
