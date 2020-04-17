package br.unoeste.fipp.testecaixa.dao;

import br.unoeste.fipp.testecaixa.model.Caixa;
import br.unoeste.fipp.testecaixa.util.Banco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CaixaDAO 
{
    public static int insert(Caixa c)
    {
        int result = -10;
        String sql = "insert into caixa(cxa_saldo_inicial,cxa_saldo_final,cxa_status) values(?,?,?) returning id;";
        Banco db = Banco.getInstance();
        try (Connection conn = db.getConnection())
        {
            if (conn != null)
            {
                try (PreparedStatement ps = conn.prepareStatement(sql))
                {
                    ps.setDouble(1, c.getSaldoInicial());
                    ps.setDouble(2, c.getSaldoFinal());
                    ps.setBoolean(3, c.isStatus());
                    
                    try(ResultSet rs = ps.executeQuery())
                    {
                        if (rs.next())
                        {
                            result = rs.getInt("id");
                        }
                    }
                }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public static int update(Caixa c)
    {
        int result = -10;
        String sql = "update caixa set cxa_saldo_inicial = ?, cxa_saldo_final = ?, cxa_status = ? where cxa_id = ?;";
        Banco db = Banco.getInstance();
        try (Connection conn = db.getConnection())
        {
            if (conn != null)
            {
                try (PreparedStatement ps = conn.prepareStatement(sql))
                {
                    ps.setDouble(1, c.getSaldoInicial());
                    ps.setDouble(2, c.getSaldoFinal());
                    ps.setBoolean(3, c.isStatus());
                    ps.setInt(4, c.getId());
                    
                    result = ps.executeUpdate();
                }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public static int delete(int id)
    {
        int result = -10;
        String sql = "delete from caixa where cxa_id = ?;";
        Banco db = Banco.getInstance();
        try (Connection conn = db.getConnection())
        {
            if (conn != null)
            {
                try (PreparedStatement ps = conn.prepareStatement(sql))
                {
                    ps.setInt(1, id);
                    
                    result = ps.executeUpdate();
                }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public static Caixa getById(int id)
    {
        Caixa c = null;
        String sql = "select * from caixa where cxa_id = ?;";
        Banco db = Banco.getInstance();
        try (Connection conn = db.getConnection())
        {
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
                            
                            conn.close();
                        }
                    }
                }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
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
