package com.fidness.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Rutina implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idRutina; private String nombreRutina; private String fechaCreacion;
    private List<Ejercicio> listaEjercicios;
    public Rutina(int id, String nombreRutina, String fechaCreacion) {
        this.idRutina = id; this.nombreRutina = nombreRutina; this.fechaCreacion = fechaCreacion; this.listaEjercicios = new ArrayList<>();
    }
    public int getIdRutina(){ return idRutina; }
    public String getNombreRutina(){ return nombreRutina; }
    public String getFechaCreacion(){ return fechaCreacion; }
    public List<Ejercicio> getListaEjercicios(){ return listaEjercicios; }
    public void agregarEjercicio(Ejercicio e){ listaEjercicios.add(e); }
    public void eliminarEjercicio(Ejercicio e){ listaEjercicios.remove(e); }
    @Override public String toString(){ return nombreRutina + " (" + listaEjercicios.size() + " ejercicios)"; }
}
