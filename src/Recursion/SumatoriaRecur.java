package Recursion;

import java.util.Scanner;

public class DivRecursión {
    public static void main(String[] args) {
        int cont=0,aux=0,cont2=0;
        float dendo, sor;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese un dividendo y el divisor: ");
        dendo=scanner.nextInt();
        sor=scanner.nextInt();
        System.out.println(restaDiv(dendo,sor,cont,cont2,aux));;

    }
    public static float restaDiv (float dendo, float sor,int cont, int cont2,int aux){
        float decimal=0;
        if (dendo>=sor){
            cont++;
            return restaDiv(dendo-sor,sor,cont,cont2,aux);
        } else {
            if(aux==0){
                aux++;
                decimal=restaDiv(dendo*10,sor,cont2,cont,aux)/10;
            }
            return cont+decimal;
        }
    }
}
