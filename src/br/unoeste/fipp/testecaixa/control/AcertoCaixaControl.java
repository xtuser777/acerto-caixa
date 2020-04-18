package br.unoeste.fipp.testecaixa.control;

import br.unoeste.fipp.testecaixa.model.Acerto;
import br.unoeste.fipp.testecaixa.model.Caixa;
import br.unoeste.fipp.testecaixa.model.MovimentoCaixa;
import br.unoeste.fipp.testecaixa.util.Banco;
import java.sql.SQLException;
import java.time.LocalDate;

public class AcertoCaixaControl 
{
    public String confirmar(int tipo, double valor, String motivo, int caixaId)
    {
        Caixa caixa = Caixa.getById(caixaId);
        
        Banco db = Banco.getInstance();
        if (db.getConnection() == null) return "Erro ao conectar-se ao banco de dados.";
        
        try
        {
            db.getConnection().setAutoCommit(false);
            if(!caixa.isStatus()) return "Caixa fechado.";
        
            if (tipo == 1 && caixa.getSaldoFinal() == 0) return "Saldo inicial insulficiente para decrementar.";
            if (tipo == 1 && valor > caixa.getSaldoFinal()) return "Saldo inicial insulficiente para decrementar.";

            Acerto a = new Acerto(0, LocalDate.now(), valor, tipo, motivo);
            int ra = a.salvar();
            if (ra == -10 || ra == -1 || ra == 0) 
            {
                db.getConnection().close();
                return "Problema ao executar o comando SQL de inserção do acerto.";
            }
            if (ra == -5)
            {
                db.getConnection().close();
                return "Um ou mais campos inválidos no acerto.";
            }

            MovimentoCaixa mc = new MovimentoCaixa(0, valor, tipo, caixa, a);
            int rmc = mc.salvar();
            if (rmc == -10 || rmc == -1) 
            {
                db.getConnection().rollback();
                db.getConnection().close();
                return "";
            }
            if (rmc == -5)
            {
                db.getConnection().rollback();
                db.getConnection().close();
                return "";
            }

            int rc = caixa.atualizarSaldo(valor);
            if (rmc == -10 || rmc == -1) 
            {
                db.getConnection().rollback();
                db.getConnection().close();
                return "";
            }
            if (rmc == -5)
            {
                db.getConnection().rollback();
                db.getConnection().close();
                return "";
            }

            db.getConnection().commit();
            db.getConnection().close();

            return "";
        } 
        catch(SQLException ex) 
        {
            return ex.getMessage();
        }
    }
}
