package Parcial2LAB;

import java.sql.*;
import java.util.ArrayList;

public class SistemaDeVentas {
    public static void main(String[] args) {

        Comerciales comerciales = new Comerciales();

        Vendedor vendedorBuscado = new Vendedor("SELECT * FROM vendedores WHERE vendedor_id = 3");

        //System.out.println(vendedorBuscado.getNombre());

        //Producto producto = Producto.obtenerProducto(2);

        //System.out.println(Producto.obtenerProducto(2).getNombre());

        //Vendedor vendedor = Comerciales.obtenerVendedorPorID(2);

        //System.out.println(Comerciales.obtenerVendedorPorID(2).getNombre());

        //Comerciales.generarInforme();

        //System.out.println(Producto.obtenerProductoMasVendido().getNombre());

        /*for (int i = 0; i < comerciales.listadoDeVendedores().size(); i++) {
            System.out.print(comerciales.listadoDeVendedores().get(i));
            if (i < comerciales.listadoDeVendedores().size() - 1) {
                System.out.print(", ");
            }
        }*/
        //comerciales.listadoDeVendedores();
    }
}

class DBHelper{

    private static final String url = "jdbc:mysql://localhost:3306/ventas";
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

class Producto {
    int producto_id;
    String nombre;
    float precio_por_unidad;
    int stock;

    public Producto(int id, String nombre, float precio_por_unidad, int stock) {
        this.producto_id = id;
        this.nombre = nombre;
        this.precio_por_unidad = precio_por_unidad;
        this.stock = stock;
    }

    public int getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio_por_unidad() {
        return precio_por_unidad;
    }

    public void setPrecio_por_unidad(float precio_por_unidad) {
        this.precio_por_unidad = precio_por_unidad;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "producto_id=" + producto_id +
                ", nombre='" + nombre + '\'' +
                ", precio_por_unidad=" + precio_por_unidad +
                ", stock=" + stock +
                '}';
    }

    public static Producto obtenerProducto(int productoID) {

        String consulta = "SELECT * FROM productos WHERE producto_id ="+productoID;

        try (ResultSet resultado = DBHelper.ejecutarConsultaConResultado(consulta)) {

            if (resultado != null && resultado.next()) {
                int id = resultado.getInt("producto_id");
                String nombre = resultado.getString("nombre");
                float precio = resultado.getFloat("precio_por_unidad");
                int stock = resultado.getInt("stock");

                Producto producto = new Producto(id, nombre, precio, stock);

                return producto;
            }

        } catch (SQLException e) {
            e.getStackTrace();
        }
        return null;
    }

    public static Producto obtenerProductoMasVendido() {

        String consulta = "SELECT producto_id, SUM(cantidad_vendida) as total_vendido\n" +
                "FROM ventas\n" +
                "GROUP BY producto_id\n" +
                "ORDER BY total_vendido DESC\n" +
                "LIMIT 1\n" +
                "\n";

        try (ResultSet resultadoVentas = DBHelper.ejecutarConsultaConResultado(consulta)) {
            if (resultadoVentas != null && resultadoVentas.next()) {
                int autoId = resultadoVentas.getInt("producto_id");
                return obtenerProducto(autoId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}



class Vendedor{
    int vendedor_id;
    String nombre;
    String apellido;
    int dni;
    String fecha_nacimiento;
    String fecha_contratacion;

    public Vendedor(int vendedor_id,String nombre, String apellido, int dni, String fecha_nacimiento, String fecha_contratacion) {
        this.vendedor_id = vendedor_id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fecha_nacimiento = fecha_nacimiento;
        this.fecha_contratacion = fecha_contratacion;
    }

    public int getVendedor_id() {
        return vendedor_id;
    }

    public void setVendedor_id(int vendedor_id) {
        this.vendedor_id = vendedor_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getFecha_contratacion() {
        return fecha_contratacion;
    }

    public void setFecha_contratacion(String fecha_contratacion) {
        this.fecha_contratacion = fecha_contratacion;
    }

    public Vendedor(String consultaBusqueda) {
        try(ResultSet resultado = DBHelper.ejecutarConsultaConResultado(consultaBusqueda)){
            if (resultado != null && resultado.next()) {
                this.nombre = resultado.getString("nombre");
                this.apellido = resultado.getString("apellido");
                this.dni = resultado.getInt("dni");
                this.fecha_nacimiento = resultado.getString("fecha_nacimiento");
                this.fecha_contratacion = resultado.getString("fecha_contratacion");
            }

        }catch(SQLException e){
            e.getStackTrace();
            e.getMessage();
        }
    }


    @Override
    public String toString() {
        return "Vendedor{" +
                "vendedor_id=" + vendedor_id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni=" + dni +
                ", fecha_nacimiento='" + fecha_nacimiento + '\'' +
                ", fecha_contratacion='" + fecha_contratacion + '\'' +
                '}';
    }
}

class Comerciales{

    public static Vendedor obtenerVendedorPorID(int vendedorID) {

        /*int id=0;
        String nombre="";
        String apellido="";
        int dni=0;
        String fecha_nacimiento="";
        String fecha_contratacion="";*/

        String consulta = "SELECT * FROM vendedores WHERE vendedor_id = "+vendedorID;


            try( ResultSet resultado = DBHelper.ejecutarConsultaConResultado(consulta)){
                if (resultado != null && resultado.next()) {
                int id = resultado.getInt("vendedor_id");
                String nombre = resultado.getString("nombre");
                String apellido = resultado.getString("apellido");
                int dni = resultado.getInt("dni");
                String fecha_nacimiento = resultado.getString("fecha_nacimiento");
                String fecha_contratacion = resultado.getString("fecha_contratacion");

                return new Vendedor(id,nombre,apellido,dni,fecha_nacimiento,fecha_contratacion);
                }

            }catch(SQLException e) {
                e.getStackTrace();
                e.getMessage();
                e.getCause();
            }
        return null;
    }


    public static void generarInforme(){

        String consulta = "SELECT * FROM productos";

        ResultSet resultado = DBHelper.ejecutarConsultaConResultado(consulta);

        System.out.println("Informe de Productos en Stock:\n" +
                "Producto                        Stock    Precio     Total");

        if (resultado != null) {
            try{
                while(resultado.next()){
                    String nombre = resultado.getString("nombre");
                    float precio = resultado.getFloat("precio_por_unidad");
                    int stock = resultado.getInt("stock");

                    float valor = precio*stock;
                    System.out.println(nombre+"      "+stock+"         "+precio+"          "+valor);
                }

            }catch (SQLException e){
                e.getStackTrace();
                e.getMessage();
                e.getCause();
            }
        }
    }

    public static ArrayList<Vendedor> listadoDeVendedores() {
        ArrayList<Vendedor> vendedores = new ArrayList<>();
        String consulta = "SELECT * FROM vendedores";

        try (ResultSet resultado = DBHelper.ejecutarConsultaConResultado(consulta)) {
            while (resultado != null && resultado.next()) {

                int id = resultado.getInt("vendedor_id");
                String nombre = resultado.getString("nombre");
                String apellido = resultado.getString("apellido");
                int dni = resultado.getInt("dni");
                String fecha_nacimiento = resultado.getString("fecha_nacimiento");
                String fecha_contratacion = resultado.getString("fecha_contratacion");

                Vendedor vendedor = new Vendedor(id, nombre, apellido, dni, fecha_nacimiento, fecha_contratacion);
                vendedores.add(vendedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
            e.getMessage();
        }

        return vendedores;
    }
}


/*
int id=0;
        String nombre="";
        String apellido="";
        int dni=0;
        String fecha_nacimiento="";
        String fecha_contratacion="";

try{
                id = resultado.getInt("vendedor_id");
                nombre = resultado.getString("nombre");
                apellido = resultado.getString("apellido");
                dni = resultado.getInt("dni");
                fecha_nacimiento = resultado.getString("fecha_nacimiento");
                fecha_contratacion = resultado.getString("fecha_contratacion");

        }catch(SQLException e){
            e.getStackTrace();
            e.getMessage();
            e.getCause();
        }
        System.out.printf("%-2d%-10s%-10s%-11d%-13s%-13s\n", "ID de Vendedor", "Nombre", "Apellido", "DNI", "Fecha de nacimiento", "Fecha de contratacion");
        System.out.printf("%-2d%-10s%-10s%-11d%-13s%-13s\n",id,nombre,apellido,dni,fecha_nacimiento,fecha_contratacion);
 */