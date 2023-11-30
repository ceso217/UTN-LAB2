package NuevaPracticaLAB.Hoteles;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ListaDoctores d = new ListaDoctores();
        d.agregarDocotr(new Doctor("Carlos",1, "21-09-1998","Traumatologo"));
        d.agregarDocotr(new Doctor("Juan",2, "12-09-1908","Gine"));
        d.agregarDocotr(new Doctor("Gab",3, "02-12-2015","Fium"));

        Hospital h = new Hospital();
        h.datosHotel("C:\\Users\\baron\\Desktop\\UTN-LAB2\\src\\NuevaPracticaLAB\\Hoteles\\datos.txt");

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        while(opcion != 8){
            System.out.println("Menú: \n" +
                    "1. Listar doctores \n" +
                    "2. Registrar nuevo paciente \n" +
                    "3. Actualizar info de un paciente \n" +
                    "4. Consultar historial medico de paciente \n" +
                    "5. Nuevo historial para paciente \n" +
                    "6. Guardar Historial de pacientes en archivo\n" +
                    "7. Cargar Historial de pacientes desde archivo\n" +
                    "8. Salir");

            opcion = scanner.nextInt();
            switch(opcion){
                case 1:
                    d.mostrarInfo();
                    break;
                case 2:
                    h.registrarPaciente(new Paciente("Pablo",1,"21-09-2000",123,1));
                    h.registrarPaciente(new Paciente("Jorge",2,"21-09-2000",321,2));
                    h.mostrarNombre();
                    break;
                case 3:
                    h.actualizarInfo(1);
                    h.mostrarNombre();
                    break;
                case 4:
                    h.verHistorial(1);
                    break;
                case 5:
                    h.cargarHistorialMedico(1);
                    break;
                case 6:
                    h.guardarReservasEnArchivo("C:\\Users\\baron\\Desktop\\UTN-LAB2\\src\\NuevaPracticaLAB\\Hoteles\\pacientes.dat");
                    break;
                case 7:
                    h = h.cargarReservasDesdeArchivo("C:\\Users\\baron\\Desktop\\UTN-LAB2\\src\\NuevaPracticaLAB\\Hoteles\\pacientes.dat");
                    break;
                case 8:
                    System.out.println("Gracias por elegirnos!");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }
}

class Hospital implements Serializable{
    ArrayList<Paciente> listaPacientes;

    public Hospital() {
        this.listaPacientes = new ArrayList<>();
    }

    public void registrarPaciente(Paciente p){
        listaPacientes.add(p);
    }

    public void actualizarInfo(int dni){
        for (Paciente h: listaPacientes) {
            if(h.getDni()==dni){
                h.setNombre("Messi");
                h.setSangre(0);
            }
        }
    }

    public void mostrarNombre(){
        System.out.println("Nombre pacientes");
        for (Paciente p:listaPacientes) {
            System.out.println(p.getNombre());
        }
    }

    public void cargarHistorialMedico(int dni){
        for (Paciente p:listaPacientes) {
            if(p.getDni()==dni){
                p.agregarEvento(new Evento("21-4-2000","Notables mejoras"));
                p.agregarEvento(new Evento("22-4-2000","Se murió"));
            }
        }
    }

    public void verHistorial(int dni){
        for (Paciente p:listaPacientes) {
            if(p.getDni()==dni){
                System.out.println("Historial de "+p.getNombre());
                p.verHistorialDeEventos();
            }
        }
    }

    public void datosHotel(String nombreArchivo) {
        try{
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));

            String linea;

            while((linea = br.readLine())!=null){
                System.out.println(linea);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardarReservasEnArchivo(String nombreArchivo) {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            salida.writeObject(this);
            System.out.println("Reservas guardadas en archivo exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar las reservas en el archivo: " + e.getMessage());
        }
    }

    public static Hospital cargarReservasDesdeArchivo(String nombreArchivo) {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            return (Hospital) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar las reservas desde el archivo: " + e.getMessage());
            return null;
        }
    }
}

abstract class Persona implements Serializable {
    private String nombre;
    private int dni;
    private String fechaN;

    public Persona(String nombre, int dni, String fechaN) {
        this.nombre = nombre;
        this.dni = dni;
        this.fechaN = fechaN;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getFechaN() {
        return fechaN;
    }

    public void setFechaN(String fechaN) {
        this.fechaN = fechaN;
    }
}

class Paciente extends Persona implements Informacion,Serializable{
    private int nroTeléfono;
    private int sangre;
    ArrayList<Evento> historialMedico;

    public Paciente(String nombre, int dni, String fechaN, int nroTeléfono, int sangre) {
        super(nombre, dni, fechaN);
        this.nroTeléfono = nroTeléfono;
        this.sangre = sangre;
        this.historialMedico = new ArrayList<>();
    }

    public void agregarEvento(Evento e){
        historialMedico.add(e);
    }

    @Override
    public void verHistorialDeEventos() {
        for (Evento e:historialMedico) {
            System.out.println(e.getFecha()+"\t\t\t"+e.getObserv());
        }
    }

    public int getNroTeléfono() {
        return nroTeléfono;
    }

    public void setNroTeléfono(int nroTeléfono) {
        this.nroTeléfono = nroTeléfono;
    }

    public int getSangre() {
        return sangre;
    }

    public void setSangre(int sangre) {
        this.sangre = sangre;
    }
}

class Evento implements Serializable{
    private String fecha;
    private String observ;

    public Evento(String fecha, String observ) {
        this.fecha = fecha;
        this.observ = observ;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObserv() {
        return observ;
    }

    public void setObserv(String observ) {
        this.observ = observ;
    }
}

interface Informacion{
    public void verHistorialDeEventos();
}
class Doctor extends Persona implements Serializable{
    private String especialidad;

    public Doctor(String nombre, int dni, String fechaN, String especialidad) {
        super(nombre, dni, fechaN);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}

class ListaDoctores implements Serializable{
    ArrayList<Doctor> listaDoctores;

    public ListaDoctores() {
        this.listaDoctores = new ArrayList<>();
    }

    public void agregarDocotr(Doctor d){
        listaDoctores.add(d);
    }

    public void mostrarInfo(){
        System.out.println("Nombre\t\t\tDNI\t\t\tFecha de Nacimeitno\t\t\tEspecialidad");
        for (Doctor d:listaDoctores) {
            System.out.println(d.getNombre()+"\t\t\t"+d.getDni()+"\t\t\t"+d.getFechaN()+"\t\t\t"+d.getEspecialidad());
        }
    }
}
