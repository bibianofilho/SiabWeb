package br.com.siab.modulo.municipio.service;

import java.util.List;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.biblioteca.utils.BBValueObject;

/**
 * 
 * @author Rudinick Aguiar
 * @version 1.0, 06/08/2006
 *
 */
public interface MunicipioService {
	public BBValueObject retrieveSegById(BBValueObject vo) throws BaseException;
	public List<BBValueObject> retrieveAll() throws BaseException;
}
