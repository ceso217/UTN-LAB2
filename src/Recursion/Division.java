package Recursion;

import java.util.Scanner;

public class Division {
    public static void main(String[] args) {
        int cont=0,aux=0,cont2=0; //variables para la funcion recursiva
        float dendo, sor; //variables para ambas funciones, son el dividendo y el divisor
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese un dividendo y el divisor: ");
        dendo=scanner.nextInt(); // ingreso por teclado de los valores
        sor=scanner.nextInt();
        System.out.println("Resultado de la función iterativa: "+division(dendo,sor)); //llamada de las funciones
        System.out.println("Resultado de la función recursiva: "+division(dendo,sor,cont,cont2,aux));


    }
    public static float division (float dendo, float sor){ //función con iteración
        int cont=0; //inicialización de los contadores
        float cont2=0;
        while(dendo>=sor){ //bucle while para saber mediante las restas sucesivas cuantas veces entra el divisor en el dividendo
            dendo = dendo-sor;
            cont++; //contador que representa la parte entera del resultado
        }
        dendo = dendo*10; //se multiplica el resto por 10 para poder hacer nuevamente las restas sucesivas
        while(dendo>=sor) {
            dendo = dendo - sor;
            cont2++; // y se utiliza el contador 2 para guardar la parte decimal del resultado
        }
        cont2=cont2/10; // y se lo divide por 10 para mover la coma a la izquierda y que quede como parte decimal
        return cont+cont2; //se devuelve la suma de las respectivas partes entera y decimal
    }

    public static float division (float dendo, float sor,int cont, int cont2,int aux){ //función con recursividad, tiene más argumentos por el hecho de no poder inicializar un contador efectivo en las funciones recursivas ya que se sobreescriben continuamente, y me sirve también para la sobrecarga de métodos
        float decimal=0;
        if (dendo>=sor){ //la condición recursiva consiste en que mientras el dividendo sea mayor o igual al divisor se va a seguir autollamando, pero cada vez utilizando como argumento a el dividendo menos el divisor
            cont++;// y utilizando un contador para saber cuantas veces entra el divisor en el dividendo
            return division(dendo-sor,sor,cont,cont2,aux); //primer uso recursivo para sacar la parte entera del resultado
        } else {
            if(aux==0){ //se utiliza el condicional if para que no entre en un blucle infito cuando se utilice  nuevamente la función recursiva para sacar la parte decimal, utilizando la variable aux de debajo
                aux++;
                decimal=division(dendo*10,sor,cont2,cont,aux)/10; //segundo uso que se utiliza para sacar la parte decimal
            }
            return cont+decimal; //se devuelve la suma de las respectivas partes entera y decimal
        }
    }
}
