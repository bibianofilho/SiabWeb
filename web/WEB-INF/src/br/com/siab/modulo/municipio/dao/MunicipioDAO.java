package br.com.siab.modulo.municipio.dao;

import java.util.List;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.biblioteca.utils.BBValueObject;

/**
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public interface MunicipioDAO {
	public BBValueObject retrieveSegById(BBValueObject vo) throws BaseException;
	public List<BBValueObject> retrieveAll() throws BaseException;
}
