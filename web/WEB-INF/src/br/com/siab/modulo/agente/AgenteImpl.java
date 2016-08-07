package br.com.siab.modulo.agente;

import java.util.List;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.biblioteca.utils.BBValueObject;
import br.com.siab.modulo.agente.model.AgenteVO;
import br.com.siab.modulo.agente.service.AgenteService;
import br.com.siab.modulo.agente.service.AgenteServiceImpl;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public class AgenteImpl implements Agente {

	private static AgenteImpl instance;
	
	private AgenteImpl(){
	}
	
	public static AgenteImpl getInstance(){
		if(instance == null){
			instance = new AgenteImpl();
		}
		return instance;
	}
	
	public List<AgenteVO> pesquisar(AgenteVO agenteVO) throws BaseException {
		AgenteService service = AgenteServiceImpl.getInstance();
		return service.pesquisar(agenteVO);
	}

	public AgenteVO inserir(AgenteVO agenteVO) throws BaseException {
		AgenteService service = AgenteServiceImpl.getInstance();
		return service.inserir(agenteVO);
	}

	public void editar(AgenteVO agenteVO) throws BaseException {
		AgenteService service = AgenteServiceImpl.getInstance();
		service.editar(agenteVO);
	}

	public void excluir(Integer codigo) throws BaseException {
		// TODO Auto-generated method stub
	}

	public void gerarDados(Integer codigo) throws BaseException {
		AgenteService service = AgenteServiceImpl.getInstance();
		service.gerarDados(codigo);	
	}

	public AgenteVO getAgenteById(AgenteVO agenteVO) throws BaseException {
		AgenteService service = AgenteServiceImpl.getInstance();
		return service.getAgenteById(agenteVO);
	}

	public List<BBValueObject> pesquisarRelAgente(Integer cdAgente, String ano) throws BaseException {
		AgenteService service = AgenteServiceImpl.getInstance();
		return service.pesquisarRelAgente(cdAgente,ano);
	}

}
