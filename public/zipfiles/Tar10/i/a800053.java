/**
/
/ @victor Clemente 800053
/ @version 6.0
/
/
**/

import java.util.*;


public class Arregloss {

	public static String concatenaNegativos(int n[]){
         String negativos = ""; 
         for(int i = 0; i<n.length; i++)
            if(n[i] < 0) {
               negativos += n[i]+",";
            }
         return negativos;
      }
   
    public static void cambiaACero(double n[]){
	
	    for (int i = 0; i<n.length ; i++) {
		if (n[i] < 0){
		    n[i] = 0;
		    }
		System.out.println( " Valor " + (i+1) + " es : " + n[i]);
	    }
    }
			
	
	
	public static void intercala(boolean n[]){
        for(int i = 0; i<n.length ;i++){
	       if(i % 2 == 0) {
               n[i]=true;
           } else {
               n[i] = false;
           }
		   }
		despliegaBoolean(n);
       }
	   
	public static void despliegaDouble( double n[]){
        for(int i = 0; i<n.length ;i++){
             System.out.println(n[i] +", ");
        }
    }

    public static void despliegaBoolean(boolean n[]){
        for(int i = 0; i < n.length ;i++) {
             System.out.print(n[i] +", ");
        }
		System.out.println(" ");
    }

        public static void main(String args[]){
            
			int arr1[] = {-2,-4,-6,8,10};
			double arr2[ ] = {2.5, -4.4,-6.2,8,10};
			boolean arr3[] = {true, true, true, true, true};

            System.out.println(" ");
			intercala(arr3);
			System.out.println(" ");
            cambiaACero(arr2);
			System.out.println(" ");
			System.out.println ("heyy...los negativos concatenados son " + concatenaNegativos(arr1));
         }
    }


	
   

            