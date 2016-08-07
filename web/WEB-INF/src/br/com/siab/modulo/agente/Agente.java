package br.com.siab.modulo.agente;

import java.util.List;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.biblioteca.utils.BBValueObject;
import br.com.siab.modulo.agente.model.AgenteVO;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public interface Agente {
	
	public static final String LISTA_AGENTES = "LISTA_AGENTES";
	public static final String LISTA_AGENTE_FAMILIAS = "LISTA_AGENTE_FAMILIAS";
		
	public List<AgenteVO> pesquisar(AgenteVO agenteVO)throws BaseException;
	public List<BBValueObject> pesquisarRelAgente(Integer cdAgente, String ano) throws BaseException;
	public AgenteVO inserir(AgenteVO agenteVO) throws BaseException;
	public void editar(AgenteVO agenteVO) throws BaseException;
	public void excluir(Integer codigo) throws BaseException;
	public AgenteVO getAgenteById(AgenteVO agenteVO) throws BaseException;
	public void gerarDados(Integer codigo) throws BaseException;
}
