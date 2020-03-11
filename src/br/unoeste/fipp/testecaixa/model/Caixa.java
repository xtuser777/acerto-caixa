package br.unoeste.fipp.testecaixa.model;

public class Caixa
{
    private int id;
    private boolean status;
    private double saldoInicial;
    private double saldoFinal;

    public Caixa(int id, boolean status, double saldoInicial, double saldoFinal) 
    {
        this.id = id;
        this.status = status;
        this.saldoInicial = saldoInicial;
        this.saldoFinal = saldoFinal;
    }

    public int getId()
    {
        return id;
    }

    public boolean isStatus()
    {
        return status;
    }

    public double getSaldoInicial()
    {
        return saldoInicial;
    }

    public double getSaldoFinal() 
    {
        return saldoFinal;
    }
}
