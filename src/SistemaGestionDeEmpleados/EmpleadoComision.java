package SistemaGestionDeEmpleados;

public class EmpleadoComision extends Empleado implements Impuesto{
    double ventasRealizadas;

    public EmpleadoComision(String nombre, int id, double sueldoBasico,double ventasRealizadas){
        super(nombre,id,sueldoBasico);
        this.ventasRealizadas=ventasRealizadas;
    }

    public double getVentasRealizadas() {
        return ventasRealizadas;
    }
    public void setVentasRealizadas(double ventasRealizadas) {
        this.ventasRealizadas = ventasRealizadas;
    }
    @Override
    public double calcularSueldo() {
     return sueldoBase+ventasRealizadas*0.10;
    }

    public double calcularImpuesto(){
        return (sueldoBase+ventasRealizadas*0.10)*0.17;
    }
}