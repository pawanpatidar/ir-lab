import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

import org.apache.commons.io.FileUtils;


public class R_Feedback {
	static HashMap<String, HashMap<Integer, Integer>> index =new HashMap<String, HashMap<Integer,Integer>>();
	static HashMap<Integer, String> docname = new HashMap<Integer, String>();
	static double count=0;
	static int docid=1;
	
	/**
	 * @param args
	 * @return 
	 * @throws IOException 
	 */
	
		 
	
	@SuppressWarnings("null")
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int[] rel_doc=new int[30];
		HashMap<Integer, HashMap<String, Integer>> Dindex=new HashMap<Integer, HashMap<String,Integer>>();
		
		File dir = new File("/home/pawan/Sem_6/IR_Lab/lab1/check");
		File[] list= dir.listFiles();
		for (File file : list) {
			System.out.println(count++);
			String filename =file.getName();
			docname.put(docid,filename);
		    String str=FileUtils.readFileToString(file);
		    Termpipeline tp=new Termpipeline(str);
		    HashMap<String, Integer> word=tp.getterms();
		    Dindex.put(docid, word);		//creating direct index
		    //creating inverted index
		    for(String s:word.keySet())
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
		//print invered index
		for(String str1:index.keySet())
		{
			System.out.print(str1+"   {");
			for(int i:index.get(str1).keySet()){
				System.out.print("{did-"+i);
				System.out.print("  fr-"+index.get(str1).get(i)+"}");
			}
			System.out.println(   "} ");
		}
		//ask for query
		System.out.println("enter search query");
		Scanner sc= new Scanner(System.in);
		String str=sc.nextLine();
		String[] words =str.split(" ");
		CalculateTfidf cal=new CalculateTfidf(words);
		HashMap<Integer, Double> sortedtf = new HashMap<Integer, Double>();
		sortedtf=cal.getTfidf();
		int i=0;
		//print 10 document which has high tfidf
		for(int t:sortedtf.keySet()){
			
				System.out.println("docid :"+t+", Doc name :"+docname.get(t)+", TFIDF :"+sortedtf.get(t)+" Rank :"+(i+1));
				i++;
				if(i==10)
				break;
		
		}
		HashMap<String, Double> queryword=new HashMap<String, Double>();
		HashMap<String, Double> relword=new HashMap<String, Double>();
		HashMap<String, Double> nonrelword=new HashMap<String, Double>();
		HashMap<String, Double> fword=new HashMap<String, Double>();
		System.out.println("please enter no. of the relavent document :");
		int no_rtdoc=sc.nextInt();
		System.out.println("Enter the document id of relavant document");
	
		//taking all document id of all relavent document
		for(int j=0;j<no_rtdoc;j++){
		   rel_doc[j]=sc.nextInt();
		}
		//create q0 for ganrating new query
		for(String s:words){
			if(queryword.containsKey(s))
			{
				queryword.put(s, queryword.get(s)+1);
			}
			queryword.put(s , 1.0);
		}
		//calcuate relavent r vector for ganrating new query
		for(int j=0;j<no_rtdoc;j++){
			for(String st:Dindex.get(rel_doc[j]).keySet()){
				for(int t:index.get(st).keySet()){
					if(relword.get(st)!=null)
					relword.put(st, relword.get(st)+index.get(st).get(t));
					else
					relword.put(st, 0.0+index.get(st).get(t));	
				}
				relword.put(st, 0.25*(relword.get(st)/no_rtdoc));
			}
			
		}
		//calcuate non-relavent nr vector for ganrating new query
		for(String st:index.keySet()){
			if(!relword.containsKey(st)){
				for(int t:index.get(st).keySet()){
					if(nonrelword.get(st)!=null)
					nonrelword.put(st, nonrelword.get(st)+index.get(st).get(t));
					else
						nonrelword.put(st, 0.0+index.get(st).get(t));	
				}
				nonrelword.put(st, 0.75*(nonrelword.get(st)/(count-no_rtdoc)));
			 
			}
			
		}
		//add all q0 r and nr vector
		for(String s:index.keySet()){
			if(queryword.containsKey(s)){
				fword.put(s, queryword.get(s));
			}else if(relword.containsKey(s)){
				fword.put(s, relword.get(s));
			} else if(nonrelword.containsKey(s)){
				fword.put(s, -(nonrelword.get(s)));
			}
		}
		//genrate new query by soring hahsmap
		HashMap<String, Double> sorthmap =cal.sortByValue(fword);
		int c=0;String[] new_word=new String[100];
		for(String new_qword:sorthmap.keySet()){
			 new_word[c]=new_qword;
			 System.out.println(new_word[c]);
			if(c==10)
				break;
		c++;
		}
		//again calculate tfidf
		HashMap<Integer, Double> sorttf = new HashMap<Integer, Double>();
		CalculateTfidf ncal= new CalculateTfidf(new_word);
		sorttf=ncal.getTfidf();
		int k=0;
		//print docid,name,TFIDF,Rank;
		for(int t:sorttf.keySet()){
			
			System.out.println("docid :"+t+", Doc name :"+docname.get(t)+", TFIDF :"+sorttf.get(t)+" Rank :"+(k+1));
			k++;
			if(k==10)
			break;
	
	    }
        
	}
			
		 
			
			
		/* PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
	       System.setOut(out);*/
		/*for(int t:docname.keySet()){
			System.out.println("docid-"+t+"docname="+docname.get(t));
			
		}*/
		
		//System.out.println("enter a query term");
		
	
	
	
}
