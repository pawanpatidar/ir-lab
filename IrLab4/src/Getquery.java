import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;


public class Getquery {

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	String did;
	public Getquery(String docid){
		did=docid;
	}
	public String getquery() throws IOException{		//function for getting query 
		HashMap<Integer, String> query= new HashMap<Integer, String>(); 
		File dir1 = new File("/home/pawan/Sem_6/IR_Lab/lab 4/abc.txt");
		String str=FileUtils.readFileToString(dir1);
		 String[] terms2= str.split("-");
		 int i=0,temp_id=25;String query1=null;
		    for(String terms:terms2){				//Storing query with document id.
		    	if(i%2!=0){
		    		//System.out.println(query1+"  "+terms);
		    		query.put(temp_id, terms);
		    		temp_id--;
		    	}
		    	query1=terms;
		    	i++;
		    }
		    int docid=Integer.parseInt(did);
		    System.out.println("query is: "+query.get(docid));
		return query.get(docid);
	}

	public void Reldoc(String[] reldoc) throws IOException
	{
		// TODO Auto-generated method stub
		HashMap<String, HashMap<String, Integer>> relv_document=new HashMap<String, HashMap<String, Integer>>();
		File dir = new File("/home/pawan/Sem_6/IR_Lab/lab 4/query.txt");//path for all file
		//	String filename =dir.getName();
		    String containt =FileUtils.readFileToString(dir);
		    String[] terms1= containt.split(" ");
		    String rel_doc = null;
		    System.out.println(".....................");
		   
		    //for storing all relevance document in hash map
		    for(String s1: terms1){
		    	HashMap<String, Integer> temp_hmp=new HashMap<String, Integer>();
		    	String flage,query_doc=null;
		    	if(s1.startsWith("1"))
		    	{
		    		flage =s1.substring(0, 1);
		    		query_doc=s1.substring(2, s1.length()-1);
		    		if(!relv_document.containsKey(query_doc))
		    		{
		    			temp_hmp.put(rel_doc, 1);
		    			relv_document.put(query_doc, temp_hmp);
		    	    }else
		    	    {
		    	    	relv_document.get(query_doc).put(rel_doc, 1);
		            }
		    	}
		    	rel_doc=s1;
		   }
		  
		    	
		    //print all relevance document of given query	
		    double count=0,no_rel=0;	
		    String q="english-document-000"+did+".tx";
		    System.out.println(q);
		    if(!relv_document.containsKey(q))
		    	System.out.println("documet with this id does not have relevance document.");
		    else
		    for(String rd:relv_document.get(q).keySet()){
	    		System.out.println(rd+" "+relv_document.get(q).get(rd));
	    		no_rel++;
	    	}
		    //for calculating average precision and counting relevant document retrieve
		    double mean=0,rel_count=0;
		    if(!relv_document.containsKey(q))
		    	System.out.println("");
		    else
		    for(String doc:reldoc){
		    	rel_count++;
		    	if(relv_document.get(q).containsKey(doc)){
		    		count++;
		    	}
		    	mean=mean+(count/rel_count);
		    }
		    mean=mean/count;
		    //print all result 
		    System.out.println();
		    System.out.println("Relevence doc retrive:"+count);
		    System.out.println("Totel relvence doc:"+no_rel);
		    System.out.println("Precission:"+(count/10));
		    if(no_rel!=0)
		    System.out.println("Recall"+(count/no_rel));
		    System.out.println("Average precision: "+mean);
			
	}
}
