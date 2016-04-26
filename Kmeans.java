import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;


public class Kmeans {

	/**
	 * @param args
	 */
	
	
	
	
 	public static void main(String[] args) throws IOException {
 		HashMap<Integer, HashMap<String, Integer>> cluster = new HashMap<Integer, HashMap<String,Integer>>();
 		HashMap<String, HashMap<String, Double>> seeds= new HashMap<String, HashMap<String,Double>>();
 		  
 		File dir = new File("/home/pawan/Sem_6/IR_Lab/lab1/check"); //path for all files
		File[] list= dir.listFiles();
		Scanner sc =new Scanner(System.in);
		System.out.println("Enter No. of cluster you want(Less than 5)");
		int cltr = sc.nextInt();
		File seed = new File("/home/pawan/Sem_6/IR_Lab/lab1/seeds");		//read 
		File[] seedlist= seed.listFiles();
		for (int i=0;i<cltr;i++){
			String filename=seedlist[i].getName();
			String str=FileUtils.readFileToString(seedlist[i]);
			TermPipeline trmp=new TermPipeline(str);			//Object created
			HashMap<String, Double> word=trmp.getterms();
			 seeds.put(filename, word); 
			
		}
		
		/*for(String docname:seeds.keySet()){
			System.out.println(docname);
			for(String st:seeds.get(docname).keySet())
			{
				System.out.print(st+"   ");
				System.out.println(seeds.get(docname).get(st));
			}
		}*/
		for( File file: list){
			Double[] store= new Double[cltr];
			String fname = file.getName();
			String str=FileUtils.readFileToString(file);
			TermPipeline trmp=new TermPipeline(str);			//Object created
			HashMap<String, Double> word=trmp.getterms();
		    HashMap<String,Integer> doc = new HashMap<String,Integer>();
			doc.put(fname,1);
			
				
				int t=0;
				for(String s:seeds.keySet())
				{
					store[t]=0.0;
					for(String st:seeds.get(s).keySet())
					{
						if(word.containsKey(st)){
						store[t] =store[t]+Math.pow(((word.get(st))-(seeds.get(s).get(st))), 2);
						
						}else{
							store[t] =store[t]+Math.pow(seeds.get(s).get(st), 2);	
						}
						
					}
					
					store[t]=Math.sqrt(store[t]);
					//System.out.println(store[t]);
					t++;
				}
				int index=0;
				Double  min=store[0];;
				for(int x=0;x<cltr;x++){
					
					//System.out.println(store[x]);
					if(store[x]<min){
						min=store[x];
						index=x;
					//	System.out.println(store[x]);
					}
				}
		cluster.put(index, doc);
		//System.out.println(index);
		
						
			
		}
		
		for(int in:cluster.keySet()){
			System.out.println("cluster-"+ in);
			for(String st:cluster.get(in).keySet()){
				//System.out.println(cluster.get(in).size());
				System.out.println(st);
			}
		}	
			
		// Read all files
	/*	for (File file : list) {    			  
			String filename =file.getName();
			//docname.put(docid,filename);
		    String str=FileUtils.readFileToString(file);
		    TermPipeline tp=new TermPipeline(str);			//Object created
		    HashMap<String, Integer> word=tp.getterms();     // call a method getterms() returns hasmap
		
		}
		*/
	}

}
