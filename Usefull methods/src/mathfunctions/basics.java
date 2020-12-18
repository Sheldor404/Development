package mathfunctions;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class basics {
	
 /*   public static double exponentiate(double x,int exponent) {
    double y = x;
    	for (int i = 0; i < exponent-1; i++) {
    		y = y*x;
    		}
    	return y;
    }*/
    
    public static void main(String args[]) { 
   /*     ArrayList<Integer> numbers = new ArrayList<>();
        String next=""; 
    	while(!next.equalsIgnoreCase("stop")) {
        Scanner in = new Scanner(System.in);   
        next =in.next();
        
        if(!next.equals("stop")) {
        	numbers.add(Integer.parseInt(next));
        	
        	}else {  
        		bubblesrt(numbers);
        		System.out.println(numbers);
        		System.out.println("1: "+ getnumbercounts(numbers,1 ));
        		System.out.println("2: "+ getnumbercounts(numbers,2 ));
        		System.out.println("3: "+ getnumbercounts(numbers,3 ));
        		System.out.println("4: "+ getnumbercounts(numbers,4 ));
        		System.out.println("5: "+ getnumbercounts(numbers,5 ));
        		System.out.println("6: "+ getnumbercounts(numbers,6 ));

        		
        		numbers.clear();
        	}
    	}*/

    }    

	public static void bubblesrt(ArrayList<Integer> list){
	    for (int j = 0; j < list.size(); j++) {
	
	        for (int i = 0; i < list.size()-1; i++) {
	            if(list.get(i) > list.get(i + 1)) {
	                int bigger = list.get(i);
	                int smaler = list.get(i + 1);
	                list.set(i,smaler);
	                list.set(i + 1,bigger);
	            }
	        }
	    }
	}
	public static int getnumbercounts(ArrayList<Integer> list,int number){
		int counter = 0;
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i) == number) {
			counter = counter + 1;
			}
			
		}
		return counter;
	}
	

	
}


















