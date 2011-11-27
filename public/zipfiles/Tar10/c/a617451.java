/*
*@authorAlejandra Robles mat 617451
*@version 1.6
*/

public class Arreglos{

public static String concatenaNegativos(int a[]){
	String nega = "";
	for(int i = 0; i < a.length; i++){
		if(a[i] < 0)
			nega = nega + a[i] + ",";
		
	}
	
	return nega;
}

public static void cambiaAcero(double a[]){
	
	for(int i = 0; i < a.length ; i++){
		if(a[i] < 0){
			a[i] = 0;
		}else{
			a[i] = a[i];
		}
		
	System.out.print(a[i] + ",");
	
	}
	
	System.out.println();
	
}

public static void intercala(boolean a[]){
	for(int i = 0; i< a.length ; i++){
		if(i == 0 || i%2== 0){
			a[i] = true;
		}else{
			a[i] = false;
	}
	
	System.out.print(a[i] + ",");

}
System.out.println();

}

public static void despliegaDouble(double a[]){
	for(int i = 0; i< a.length; i++)
	System.out.print(a[i] + ",");
	
	System.out.println();
}

public static void despliegaBoolean(boolean a[]){
	for(int i = 0; i < a.length; i++)
	System.out.print(a[i] + ",");

	System.out.println();
}

public static void main(String args[]){

int arr1[] = {-2, -4, -6, 8, 10};   // para el m?todo concatenaNegativos
double arr2[] = {2.5, -4.4, -6.2, 8, 10};     // para el m?todo cambiaACero
boolean arr3[] = {true, true, true, true, true};   // para el m?todo intercala

String negativos = concatenaNegativos(arr1);
System.out.println(negativos);

cambiaAcero(arr2);

intercala(arr3);

despliegaDouble(arr2);

despliegaBoolean(arr3);
}
}