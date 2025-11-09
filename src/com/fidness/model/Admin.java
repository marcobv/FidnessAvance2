package com.fidness.model;
public class Admin extends Usuario {
    public Admin(int id, String nombre, String correo, String contrasena) { super(id, nombre, correo, contrasena); }
    @Override public String getRol(){ return "ADMIN"; }
    @Override public String mensajeBienvenida(){ return "Hola " + getNombre() + ", tienes privilegios de administración."; }
}
