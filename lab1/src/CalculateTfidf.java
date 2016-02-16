import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;


public class CalculateTfidf extends tfidf {

	String[] query;
	public CalculateTfidf(String[] words) {
		
		this.query=words;
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap sortByValue(HashMap unSortedHmap){
		List list = new LinkedList(unSortedHmap.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
			}
		});
		HashMap sortedHmap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHmap.put(entry.getKey(), entry.getValue());
		}
		return sortedHmap;
	}
	// Method for geting TFIDF with document id
	public HashMap<Integer, Double> getTfidf() {
		
		
	    double[] idf =new double[1000];
	    double[] flist =new double[(int)count];
	
	    HashMap<Integer, Double> backup = new HashMap<Integer,Double>();
	    double[][] tfidf =new double[1000][(int)count];
	    HashMap<Integer, String> fnldoc =new HashMap<Integer, String>();
	    int id=0,did=0;
		
		for(String s:query)   			//calculate idf and tfidf for query word store idf in idf array and tfidf in tfidf array.
		{
			if(index.get(s)!=null)
			{
			
				idf[id++]=Math.log10(count/(double)index.get(s).size());
				for(int t:index.get(s).keySet())
				{
					fnldoc.put(t, docname.get(t));
		 
					tfidf[id-1][t]=(double)(index.get(s).get(t))*idf[id-1];
				}
			}
		}
		int c=0;
		
		for(int t:fnldoc.keySet())		//adding all tfidf
		{
			int r=0;
			for(String s:query)
			{
				flist[c]=flist[c]+tfidf[r][t];
				r++;
			}
			backup.put(t, flist[c]);
			c++;
		}
	
		 
		HashMap<Integer, Double> sortedlist=sortByValue(backup);
		
		return sortedlist;
	}
	
	

}
