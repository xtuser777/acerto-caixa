package br.unoeste.fipp.testecaixa.control;

import br.unoeste.fipp.testecaixa.dao.Conexao;
import br.unoeste.fipp.testecaixa.model.Caixa;
import java.sql.Connection;

public class AcertoCaixaControl 
{
    public String confirmar(int tipo, double valor, String motivo, int caixaId){
        String erro = "";
        Caixa caixa = Caixa.getById(caixaId);
        
        Connection conn  = Conexao.open();
        
        if(caixa.isStatus()){
            
            
        }
        else
            return "Caixa fechado.";
        return erro;
    }
}
