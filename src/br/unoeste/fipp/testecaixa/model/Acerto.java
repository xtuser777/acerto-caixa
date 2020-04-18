package br.unoeste.fipp.testecaixa.model;

import br.unoeste.fipp.testecaixa.dao.AcertoDAO;
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
    
    public int salvar()
    {
        if (this.id != 0 || this.data == null || this.tipo == 0 || this.valor <= 0 || this.motivo == null || this.motivo.isEmpty()) return -5;
        
        int resultado = AcertoDAO.insert(this);
        
        this.id = resultado;
        
        return resultado;
    }
}
