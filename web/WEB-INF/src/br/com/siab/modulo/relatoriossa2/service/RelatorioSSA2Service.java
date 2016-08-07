package br.com.siab.modulo.relatoriossa2.service;

import java.util.List;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.modulo.relatoriossa2.model.RelatorioSSA2VO;

/**
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public interface RelatorioSSA2Service {
	public List<RelatorioSSA2VO> pesquisar(RelatorioSSA2VO vo) throws BaseException;
	public void inserir(RelatorioSSA2VO vo) throws BaseException;
	public void editar(RelatorioSSA2VO vo) throws BaseException;
	public void exportar(RelatorioSSA2VO vo) throws BaseException;
	public RelatorioSSA2VO getRelatorioById(String codigo) throws BaseException;
}
