package br.com.siab.modulo.sincronismo;

import javax.servlet.ServletContext;

import br.com.siab.biblioteca.exception.BaseException;

public interface Sincronismo {
	public void realizarSincronismo(String ano,ServletContext context) throws BaseException;
}
