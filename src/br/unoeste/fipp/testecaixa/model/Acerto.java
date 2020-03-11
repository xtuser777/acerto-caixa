package br.unoeste.fipp.testecaixa.model;

import java.time.LocalDate;

public class Acerto 
{
    private int id;
    private LocalDate data;
    private double valor;
    private int tipo;
    private String motivo;

    public Acerto(int id, LocalDate data, double valor, int tipo, String motivo) 
    {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.tipo = tipo;
        this.motivo = motivo;
    }

    public int getId() 
    {
        return id;
    }

    public LocalDate getData() 
    {
        return data;
    }

    public double getValor()
    {
        return valor;
    }

    public int getTipo()
    {
        return tipo;
    }

    public String getMotivo() 
    {
        return motivo;
    }
}
