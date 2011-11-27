
/*
 * Descripcion:aplicacion con metodos de modificacion de arreglos
 *@author Jose alfonso
 *@version 1.6
 */
 
public class ArreglosUnaDimension{
	public static String concatenaNegativos (int arr[]){
		String negativos=	"";
		for (int i=0; i<arr.length-1;i++){
			if( arr[i]<0)
				negativos = negativos + arr[i]+ ",";
		}
		negativos=negativos.substring(0,negativos.length()-1);
		if( arr[arr.length-1]<0)
			negativos = negativos + ","+ arr[arr.length-1];
		negativos=negativos+".";
		return negativos;
	}
	
	public static void cambiaACero (double arr[]){
		for (int i=0;i< arr.length;i++){
			if (arr[i]<0)
				arr [i]=0;
		}
		despliegaDOUBLE (arr);
	}
	
	public static void intercala (boolean arr[]){
		for (int i=0;i< arr.length;i++){
			if ((i+1)%2==0)
				arr[i]=false;
			else
				arr[i]=true;
		}
		despliegaBOOLEAN(arr);
	}
	
	
	public static void despliegaDOUBLE(double arr[]){
		for (int i=0;i< arr.length-1;i++){
			System.out.print (arr[i]+",");
		}
		System.out.println (arr[arr.length-1]+".");
	}
	
	public static void despliegaBOOLEAN(boolean arr[]){
		for (int i=0;i< arr.length-1;i++){
			System.out.print (arr[i]+",");
		}
		System.out.println (arr[arr.length-1]+".");
	}
	
	
	public static void main (String args[]){
	
		int arr1[] = {-2, -4, -6, 8, 10}; // para el método concatenaNegativos
		double arr2[] = {2.5, -4.4, -6.2, 8, 10}; // para el método cambiaACero
		boolean arr3[] = {true, true, true, true, true}; // para el método intercala
		System.out.println (concatenaNegativos(arr1));
		cambiaACero(arr2);
		intercala(arr3);
	}
}
