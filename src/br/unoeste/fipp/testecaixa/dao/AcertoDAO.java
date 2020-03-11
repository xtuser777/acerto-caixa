package br.unoeste.fipp.testecaixa.dao;

import br.unoeste.fipp.testecaixa.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AcertoDAO
{
    public static int insert(Acerto a)
    {
        String sql = "insert into acerto(act_data,act_valor,act_tipo,act_motivo) values(?,?,?,?) returning id;";
        try (Connection conn = Conexao.open())
        {
            if (conn != null)
            {
                try (PreparedStatement ps = conn.prepareStatement(sql))
                {
                    ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.of(a.getData(), LocalTime.now())));
                    ps.setDouble(2, a.getValor());
                    ps.setInt(3, a.getTipo());
                    ps.setString(4, a.getMotivo());
                    
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
            Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -10;
    }
    
    public static int update(Acerto a)
    {
        String sql = "update acerto set act_data = ?,act_valor = ?,act_tipo = ?,act_motivo = ? where act_id = ?;";
        try (Connection conn = Conexao.open())
        {
            if (conn != null)
            {
                try (PreparedStatement ps = conn.prepareStatement(sql))
                {
                    ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.of(a.getData(), LocalTime.now())));
                    ps.setDouble(2, a.getValor());
                    ps.setInt(3, a.getTipo());
                    ps.setString(4, a.getMotivo());
                    ps.setInt(5, a.getId());
                    
                    return ps.executeUpdate();
                }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -10;
    }
    
    public static int delete(int id)
    {
        String sql = "delete from acerto where act_id = ?;";
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
            Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -10;
    }
    
    public static Acerto getById(int id)
    {
        String sql = "select * from acerto where act_id = ?;";
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
                            ? new Acerto(
                                rs.getInt("act_id"),
                                rs.getTimestamp("act_data").toLocalDateTime().toLocalDate(),
                                rs.getDouble("act_valor"),
                                rs.getInt("act_tipo"),
                                rs.getString("act_motivo")
                            )
                            : null;
                    }
                }
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static List<Acerto> getAll()
    {
        String sql = "select * from acerto;";
        try (Connection conn = Conexao.open())
        {
            if (conn != null)
            {
                try (PreparedStatement ps = conn.prepareStatement(sql))
                {
                    try(ResultSet rs = ps.executeQuery())
                    {
                        List<Acerto> list = new ArrayList<>();
                        while (rs.next())
                        {
                            list.add(
                                new Acerto(
                                    rs.getInt("act_id"),
                                    rs.getTimestamp("act_data").toLocalDateTime().toLocalDate(),
                                    rs.getDouble("act_valor"),
                                    rs.getInt("act_tipo"),
                                    rs.getString("act_motivo")
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
            Logger.getLogger(CaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
