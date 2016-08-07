package br.com.siab.modulo.municipio;

import java.util.List;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.biblioteca.utils.BBValueObject;
import br.com.siab.modulo.municipio.service.MunicipioService;
import br.com.siab.modulo.municipio.service.MunicipioServiceImpl;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public class MunicipioImpl implements Municipio{

	private static MunicipioImpl instance;
	
	private MunicipioImpl() {}
	
	public static MunicipioImpl getInstance(){
		if(instance == null){
			instance = new MunicipioImpl();
		}
		
		return instance;
	}
	
	public BBValueObject retrieveSegById(BBValueObject vo) throws BaseException {
		MunicipioService service = MunicipioServiceImpl.getInstance();
		return service.retrieveSegById(vo);
	}

	public List<BBValueObject> retrieveAll() throws BaseException {
		MunicipioService service = MunicipioServiceImpl.getInstance();
		return service.retrieveAll();
	}

}
