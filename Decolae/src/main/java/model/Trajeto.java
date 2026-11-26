/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalTime;

public class Trajeto {

    private String origem;
    private String destino;
    private LocalTime horaPartida;
    private LocalTime horaChegada;

    public Trajeto() {}

    public Trajeto(String origem, String destino, LocalTime horaPartida, LocalTime horaChegada) {
        this.origem = origem;
        this.destino = destino;
        this.horaPartida = horaPartida;
        this.horaChegada = horaChegada;
    }

    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public LocalTime getHoraPartida() { return horaPartida; }
    public void setHoraPartida(LocalTime horaPartida) { this.horaPartida = horaPartida; }

    public LocalTime getHoraChegada() { return horaChegada; }
    public void setHoraChegada(LocalTime horaChegada) { this.horaChegada = horaChegada; }

    @Override
    public String toString() {
        return origem + " → " + destino + "  (" + horaPartida + " - " + horaChegada + ")";
    }
}