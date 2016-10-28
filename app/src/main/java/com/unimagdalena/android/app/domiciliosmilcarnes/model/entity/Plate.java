package com.unimagdalena.android.app.domiciliosmilcarnes.model.entity;

import java.io.Serializable;

public class Plate implements Serializable {

    private Integer idplato;
    private String nombre;
    private Long precioUnitario;
    private String detalles;
    private Integer calificacion;
    private String tipo;

    public Plate(Integer idplato, String nombre, Long precioUnitario, String detalles, Integer calificacion, String tipo) {
        this.idplato = idplato;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.detalles = detalles;
        this.calificacion = calificacion;
        this.tipo = tipo;
    }

    public Integer getIdplato() {
        return idplato;
    }

    public void setIdplato(Integer idplato) {
        this.idplato = idplato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Long precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
