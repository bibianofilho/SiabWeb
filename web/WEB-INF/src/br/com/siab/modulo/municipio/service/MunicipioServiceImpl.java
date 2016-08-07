package br.com.siab.modulo.municipio.service;

import java.util.List;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.biblioteca.utils.BBValueObject;
import br.com.siab.modulo.municipio.dao.MunicipioDAO;
import br.com.siab.modulo.municipio.dao.MunicipioDAOImpl;

/**
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public class MunicipioServiceImpl implements MunicipioService {
	
	private static MunicipioServiceImpl instance;
	
	private MunicipioServiceImpl(){}
	
	public static MunicipioServiceImpl getInstance(){
		if(instance == null){
			instance = new MunicipioServiceImpl();
		}
		return instance;
	}

	public BBValueObject retrieveSegById(BBValueObject vo) throws BaseException {
		MunicipioDAO dao = MunicipioDAOImpl.getInstance();
		return dao.retrieveSegById(vo);
	}

	public List<BBValueObject> retrieveAll() throws BaseException {
		MunicipioDAO dao = MunicipioDAOImpl.getInstance();
		return dao.retrieveAll();
	}

}
