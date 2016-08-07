package br.com.siab.modulo.relatorio.service;

import java.sql.Connection;

import br.com.siab.modulo.relatorio.dao.FabricaDAO;
import br.com.siab.modulo.relatorio.dao.RelatorioDAO;

/**
 * Classe de negocio do modulo de relatório
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public class RelatorioService {
	public Connection getCurrentConnection() throws Exception{
		RelatorioDAO relatorioDAO = FabricaDAO.getRelatorioDAO();
		return relatorioDAO.getCurrentConnection();
	}
	
	public void closeCurrentConnection( Connection conn ) throws Exception {
		RelatorioDAO relatorioDAO = FabricaDAO.getRelatorioDAO();
		relatorioDAO.closeCurrentConnection( conn );		
	}
}
