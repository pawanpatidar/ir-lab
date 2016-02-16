import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

import org.apache.commons.io.FileUtils;


public class index {

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
	    HashMap<String, HashMap<Integer, Integer>> index =new HashMap<String, HashMap<Integer,Integer>>();
		HashMap<Integer, String> docname = new HashMap<Integer, String>();
		
		int docid=1,count=0;
		File dir = new File("/home/pawan/Sem_6/IR_Lab/lab1/hindi");
		File[] list= dir.listFiles();
		//read all files
		for (File file : list) {
			System.out.println(count++);
			String filename =file.getName();
			docname.put(docid,filename);
		    String str=FileUtils.readFileToString(file);
		    Termpipeline tp=new Termpipeline(str);
		    HashMap<String, Integer> word=tp.getterms();  // call for function
	     	//create inverted index.
		    for(String s:word.keySet())
		    {
		    
		    	HashMap<Integer, Integer> hmap=new HashMap<Integer,Integer>();
		    	if(index.containsKey(s))
		    	{
		    		int m=word.get(s);
		    		index.get(s).put(docid,m);
		    	
		    	}else
		    	{
		    		hmap.put(docid,word.get(s));
		    		index.put(s, hmap);
		    	}
		    	
		    }
		    docid++;
		}
	
		
		for(String str:index.keySet())		// print index
		{
			System.out.print(str+"   {");
			for(int i:index.get(str).keySet()){
				System.out.print("{did-"+i);
				System.out.print("  fr-"+index.get(str).get(i)+"}");
			}
			System.out.println(   "} ");
		}
		
		
	}
	
	

}
