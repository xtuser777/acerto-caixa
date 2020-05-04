package br.unoeste.fipp.testecaixa.control;

import br.unoeste.fipp.testecaixa.model.Acerto;
import br.unoeste.fipp.testecaixa.model.Caixa;
import br.unoeste.fipp.testecaixa.model.MovimentoCaixa;
import br.unoeste.fipp.testecaixa.util.Banco;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class AcertoCaixaControl 
{
    public String confirmar(int tipo, double valor, String motivo, int caixaId)
    {
        Connection conn = Banco.getInstance().getConnection();
        if (conn == null) return "Erro ao conectar-se ao banco de dados.";
        
        Caixa caixa = Caixa.getById(conn, caixaId);
        
        try
        {
            conn.setAutoCommit(false);
            if(!caixa.isStatus()) return "Caixa fechado.";
        
            if (tipo == 1 && caixa.getSaldoFinal() == 0) return "Saldo inicial insulficiente para decrementar.";
            if (tipo == 1 && valor > caixa.getSaldoFinal()) return "Saldo inicial insulficiente para decrementar.";

            Acerto a = new Acerto(0, LocalDate.now(), valor, tipo, motivo);
            int ra = a.salvar(conn);
            if (ra == -10 || ra == -1 || ra == 0) 
            {
                conn.rollback();
                conn.close();
                return "Problema ao executar o comando SQL de inserção do acerto.";
            }
            if (ra == -5)
            {
                conn.rollback();
                conn.close();
                return "Um ou mais campos inválidos no acerto.";
            }

            MovimentoCaixa mc = new MovimentoCaixa(0, valor, tipo, caixa, a);
            int rmc = mc.salvar(conn);
            if (rmc == -10 || rmc == -1) 
            {
                conn.rollback();
                conn.close();
                return "";
            }
            if (rmc == -5)
            {
                conn.rollback();
                conn.close();
                return "";
            }

            int rc = caixa.atualizarSaldo(conn, valor);
            if (rmc == -10 || rmc == -1) 
            {
                conn.rollback();
                conn.close();
                return "";
            }
            if (rmc == -5)
            {
                conn.rollback();
                conn.close();
                return "";
            }

            conn.commit();
            conn.setAutoCommit(true);
            conn.close();

            return "";
        } 
        catch(SQLException ex) 
        {
            return ex.getMessage();
        }
    }
}
