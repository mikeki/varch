/**
*
*Programa que maneja arreglos
*@author Juan Jacobo Zuniga 1135683
*@version 1.6
*/

import java.util.*;

public class Arreglos3 {

		public static Scanner entrada = new Scanner(System.in); //variable global que puede ser utilizada por todos los metodos

		public static String concatenaNegativos(int a[]){
		 	String negativo = ""; 	
			for(int i = 0; i<a.length-1; i++)
				if(a[i]<0){
               negativo += a[i]+",";
					}
					return negativo;
				}

		
		public static void cambiaACero(double a[]) {
			for(int i = 0; i<a.length; i++){
				if(a[i]<0){
					a[i] = 0;
					}
				}
			despliegaDouble(a);
		}
		
		public static void intercala(boolean a[]){
         for(int i = 0; i<a.length;i++){
            if(i%2==0)
               a[i] = true;
            else
               a[i] = false;  
         }
         despliegaBoolean(a);
      }		

		public static void despliegaDouble(double a[]) {
			for(int i=0; i<a.length; i++) {
			System.out.print(a[i] + " , ");
			}
		}
		
		public static void despliegaBoolean(boolean a[]) {
			for(int i=0; i<a.length; i++) {
			System.out.print(a[i] + " , ");
			}
		}
		
		 
	   public static void main(String args[]) {
		 	int arr1[] = {-2, -4, -6, 8, 10};  
			double arr2[] = {2.5, -4.4, -6.2, 8, 10}; 
			boolean arr3[] = {true, true, true, true, true};	
			System.out.println(concatenaNegativos(arr1));
         cambiaACero(arr2);
         System.out.println();
         intercala(arr3);		
}

}