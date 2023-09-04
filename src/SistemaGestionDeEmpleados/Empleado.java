package SistemaGestionDeEmpleados;

public abstract class Empleado {
    String nombre;
    int id;
    double sueldoBase;

    public Empleado(String nombre,int id,double sueldoBase){
        this.nombre=nombre;
        this.id=id;
        this.sueldoBase=sueldoBase;
    }

    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSueldoBase() {
        return sueldoBase;
    }

    public void setSueldoBase(double sueldoBase) {
        this.sueldoBase = sueldoBase;
    }

    public abstract double calcularSueldo();
}