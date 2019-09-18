package com.example.tutor_inteligente.components;

import java.util.ArrayList;

public class Nivel {

    int id;
    String descripcion;
    String tipo;
    int dificultad;
    String comprobacionSalida;
    String comprobacionCodigo;
    String helps;

    public Nivel(int id, String descripcion, String tipo, int dificultad, String comprobacionSalida, String comprobacionCodigo, String helps) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.dificultad = dificultad;
        this.comprobacionSalida = comprobacionSalida;
        this.comprobacionCodigo = comprobacionCodigo;
        this.helps = helps;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public int getDificultad() {
        return dificultad;
    }

    public String getComprobacionSalida() {
        return comprobacionSalida;
    }

    public String getComprobacionCodigo() {
        return comprobacionCodigo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    public void setComprobacionSalida(String comprobacionSalida) {
        this.comprobacionSalida = comprobacionSalida;
    }

    public void setComprobacionCodigo(String comprobacionCodigo) {
        this.comprobacionCodigo = comprobacionCodigo;
    }
}
