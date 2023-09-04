package SistemaGestionDeEmpleados;

public class EmpleadoAsalariado extends Empleado implements Impuesto{

    public EmpleadoAsalariado(String nombre,int id, double sueldoBase){
        super(nombre,id,sueldoBase);
    }

    @Override
    public double calcularSueldo(){
        return sueldoBase;
    }

    public double calcularImpuesto(){
        return sueldoBase*0.17;
    }
}
