package SistemaGestionDeEmpleados;

import java.util.ArrayList;
import java.util.Scanner;

public class GestorEmpleados {
    ArrayList<Empleado> listaEmpleados;

    public GestorEmpleados() {
        listaEmpleados = new ArrayList<>();
    }

    public void agregarEmpleado(Empleado empleado) {
        listaEmpleados.add(empleado);
    }

    public void eliminarEmpleado(int id) {
            for (Empleado empleado : listaEmpleados) {
                if (empleado.getId()==id) {
                    listaEmpleados.remove(empleado);
                    break;
                }
            }
        }

    public void modificarEmpleado(int id) {
        Scanner scanner=new Scanner(System.in);
        for (Empleado empleado : listaEmpleados) {
            if (empleado.getId() == id) {
                System.out.println("El nombre actual es: "+empleado.getNombre()+". Ingrese el nuevo nombre: ");
                empleado.setNombre(scanner.nextLine());
                System.out.println("El sueldo base actual es: "+empleado.getSueldoBase()+". Ingrese el nuevo sueldo base: ");
                empleado.setSueldoBase(scanner.nextDouble());
                if(empleado instanceof EmpleadoPorHoras) {
                    System.out.println("El número de horas trabajadas actual es: "+((EmpleadoPorHoras)empleado).getHorasTrabajadas()+". Ingrese la nueva cantidad de horas trabajadas: ");
                    ((EmpleadoPorHoras)empleado).setHorasTrabajadas(scanner.nextInt());
                } else if (empleado instanceof EmpleadoComision){
                    System.out.println("La cantidad de ventas realizadas actual es: "+((EmpleadoComision)empleado).getVentasRealizadas()+". Ingrese la nueva cantidad correspondiente: ");
                    ((EmpleadoComision)empleado).setVentasRealizadas(scanner.nextDouble());
                }
            }
        }
    }

    public void mostrarEmpleados() {
        for (Empleado empleado : listaEmpleados) {
            System.out.println("\nNombre: " + empleado.getNombre()+"\nId: "+empleado.getId()+"\nSueldo Base: "+empleado.getSueldoBase());
            if(empleado instanceof EmpleadoComision) {
                System.out.print("Ventas Realizadas: "+((EmpleadoComision)empleado).getVentasRealizadas()+"\n");
            } else if (empleado instanceof EmpleadoPorHoras){
                System.out.print("Horas Tranajadas: "+((EmpleadoPorHoras)empleado).getHorasTrabajadas()+"\n");
            }
        }
    }

    public void calcularSueldo(){
        int id;
        Scanner scanner=new Scanner(System.in);
        id=scanner.nextInt();
        for(Empleado empleado : listaEmpleados){
            if(empleado.getId()==id){
                System.out.println("El sueldo de "+empleado.getNombre()+" es "+empleado.calcularSueldo());
            }
        }
    }

    public void calcularImpuesto(){
        int id;
        Scanner scanner=new Scanner(System.in);
        id=scanner.nextInt();
        for(Empleado empleado : listaEmpleados){
            if(empleado.getId()==id){
                if(empleado instanceof EmpleadoComision) {
                    System.out.println("El impuesto a pagar de "+((EmpleadoComision)empleado).getNombre()+" es "+((EmpleadoComision)empleado).calcularImpuesto());
                } else if (empleado instanceof EmpleadoPorHoras){
                    System.out.println("El impuesto a pagar de "+((EmpleadoPorHoras)empleado).getNombre()+" es "+((EmpleadoPorHoras)empleado).calcularImpuesto());
                } else if (empleado instanceof EmpleadoAsalariado){
                    System.out.println("El impuesto a pagar de "+((EmpleadoAsalariado)empleado).getNombre()+" es "+((EmpleadoAsalariado)empleado).calcularImpuesto());
                }
            }
        }
    }
}
