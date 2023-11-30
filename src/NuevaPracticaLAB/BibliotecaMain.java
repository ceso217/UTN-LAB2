package NuevaPracticaLAB;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BibliotecaMain {
    public static void main(String[] args) {

        Prestamos p = new Prestamos();
        p.agregarUsuario(new Usuario("Cecilio",1));
        p.agregarUsuario(new Usuario("Santiago",2));

        Biblioteca b = new Biblioteca();
        b.agregarLibro(new Libro("Percy Jackson y el ladron del rayo","Rick Riordan",1,2012));
        b.agregarLibro(new Libro("Fahrenheit 451","Ray Bradbury",2,1985));
        b.agregarLibro(new Libro("Nacidos de la bruma: El imperio final","Brandon Sanderson",3,2004));

        b.nombreBiblioteca("C:\\Users\\baron\\Desktop\\UTN-LAB2\\src\\NuevaPracticaLAB\\biblionom.txt");

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        while(opcion != 8){
            System.out.println("Menú: \n" +
                    "1. Listar libros \n" +
                    "2. Registrar un nuevo libro \n" +
                    "3. Prestar un libro a un usuario \n" +
                    "4. Devolver un libro \n" +
                    "5. Consultar préstamos de un usuario \n" +
                    "6. Guardar información de libros en archivo\n" +
                    "7. Cargar información de libros desde archivo\n" +
                    "8. Salir");

            opcion = scanner.nextInt();
            switch(opcion){
                case 1:
                    b.mostrarInfo();
                    break;
                case 2:
                    b.agregarLibro(new Libro("Las olas","Virginia Wolf",4,1987));
                case 3:
                    p.setListaLibros(b.getListaLibros());
                    p.prestarLibro(1,1);
                    b.setListaLibros(p.getListaLibros());
                    break;
                case 4:
                    p.setListaLibros(b.getListaLibros());
                    p.devolverLibro(1,1);
                    b.setListaLibros(p.getListaLibros());
                    break;
                case 5:
                    p.consultarPrestados(1);
                    break;
                case 6:
                    b.guardarLibrosEnArchivo("C:\\Users\\baron\\Desktop\\UTN-LAB2\\src\\NuevaPracticaLAB\\libros.dat");
                    break;
                case 7:
                    b = b.cargarLibrosDesdeArchivo("C:\\Users\\baron\\Desktop\\UTN-LAB2\\src\\NuevaPracticaLAB\\libros.dat");
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

class Biblioteca implements Serializable{
    private ArrayList<Libro> listaLibros;

    public Biblioteca(){
        this.listaLibros = new ArrayList<>();
    }

    public void agregarLibro(Libro libro){
        listaLibros.add(libro);
    }

    public void mostrarInfo(){
        System.out.printf("%-40s %-20s %-5s %-20s %-12s \n","Nombre","Autor","ISBN","Año de publicación","Estado");
        for (Libro l:listaLibros) {
            System.out.printf("%-40s %-20s %-5d %-20d %-12s \n",l.getTitulo(),l.getAutor(),l.getIsbn(),l.getAnioPubli(),l.getEstado());
        }
    }

    public ArrayList<Libro> getListaLibros() {
        return listaLibros;
    }

    public void setListaLibros(ArrayList<Libro> listaLibros) {
        this.listaLibros = listaLibros;
    }

    public Libro buscarLibro(int isbn){
        for (Libro l:listaLibros) {
            if(l.getIsbn() == isbn){
                return l;
            }
        }
        return null;
    }

    public void guardarLibrosEnArchivo(String nombreArchivo) {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            salida.writeObject(this);
            System.out.println("Reservas guardadas en archivo exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar las reservas en el archivo: " + e.getMessage());
        }
    }

    public static Biblioteca cargarLibrosDesdeArchivo(String nombreArchivo) {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            return (Biblioteca) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar las reservas desde el archivo: " + e.getMessage());
            return null;
        }
    }

    public static void nombreBiblioteca(String nombreArchivo) {
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
}
class Libro implements Serializable{
    private String titulo;
    private String autor;
    private int isbn;
    private int anioPubli;
    private String estado;

    public Libro(String titulo, String autor, int isbn, int anioPubli) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.anioPubli = anioPubli;
        this.estado = "Disponible";
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public int getAnioPubli() {
        return anioPubli;
    }

    public void setAnioPubli(int anioPubli) {
        this.anioPubli = anioPubli;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

class Prestamos implements Serializable{
    ArrayList<Usuario> listaUsuarios;
    private ArrayList<Libro> listaLibros;

    public Prestamos(){
        this.listaUsuarios = new ArrayList<>();
        this.listaLibros = new ArrayList<>();
    }

    public void agregarUsuario(Usuario u){
        listaUsuarios.add(u);
    }

    public void prestarLibro(int isbn,int dni){
        for (Libro l:listaLibros) {
            if(l.getIsbn()==isbn){
                for (Usuario u:listaUsuarios) {
                    if(u.getDni()==dni){
                        u.agregarPrestado(l);
                        l.setEstado("Prestado");
                        System.out.println("El libro se ha prestado exitosamente!");
                    }
                }
            }
        }
    }

    public void devolverLibro(int isbn,int dni){
        for (Libro l:listaLibros) {
            if(l.getIsbn()==isbn){
                for (Usuario u:listaUsuarios) {
                    if(u.getDni()==dni){
                        for (Libro li:u.getListaPrestados()) {
                            if(li.getIsbn()==isbn){
                                u.eliminarPrestado(li);
                                l.setEstado("Disponible");
                                System.out.println("Se ha devuelto el libro exitosamente");
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
    }


    public void consultarPrestados(int dni){
        for (Usuario u:listaUsuarios) {
            if(u.getDni()==dni){
                System.out.println("Libros prestados a "+u.getNombre()+":");
                u.mostrarPrestados();
            }
        }
    }

    public void setListaLibros(ArrayList<Libro> listaLibros) {
        this.listaLibros = listaLibros;
    }

    public ArrayList<Libro> getListaLibros() {
        return listaLibros;
    }
}

class Usuario implements Serializable{
    private String nombre;
    private int dni;
    private static ArrayList<Libro> listaPrestados;

    public Usuario(String nombre, int dni) {
        this.nombre = nombre;
        this.dni = dni;
        this.listaPrestados = new ArrayList<>();
    }

    public void mostrarPrestados(){
        int i = 0;
        for (Libro l:listaPrestados) {
            System.out.println(l.getTitulo()+" - "+l.getAutor());
            i++;
        }
        if(i==0){
            System.out.println("No tiene ningún libro en prestamo");
        }
    }

    public void agregarPrestado(Libro l){
        listaPrestados.add(l);
    }

    public void eliminarPrestado(Libro l){
        listaPrestados.remove(l);
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

    public static ArrayList<Libro> getListaPrestados() {
        return listaPrestados;
    }
}