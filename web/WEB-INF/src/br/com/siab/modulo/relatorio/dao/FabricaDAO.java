package br.com.siab.modulo.relatorio.dao;


/**
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public class FabricaDAO {
	
	private static RelatorioDAO relatorioDAO;
    
    public static RelatorioDAO getRelatorioDAO() {
        if (relatorioDAO == null) {            
        	relatorioDAO = new RelatorioDAO();
        }
        return relatorioDAO;
    }

}
