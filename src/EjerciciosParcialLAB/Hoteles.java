package EjerciciosParcialLAB;


import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Hoteles {
    public static void main(String[] args) {
        int opcion = 0;

        nombreHotel("C:\\Users\\Ceso\\Desktop\\UTN-LAB2\\src\\EjerciciosParcialLAB\\nombreHotel.txt"); // conviene usar 'File.separator' en lugar de las barras para asegurarse que se ejecute correctamente en otros sistemas operativos, si se hace esto hay que asignarle la path a un String para poder concatenar el 'File.separator'

        Scanner scanner = new Scanner(System.in);
        ListaHabitaciones lista = new ListaHabitaciones();
        lista.agregarHabitacion(new Habitacion(101,5,5));
        lista.agregarHabitacion(new Habitacion(102,1,2));
        lista.agregarHabitacion(new Habitacion(103,3,5));

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
                    System.out.println("Nro de habitación \t Nro de camas \t Capacidad \t Estado \t Lista de Huespedes");
                    lista.mostrarInformacion();
                    break;
                case 2:
                    System.out.println("Seleccione la habitación que desea reservar:");
                    lista.reservarHabitacion(scanner.nextInt());
                        break;
                case 3:
                    System.out.println("Seleccione la habitación de la cual quiere cancelar la reserva:");
                    lista.cancelarHabitacion(scanner.nextInt());
                    break;
                case 4:
                    lista.guardarReservasEnArchivo("C:\\Users\\Ceso\\Desktop\\UTN-LAB2\\reservas.dat");
                    break;
                case 5:
                    lista = ListaHabitaciones.cargarReservasDesdeArchivo("C:\\Users\\Ceso\\Desktop\\UTN-LAB2\\reservas.dat");
                case 6:
                    System.out.println("Gracias por elegirnos!");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
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


class Habitacion implements mostrarInformacion,Serializable{
    private int nroHabitacion;
    private int cantCamas;
    private int capHuespedes;
    private String estado;
    private ArrayList<Huesped> listaHuespedes;

    public Habitacion(int nroHabitacion, int cantCamas, int capHuespedes) {
        this.nroHabitacion = nroHabitacion;
        this.cantCamas = cantCamas;
        this.capHuespedes = capHuespedes;
        this.estado = "libre";
        this.listaHuespedes = new ArrayList<>();
    }

    public void agregarHuesped(Huesped huesped){
        listaHuespedes.add(huesped);
    }

    public void eliminarHuespedes(){
        listaHuespedes.clear();
    }

    @Override
    public void mostrarInformacion() {
        for(Huesped h : listaHuespedes){
            h.mostrarInformacion();
        }
    }

    public int getNroHabitacion() {
        return nroHabitacion;
    }
    public void setNroHabitacion(int nroHabitacion) {
        this.nroHabitacion = nroHabitacion;
    }
    public int getCantCamas() {
        return cantCamas;
    }
    public void setCantCamas(int cantCamas) {
        this.cantCamas = cantCamas;
    }
    public int getCapHuespedes() {
        return capHuespedes;
    }
    public void setCapHuespedes(int capHuespedes) {
        this.capHuespedes = capHuespedes;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public ArrayList<Huesped> getListaHuespedes() {
        return listaHuespedes;
    }
    public void setListaHuespedes(ArrayList<Huesped> listaHuespedes) {
        this.listaHuespedes = listaHuespedes;
    }
}

class ListaHabitaciones implements mostrarInformacion, Serializable{
    ArrayList <Habitacion> listaHabitaciones;

    public ListaHabitaciones(){
        listaHabitaciones = new ArrayList<>();
    }

    public void agregarHabitacion(Habitacion habitacion){
        listaHabitaciones.add(habitacion);
    }

    public void mostrarInformacion(){
        for(Habitacion h : listaHabitaciones){
            System.out.print(h.getNroHabitacion()+" \t\t\t\t "+h.getCantCamas()+" \t\t\t\t "+h.getCapHuespedes()+" \t\t\t "+h.getEstado()+" \t ");
            h.mostrarInformacion();
            System.out.println("");
        }
    }

    public void reservarHabitacion(int nro){
        int cantH=0;
        String nom,nac;
        Scanner scanner = new Scanner(System.in);
        if(nro<101 || nro>103){
            System.out.println("No hay ninguna habitación con ese número");
        }
        for(Habitacion h : listaHabitaciones){
            if (h.getNroHabitacion()==nro){
                if(h.getEstado().equals("libre")){
                    System.out.println("Ingrese la cantidad de huespedes que quiere agregar");
                    cantH=scanner.nextInt();
                    scanner.nextLine();
                    if(cantH<=h.getCapHuespedes()){
                        h.setEstado("ocupado");
                        for(int i=0; i<cantH;i++){
                            System.out.println("Ingrese el nombre del huesped nro "+(i+1));
                            nom = scanner.nextLine();
                            System.out.println("Ahora ingrese su nacionalidad");
                            nac = scanner.nextLine();
                            h.agregarHuesped(new Huesped(nom,nac));
                        }
                    } else {
                        System.out.println("Esta habitación no posee la capacidad necesaria para alojar ese número de huespedes");
                    }
                } else {
                    System.out.println("La habitacion seleccionada no esta disponible");
                }
            }
        }
    }

    public void cancelarHabitacion(int nro){
        if(nro<101 || nro>103){
            System.out.println("No hay ninguna habitación con ese número");
        }
        for(Habitacion h : listaHabitaciones){
            if (h.getNroHabitacion()==nro){
                if(h.getEstado().equals("libre")){
                    System.out.println("Esta habitación no tiene ninguna reserva para cancelar");
                } else {
                    h.setEstado("libre");
                    h.eliminarHuespedes();
                }
            }
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
}

interface mostrarInformacion {
    public void mostrarInformacion();
}

abstract class Persona implements Serializable{
    private String nombre;
    private String nacionalidad;

    public Persona(String nombre, String nacionalidad){
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNacionalidad() {
        return nacionalidad;
    }
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
}
class Huesped extends Persona implements mostrarInformacion,Serializable{
    public Huesped(String nombre, String nacionalidad ) {
        super(nombre,nacionalidad);
    }

    public void mostrarInformacion(){
        System.out.print("||Nombre: "+this.getNombre()+", Nacionalidad: "+this.getNacionalidad()+" ||   ");
    }
}