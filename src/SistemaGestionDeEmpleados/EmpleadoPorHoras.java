package SistemaGestionDeEmpleados;

public class EmpleadoPorHoras extends Empleado implements Impuesto{
    int horasTrabajadas;

    public EmpleadoPorHoras(String nombre, int id, double sueldoBasico,int horasTrabajadas){
        super(nombre,id,sueldoBasico);
        this.horasTrabajadas=horasTrabajadas;
    }

    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }
    public void setHorasTrabajadas(int horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    @Override
    public double calcularSueldo() {
        return sueldoBase*horasTrabajadas;
    }

    public double calcularImpuesto(){
        return (sueldoBase*horasTrabajadas)*0.17;
    }
}
