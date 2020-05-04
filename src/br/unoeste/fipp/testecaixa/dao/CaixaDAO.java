package br.unoeste.fipp.testecaixa.dao;

import br.unoeste.fipp.testecaixa.model.Caixa;
import br.unoeste.fipp.testecaixa.util.Banco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CaixaDAO 
{
    public static int insert(Connection conn, double sinicial, double sfinal, boolean status)
    {
        int result = -10;
        String sql = "insert into caixa(cxa_saldo_inicial,cxa_saldo_final,cxa_status) values(?,?,?) returning cxa_id;";
        
        if (conn != null)
        {
            try (PreparedStatement ps = conn.prepareStatement(sql))
            {
                ps.setDouble(1, sinicial);
                ps.setDouble(2, sfinal);
                ps.setBoolean(3, status);

                try(ResultSet rs = ps.executeQuery())
                {
                    if (rs.next())
                    {
                        result = rs.getInt("cxa_id");
                    }
                    rs.close();
                }
                ps.close();
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static int update(Connection conn, int id, double sinicial, double sfinal, boolean status)
    {
        int result = -10;
        String sql = "update caixa set cxa_saldo_inicial = ?, cxa_saldo_final = ?, cxa_status = ? where cxa_id = ?;";
        
        if (conn != null)
        {
            try (PreparedStatement ps = conn.prepareStatement(sql))
            {
                ps.setDouble(1, sinicial);
                ps.setDouble(2, sfinal);
                ps.setBoolean(3, status);
                ps.setInt(4, id);
                result = ps.executeUpdate();
                ps.close();
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static int delete(Connection conn, int id)
    {
        int result = -10;
        String sql = "delete from caixa where cxa_id = ?;";
        
        if (conn != null)
        {
            try (PreparedStatement ps = conn.prepareStatement(sql))
            {
                ps.setInt(1, id);
                result = ps.executeUpdate();
                ps.close();
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static int atualizarSaldo(Connection conn, int id, double inicial, double finaL)
    {
        int result = -10;
        String sql = "update caixa set cxa_saldo_inicial = ?, cxa_saldo_final = ? where cxa_id = ?;";
        if (conn != null)
        {
            try (PreparedStatement ps = conn.prepareStatement(sql))
            {
                ps.setDouble(1, inicial);
                ps.setDouble(2, finaL);
                ps.setInt(3, id);
                result = ps.executeUpdate();
                ps.close();
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static int abre(Connection conn, int id)
    {
        int result = -10;
        String sql = "update caixa set cxa_status = true where cxa_id = ?;";
        if (conn != null)
        {
            try (PreparedStatement ps = conn.prepareStatement(sql))
            {
                ps.setInt(1, id);

                result = ps.executeUpdate();
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        
        return result;
    }
    
    public static int fechar(Connection conn, int id)
    {
        int result = -10;
        String sql = "update caixa set cxa_status = false where cxa_id = ?;";
        if (conn != null)
        {
            try (PreparedStatement ps = conn.prepareStatement(sql))
            {
                ps.setInt(1, id);

                result = ps.executeUpdate();
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        
        return result;
    }
    
    public static Caixa getById(Connection conn, int id)
    {
        Caixa c = null;
        String sql = "select * from caixa where cxa_id = ?;";
        if (conn != null)
        {
            try (PreparedStatement ps = conn.prepareStatement(sql))
            {
                ps.setInt(1, id);

                try(ResultSet rs = ps.executeQuery())
                {
                    if (rs.next())
                    {
                        c = new Caixa(
                            rs.getInt("cxa_id"),
                            rs.getBoolean("cxa_status"),
                            rs.getDouble("cxa_saldo_inicial"),
                            rs.getDouble("cxa_saldo_final")                                
                        );

                        //conn.close();
                    }
                }
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return c;
    }
    
    public static List<Caixa> getAll()
    {
        List<Caixa> caixas = null;
        String sql = "select * from caixa;";
        Banco db = Banco.getInstance();
        try (Connection conn = db.getConnection())
        {
            if (conn != null)
            {
                try (PreparedStatement ps = conn.prepareStatement(sql))
                {
                    try(ResultSet rs = ps.executeQuery())
                    {
                        caixas = new ArrayList<>();
                        while (rs.next())
                        {
                            caixas.add(
                                new Caixa(
                                    rs.getInt("cxa_id"),
                                    rs.getBoolean("cxa_status"),
                                    rs.getDouble("cxa_saldo_inicial"),
                                    rs.getDouble("cxa_saldo_final")
                                )
                            );
                        }
                        
                        conn.close();
                    }
                }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return caixas;
    }
}
