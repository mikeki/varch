//
//  A00598335tareaL7.java
//  
//
//  Created by Alejandra Rodriguez on 4/6/08.
//  Copyright 2008 __MyCompanyName__. All rights reserved.
//

import java.util.*;
public class A00598335tareaL7 {

	public static String concatenaNegativos(int arr[]){
		String num= " ";
		for(int i=0; i<arr.length; i++){
			if( arr[i]< 0)
				num = Integer.toString(arr[i]);
			System.out.print(", ");	
		}
		return num;
	}

	public static void despliegaDOUBLE(double arr[]) {
		for(int i=0;i<arr.length;i++)
			System.out.println(arr[i]+",");
	}
	
	public static boolean intercala(int arr[]) {
		for(int i=0;i<arr.length;i++) {
		boolean tru[] = new boolean[i];		
			if(i%2 == 0)
				return tru[i];
		}
	}
	
	public static void main(String arr[]){
		int arr1[] = {-2, -4, -6, 8, 10};   // para el mŽtodo concatenaNegativos
		double arr2[] = {2.5, -4.4, -6.2, 8, 10};     // para el mŽtodo cambiaACero
		boolean arr3[] = {true, true, true, true, true};   // para el mŽtodo intercala
		System.out.println(concatenaNegativos(arr1));
		System.out.println(cambiaACero(arr2)); 
		System.out.println(intercala(arr3)); 
	}
}
