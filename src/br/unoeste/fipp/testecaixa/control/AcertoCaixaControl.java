package br.unoeste.fipp.testecaixa.control;

import br.unoeste.fipp.testecaixa.model.Caixa;
import br.unoeste.fipp.testecaixa.util.Banco;
import java.sql.SQLException;



public class AcertoCaixaControl 
{
    public String confirmar(int tipo, double valor, String motivo, int caixaId){
        String erro = "";
        Caixa caixa = Caixa.getById(caixaId);
        
        Banco db = Banco.getInstance();
        if (db.getConnection() == null) return "Erro ao conectar-se ao banco de dados.";
        
        try{db.getConnection().setAutoCommit(false);} catch(SQLException ex) {ex.printStackTrace();}
        
        if(caixa.isStatus())
        {
            
        }
        else return "Caixa fechado.";
        
        return erro;
    }
}
