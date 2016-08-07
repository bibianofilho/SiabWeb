package br.com.siab.modulo.equipe;

import java.util.List;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.biblioteca.utils.BBValueObject;
import br.com.siab.modulo.equipe.service.EquipeService;
import br.com.siab.modulo.equipe.service.EquipeServiceImpl;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public class EquipeImpl implements Equipe{

	private static EquipeImpl instance;
	
	private EquipeImpl() { }
	
	public static EquipeImpl getInstance() {
		if(instance == null){
			instance = new EquipeImpl();
		}
		return instance;
	}
	
	public List<BBValueObject> retrieveAll(String codSeg) throws BaseException {
		EquipeService service = EquipeServiceImpl.getInstance();
		return service.retrieveAll(codSeg);
	}

	public BBValueObject retrieveAreaById(String codSeg, String codArea) throws BaseException {
		EquipeService service = EquipeServiceImpl.getInstance();
		return service.retrieveAreaById(codSeg,codArea);
	}

	public List<BBValueObject> retrieveAllMicroa(String codSeg, String codArea) throws BaseException {
		EquipeService service = EquipeServiceImpl.getInstance();
		return service.retrieveAllMicroa(codSeg,codArea);
	}

	public BBValueObject retrieveMicroaById(String codSeg, String codArea, String codMicroa) throws BaseException {
		EquipeService service = EquipeServiceImpl.getInstance();
		return service.retrieveMicroaById(codSeg,codArea,codMicroa);
	}

}
