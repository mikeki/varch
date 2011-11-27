/**
  * 
  * @author H. Nahun Quintero Milian 796862 grupo02
  * @version 1.6.0
  */

import java.util.*;

  public class ArreglosDeUnaDimension{
  
   public static String concatenaNegativos(int arr1[]){
	 String g = "";
	 for(int i=0; i<arr1.length;i++){
	   if(arr1[i] < 0)
		 g+= i;
	 }
	 return g;
	}
	
	public static void cambiaACero(double arr2[]) {
	 for(int i = 0; i < arr2.length; i++){
	  if(i < 0)
	   arr2[i] = 0;
	 }
	}
   
	public static void intercala(boolean arr3[]){
	 for(int i = 0; i < arr3.length; i++){
	  if(i%2 ==0)
	   arr3[i] = true;
	  else 
	   arr3[i] = false;
	 }
	 
	}
	
	public static void despliegaDouble(int arr[]){
	 for(int i = 0; i < arr.length - 1; i++){  
		 System.out.print(arr[i] + " ,");  
		 
		}
		System.out.println(arr[arr.length-1]);
	 }
	
	public static void despliegaBoolean(boolean arr[]){
	 for(int i = 0; i < arr.length; i++){
	     System.out.println(arr);
	 }
	}
  
    public static void main(String args[]){
	  
	  Scanner entrada = new Scanner(System.in);
	 
	   System.out.println("Dime el tamanio del arreglo: ");
		int tam = entrada.nextInt();
		int arr1[] = new int[tam];
		for(int i = 0; i < tam; i++){
		
		System.out.println("Los numeros son : " + concatenaNegativos(arr1));
		}
		
		System.out.println("Dime otro numero: ");
		double arr2 = entrada.nextDouble();
		
		
		System.out.println("Dime un valor: ");
		
	 
	 }
  }
