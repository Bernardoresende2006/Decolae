/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Voo {

    private int numVoo;
    private String admCpf;
    private int idAeronave;
    private LocalDate data;
    private int assentosDisponiveis;
    private String origem;
    private String destino;
    private LocalTime horaPartida;
    private LocalTime horaChegada;

    private String pilotoCpf;
    private String copilotoCpf;
    private String comissario1Cpf;
    private String comissario2Cpf;

    public Voo() {}


    public int getNumVoo() { return numVoo; }
    public void setNumVoo(int numVoo) { this.numVoo = numVoo; }

    public String getAdmCpf() { return admCpf; }
    public void setAdmCpf(String admCpf) { this.admCpf = admCpf; }

    public int getIdAeronave() { return idAeronave; }
    public void setIdAeronave(int idAeronave) { this.idAeronave = idAeronave; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public int getAssentosDisponiveis() { return assentosDisponiveis; }
    public void setAssentosDisponiveis(int assentosDisponiveis) { this.assentosDisponiveis = assentosDisponiveis; }

    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public LocalTime getHoraPartida() { return horaPartida; }
    public void setHoraPartida(LocalTime horaPartida) { this.horaPartida = horaPartida; }

    public LocalTime getHoraChegada() { return horaChegada; }
    public void setHoraChegada(LocalTime horaChegada) { this.horaChegada = horaChegada; }

    public String getPilotoCpf() { return pilotoCpf; }
    public void setPilotoCpf(String pilotoCpf) { this.pilotoCpf = pilotoCpf; }

    public String getCopilotoCpf() { return copilotoCpf; }
    public void setCopilotoCpf(String copilotoCpf) { this.copilotoCpf = copilotoCpf; }

    public String getComissario1Cpf() { return comissario1Cpf; }
    public void setComissario1Cpf(String comissario1Cpf) { this.comissario1Cpf = comissario1Cpf; }

    public String getComissario2Cpf() { return comissario2Cpf; }
    public void setComissario2Cpf(String comissario2Cpf) { this.comissario2Cpf = comissario2Cpf; }
}
