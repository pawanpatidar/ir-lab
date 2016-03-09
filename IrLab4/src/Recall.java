import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Arrays;
import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

import org.apache.commons.io.FileUtils;


public class Recall {

	static HashMap<String, HashMap<Integer, Integer>> index =new HashMap<String, HashMap<Integer,Integer>>();
	static HashMap<Integer, String> docname = new HashMap<Integer, String>();
	static double count=0;
	/**
	 * @param args
	 * @throws IOException 
	 */
	
	
	
	@SuppressWarnings("null")
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
	
		int docid=1;
		File dir = new File("/home/pawan/Sem_6/IR_Lab/lab1/hindi"); //path for all files
		File[] list= dir.listFiles();
		
		// Read all files
		for (File file : list) {    			  
			System.out.println(count++);
			String filename =file.getName();
			docname.put(docid,filename);
		    String str=FileUtils.readFileToString(file);
		    Termpipline tp=new Termpipline(str);			//Object created
		    HashMap<String, Integer> word=tp.getterms();     // call a method getterms() returns hasmap
		
		    for(String s:word.keySet())				        //form a inverted index stored in index hasmap
		    {
		    
		    	HashMap<Integer, Integer> hmap=new HashMap<Integer,Integer>();
		    	if(index.containsKey(s))
		    	{
		    		int m=word.get(s);
		    		
		    		index.get(s).put(docid,m);
		    	
		    	}else{
		    		hmap.put(docid,word.get(s));
		    	
		 
		    		index.put(s, hmap);
		    	}
		    	
		    }
		    docid++;
		}
		
		//print inverted index 
		/*for(String str1:index.keySet())				
		{
			System.out.print(str1+"   {");
			for(int i:index.get(str1).keySet()){
				System.out.print("{did-"+i);
				System.out.print("  fr-"+index.get(str1).get(i)+"}");
			}
			System.out.println(   "} ");
		}*/
		
		//ask for enter query. 
		
		System.out.println("enter the document  id to use query");
		Scanner sc= new Scanner(System.in);
		String did=sc.next();
		Getquery gtq=new Getquery(did);
		String str=gtq.getquery();
		String[] words =str.split(" ");
	   	CalculateTfidf cal=new CalculateTfidf(words);
		HashMap<Integer, Double> sortedtf = new HashMap<Integer, Double>();
		sortedtf=cal.getTfidf();
		int i=0;
		String[] arr=new String[10];
		
		//Print 10 document id ,name, TFIDF calculate and rank of document
		for(int t:sortedtf.keySet()){  
			
				System.out.println("docid :"+t+", Doc name :"+docname.get(t)+", TFIDF :"+sortedtf.get(t)+" Rank :"+(i+1));
				arr[i]=docname.get(t);
				i++;
				if(i==10)
				break;
		
		}
		gtq.Reldoc(arr);
		   
		
	}
}			
		 
			
			
		/* PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
	       System.setOut(out);*/
		/*for(int t:docname.keySet()){
			System.out.println("docid-"+t+"docname="+docname.get(t));
			
		}*/
		
		//System.out.println("enter a query term");
		
	
	
	

