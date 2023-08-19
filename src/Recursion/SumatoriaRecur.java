package Recursion;

import java.util.Scanner;

public class SumatoriaRecur {
    public static void main(String[] args) {
        int nro=0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese un número: ");
        nro=scanner.nextInt();  // ingreso por teclado del valor
        System.out.println(sumatoria (nro)); // llamada de la función
    }
    public static int sumatoria (int nro) { //esta función recursiva suma nro más nro menos 1 hasta que este (nro-1) sea igual a 1 por ende returna 1 y se van resolviendo las sumas sucesivamente
        int result;
        if(nro>1){
            return nro+sumatoria(nro-1);
        } else {
            return 1;
        }
    }
}
