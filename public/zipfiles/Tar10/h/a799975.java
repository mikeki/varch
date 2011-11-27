import java.util.*;
/**
 * Tarea 10. Arreglos (una dimension) 
 * 
 * Manejo de Array's
 * @author Maria Olmedo :: 799975 :: Grupo 02 :: 7 Abril 2008.
 * @version 1.6
 * 
 */

public class ArreglosTarea {

	public static Scanner stdin = new Scanner (System.in);
		
	public static void despliegaDouble (double arr[]) {
		for (int i=0; i<arr.length-1;i++) {
			System.out.print(arr[i]+",");
		}
		System.out.println(arr[arr.length-1]);
	}
	
	public static void despliegaBoolean (boolean arr[]) {
		for (int i=0; i<arr.length-1;i++) {
			System.out.print(arr[i]+",");
		}
		System.out.println(arr[arr.length-1]);
	}
	
	public static String concatenaNegativos (int arr[]) {
		String var="";
		for(int i=0; i<arr.length; i++){
			if (arr[i]<0) {
				var+=arr[i]+",";
			}
			else {	
			}
		}
		return var;
	}
	
	public static void cambiaACero(double arr[]) {
		for(int i=0; i<arr.length; i++) {
			if (arr[i]<0) {
				arr[i]=0;
			}
		}
		despliegaDouble(arr);
	}
	
	
	public static void intercala(boolean arr[]){
		for(int i=0; i<arr.length; i++) {
			if (i%2==0)
				arr[i]=true;
			else
				arr[i]=false;
		}
		despliegaBoolean(arr);
	}
	
	public static void main (String args[]) {
	
	int arr1[] = {-2, -4, -6, 8, 10};   // para el método concatenaNegativos
	
	double arr2[] = {2.5, -4.4, -6.2, 8, 10};     // para el método cambiaACero
	
	boolean arr3[] = {true, true, true, true, true};   // para el método intercala
	
	System.out.println(concatenaNegativos(arr1));
	System.out.println();
	
	cambiaACero(arr2);
	System.out.println();
	
	intercala(arr3);
	System.out.println();
		
	}
}