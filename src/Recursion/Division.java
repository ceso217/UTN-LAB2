package Recursion;

import java.util.Scanner;

public class Division {
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
        while(dendo>=sor){
            dendo = dendo-sor;
            cont++;
        }
        dendo = dendo*10;
        while(dendo>=sor) {
            dendo = dendo - sor;
            aux++;
        }
        for(int i=0;i<aux;i++){
            aux=aux/10;
        }
        return cont+aux;
    }
}
