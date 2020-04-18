package br.unoeste.fipp.testecaixa.model;

import br.unoeste.fipp.testecaixa.dao.CaixaDAO;

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
    
    public void setStatus(boolean status)
    {
        this.status = status;
    }

    public double getSaldoInicial()
    {
        return saldoInicial;
    }

    public double getSaldoFinal() 
    {
        return saldoFinal;
    }
    
    public int abre()
    {
        return this.id > 0 ? CaixaDAO.abre(this.id) : -5;
    }
    
    public int fechar()
    {
        return this.id > 0 ? CaixaDAO.fechar(this.id) : -5;
    }
    
    public static Caixa getById(int id)
    {
        return id > 0 ? CaixaDAO.getById(id) : null;
    }
}
