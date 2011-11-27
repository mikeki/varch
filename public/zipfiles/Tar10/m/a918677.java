

import java.util.*;

public class TareaArreglos {
	
	public static String concatenaNegativos(int arr[]) {
		String negativo = " ";
		for(int i=0;i<arr.length;i++){
			if(arr[i]<0)
				negativo = Integer.toString(arr[i]);
			System.out.print(",");	
		}
		return negativo;
	}
	
	public static void cambiaACero(double arr[]) {
		for(int i=0;i<arr.length;i++) {
			if(arr[i] < 0.0)
				arr[i] =0;
		}
	despliegaDOUBLE(arr);
	}
	
	public static boolean intercala(int arr[]) {
		for(int i=0;i<arr.length;i++) {
			boolean temp[] = new boolean[i];
				if(arr[i]%2 == 0)
					return temp[i];
		}
		return temp[i];
	}
	
	public static void despliegaDOUBLE(double arr[]) {
		for(int i=0;i<arr.length;i++)
			System.out.println(arr[i]+",");
	}
	
	public static void despliegaBOOLEAN(boolean arr[]) {
		for(int i=0;i<arr.length;i++){
			boolean temp[] = new boolean[i];
			System.out.println(temp[i]);
		}
	}
	
	public static void main (String arr[]) {
		int arr1[] = {-2, -4, -6, 8, 10}; //mŽtodo concatenaNegativos
		double arr2[] = {2.5, -4.4, -6.2, 8, 10}; //mŽtodo cambiaACero
		boolean arr3[] = {true, true, true, true, true}; //mŽtodo intercala
		System.out.println(concatenaNegativos(arr1));

		}
}