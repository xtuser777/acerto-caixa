package br.unoeste.fipp.testecaixa.dao;

import br.unoeste.fipp.testecaixa.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovimentoCaixaDAO 
{
    public static int insert(Connection conn, MovimentoCaixa c)
    {
        int result = -10;
        String sql = "insert into movimento_caixa(cxa_id,mc_valor,mc_tipo,act_id) values(?,?,?,?) returning mc_id;";
        
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
                        result = rs.getInt("mc_id");
                    }
                    rs.close();
                }
                ps.close();
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(MovimentoCaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static int update(Connection conn, MovimentoCaixa c)
    {
        int result = -10;
        String sql = "update movimento_caixa set cxa_id = ?, mc_valor = ?, mc_tipo = ?, act_id = ? where mc_id = ?;";
        
        if (conn != null)
        {
            try (PreparedStatement ps = conn.prepareStatement(sql))
            {
                ps.setInt(1, c.getCaixa().getId());
                ps.setDouble(2, c.getValor());
                ps.setInt(3, c.getTipo());
                ps.setInt(4, c.getAcerto().getId());
                ps.setInt(5, c.getId());
                result = ps.executeUpdate();
                ps.close();
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(MovimentoCaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static int delete(Connection conn, int id)
    {
        int result = -10;
        String sql = "delete from movimento_caixa where mc_id = ?;";
        
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
                Logger.getLogger(MovimentoCaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public static MovimentoCaixa getById(Connection conn, int id)
    {
        MovimentoCaixa mc = null;
        String sql = "select * from movimento_caixa where mc_id = ?;";
        
        if (conn != null)
        {
            try (PreparedStatement ps = conn.prepareStatement(sql))
            {
                ps.setInt(1, id);

                try(ResultSet rs = ps.executeQuery())
                {
                    if (rs.next())
                    {
                        mc = new MovimentoCaixa(
                            rs.getInt("mc_id"),
                            rs.getDouble("mc_valor"),
                            rs.getInt("mc_tipo"),
                            CaixaDAO.getById(conn, rs.getInt("cxa_id")),
                            AcertoDAO.getById(conn, rs.getInt("act_id"))
                        );
                    }
                    rs.close();
                }
                ps.close();
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(MovimentoCaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return mc;
    }
    
    public static List<MovimentoCaixa> getAll(Connection conn)
    {
        List<MovimentoCaixa> movimentos = null;
        String sql = "select * from movimento_caixa;";
        
        if (conn != null)
        {
            try (PreparedStatement ps = conn.prepareStatement(sql))
            {
                try(ResultSet rs = ps.executeQuery())
                {
                    movimentos = new ArrayList<>();
                    while (rs.next())
                    {
                        movimentos.add(
                            new MovimentoCaixa(
                                rs.getInt("mc_id"),
                                rs.getDouble("mc_valor"),
                                rs.getInt("mc_tipo"),
                                CaixaDAO.getById(conn, rs.getInt("cxa_id")),
                                AcertoDAO.getById(conn, rs.getInt("act_id"))
                            )
                        );
                    }
                    rs.close();
                }
                ps.close();
            }
            catch (SQLException ex) 
            {
                Logger.getLogger(MovimentoCaixaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return movimentos;
    }
}
