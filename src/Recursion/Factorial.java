package Recursion;

import java.util.Scanner;

public class Factorial {
    public static void main(String[] args) {
        int nro=0;
        int nroLong;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese un número: ");
        nro=scanner.nextInt();  // ingreso por teclado del valor
        nroLong=nro; //igualo a long para mostrar el resultado de ambas funciones
        System.out.println("Resultado del factorial con la función recursiva: "+factorial (nro)); // llamada de la función
        System.out.println("Resultado del factorial con la función iterativa: "+factorial (nroLong));  //
    }
    public static int factorial (int nro) { //esta función recursiva suma nro más nro menos 1 hasta que este (nro-1) sea igual a 1 por ende returna 1 y se van resolviendo las sumas sucesivamente
        int result;
        if(nro>1){
            return nro*factorial(nro-1);
        } else {
            return 1;
        }
    }

    public static long factorial(long nro) { //esta es la función iterativa, utiliza la variable long para la sobrecarga de métodos
        long result=0;
        for(int i=0;i<=nro;i++) { //bucle for usando nro-1 para ir multipicando por los números sucesivos
            result=result*nro-i;
        }
        return result;
    }
}
    //por algún motivo solo me dan correctamente los resultados de los factoriales hasta el 12, del 13 en adelante salen mal los números pero no sé porque