package br.unoeste.fipp.testecaixa.model;

public class MovimentoCaixa 
{
    private int id;
    private double valor;
    private int tipo;
    private Caixa caixa;
    private Acerto acerto;

    public MovimentoCaixa(int id, double valor, int tipo, Caixa caixa, Acerto acerto) 
    {
        this.id = id;
        this.valor = valor;
        this.tipo = tipo;
        this.caixa = caixa;
        this.acerto = acerto;
    }

    public int getId()
    {
        return id;
    }

    public double getValor() 
    {
        return valor;
    }

    public int getTipo() 
    {
        return tipo;
    }

    public Caixa getCaixa() 
    {
        return caixa;
    }

    public Acerto getAcerto()
    {
        return acerto;
    }
}
