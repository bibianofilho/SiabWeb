package br.com.siab.modulo.agente.service;

import java.util.List;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.biblioteca.utils.BBValueObject;
import br.com.siab.modulo.agente.dao.AgenteDAO;
import br.com.siab.modulo.agente.dao.AgenteDAOImpl;
import br.com.siab.modulo.agente.model.AgenteVO;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public class AgenteServiceImpl implements AgenteService {
	
	private static AgenteServiceImpl instance;
	
	private AgenteServiceImpl(){
	}
	
	public static AgenteServiceImpl getInstance(){
		if(instance == null){
			instance = new AgenteServiceImpl();
		}
		
		return instance;
	}
	
	public AgenteVO inserir(AgenteVO agenteVO)throws BaseException{
		AgenteDAO agenteDAO = AgenteDAOImpl.getInstance();
		return agenteDAO.inserir(agenteVO);
	}

	public List<AgenteVO> pesquisar(AgenteVO agenteVO) throws BaseException {
		AgenteDAO agenteDAO = AgenteDAOImpl.getInstance();
		return agenteDAO.pesquisar(agenteVO);
	}

	public void editar(AgenteVO agenteVO) throws BaseException {
		AgenteDAO agenteDAO = AgenteDAOImpl.getInstance();
		agenteDAO.editar(agenteVO);
	}

	public AgenteVO getAgenteById(AgenteVO agenteVO) throws BaseException {
		AgenteDAO agenteDAO = AgenteDAOImpl.getInstance();
		return agenteDAO.getAgenteById(agenteVO);
	}

	public void gerarDados(Integer codigo) throws BaseException {
		AgenteDAO agenteDAO = AgenteDAOImpl.getInstance();
		agenteDAO.gerarDados(codigo);
	}

	public List<BBValueObject> pesquisarRelAgente(Integer cdAgente, String ano) throws BaseException {
		AgenteDAO agenteDAO = AgenteDAOImpl.getInstance();
		return agenteDAO.pesquisarRelAgente(cdAgente,ano);
	}
}
