package SistemaGestionDeEmpleados;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int opcion,opcion2,id,horasTrabajadas;
        String nombre;
        double sueldoBase,ventasRealizadas;
        Scanner scanner = new Scanner(System.in);
        GestorEmpleados gestor = new GestorEmpleados();
        do{
            System.out.println("\nSeleccione la opción que desea realizar a continuación:"); //menú de opciones para el usuario
            System.out.println("1-Agregar empleado\n2-Editar empleado\n3-Eliminar emplado\n4-Calcular sueldo\n5-Calcular impuestos\n6-Ver lista de empleados\n7-Salir\n");
            try{
                opcion = scanner.nextInt();
            } catch(Exception e) {
                scanner.nextLine();
                opcion = 0;
            }
            switch(opcion) { //entrada de la eleccion del primer menú
                case 1:
                    scanner.nextLine();
                    System.out.println("\nIngrese el nombre: ");
                    try {
                        nombre = scanner.nextLine();
                        System.out.println("Ingrese el id: ");
                        id = scanner.nextInt();
                        System.out.println("Ingrese el sueldo base: ");
                        sueldoBase = scanner.nextDouble();
                    } catch(Exception e){
                        break;
                    }
                    System.out.println("Seleccione el tipo de empleado que corresponda: \n1-Asalariado\n2-Por horas\n3-Por comisión\n"); //segundo menu para seleccionar el tipo de empleado
                    Empleado empleado = null;       //creacion de la variable empleado con valor null para despues poder asignarselo más facilmente
                    do{
                        try{
                        opcion2=scanner.nextInt();
                        } catch(Exception e){
                            break;
                        }
                        switch(opcion2){ //entrada de la segunda eleccion del usuario, donde se agrega finalmente al empleado a la lista de empleados
                            case 1:
                                empleado = new EmpleadoAsalariado(nombre,id,sueldoBase);
                                gestor.listaEmpleados.add(empleado);
                                break;
                            case 2:
                                System.out.println("Ingrese la cantidad de horas: ");
                                try{
                                    horasTrabajadas=scanner.nextInt();
                                } catch(Exception e){
                                    break;
                                }
                                empleado = new EmpleadoPorHoras(nombre,id,sueldoBase,horasTrabajadas);
                                gestor.listaEmpleados.add(empleado);
                                break;
                            case 3:
                                System.out.println("Ingrese las ventas realizadas: ");
                                try{
                                    ventasRealizadas= scanner.nextDouble();
                                } catch(Exception e){
                                    break;
                                }
                                empleado = new EmpleadoComision(nombre,id,sueldoBase,ventasRealizadas);
                                gestor.listaEmpleados.add(empleado);
                                break;
                            default:
                                System.out.println("Opción inválida, vuelva a seleccionar:\n");
                                break;
                        }
                    } while (opcion2<1 || opcion2>3);
                    break;
                case 2:         //caso 2, donde se invoca la funcion para editar a los empleados
                    System.out.println("\nIngrese el id del empleado que quiere modificar: ");
                    try{
                        gestor.modificarEmpleado(scanner.nextInt());
                    } catch(Exception e){
                        break;
                    }
                    break;
                case 3:         //caso 3, se invoca la funcion para eliminar a un empleado
                    System.out.println("\nIngrese el id del empleado que quiere eliminar: ");
                    try{
                        gestor.eliminarEmpleado(scanner.nextInt());
                    } catch(Exception e){
                        break;
                    }
                    break;
                case 4:  //caso 4, se invoca funcion para calcular el sueldo
                    System.out.println("\nIngrese el id del empleado del que quiere calcular el sueldo:");
                    gestor.calcularSueldo();
                    break;
                case 5:     //caso 5, se invoca funcion para calcular el impuesto
                    System.out.println("\nIngrese el id del empleado del que quiere calcular los impuestos:");
                    gestor.calcularImpuesto();
                    break;
                case 6:     //caso 6, se invoca funcion para mostrar lista de empleados
                    System.out.println("Lista de empleados:");
                    gestor.mostrarEmpleados();
                    break;
                case 7:     //salir
                    System.out.println("¡Gracias por usar este programa!");
                default:        //mensaje de error frente a una eleccion invalida
                    System.out.println("Opción inválida, asegúrese de ingresar el caracter o número correspondiente según lo indicado:\n");
                    break;
            }
        }while(opcion!=7);      //mientras no se haya elegido la opcion 7 el bucle continua
    }
}
