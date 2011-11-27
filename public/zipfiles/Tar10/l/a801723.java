/**
 * Esta Clase Trabaja con Arreglos de una Dimensión 
 * 
 *@author José Luis Hernández Cruz, A00801723, 07.04.2008 
 *@version 1.6
 *
 */
import java.util.*;

public class Arreglos_801723 {
	
	
	public static String concatenaNegativos(int arr[]) {
		
		String negativos ="";
	
		for(int i=0; i<arr.length; i++)
			if(arr[i]<0)
				negativos += arr[i] + " ";	
		
		return negativos;	 
		
	}
	
	public static void cambiaACero(double arr[]) {
	
		for(int i=0; i<arr.length; i++)
			if(arr[i]<0)
				arr[i] = 0;
		
		despliegaDouble(arr);
	}
	
	public static void intercala(boolean arr[]) {
	
		for(int i=0; i<arr.length; i++)
			if((i%2) == 0)
				arr[i] = true;
			else
				arr[i] = false;
		
	}	
	
	public static void despliegaDouble (double arr[]) {
		
		String impresor = "[" + arr[0];
		
		for(int i=1; i<arr.length-1; i++)
			impresor += ", " + arr[i];
		
		impresor += ", " + arr[arr.length-1] + "]";
		
		System.out.print(impresor);
	}
	
	public static void despliegaBoolean (boolean arr[]) {
		
		String impresor = "[" + arr[0];
		
		for(int i=1; i<arr.length-1; i++)
			impresor += ", " + arr[i];
		
		impresor += ", " + arr[arr.length-1] + "]";
		
		System.out.print(impresor);
		
	}
	
	public static void main(String args[]) {
		
		int arr1[] = {-2, -4, -6, 8, 10};   // para el método concatenaNegativos
		double arr2[] = {2.5, -4.4, -6.2, 8, 10};     // para el método cambiaACero
		boolean arr3[] = {true, true, true, true, true};   // para el método intercala

		System.out.print("Concatena hace esto: " + concatenaNegativos(arr1));
		System.out.print("\nCambia a cero: ");
		cambiaACero(arr2);
		System.out.println();
		intercala(arr3);
		System.out.print("Intercala: ");
		despliegaBoolean(arr3);
				
	}
	
}