package br.com.siab.modulo.relatoriossa2.service;

import java.util.List;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.modulo.relatoriossa2.dao.RelatorioSSA2DAO;
import br.com.siab.modulo.relatoriossa2.dao.RelatorioSSA2DAOImpl;
import br.com.siab.modulo.relatoriossa2.model.RelatorioSSA2VO;

/**
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public class RelatorioSSA2ServiceImpl implements RelatorioSSA2Service {

	private static RelatorioSSA2ServiceImpl instance;

	private RelatorioSSA2ServiceImpl() {
	}

	public static RelatorioSSA2ServiceImpl getInstance() {
		if (instance == null) {
			instance = new RelatorioSSA2ServiceImpl();
		}
		return instance;
	}

	public List<RelatorioSSA2VO> pesquisar(RelatorioSSA2VO vo)
			throws BaseException {
		RelatorioSSA2DAO dao = RelatorioSSA2DAOImpl.getInstance();
		return dao.pesquisar(vo);
	}

	public void inserir(RelatorioSSA2VO vo) throws BaseException {
		Integer ano = Integer.valueOf(vo.getAno());

		if (ano < 1950) {
			throw new BaseException("Ano inválido!");
		}

		RelatorioSSA2DAO dao = RelatorioSSA2DAOImpl.getInstance();
		dao.inserir(vo);
	}

	public void editar(RelatorioSSA2VO vo) throws BaseException {
		String exportado = vo.getExportado();

		if ("S".equals(exportado)) {
			throw new BaseException("Esse registro já foi exportado!");
		}

		RelatorioSSA2DAO dao = RelatorioSSA2DAOImpl.getInstance();
		dao.editar(vo);
	}

	public RelatorioSSA2VO getRelatorioById(String codigo) throws BaseException {
		RelatorioSSA2DAO dao = RelatorioSSA2DAOImpl.getInstance();
		return dao.getRelatorioById(codigo);
	}

	public void exportar(RelatorioSSA2VO vo) throws BaseException {
		String exportado = vo.getExportado();

		if ("S".equals(exportado)) {
			throw new BaseException("Esse registro já foi exportado!");
		}

		RelatorioSSA2DAO dao = RelatorioSSA2DAOImpl.getInstance();
		dao.exportar(vo);
	}

}
