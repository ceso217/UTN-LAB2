package EjercicioParcialLAB2;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hospital";
        String user = "root";
        String pass = "";

        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital();

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

class Hospital{
// listar pacientes y pacientes entre fechas usando DBHelper
    //ejecuta la consulta respectiva y la guarda en un objeto ResultSet para enviar al método listarPacientes(ResultSet resultado) a que lo imprima
    public void listarPacientesDBH() {
        String consulta = "SELECT * FROM pacientes";
        ResultSet resultado = DBHelper.ejecutarConsultaConResultado(consulta);
        listarPacientes(resultado);
    }

    //ejecuta la consulta respectiva y la guarda en un objeto ResultSet para enviar al método listarPacientes(ResultSet resultado) a que lo imprima
    public void listarPacientesEntreFechasDBH(String fechaDesde, String fechaHasta) {
        String consulta = "SELECT * FROM pacientes WHERE fecha_ingreso BETWEEN '"+fechaDesde+"' AND '"+fechaHasta+"';";
        ResultSet resultado = DBHelper.ejecutarConsultaConResultado(consulta);
        listarPacientes(resultado);
    }
    //muestra efectiva de los datos pedidos en la consulta respectiva que se guardó en el resultSet (en este caso el que se pide en listarPacientes() o en listarPacientesEntreDosFechas(Date fechaDesde, Date fechaHasta))
    //en resumen, este método solo muestra datos, no tiene ninguna consulta, sino que recibe una que ya se ejecutó y guardo en el resultSet
    public void listarPacientes(ResultSet resultado){
        if (resultado != null) {
            try {
                System.out.println("Lista de Pacientes:");
                System.out.printf("%-10s %-15s %-5s %-20s %-12s %-10s\n", "ID", "Nombre", "Edad", "Historial Médico", "Fecha Ingreso", "Doctor");

                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String nombre = resultado.getString("nombre");
                    int edad = resultado.getInt("edad");
                    String historialMedico = resultado.getString("historial_medico");
                    Date fechaIngreso = resultado.getDate("fecha_ingreso");
                    int idDoctor = resultado.getInt("doctor");

                    System.out.printf("%-10d %-15s %-5d %-20s %-12s %-10d\n", id, nombre, edad, historialMedico, fechaIngreso, idDoctor);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

//mostrar pacientes y pacientes entre fechas sin utilización del DBHelper
    public void listarPacientes(){

        /*En este metodo pasamos como parametro nuestra conexion a la BD para poder acceder a ella
         * y ejecutamos la consulta que selecciona toda la tabla que le pedimos
         *
         * Luego a traves del while recorremos fila por fila cada campo de la tabla, capturando y mostrando
         * en cada ciclo los datos de los campos de la misma, hasta el final de la tabla*/

        try{

            String url = "jdbc:mysql://localhost:3306/hospital";
            String user = "root";
            String pass = "";
            Connection conexion = DriverManager.getConnection(url, user, pass);

            String consulta = "SELECT * FROM pacientes";

            PreparedStatement statement = conexion.prepareStatement(consulta);

            // Ejecuta la consulta SQL y almacena los resultados en un ResultSet.
            ResultSet resultado = statement.executeQuery();

            // Imprime una cabecera de columnas para los datos de los estudiantes.
            System.out.printf("%-4s%-20s%-6s%-20s%-20s%-10s\n", "ID", "Nombre", "Edad", "Historial Médico", "Fecha de ingreso", "Doctor");

            // Itera a trav�s de los resultados y muestra los datos de cada estudiante en forma de tabla.
            while (resultado.next()) {

                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                int edad = resultado.getInt("edad");
                String historialMedico = resultado.getString("historial_medico");
                String fechaIngreso = resultado.getString("fecha_ingreso");
                String doctor = resultado.getString("doctor");

                // Imprime los datos del estudiante con tabulaciones para formatear como una tabla.
                System.out.printf("%-4d%-20s%-6d%-20s%-20s%-10s\n", id, nombre, edad, historialMedico, fechaIngreso, doctor);
            }

            // Cierra el ResultSet y la declaraci�n para liberar recursos.
            resultado.close();
            statement.close();

        } catch(SQLException e){
            System.out.println("Error: No se pudo conectar a la BD en metodo mostrar Pacientes");
            e.printStackTrace();
            e.getMessage();
            e.getCause();
        }
    }

    public void listarPacientesEntreFechas(String fechaInicio, String fechaFin) {

        String consulta = "SELECT * FROM pacientes WHERE fecha_ingreso BETWEEN '"+fechaInicio+"' AND '"+fechaFin+"';";

        try{
            String url = "jdbc:mysql://localhost:3306/hospital";
            String user = "root";
            String pass = "";
            Connection conexion = DriverManager.getConnection(url, user, pass);

            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);

            ResultSet resultado = preparedStatement.executeQuery();

            System.out.printf("%-4s%-20s%-6s%-20s%-20s%-10s\n", "ID", "Nombre", "Edad", "Historial Médico", "Fecha de ingreso", "Doctor");

            while(resultado.next()){

                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                int edad = resultado.getInt("edad");
                String historialMedico = resultado.getString("historial_medico");
                String fechaIngreso = resultado.getString("fecha_ingreso");
                String doctor = resultado.getString("doctor");

                // Imprime los datos del estudiante con tabulaciones para formatear como una tabla.
                System.out.printf("%-4d%-20s%-6d%-20s%-20s%-10s\n", id, nombre, edad, historialMedico, fechaIngreso, doctor);
            }

            preparedStatement.close();
            resultado.close();
        } catch(SQLException e){
            e.getMessage();
            e.getStackTrace();
            e.getCause();
        }
    }

    //agregar paciente usando los métodos del DBHelper
    public void agregarPacienteDBH(String nombre,int edad,String historial){

        Scanner scanner = new Scanner(System.in);

        String fecha;

        System.out.println("Ingrese la fecha de ingreso");
        fecha = scanner.nextLine();

        String consulta = "INSERT INTO pacientes (nombre, edad, historial_medico, doctor, fecha_ingreso) VALUES ('" + nombre + "', " + edad + ", '" + historial + "', " + 0 + ", '" + fecha + "')";

        DBHelper.ejecutarConsulta(consulta);
    }

    //agregar paciente de la forma manual usando los métodos setString y setInt de preparedStatetment para reemplazor los valores en los correspondientes (?,?,?,?,...)
    public void agregarPaciente(){

        String url = "jdbc:mysql://localhost:3306/hospital";
        String user = "root";
        String pass = "";

        Scanner scanner = new Scanner(System.in);

        String nombre="";
        String historial="";
        int edad=0;
        String fecha;

        try{

            String consulta = "INSERT INTO pacientes(nombre, edad, historial_medico, fecha_ingreso, doctor) VALUES (?,?,?,?,?)";

            System.out.println("Ingrese el nombre del paciente");
            nombre=scanner.nextLine();
            System.out.println("Ingrese la edad del paciente");
            edad=scanner.nextInt();
            scanner.nextLine();
            System.out.println("Ingrese el historial del paciente");
            historial=scanner.nextLine();
            System.out.println("Ingrese la fecha de ingreso");
            fecha = scanner.nextLine();

            Paciente paciente = new Paciente(nombre,edad,historial);


            Connection conexion = DriverManager.getConnection(url, user, pass);

            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);


            preparedStatement.setString(1, paciente.getNombre()); // Asigna el valor de nombre al segundo marcador de posici�n
            preparedStatement.setInt(2, paciente.getEdad()); // Asigna el valor de edad al tercer marcador de posici�n
            preparedStatement.setString(3, paciente.getHistorialMedico()); // Asigna el valor de historial al cuarto marcador de posici�n
            preparedStatement.setString(4, fecha); // Asigna el valor de fecha al cuarto marcador de posici�n
            preparedStatement.setString(5, "0"); // Asigna el valor de doctor al cuarto marcador de posici�n

            //Ejecuta la consulta y obtiene el n�mero de filas afectadas.
            int filasAfectadas = preparedStatement.executeUpdate();

            //Verifica si la inserci�n fue exitosa y muestra un mensaje apropiado.
            if (filasAfectadas > 0) {
                System.out.println("Estudiante agregado exitosamente.");
            } else {
                System.out.println("No se pudo agregar el estudiante.");
            }

            // Cierra el PreparedStatement para liberar recursos.
            preparedStatement.close();
        }catch(SQLException e){
            e.getMessage();
            e.getStackTrace();
            e.getCause();
        }
    }


    //asignar doctor de cabecera sin usando DBHelper
    public void asignarDoctorCabecera(String nombrePaciente, String nombreDoctor) {

        try{
            String url = "jdbc:mysql://localhost:3306/hospital";
            String user = "root";
            String pass = "";
            Connection conexion = DriverManager.getConnection(url, user, pass);

            String consulta = "UPDATE pacientes SET doctor = (SELECT id FROM doctores WHERE nombre = '"+nombreDoctor+"') WHERE nombre = '"+nombrePaciente+"'";

            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        }catch(SQLException e){
            e.printStackTrace();
            e.getMessage();
            e.getCause();
        }
    }

    //asignar doctor de cabecera usando DBHelper
    public void asignarDoctorCabeceraDBH(String nombrePaciente, String nombreDoctor) {

        String consulta = "UPDATE pacientes SET doctor = (SELECT id FROM doctores WHERE nombre = '"+nombreDoctor+"') WHERE nombre = '"+nombrePaciente+"'";

        DBHelper.ejecutarConsulta(consulta);
    }


    //eliminar paciente sin DBHelper
    public void eliminarPaciente(String nombrePaciente) {

        String url = "jdbc:mysql://localhost:3306/hospital";
        String user = "root";
        String pass = "";

        try{
            Connection conexion = DriverManager.getConnection(url, user, pass);

            String consulta = "DELETE FROM pacientes WHERE nombre = '" +nombrePaciente+"'";

            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        }catch(SQLException e){
            e.getMessage();
            e.getStackTrace();
            e.getCause();
        }
    }

    //eliminar paciente con DBHelper
    public void eliminarPacienteDBH(String nombrePaciente) {

        String consulta = "DELETE FROM pacientes WHERE nombre = '" +nombrePaciente+"'";

        DBHelper.ejecutarConsulta(consulta);
    }
}

class DBHelper{

    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String user = "root";
    private static final String pass = "";

    public static void ejecutarConsulta(String consulta){

        try{

            Connection conexion = DriverManager.getConnection(url, user, pass);

            PreparedStatement preparedStatement = conexion.prepareStatement(consulta);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        }catch(SQLException e){
            e.getMessage();
            e.getStackTrace();
            e.getCause();
        }
    }

    public static ResultSet ejecutarConsultaConResultado(String consulta) {
        try {
            // Establecer la conexión con la base de datos
            Connection connection = DriverManager.getConnection(url, user, pass);

            // Crear la declaración
            PreparedStatement statement = connection.prepareStatement(consulta);

            // Ejecutar la consulta y devolver el conjunto de resultados
            return statement.executeQuery();
        } catch (SQLException e) {
            e.getMessage();
            e.getStackTrace();
            e.getCause();
            return null;
        }
    }
}

abstract class Persona {
    private int id;
    private String nombre;
    private int edad;

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}

class Paciente extends Persona {
    private String historialMedico;

    public Paciente(String nombre, int edad, String historialMedico) {
        super(nombre, edad);
        this.historialMedico = historialMedico;
    }

    public String getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(String historialMedico) {
        this.historialMedico = historialMedico;
    }
}

class Doctor extends Persona {
    private String especialidad;

    public Doctor(String nombre, int edad, String especialidad) {
        super(nombre, edad);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}