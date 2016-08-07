package br.com.siab.modulo.relatorio.view.taglib;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import br.com.siab.biblioteca.utils.BBValueObject;
import br.com.siab.modulo.agente.Agente;

/**
 * Classe reponsável pela geração da treeview de fichas por agente.
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public class ArvoreAgente extends TagSupport {
	
	public ArvoreAgente() {
		super();
	}
	
	public int doEndTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			StringBuilder tree = getArvoreRelatorio();

			if (tree != null) {
				out.print(tree.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	private StringBuilder getArvoreRelatorio() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		List<BBValueObject> lista = 
			(List) request.getSession(true).getAttribute(Agente.LISTA_AGENTE_FAMILIAS);
		BBValueObject vo = lista.get(0);
		Integer cdAgente = vo.getInteger(0,"CD_AGENTE");
		String ano = vo.getString(0,"ANO");
		
		StringBuilder sb = new StringBuilder();
		sb.append("<ul>");
		sb.append("<li><a href=\"#\" onclick=\"javascript:imprimirFichaA()\">Ficha A</a></li>");
		sb.append("<li><a href=\"#\" onclick=\"javascript:imprimirFichaCrianca(").append(cdAgente).append(",").append(ano).append(")\">Ficha Criança</a></li>");
		sb.append("<li><a href=\"#\" onclick=\"javascript:imprimirFichaDia(").append(cdAgente).append(",").append(ano).append(")\">Ficha Dia</a></li>");
		sb.append("<li><a href=\"#\" onclick=\"javascript:imprimirFichaGes(").append(cdAgente).append(",").append(ano).append(")\">Ficha Ges</a></li>");
		sb.append("<li><a href=\"#\" onclick=\"javascript:imprimirFichaHa(").append(cdAgente).append(",").append(ano).append(")\">Ficha Ha</a></li>");
		sb.append("<li><a href=\"#\" onclick=\"javascript:imprimirFichaHan(").append(cdAgente).append(",").append(ano).append(")\">Ficha Han</a></li>");
		sb.append("<li><a href=\"#\" onclick=\"javascript:imprimirFichaIdoso(").append(cdAgente).append(",").append(ano).append(")\">Ficha Idoso</a></li>");
		sb.append("<li><a href=\"#\" onclick=\"javascript:imprimirFichaTb(").append(cdAgente).append(",").append(ano).append(")\">Ficha Tb</a></li>");
		sb.append("</ul>");
		
		return sb;
	}
	
}
