import java.util.*;
/*
 * Fundamentos de programación  
 *      TERCER PARCIAL
 *   Miercoles 09 Abril 2008
 *
 *@author Linda C. Rodriguez, 421247
 *@version 1.6
 *
 */
 
public class AreglosTarea{

	public static String concatenaNegativos(int arr[]){
		String acum = "";
		for(int i=0; i<arr.length; i++){
			if(arr[i] < 0)
				acum = acum + arr[i] + ",";
		}
		return acum + ".";
	}
	
	public static void cambiaACero(double arr[]){
		for(int i=0; i<arr.length; i++){
			if(arr[i] < 0)
				arr[i] = 0;
		}
		despliegaDOUBLE(arr);
	}
	
	public static void intercala(boolean arr[]){
		for(int i=0; i<arr.length; i++){
			if(i%2 == 0)
				arr[i] = true;
			else
				arr[i] = false;
		}
		despliegaBOOLEAN(arr);
	}
	
	public static void despliegaDOUBLE(double arr[]){
		String acum = "";
		for(int i=0; i<arr.length; i++)
			acum = acum + arr[i] + "\t";
		System.out.println(acum);
	}
	
	public static void despliegaBOOLEAN(boolean arr[]){
		String acum = "";
		for(int i=0; i<arr.length; i++)
			acum = acum + arr[i] + "\t";
		System.out.println(acum);
	}
	
	public static void main(String[]args){
		int arr1[] = {-2, -4, -6, 8, 10};   // para el método concatenaNegativos
		double arr2[] = {2.5, -4.4, -6.2, 8, 10};     // para el método cambiaACero
		boolean arr3[] = {true, true, true, true, true};   // para el método intercala

		System.out.println("\n" + concatenaNegativos(arr1));
		cambiaACero(arr2);
		intercala(arr3);
	}	
}