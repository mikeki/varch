/*
*
*Luis Dumois Petersen 1083847 Grupo 02 
*Java 1.6
*
*/

import java.util.*;

	public class tarea{
	
		public static void despliegaDouble(double arr[]){
	for(int i = 0; i <arr.length-1; i++){
		System.out.print(arr[i]+ ",");
	}
		System.out.println(arr[arr.length-1]);
}


	public static void despliegaBoolean(boolean arr[]){
	for(int i = 0; i <arr.length-1; i++){
			System.out.print(arr[i]+ ",");
	}
	System.out.println(arr[arr.length-1]);
}
	public static void main(String [] args){

		int arr1[]={-2, -4, -6, 8, 10};
		double arr2[] = {2.5, -4.4, -6.2, 8, 10};
		boolean arr3[] = {true, true, true, true, true};
		
		System.out.println(concatenaNegativos(arr1));
		cambiaACero(arr2);
		intercala(arr3);



}

	
	public static String concatenaNegativos(int arr[]){
	String x = "";
		for(int i=0; i<arr.length; i++)
			if(arr[i]<0){
			x= x+arr[i]+",";
			}
		return x;
	}
	
	public static void cambiaACero(double arr[]){
		for(int i=0; i<arr.length; i++)
			if(arr[i]<0){
			arr[i]=0;
			}
		despliegaDouble(arr);
	}
	
	public static void intercala(boolean arr[]){
		for(int i =0;i<=arr.length-1; i++){
			if(i%2==0)
				arr[i]= true;
			else
				arr[i] = false;
			}
		
		despliegaBoolean(arr);	
		

	}

}