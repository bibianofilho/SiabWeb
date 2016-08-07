package br.com.siab.modulo.equipe.service;

import java.util.List;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.biblioteca.utils.BBValueObject;
import br.com.siab.modulo.equipe.dao.EquipeDAO;
import br.com.siab.modulo.equipe.dao.EquipeDAOImpl;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public class EquipeServiceImpl implements EquipeService{
	
	private static EquipeServiceImpl instance;
	
	private EquipeServiceImpl() { }
	
	public static EquipeServiceImpl getInstance(){
		if(instance == null){
			instance = new EquipeServiceImpl();
		}
		
		return instance;
	}

	public List<BBValueObject> retrieveAll(String codSeg) throws BaseException {
		EquipeDAO dao = EquipeDAOImpl.getInstance();
		return dao.retrieveAll(codSeg);
	}

	public BBValueObject retrieveAreaById(String codSeg, String codArea) throws BaseException {
		EquipeDAO dao = EquipeDAOImpl.getInstance();
		return dao.retrieveAreaById(codSeg,codArea);
	}

	public List<BBValueObject> retrieveAllMicroa(String codSeg, String codArea) throws BaseException {
		EquipeDAO dao = EquipeDAOImpl.getInstance();
		return dao.retrieveAllMicroa(codSeg,codArea);
	}

	public BBValueObject retrieveMicroaById(String codSeg, String codArea, String codMicroa) throws BaseException {
		EquipeDAO dao = EquipeDAOImpl.getInstance();
		return dao.retrieveMicroaById(codSeg,codArea,codMicroa);
	}

}
