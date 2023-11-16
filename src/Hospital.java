import java.util.ArrayList;
import java.util.Scanner;

public class Hospital {
    public static void main(String[] args) {

        int opcion=0, DNI, nroTelefono, tipoSangre;
        String nombre, fechaNacimiento, fechaEventos,observ;
        ListaDoctores listaDoc = new ListaDoctores();
        ListaPaciente listaPac = new ListaPaciente();
        Scanner scanner = new Scanner(System.in);

        listaDoc.agregarDoc(new Doctor("Julian",1,"21/09/2000","Neurocirugia"));
        listaDoc.agregarDoc(new Doctor("Carmen",2,"21/01/1990","Pediatra"));

        while(opcion!=8){
            System.out.println("\nHospital Julio C. Perrando - Av. 9 de Julio 1100 · 0362 444-2399");
            System.out.println("Menú: \n" +
                    "1. Listar Doctores. \n" +
                    "2. Registrar un nuevo paciente. \n" +
                    "3. Actualizar información personal de un paciente. \n" +
                    "4. Consultar el historial médico de un paciente. \n" +
                    "5. Nuevo historial para un paciente. \n" +
                    "6. Guardar Historial de pacientes en archivo \n" +
                    "7. Cargar Historial de pacientes desde archivo  \n" +
                    "8. Salir");

            opcion = scanner.nextInt();
            scanner.nextLine();
            switch(opcion){
                case 1:
                    listaDoc.mostrarInformacion();
                    break;
                case 2:
                    System.out.println("Ingrese el nombre del paciente: ");
                    nombre = scanner.nextLine();
                    System.out.println("Ahora el DNI:");
                    DNI = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Su fecha de nacimiento");
                    fechaNacimiento = scanner.nextLine();
                    System.out.println("Su número de telefono:");
                    nroTelefono = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Y su tipo de sangre:");
                    tipoSangre = scanner.nextInt();
                    scanner.nextLine();
                    listaPac.agregarPac(new Paciente(nombre,DNI,fechaNacimiento,nroTelefono,tipoSangre));
                    break;
                case 3:
                    System.out.println("Ingrese el DNI del paciente del que quiere actualizar la informacion");
                    listaPac.actualizaInfo(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case 4:
                    System.out.println("Ingrese el DNI del paciente del que quiere ver su historial");
                    (listaPac).verHistorialDeEventos(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case 5:
                    break;
            }
        }
    }
}

abstract class Persona{
    private String nombre;
    private int DNI;
    private String fechaNacimiento;

    public Persona(String nombre, int DNI, String fechaNacimiento) {
        this.nombre = nombre;
        this.DNI = DNI;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getDNI() {
        return DNI;
    }
    public void setDNI(int DNI) {
        this.DNI = DNI;
    }
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}

class Doctor extends Persona{
    private String especialidad;

    public Doctor(String nombre, int DNI, String fechaNacimiento, String especialidad) {
        super(nombre, DNI, fechaNacimiento);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}

class ListaDoctores {
    ArrayList<Doctor> listaDoctores;

    public ListaDoctores(){
        listaDoctores = new ArrayList<>();
    }
    public void mostrarInformacion() {
        System.out.println("Nombre \t\t DNI \t\t Fecha de nacimiento \t\t Especialidad");
        for (Doctor d : listaDoctores) {
            System.out.print(d.getNombre() + " \t\t " + d.getDNI() + " \t\t\t " + d.getFechaNacimiento() + " \t\t\t\t " + d.getEspecialidad());
            System.out.println("");
        }
    }

    public void agregarDoc(Doctor d){
        listaDoctores.add(d);
    }
}

interface Informacion{
    public void verHistorialDeEventos(int dni);
}

class ListaPaciente{
    private ArrayList<Paciente> listaPac;

    public ListaPaciente() {
        this.listaPac = new ArrayList <>();
    }

    public void agregarPac(Paciente pac){
        listaPac.add(pac);
    }

    public void actualizaInfo(int dni){
        Scanner scanner = new Scanner(System.in);
        for(Paciente p:listaPac){
            if(p.getDNI()== dni){
                System.out.println("Se accedio a la informacion de "+p.getNombre());
                System.out.println("Ingrese el nuevo nombre del paciente: ");
                p.setNombre(scanner.nextLine());
                System.out.println("Ahora el nuevo DNI:");
                p.setDNI(scanner.nextInt());
                scanner.nextLine();
                System.out.println("Su nueva fecha de nacimiento");
                p.setFechaNacimiento(scanner.nextLine());
                System.out.println("Su nuevo número de telefono:");
                p.setNroTelefono(scanner.nextInt());
                scanner.nextLine();
                System.out.println("Y su nuevo tipo de sangre:");
                p.setTipoSangre(scanner.nextInt());
                scanner.nextLine();
            }
        }
    }

    public void verHistorialDeEventos(int dni){
        for(Paciente p : listaPac){
            if(p.getDNI()== dni){
                p.verHistorialDeEventos(dni);
            }
        }
    }

    /*public void agregarHisto(int dni){
        Scanner scanner = new Scanner(System.in);
        String fechaEventos,observ;
        for(Paciente p : listaPac){
            if(p.getDNI()== dni){
                System.out.println("Ingrese la fecha: ");
                fechaEventos = scanner.nextLine();
                System.out.println("Ingrese la observaciono correspondiente: ");
                observ = scanner.nextLine();
                p.agregarHisto(fechaEventos,observ);
            }
        }
    }*/
}
class Paciente extends Persona implements Informacion{
    private int nroTelefono;
    private int tipoSangre;
    private ArrayList<Eventos> historialMedico;

    public Paciente(String nombre, int DNI, String fechaNacimiento, int nroTelefono, int tipoSangre) {
        super(nombre, DNI, fechaNacimiento);
        this.nroTelefono = nroTelefono;
        this.tipoSangre = tipoSangre;
        this.historialMedico = new ArrayList <>();
    }

    /*public void agregarHisto(String a,String b){
        historialMedico.add(new );
    }*/

    public void verHistorialDeEventos(int dni){
        for(Eventos e : historialMedico){
            e.mostrarInformacion();
        }
    }

    public int getNroTelefono() {
        return nroTelefono;
    }
    public void setNroTelefono(int nroTelefono) {
        this.nroTelefono = nroTelefono;
    }
    public int getTipoSangre() {
        return tipoSangre;
    }
    public void setTipoSangre(int tipoSangre) {
        this.tipoSangre = tipoSangre;
    }
}

class Eventos{
    private String fecha;
    private String observaciones;

    public Eventos(String fecha, String observaciones) {
        this.fecha = fecha;
        this.observaciones = observaciones;
    }

    public void mostrarInformacion(){
        System.out.println(this.getFecha() +" - " + this.getObservaciones());
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}