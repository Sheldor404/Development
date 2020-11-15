package mathfunctions;

public class basics {
	
    public static double exponentiate(double x,int exponent) {
    double y = x;
    	for (int i = 0; i < exponent-1; i++) {
    		y = y*x;
    		}
    	return y;
    }
    
    
    
}
