package qtrip;

public class SingleTon {
	 private static SingleTon st = null;

	    private SingleTon(){

	    }
	    
	    public static SingleTon getInstance(){

	        if (st == null) 
	            st = new SingleTon();
	        
	            
	        return st;
	        
	    }
}
