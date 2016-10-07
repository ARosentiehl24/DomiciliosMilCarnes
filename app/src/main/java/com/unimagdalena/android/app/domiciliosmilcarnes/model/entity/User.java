package com.unimagdalena.android.app.domiciliosmilcarnes.model.entity;

import java.io.Serializable;

public class User implements Serializable {

    private String cedula;
    private String nombres;
    private String apellidos;
    private String correo;
    private String password;
    private String telefono;
    private String direccion;
    private String Rol;

    public User(String id, String name, String lastName, String email, String password, String phoneNumber, String address, String Rol) {
        this.cedula = id;
        this.nombres = name;
        this.apellidos = lastName;
        this.correo = email;
        this.password = password;
        this.telefono = phoneNumber;
        this.direccion = address;
        this.Rol = Rol;
    }

    public String getId() {
        return cedula;
    }

    public void setId(String id) {
        this.cedula = id;
    }

    public String getName() {
        return nombres;
    }

    public void setName(String name) {
        this.nombres = name;
    }

    public String getLastName() {
        return apellidos;
    }

    public void setLastName(String lastName) {
        this.apellidos = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return telefono;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.telefono = phoneNumber;
    }

    public String getAddress() {
        return direccion;
    }

    public void setAddress(String address) {
        direccion = address;
    }

    public String getEmail() {
        return correo;
    }

    public void setEmail(String email) {
        this.correo = email;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }
}
