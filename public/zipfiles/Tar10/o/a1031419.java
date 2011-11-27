//
//  TareaLunes.java
//  
//
//  Created by eduardo padrón corral on 07/04/08.
//  Copyright 2008 __MyCompanyName__. All rights reserved.
//

import java.util.*;
public class TareaLunes1 {
public static String concatenaNegativos(int arr[]){
String usuario ="";

for(int i = 0;i<arr.length;i++){
if(arr[i]<0){
usuario +=arr[i]+", ";
}
}
return usuario;
}

public static void cambiaACero( double arr[]){
for(int i=0;i<arr.length;i++){
if(arr[i]<0)
arr[i]=0;
}
despliegaDouble(arr);
}

public static void intercala(boolean arr[]){
for(int i =0; i<arr.length ;i++){
if(i % 2 == 0) {

arr[i]=true;

} else
arr[i] = false;
}
 despliegaBoolean(arr);
}

public static void despliegaDouble( double arr[]){
for(int i=0; i<arr.length;i++){
System.out.print(arr[i] +", ");
}
System.out.println();
}

public static void despliegaBoolean(boolean arr[]){
for(int i=0; i<arr.length;i++){
System.out.print(arr[i] +", ");
}
System.out.println();
}

public static void main(String args[]){

int arr1[] = {-2,-4,-6,8,10};

double arr2[ ] = {2.5, -4.4,-6.2,8,10};

boolean arr3[] = {true, true, true, true, true};

System.out.println(concatenaNegativos(arr1));

cambiaACero(arr2);

intercala(arr3);
}
}
