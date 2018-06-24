

import java.util.*;

public class calling extends Thread {
	
	

	public void start_thread(HashMap <String,ArrayList<String> >map) throws InterruptedException {
		//exchange[] exc=new exchange[map.size()];
		
		 for(Map.Entry<String, ArrayList<String>> listEntry : map.entrySet()){
	            // iterating over a list
	            	
			       exchange exc=new exchange();
	            	exc.setName(listEntry.getKey());
	     
	    			exc.start();
	            	
	    			
	    			//exc.setName(listEntry.getKey());		//System.out.println("Thread name is:"+exc.getName());
	       
		 }
		 
		
		 for(Map.Entry<String, ArrayList<String>> listEntry : map.entrySet()){
			 
			 currentThread().join(1000);
			 
			
		 }
		
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		
		
	}

	public static void communicate(String Key,String values, String name) throws InterruptedException {
		// TODO Auto-generated method stub
		//Thread.currentThread().sleep(2000);
		long NanoTime= (System.nanoTime());
		System.out.printf("%s recieved intro message from %s [ %d ]\n", values,Key,NanoTime);
		//System.out.println(Key+"recieved intro message from"+ values+ "["+ NanoTime+"]");
		Thread.sleep((long) (Math.random() * 100));
		printreply(values,Key,NanoTime);
		
		
		//System.out.println(values+"recieved reply message from"+ Key+ "["+ NanoTime+"]");
		
	}

	private static void printreply(String values, String key, long nanoTime) {
		// TODO Auto-generated method stub
		System.out.printf("%s recieved reply message from %s [ %d ]\n", key,values,nanoTime);
	}

}
