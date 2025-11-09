package com.fidness.model;
public class EjercicioCardio extends Ejercicio {
    public EjercicioCardio(int id, String nombre, String tipo, String descripcion, String imagenPath) {
        super(id, nombre, tipo, descripcion, imagenPath);
    }
    @Override public double caloriasEstimadas(int minutos){ return minutos * 8.0; }
}
