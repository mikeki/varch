/*
 * Descripcion: tarea 10
 *@author Grupo02..... Hugo Lizardi, 1102177, 7 de abril de 2008
 *@version 1.6
 *
 */




import java.util.*;

	public class tarea10{
	
		public static String concatenaNegativos(int arr[]){
		String acum = "";
		for(int i = 0; i<arr.length; i++){
			if(arr[i]<0)
				acum = acum + arr[i];
			}
			return acum;
		}
		
		
		public static void cambiaAcero(double arr[]){
			for(int i = 0; i<arr.length; i++){
				if(arr[i]<0)
					arr[i] = 0;
			}	
			despliegaDouble(arr);
		}
			
		public static void intercala(boolean arr[]){
		for(int i = 0; i<arr.length; i++){
			if (i% 2== 0)
				arr[i]= true;
			else
				arr[i]= false;
				
		}
		despliegaBoolean(arr);
	
	}	
		
		
		public static void despliegaDouble(double arr[]){
		for(int i = 0; i<arr.length-1; i++){
		System.out.print(arr[i] + ",");
		
		}
		
			System.out.println(arr[arr.length-1]);

		}	
				
			
		public static void despliegaBoolean(boolean arr[]){
		for(int i= 0; i<arr.length-1; i++){
		System.out.print(arr[i]+ ";");
		
	}
		System.out.println(arr[arr.length-1]);
		
		
		}	

		
	
	
	
		public static void main(String args[]){
		
int arr1[] = {-2, -4, -6, 8, 10};   
System.out.println(concatenaNegativos(arr1));
double arr2[] = {2.5, -4.4, -6.2, 8, 10};
cambiaAcero(arr2);     
boolean arr3[] = {true, true, true, true, true};

intercala(arr3);
		
	
	
 }
}

			