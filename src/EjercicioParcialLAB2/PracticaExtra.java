package EjercicioParcialLAB2;

import java.sql.*;
import java.util.Scanner;

public class PracticaExtra {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hospital";
        String user = "root";
        String pass = "";

        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital();
        NuevasFunciones hospital1 = new NuevasFunciones();

        int opcion =0;

        try {
            // Establecer la conexión con la base de datos
            Connection conexion = DriverManager.getConnection(url, user, pass);
            System.out.println("Conexión exitosa");

            while(opcion !=6){

                System.out.println("\n ---------------------------------");

                System.out.println("1. Mostrar lista de pacientes ");
                System.out.println("2. Agregar un nuevo paciente ");
                System.out.println("3. Eliminar un paciente ");
                System.out.println("4. Asignar doctor a un paciente ");
                System.out.println("5. Listar pacientes entre fechas ");

                System.out.println("6. Salir ");


                System.out.print("\nIngrese su opción: ");

                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        hospital.listarPacientesDBH();
                        break;
                    case 2:
                        hospital.agregarPacienteDBH("Berru",29,"Walala");
                        break;
                    case 3:

                        hospital.eliminarPacienteDBH("Berru");

                        break;
                    case 4:

                        hospital.asignarDoctorCabeceraDBH("Leo","Doctor2");

                        break;
                    case 5:

                        hospital.listarPacientesEntreFechasDBH("2018-5-12","2025-8-9");

                        break;
                    case 7:
                        hospital1.cantidadPorEdad();
                        break;
                    case 6:
                        System.out.println("Gracias por usar nuestro programa!");
                        conexion.close();
                        break;
                    default:
                        System.out.println("Ingrese un caracter valido");
                        break;
                }
            }

        }catch(SQLException e){
            System.out.println("Error: No se pudo conectar a la base de datos");
            e.printStackTrace();
            e.getMessage();
            e.getCause();
        }
    }
}


class NuevasFunciones{

    public void cantidadPorEdad(){


        System.out.println("\nCantidad de pacientes por edad:\n");

        String consulta = "SELECT edad, COUNT(*) as cantidad_pacientes FROM pacientes GROUP BY edad";

        ResultSet resultado = DBHelper.ejecutarConsultaConResultado(consulta);

        try{
            while(resultado.next()){
                int edad = resultado.getInt("edad");
                int cantidad = resultado.getInt("cantidad_pacientes");

                System.out.println("De "+edad+" años hay: "+cantidad);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /*otras consultas que no llegue a implementar:
    SELECT doctor, MAX(fecha_ingreso) AS fecha_mas_reciente FROM pacientes GROUP BY doctor; para seleccionar los pacientes más recientes que tiene cada doctor
    SELECT nombre, edad FROM pacientes WHERE historial_medico LIKE '%cirugía%'; para comprobar si el historial de los pacientes está relacionado con cirugías
    SELECT AVG(edad) AS edad_promedio FROM pacientes; para sacar el promedio de edad de los pacientes
    */
}
