import java.util.*;

public class ArreglosUnaDimension{


 public static String concatenaNegativos(int arr[]){
 	String acum = "";
	for(int i = 0; i<arr.length; i++){
	 if(arr[i]<0)
	 	acum = acum + arr[i] + ", ";
	}	
	return acum;	
	}
 public static void cambiaACero(double arr[]){
 	for(int i = 0; i<arr.length; i++){
	 if(arr[i]<0)
	 	arr[i] = 0;
		
	}
 	despliegaDouble(arr);
 }	
  
 public static void intercala(boolean arr[]){
 	for(int i = 0; i<arr.length; i++){
		if(i%2 == 0)
			arr[i] = true;
		else
			arr[i] = false;
	}
 	despliegaBoolean(arr);		
 }

 public static void despliegaDouble(double arr[]){
 	for(int i = 0; i<arr.length-1; i++){
		System.out.print(arr[i] + ", ");
	}
	System.out.print(arr[arr.length-1]);
 	System.out.println();
 }	

 public static void despliegaBoolean(boolean arr[]){
 	for(int i = 0; i<arr.length-1; i++){
		System.out.print(arr[i] + ", ");
	}
		System.out.print(arr[arr.length-1]);
 }	
 
 
  public static void main(String args[]){
	int arr1[] = {-2, -4, -6, 8, 10};   // para el método concatenaNegativos
	double arr2[] = {2.5, -4.4, -6.2, 8, 10};     // para el método cambiaACero
	boolean arr3[] = {true, true, true, true, true};   // para el método intercala
	
	System.out.println(concatenaNegativos(arr1));
	cambiaACero(arr2);
	intercala(arr3);	
  }
} 