package com.fidness.model;
import java.io.Serializable;
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idUsuario; private String nombre; private String correo; private String contrasena;
    public Usuario(int id, String nombre, String correo, String contrasena) {
        this.idUsuario = id; this.nombre = nombre; this.correo = correo; this.contrasena = contrasena;
    }
    public int getIdUsuario(){ return idUsuario; }
    public String getNombre(){ return nombre; }
    public String getCorreo(){ return correo; }
    public String getContrasena(){ return contrasena; }
    public void setNombre(String n){ nombre = n; }
    public void setCorreo(String c){ correo = c; }
    public void setContrasena(String p){ contrasena = p; }
    public String getRol(){ return "USUARIO"; }
    public String mensajeBienvenida(){ return "Bienvenido/a " + nombre + " (" + getRol() + ")"; }
}
