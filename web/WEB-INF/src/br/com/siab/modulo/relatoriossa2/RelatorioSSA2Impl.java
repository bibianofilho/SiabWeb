package br.com.siab.modulo.relatoriossa2;

import java.util.List;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.modulo.relatoriossa2.model.RelatorioSSA2VO;
import br.com.siab.modulo.relatoriossa2.service.RelatorioSSA2Service;
import br.com.siab.modulo.relatoriossa2.service.RelatorioSSA2ServiceImpl;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public class RelatorioSSA2Impl implements RelatorioSSA2 {
	
	private static RelatorioSSA2Impl instance;
	
	private RelatorioSSA2Impl(){
		
	}
	
	public static RelatorioSSA2Impl getInstance(){
		if(instance == null){
			instance = new RelatorioSSA2Impl();
		}
		
		return instance;
	}

	public List<RelatorioSSA2VO> pesquisar(RelatorioSSA2VO vo) throws BaseException {
		RelatorioSSA2Service service = RelatorioSSA2ServiceImpl.getInstance();
		return service.pesquisar(vo);
	}

	public void inserir(RelatorioSSA2VO vo) throws BaseException {
		RelatorioSSA2Service service = RelatorioSSA2ServiceImpl.getInstance();
		service.inserir(vo);
	}

	public void editar(RelatorioSSA2VO vo) throws BaseException {
		RelatorioSSA2Service service = RelatorioSSA2ServiceImpl.getInstance();
		service.editar(vo);		
	}

	public RelatorioSSA2VO getRelatorioById(String codigo) throws BaseException {
		RelatorioSSA2Service service = RelatorioSSA2ServiceImpl.getInstance();
		return service.getRelatorioById(codigo);
	}

	public void exportar(RelatorioSSA2VO vo) throws BaseException {
		RelatorioSSA2Service service = RelatorioSSA2ServiceImpl.getInstance();
		service.exportar(vo);			
	}

}
