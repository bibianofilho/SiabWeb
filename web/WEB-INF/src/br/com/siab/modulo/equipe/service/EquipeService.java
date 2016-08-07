package br.com.siab.modulo.equipe.service;

import java.util.List;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.biblioteca.utils.BBValueObject;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public interface EquipeService {
	public List<BBValueObject> retrieveAll(String codSeg) throws BaseException;
	public List<BBValueObject> retrieveAllMicroa(String codSeg, String codArea) throws BaseException;
	public BBValueObject retrieveAreaById(String codSeg, String codArea) throws BaseException;
	public BBValueObject retrieveMicroaById(String codSeg, String codArea, String codMicroa) throws BaseException;
}
