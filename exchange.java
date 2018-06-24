

import java.io.*;
import java.util.*;


public class exchange extends Thread {
	
	static HashMap<String,ArrayList<String>> map;
	public void run()
    {
        try
        {
        	
        	for(Map.Entry<String, ArrayList<String>> listEntry : map.entrySet()){
	            // iterating over a list
	            	for(String values:listEntry.getValue()) {
	            		
	            		if(Thread.currentThread().getName()==listEntry.getKey()) {	
	            			  
	            		       calling.communicate(listEntry.getKey(),values,Thread.currentThread().getName());
	            		       
	            		}
	            	}
	        }
 
        }
        catch (Exception e)
        {
            // Throwing an exception
            System.out.println ("Exception is caught");
        }
    }

	public static void main(String[] args) throws Exception {
		
		map=new HashMap<String,ArrayList<String>>();
		File file=new File("calls.txt");
		BufferedReader br=new BufferedReader(new FileReader(file));
		String str;
		String[] words = null;
		String match=",";
		while((str=br.readLine())!=null) {
			ArrayList<String> arr=new ArrayList<String>();
			String newstr = str.replaceAll("[^A-Za-z,0-9]+", "");
			 int index=newstr.indexOf(match);
			 String key=newstr.substring(0,index);
			 String value=newstr.substring(index+1,newstr.length());
			 words=value.split(",");
			 for(String s:words) {
				 arr.add(s);
				 
			 }
			 map.put(key, arr);			
	  }
		
		System.out.println("*** Calls to be made ****");
		for(Map.Entry<String, ArrayList<String>> listEntry : map.entrySet()){
			
			System.out.println(listEntry.getKey() + ":" + listEntry.getValue());
			
		}
		System.out.println("\n");
		calling call=new calling();
		call.start_thread(map);
		System.out.println("\n");
	 for(Map.Entry<String, ArrayList<String>> listEntry : map.entrySet()){
	            // iterating over a list
	            	
	           System.out.println("process "+listEntry.getKey()+" has received no calls for 1 second, ending...\n");
	        }
		System.out.println("Master has received no replies for 1.5 seconds, ending...");
	
}
}