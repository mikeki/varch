/**
  * Este programa tiene un par de arreglos predefinidos
  * a partir de los cuales desarrolla diversos metodos
  * de manejo de arreglos.
  *
  *@author Adrian Garcia Betancourt 1087680
  *@version 1.6.5_05
  *
  */

public class ManejaArreglos {

	public static String concatenaNegativos( int arr[] ) {
		String salida = "";
		for( int x = 0; x < arr.length - 1; x++ )
			if( arr[ x ] < 0 )
				salida += arr[ x ] + ", ";
		salida += arr[ arr.length - 1 ] + ".";
		return salida;
	}
	
	public static void cambiaACero( double arr[] ) {
		for( int x = 0; x < arr.length; x++ )
			if( arr[ x ] < 0 )
				arr[ x ] = 0;
		despliegaDouble( arr );
	}
	
	public static void intercala( boolean arr[] ) {
		for( int x = 0; x < arr.length; x++ )
			if( x % 2 == 0 )
				arr[ x ] = true;
			else
				arr[ x ] = false;
		despliegaBoolean( arr );
	}
	
	public static void despliegaDouble( double arr[] ) {
		String salida = "[]\t#\n";
		for( int x = 0; x < arr.length; x++ )
			salida += x + "\t" + arr[ x ] + "\n";
		System.out.println( salida );
	}
	
	public static void despliegaBoolean( boolean arr[] ) {
		String salida = "[]\t#\n";
		for( int x = 0; x < arr.length; x++ )
			salida += x + "\t" + arr[ x ] + "\n";
		System.out.println( salida );
	}	
	
	public static void despliegaInt( int arr[] ) {
		String salida = "[]\t#\n";
		for( int x = 0; x < arr.length; x++ ) 
			salida += x + "\t" + arr[ x ] + "\n";
		System.out.println( salida );
	}
	
	public static void main( String args[] ) {
	
		int arr1[] = { -2, -4, -6, 8, 10 };
		double arr2[] = { 2.5, -4.4, -6.2, 8, 10 };
		boolean arr3[] = { true, true, true, true, true };
		
		System.out.println( "El arreglo int inicial es:" );
		despliegaInt( arr1 );
		System.out.println( "Los numeros negativos del arreglo anterior son: " + concatenaNegativos( arr1 ) + "\n" );
		System.out.println( "El arreglo double inicial es:" );
		despliegaDouble( arr2 );
		System.out.println( "Los numeros negativos se sustituyen por ceros:" );
		cambiaACero( arr2 );
		System.out.println( "El arreglo boolean inicial es:" );
		despliegaBoolean( arr3 );
		System.out.println( "Los valores del arreglo se intercalan:" );
		intercala( arr3 );
		
	}
	
} 
		

		
	
	
			