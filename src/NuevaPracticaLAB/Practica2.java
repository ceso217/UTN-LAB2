package NuevaPracticaLAB;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Practica2 {
    public static void main(String[] args) {

        ListaHabitaciones lista = new ListaHabitaciones();
        lista.agregarHabitacion(new Habitacion(101,5,5));
        lista.agregarHabitacion(new Habitacion(102,1,2));
        lista.agregarHabitacion(new Habitacion(103,3,5));

        lista.nombreHotel("C:\\Users\\baron\\Desktop\\UTN-LAB2\\src\\EjerciciosParcialLAB\\nombreHotel.txt");

        //menú
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        while(opcion != 6){
            System.out.println("Menú: \n" +
                    "1. Ver la lista de las habitaciones \n" +
                    "2. Reservar una habitación \n" +
                    "3. Cancelar una reserva \n" +
                    "4. Guardar reservas en un archivo \n" +
                    "5. Cargar reservas desde un archivo \n" +
                    "6. Salir");

            opcion = scanner.nextInt();
            switch(opcion){
                case 1:
                    lista.mostrarInfo();
                    break;
                case 2:
                    lista.reservarHabitacion(102);
                    break;
                case 3:
                    lista.cancelarReserva(102);
                    break;
                case 4:
                    lista.guardarReservasEnArchivo("C:\\Users\\baron\\Desktop\\UTN-LAB2\\src\\NuevaPracticaLAB\\reservas.dat");
                    break;
                case 5:
                    lista = lista.cargarReservasDesdeArchivo("C:\\Users\\baron\\Desktop\\UTN-LAB2\\src\\NuevaPracticaLAB\\reservas.dat");
                    break;
                case 6:
                    System.out.println("Gracias por elegirnos!");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }
}

class Habitacion implements mostrarInformacion,Serializable{

    private int nroHabitacion;
    private int cantCamas;
    private int capacHuespedes;
    private String estado;
    private ArrayList<Huesped> listaHuespedes;


    public Habitacion(int nroHabitacion, int cantCamas, int capacHuespedes) {
        this.nroHabitacion = nroHabitacion;
        this.cantCamas = cantCamas;
        this.capacHuespedes = capacHuespedes;
        this.estado = "Libre";
        this.listaHuespedes = new ArrayList<>();
    }

    public void agregarHuesped(Huesped h){
        listaHuespedes.add(h);
    }

    public void eliminarHuespedes(){
        listaHuespedes.clear();
    }

    @Override
    public void mostrarInfo() {
        System.out.print(this.getNroHabitacion()+" \t\t\t\t "+this.getCantCamas()+" \t\t\t\t "+this.getCapacHuespedes()+" \t\t\t "+this.getEstado()+" \t ");
        for (Huesped h: listaHuespedes) {
            h.mostrarInfo();
        }
        System.out.println("");
    }

    public int getCantCamas() {
        return cantCamas;
    }

    public void setCantCamas(int cantCamas) {
        this.cantCamas = cantCamas;
    }

    public int getNroHabitacion() {
        return nroHabitacion;
    }

    public void setNroHabitacion(int nroHabitacion) {
        this.nroHabitacion = nroHabitacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCapacHuespedes() {
        return capacHuespedes;
    }

    public void setCapacHuespedes(int capacHuespedes) {
        this.capacHuespedes = capacHuespedes;
    }
}

class ListaHabitaciones implements mostrarInformacion,Serializable{

    private ArrayList<Habitacion> listaHabitaciones;

    public ListaHabitaciones() {
        listaHabitaciones = new ArrayList<>();
    }

    public void agregarHabitacion(Habitacion h){
        listaHabitaciones.add(h);
    }

    public void reservarHabitacion(int nroHabitacion){
        int cantH=0;
        String nom,nac;
        Scanner scanner = new Scanner(System.in);

        for (Habitacion h :listaHabitaciones) {
            if (h.getNroHabitacion()==nroHabitacion){
                if(h.getEstado()=="Libre"){
                    System.out.println("Ingrese la cantidad de huespedes que quiere agregar");
                    cantH=scanner.nextInt();
                    scanner.nextLine();
                    if(cantH<=h.getCapacHuespedes()){
                        h.setEstado("ocupado");
                        for(int i=0; i<cantH;i++){
                            System.out.println("Ingrese el nombre del huesped nro "+(i+1));
                            nom = scanner.nextLine();
                            System.out.println("Ahora ingrese su nacionalidad");
                            nac = scanner.nextLine();
                            h.agregarHuesped(new Huesped(nom,nac));
                        }
                    }
                }
            }
        }
    }

    public void cancelarReserva(int nroHabitacion){
        Scanner scanner = new Scanner(System.in);

        for (Habitacion h :listaHabitaciones) {
            if (h.getNroHabitacion()==nroHabitacion){
                if(h.getEstado()=="Libre"){
                    System.out.println("Esta habitación no esta reservada");
                } else {
                    h.setEstado("Libre");
                    h.eliminarHuespedes();
                }
            }
        }
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Nro de habitación \t Nro de camas \t Capacidad \t Estado \t Lista de Huespedes");
        for (Habitacion h:listaHabitaciones) {
            h.mostrarInfo();
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

    public static ListaHabitaciones cargarReservasDesdeArchivo(String nombreArchivo) {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            return (ListaHabitaciones) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar las reservas desde el archivo: " + e.getMessage());
            return null;
        }
    }

    public static void nombreHotel(String nombreArchivo) {
        try{
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));

            String linea;

            while((linea = br.readLine())!=null){
                System.out.println("El hotel se llama: "+linea);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

abstract class Persona implements Serializable{
    private String nombre;
    private String nac;

    public Persona(String nombre, String nac) {
        this.nombre = nombre;
        this.nac = nac;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNac() {
        return nac;
    }

    public void setNac(String nac) {
        this.nac = nac;
    }
}
class Huesped extends Persona implements mostrarInformacion,Serializable{
    public Huesped(String nombre, String nac) {
        super(nombre, nac);
    }

    @Override
    public void mostrarInfo() {
        System.out.print("||Nombre: "+this.getNombre()+" Nac: "+ this.getNac()+"|| ");
    }
}

interface mostrarInformacion{
    public void mostrarInfo();
}
