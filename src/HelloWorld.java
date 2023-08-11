import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        int [] nro = new int [3];
        int aux;
        System.out.println("Hola Mundo");
        System.out.println("Ingrese tres números:");
        Scanner scanner = new Scanner(System.in);
        nro[0]=scanner.nextInt();
        nro[1]=scanner.nextInt();
        nro[2]=scanner.nextInt();
        for (int i = 0; i < 2 ; i++) {
            for (int j = 0; j < 2; j++) {
                if (nro[j] < nro[j + 1]) {
                    aux = nro[j];
                    nro[j] = nro[j + 1];
                    nro[j + 1] = aux;
                }
            }
        }
        System.out.println("Los números ordenados de mayor a menor son:"+" "+nro[0]+" "+nro[1]+" "+nro[2]);
    }
}
