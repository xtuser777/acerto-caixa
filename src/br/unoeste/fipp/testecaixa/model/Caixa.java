package br.unoeste.fipp.testecaixa.model;

import br.unoeste.fipp.testecaixa.dao.CaixaDAO;
import java.sql.Connection;

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
    
    public int abre(Connection conn)
    {
        this.status = true;
        return this.id > 0 ? CaixaDAO.abre(conn, this.id) : -5;
    }
    
    public int fechar(Connection conn)
    {
        this.status = false;
        return this.id > 0 ? CaixaDAO.fechar(conn, this.id) : -5;
    }
    
    public int decrementar(Connection conn, Double valor)
    {
        if (valor == null || valor < 0 || valor > this.saldoFinal || !status) return -5;
        
        if(valor.intValue() != valor && valor.toString().replace(".","#").split("#")[1].length() > 2) return -4;
        
        this.saldoInicial = this.saldoFinal;
        this.saldoFinal = this.saldoFinal - valor;
        
        return CaixaDAO.atualizarSaldo(conn, this.id, this.saldoInicial, this.saldoFinal);
    }
    
    public int incrementar(Connection conn, Double valor)
    {
        if (valor == null || valor < 0 || !status) return -5;
        
        if(valor.intValue() != valor && valor.toString().replace(".","#").split("#")[1].length() > 2) return -4;
        
        this.saldoInicial = this.saldoFinal;
        this.saldoFinal = this.saldoFinal + valor;
        
        return CaixaDAO.atualizarSaldo(conn, this.id, this.saldoInicial, this.saldoFinal);
    }
    
    public int atualizarSaldo(Connection conn, Double valor)
    {
        if (valor == null || valor < 0 || !status) return -5;
        
        if(valor.intValue() != valor && valor.toString().replace(".","#").split("#")[1].length() > 2) return -4;
        
        this.saldoInicial = this.saldoFinal;
        this.saldoFinal = valor;
        
        return CaixaDAO.atualizarSaldo(conn, this.id, this.saldoInicial, this.saldoFinal);
    }
    
    public static Caixa getById(Connection conn, int id)
    {
        return id > 0 ? CaixaDAO.getById(conn, id) : null;
    }
}
