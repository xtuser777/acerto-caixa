package br.unoeste.fipp.testecaixa.dao;

import br.unoeste.fipp.testecaixa.model.*;
import br.unoeste.fipp.testecaixa.util.Banco;
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
        int result = -10;
        String sql = "insert into acerto(act_data,act_valor,act_tipo,act_motivo) values(?,?,?,?) returning act_id;";
        Banco db = Banco.getInstance();
        try (Connection conn = db.getConnection())
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
                            result = rs.getInt("act_id");
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
    
    public static int update(Acerto a)
    {
        int result = -10;
        String sql = "update acerto set act_data = ?,act_valor = ?,act_tipo = ?,act_motivo = ? where act_id = ?;";
        Banco db = Banco.getInstance();
        try (Connection conn = db.getConnection())
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
        String sql = "delete from acerto where act_id = ?;";
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
    
    public static Acerto getById(int id)
    {
        Acerto a = null;
        String sql = "select * from acerto where act_id = ?;";
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
                            a = new Acerto(
                                rs.getInt("act_id"),
                                rs.getTimestamp("act_data").toLocalDateTime().toLocalDate(),
                                rs.getDouble("act_valor"),
                                rs.getInt("act_tipo"),
                                rs.getString("act_motivo")
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
        
        return a;
    }
    
    public static List<Acerto> getAll()
    {
        List<Acerto> acertos = null;
        String sql = "select * from acerto;";
        Banco db = Banco.getInstance();
        try (Connection conn = db.getConnection())
        {
            if (conn != null)
            {
                try (PreparedStatement ps = conn.prepareStatement(sql))
                {
                    try(ResultSet rs = ps.executeQuery())
                    {
                        acertos = new ArrayList<>();
                        while (rs.next())
                        {
                            acertos.add(
                                new Acerto(
                                    rs.getInt("act_id"),
                                    rs.getTimestamp("act_data").toLocalDateTime().toLocalDate(),
                                    rs.getDouble("act_valor"),
                                    rs.getInt("act_tipo"),
                                    rs.getString("act_motivo")
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
        
        return acertos;
    }
}
