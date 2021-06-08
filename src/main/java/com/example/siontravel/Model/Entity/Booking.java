package com.example.siontravel.Model.Entity;

import javax.persistence.*;
import java.io.Serializable;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String tipo_reserva;
    @OneToOne
    @JoinColumn(name = "id_from")
    private Areas areas_from;
    @OneToOne
    @JoinColumn(name = "id_from")
    private Areas areas_to;

    String fecha_salida;
    String fecha_llegada;
    int adulto;
    int infante;

    public Booking() {
    }

    public Booking(int id, String tipo_reserva, Areas areas_from, Areas areas_to, String fecha_salida, String fecha_llegada, int adulto, int infante) {
        this.id = id;
        this.tipo_reserva = tipo_reserva;
        this.areas_from = areas_from;
        this.areas_to = areas_to;
        this.fecha_salida = fecha_salida;
        this.fecha_llegada = fecha_llegada;
        this.adulto = adulto;
        this.infante = infante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo_reserva() {
        return tipo_reserva;
    }

    public void setTipo_reserva(String tipo_reserva) {
        this.tipo_reserva = tipo_reserva;
    }

    public Areas getAreas_from() {
        return areas_from;
    }

    public void setAreas_from(Areas areas_from) {
        this.areas_from = areas_from;
    }

    public Areas getAreas_to() {
        return areas_to;
    }

    public void setAreas_to(Areas areas_to) {
        this.areas_to = areas_to;
    }

    public String getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(String fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public String getFecha_llegada() {
        return fecha_llegada;
    }

    public void setFecha_llegada(String fecha_llegada) {
        this.fecha_llegada = fecha_llegada;
    }

    public int getAdulto() {
        return adulto;
    }

    public void setAdulto(int adulto) {
        this.adulto = adulto;
    }

    public int getInfante() {
        return infante;
    }

    public void setInfante(int infante) {
        this.infante = infante;
    }
}
