package br.com.siab.modulo.sincronismo.service;

import javax.servlet.ServletContext;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.modulo.sincronismo.dao.SincronismoDAO;
import br.com.siab.modulo.sincronismo.dao.SincronismoDAOImpl;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public class SincronismoServiceImpl implements SincronismoService{

	private static SincronismoServiceImpl instance;
	
	private SincronismoServiceImpl() {
		
	}
	
	public static SincronismoServiceImpl getInstance(){
		if(instance == null){
			instance = new SincronismoServiceImpl();
		}
		return instance;
	}
	
	public void realizarSincronismo(String ano,ServletContext context) throws BaseException {
		SincronismoDAO importacaoDAO = SincronismoDAOImpl.getInstance();
		importacaoDAO.realizarSincronismo(ano,context);
		
	}

}
