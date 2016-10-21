package com.unimagdalena.android.app.domiciliosmilcarnes.model.entity;

import java.io.Serializable;

public class Plate implements Serializable {

    private Integer idPlato;
    private String nombre;
    private Long precioUnitario;
    private String detalles;
    private Integer calificacion;
    private String TipoPlato;

    public Plate(Integer idPlato, String nombre, Long precioUnitario, String detalles, Integer calificacion, String tipoPlato) {
        this.idPlato = idPlato;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.detalles = detalles;
        this.calificacion = calificacion;
        TipoPlato = tipoPlato;
    }

    public Integer getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(Integer idPlato) {
        this.idPlato = idPlato;
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

    public String getTipoPlato() {
        return TipoPlato;
    }

    public void setTipoPlato(String tipoPlato) {
        TipoPlato = tipoPlato;
    }
}
