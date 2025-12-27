package com.mycompany.gestion.juegos.model.enums;

public enum TipoMultimedia {
    imagen,
    video;
    
    public static TipoMultimedia from(String value) {
        return TipoMultimedia.valueOf(value.toLowerCase());
    }
}
