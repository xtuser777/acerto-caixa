import br.unoeste.fipp.testecaixa.model.Caixa;
import br.unoeste.fipp.testecaixa.util.Banco;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import junitparams.JUnitParamsRunner;
import junitparams.FileParameters;
import junitparams.mappers.CsvWithHeaderMapper;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 *
 * @author lucas
 */
@RunWith(JUnitParamsRunner.class)
public class AtualizarSaldoTeste {
    
    Caixa caixa;
    static Connection conn;
    
    public AtualizarSaldoTeste() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.err.println("Iniciando teste!");
        Banco bd = Banco.getInstance();
        conn = bd.getConnection();
        try {
            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(AtualizarSaldoTeste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        try {
            conn.setAutoCommit(true);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AtualizarSaldoTeste.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.err.println("Teste finalizado!");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    @FileParameters(value = "test/data.csv", mapper = CsvWithHeaderMapper.class)
    public void testAtualizarSaldo(Double valor, int idcaixa, Boolean status, int result) throws SQLException {
        caixa = Caixa.getById(conn, idcaixa);
        if(status)
            caixa.abre(conn);
        else
            caixa.fechar(conn);
        
        assertEquals(result, caixa.atualizarSaldo(conn, valor));
        
        conn.rollback();
    }
}
