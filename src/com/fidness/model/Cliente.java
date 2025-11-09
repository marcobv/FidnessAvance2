package com.fidness.model;
public class Cliente extends Usuario {
    public Cliente(int id, String nombre, String correo, String contrasena) { super(id, nombre, correo, contrasena); }
    @Override public String getRol(){ return "CLIENTE"; }
}
