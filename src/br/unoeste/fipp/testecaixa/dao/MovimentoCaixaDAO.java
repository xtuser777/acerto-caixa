package br.unoeste.fipp.testecaixa.dao;

import br.unoeste.fipp.testecaixa.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovimentoCaixaDAO 
{
    public static int insert(MovimentoCaixa c)
    {
        String sql = "insert into movimento_caixa(cxa_id,mc_valor,mc_tipo,act_id) values(?,?,?,?) returning id;";
        try (Connection conn = Conexao.open())
        {
            if (conn != null)
            {
                try (PreparedStatement ps = conn.prepareStatement(sql))
                {
                    ps.setInt(1, c.getCaixa().getId());
                    ps.setDouble(2, c.getValor());
                    ps.setInt(3, c.getTipo());
                    ps.setInt(4, c.getAcerto().getId());
                    
                    try(ResultSet rs = ps.executeQuery())
                    {
                        if (rs.next())
                        {
                            return rs.getInt("id");
                        }
                        
                        return -10;
                    }
                }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(MovimentoCaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -10;
    }
    
    public static int update(MovimentoCaixa c)
    {
        String sql = "update movimento_caixa set cxa_id = ?, mc_valor = ?, mc_tipo = ?, act_id = ? where mc_id = ?;";
        try (Connection conn = Conexao.open())
        {
            if (conn != null)
            {
                try (PreparedStatement ps = conn.prepareStatement(sql))
                {
                    ps.setInt(1, c.getCaixa().getId());
                    ps.setDouble(2, c.getValor());
                    ps.setInt(3, c.getTipo());
                    ps.setInt(4, c.getAcerto().getId());
                    ps.setInt(5, c.getId());
                    
                    return ps.executeUpdate();
                }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(MovimentoCaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -10;
    }
    
    public static int delete(int id)
    {
        String sql = "delete from movimento_caixa where mc_id = ?;";
        try (Connection conn = Conexao.open())
        {
            if (conn != null)
            {
                try (PreparedStatement ps = conn.prepareStatement(sql))
                {
                    ps.setInt(1, id);
                    
                    return ps.executeUpdate();
                }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(MovimentoCaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -10;
    }
    
    public static MovimentoCaixa getById(int id)
    {
        String sql = "select * from movimento_caixa where mc_id = ?;";
        try (Connection conn = Conexao.open())
        {
            if (conn != null)
            {
                try (PreparedStatement ps = conn.prepareStatement(sql))
                {
                    ps.setInt(1, id);
                    
                    try(ResultSet rs = ps.executeQuery())
                    {
                        return rs.next() 
                            ? new MovimentoCaixa(
                                rs.getInt("mc_id"),
                                rs.getDouble("mc_valor"),
                                rs.getInt("mc_tipo"),
                                CaixaDAO.getById(rs.getInt("cxa_id")),
                                AcertoDAO.getById(rs.getInt("act_id"))
                            )
                            : null;
                    }
                }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(MovimentoCaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static List<MovimentoCaixa> getAll()
    {
        String sql = "select * from movimento_caixa;";
        try (Connection conn = Conexao.open())
        {
            if (conn != null)
            {
                try (PreparedStatement ps = conn.prepareStatement(sql))
                {
                    try(ResultSet rs = ps.executeQuery())
                    {
                        List<MovimentoCaixa> list = new ArrayList<>();
                        while (rs.next())
                        {
                            list.add(
                                new MovimentoCaixa(
                                    rs.getInt("mc_id"),
                                    rs.getDouble("mc_valor"),
                                    rs.getInt("mc_tipo"),
                                    CaixaDAO.getById(rs.getInt("cxa_id")),
                                    AcertoDAO.getById(rs.getInt("act_id"))
                                )
                            );
                        }
                        
                        return list;
                    }
                }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(MovimentoCaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
