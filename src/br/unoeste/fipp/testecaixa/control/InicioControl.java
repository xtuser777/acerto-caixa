package br.unoeste.fipp.testecaixa.control;

import br.unoeste.fipp.testecaixa.model.Caixa;

/**
 *
 * @author lucas
 */
public class InicioControl 
{
    public Caixa obterCaixa(int id)
    {
        Caixa c = null;
        c = Caixa.getById(id);
        
        return c;
    }
    
    public String abreCaixa(Caixa c)
    {
        if (c == null || c.getId() == 0) return "Erro interno do sistema.";
        
        int res = c.abre();
        
        switch (res)
        {
            case -10: return "Erro durante a aexecução do comando SQL, contate o suporte.";
            case -5: return "Erro de parâmetros de sistema.";
            case -1: return "Erro durante a aexecução do comando SQL, contate o suporte.";
            default: 
                c.setStatus(true); 
                return "";
        }
    }
    
    public String fecharCaixa(Caixa c)
    {
        if (c == null || c.getId() == 0) return "Erro interno do sistema.";
        
        int res = c.fechar();
        
        switch (res)
        {
            case -10: return "Erro durante a aexecução do comando SQL, contate o suporte.";
            case -5: return "Erro de parâmetros de sistema.";
            case -1: return "Erro durante a aexecução do comando SQL, contate o suporte.";
            default: 
                c.setStatus(false); 
                return "";
        }
    }
}
