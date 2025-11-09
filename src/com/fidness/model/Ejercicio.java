package com.fidness.model;
import java.io.Serializable;
public class Ejercicio implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idEjercicio; private String nombre; private String tipo; private String descripcion; private String imagenPath;
    public Ejercicio(int id, String nombre, String tipo, String descripcion, String imagenPath) {
        this.idEjercicio = id; this.nombre = nombre; this.tipo = tipo; this.descripcion = descripcion; this.imagenPath = imagenPath;
    }
    public int getIdEjercicio(){ return idEjercicio; }
    public String getNombre(){ return nombre; }
    public String getTipo(){ return tipo; }
    public String getDescripcion(){ return descripcion; }
    public String getImagenPath(){ return imagenPath; }
    public void setNombre(String n){ nombre=n; } public void setTipo(String t){ tipo=t; }
    public void setDescripcion(String d){ descripcion=d; } public void setImagenPath(String p){ imagenPath=p; }
    public double caloriasEstimadas(int minutos){ return minutos * 5.0; }
    @Override public String toString(){ return nombre + " (" + tipo + ")"; }
}
