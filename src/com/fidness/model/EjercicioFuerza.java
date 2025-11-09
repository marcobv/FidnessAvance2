package com.fidness.model;
public class EjercicioFuerza extends Ejercicio {
    public EjercicioFuerza(int id, String nombre, String tipo, String descripcion, String imagenPath) {
        super(id, nombre, tipo, descripcion, imagenPath);
    }
    @Override public double caloriasEstimadas(int minutos){ return minutos * 6.5; }
}
