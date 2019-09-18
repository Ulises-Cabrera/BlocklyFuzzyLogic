package com.example.tutor_inteligente.components;

public enum Comando {
    CAMINAR_DERECHA("derecha"),
    CAMINAR_IZQUIERDA("izquierda"),
    SALTAR("saltar"),
    DISPARAR("disparar"),
    SUMA("suma"),
    NADA("nada");

    private String value;

    Comando(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}