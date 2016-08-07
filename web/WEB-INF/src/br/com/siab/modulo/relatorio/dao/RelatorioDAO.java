package br.com.siab.modulo.relatorio.dao;

import java.sql.Connection;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.biblioteca.persistence.BaseDAO;

/**
 * Classe dao do modulo de relatório
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public class RelatorioDAO extends BaseDAO {
	public Connection getCurrentConnection() throws BaseException {
		Connection connection = null;
		try {
			connection = getConnection();
			return connection;

		} catch (Exception e) {
			throw new BaseException(e);
		} 
	}
	
	public void closeCurrentConnection( Connection conn ) throws BaseException {		
		try {
			closeConnection( conn );					
		} catch (Exception e) {
			throw new BaseException(e);
		} 
	}
}
