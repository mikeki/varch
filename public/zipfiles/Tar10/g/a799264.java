/* Descripcion: Arreglos
 * @Author Francisco Badillo grupo 02
 * @version 1.6
 */
 
 import java.util.*;
 
 public class ArreglosTarea{
 
 public static String concatenaNegativos(int arr[]){
 String x= "";
 for(int i=0; i<arr.length; i++){
 if(arr[i]<0)
 x= x + arr[i] +",";
 }
 return x;
 }
 
 public static void cambiaACero(double arr[]){
 for(int i=0; i<arr.length; i++){
 if(arr[i]<0)
 arr[i]=0;
 }
 despliegaDouble(arr);
 }
 
 public static void intercala(boolean arr[]){
 for(int i=0; i<arr.length; i++){
 if(i%2==0)
 arr[i]=true;
  else
 arr[i]=false;
 }
 despliegaBoolean(arr);
 }
 
 public static void despliegaDouble(double arr[]){
 for(int i=0; i<arr.length; i++){
 System.out.println(arr[i]);
 }
 }
 
 public static void despliegaBoolean(boolean arr[]){
 for(int i=0; i<arr.length; i++){
 System.out.print(arr[i] +" ");
 }
 }
 
 public static void main(String arrgs[]){
 
 int arr1[] = {-2, -4, -6, 8, 10}; 
double arr2[] = {2.5, -4.4, -6.2, 8, 10};     
boolean arr3[] = {true, true, true, true, true};  

System.out.println("\n" +concatenaNegativos(arr1));
cambiaACero(arr2);
intercala(arr3);

}
}