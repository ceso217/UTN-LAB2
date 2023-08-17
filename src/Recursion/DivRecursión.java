package Recursion;

import java.util.Scanner;

public class DivRecursión {
    public static void main(String[] args) {
        float dendo, sor;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese un dividendo y el divisor: ");
        dendo=scanner.nextInt();
        sor=scanner.nextInt();
        System.out.println(division(dendo,sor));;

    }
    public static float division (float dendo, float sor){
        int cont=0;
        float aux=0;
        if (dendo>=sor){
            return division(dendo-sor,sor);
        }
    }
}
