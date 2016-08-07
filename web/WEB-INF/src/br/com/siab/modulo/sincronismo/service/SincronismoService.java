package br.com.siab.modulo.sincronismo.service;

import javax.servlet.ServletContext;

import br.com.siab.biblioteca.exception.BaseException;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public interface SincronismoService {
	public void realizarSincronismo(String ano,ServletContext context) throws BaseException;
}
