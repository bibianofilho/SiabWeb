package br.com.siab.modulo.relatoriossa2.view.decorator;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.decorator.TableDecorator;

import br.com.siab.biblioteca.utils.Utils;
import br.com.siab.modulo.relatoriossa2.model.RelatorioSSA2VO;

/**
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public class RelatorioSSA2Decorator extends TableDecorator {

	public String getEditar() {
		RelatorioSSA2VO vo = (RelatorioSSA2VO) getCurrentRowObject();
		String exportado = vo.getExportado();
		StringBuilder sb = new StringBuilder();
		sb.append(vo.getCodSeg()).append(
				Utils.formataCodigo("" + vo.getCodArea(), 3)).append(
				Utils.formataCodigo("" + vo.getCodMicroa(), 2)).append(
				vo.getMes()).append(vo.getAno());
		Long id = new Long(sb.toString());

		StringBuffer strUrl = new StringBuffer();

		HttpServletRequest request = (HttpServletRequest) this.getPageContext()
				.getRequest();
		String contexto = request.getContextPath();

		if ("N".equals(exportado)) {
			strUrl.append(contexto).append(
					"/relatorioSSA2.do?method=preparaEdicao&id=").append(id);
		} else {
			strUrl.append(contexto).append(
					"/relatorioSSA2.do?method=preparaEdicao&id=").append(id)
					.append("&view=exp");
		}
		
		return "<a href='" + strUrl.toString()
				+ "' title=\"Editar Relatório\" >" + "<img src='" + contexto
				+ "/images/bt_editar.gif' border='0' >" + "</a>";
	}
	
	public String getExportar() {
		RelatorioSSA2VO vo = (RelatorioSSA2VO) getCurrentRowObject();
		String exportado = vo.getExportado();
		StringBuilder sb = new StringBuilder();
		sb.append(vo.getCodSeg()).append(
				Utils.formataCodigo("" + vo.getCodArea(), 3)).append(
				Utils.formataCodigo("" + vo.getCodMicroa(), 2)).append(
				vo.getMes()).append(vo.getAno());
		Long id = new Long(sb.toString());

		StringBuffer strUrl = new StringBuffer();

		HttpServletRequest request = (HttpServletRequest) this.getPageContext()
				.getRequest();
		String contexto = request.getContextPath();

		strUrl.append(contexto).append(
				"/relatorioSSA2.do?method=preparaEdicao&id=").append(id).append("&view=exp");

		if ("N".equals(exportado)) {
			return "<a href='" + strUrl.toString()
					+ "' title=\"Exportar Relatório\" >" + "<img src='"
					+ contexto + "/images/btn_exporta.gif' border='0' >"
					+ "</a>";
		} else {
			return "";
		}
	}

	public String getExportado() {
		HttpServletRequest request = (HttpServletRequest) this.getPageContext()
				.getRequest();
		String contexto = request.getContextPath();
		RelatorioSSA2VO vo = (RelatorioSSA2VO) getCurrentRowObject();
		String exportado = vo.getExportado();

		if ("S".equals(exportado)) {
			return "<img src='"
					+ contexto
					+ "/images/btn_confirmar_no_bg.gif' title='Relatório exportado' border='0' style='cursor:pointer' >";
		} else {
			return "<img src='"
					+ contexto
					+ "/images/btn_cancelar_no_bg.gif'  title='Relatório não exportado' border='0'  style='cursor:pointer' >";
		}
	}

	public String getCodSeg() {
		RelatorioSSA2VO vo = (RelatorioSSA2VO) getCurrentRowObject();
		String codigo = vo.getCodSeg().toString();
		return Utils.formataCodigo(codigo, 2);
	}

	public String getCodArea() {
		RelatorioSSA2VO vo = (RelatorioSSA2VO) getCurrentRowObject();
		String codigo = vo.getCodArea().toString();
		return Utils.formataCodigo(codigo, 3);
	}

	public String getCodMicroa() {
		RelatorioSSA2VO vo = (RelatorioSSA2VO) getCurrentRowObject();
		String codigo = vo.getCodMicroa().toString();
		return Utils.formataCodigo(codigo, 2);
	}
}
