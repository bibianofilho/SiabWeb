package br.com.siab.modulo.relatorio.view.action;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.siab.biblioteca.struts.action.BaseAction;
import br.com.siab.biblioteca.utils.BBValueObject;
import br.com.siab.modulo.agente.Agente;
import br.com.siab.modulo.agente.AgenteImpl;
import br.com.siab.modulo.relatorio.Relatorio;
import br.com.siab.modulo.relatorio.helpers.RelatorioHelper;
import br.com.siab.modulo.relatorio.model.RelatorioDTO;
import br.com.siab.modulo.relatorio.view.form.RelatorioForm;

/**
 * Action responsável pelo tratamento de relatórios por agente
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class RelatorioAgenteAction extends BaseAction {

	private final String FORWARD_ARVORE = "arvoreAgente";
	private final String FORWARD_LISTAR = "listarFamilia";
	
	public ActionForward pesquisarAgente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		HttpSession session = request.getSession(true);
		RelatorioForm relatorioForm = (RelatorioForm) form;
		
		try {
			String cdAgente = relatorioForm.getCdAgente();
			String ano = relatorioForm.getAno();
			
			Agente agente = AgenteImpl.getInstance();
			List<BBValueObject> vo = agente.pesquisarRelAgente(Integer.valueOf(cdAgente),ano);
			session.setAttribute(Agente.LISTA_AGENTE_FAMILIAS,vo);
			/*request.setAttribute("cdAgente",cdAgente);
			request.setAttribute("ano",ano);*/
			
			forward = mapping.findForward(FORWARD_ARVORE);			
			
		}catch (Exception e) {
			saveErrors(e, request);
			forward = mapping.findForward("ERROR_PAGE");
		}
		
		return forward;
	}
	
	/**
	 * Action responsável pela renderização da tela de listagem de famílias
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listarFamilia(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return mapping.findForward(FORWARD_LISTAR);
	}
	
	/**
	 * Relatório Ficha A
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward fichaA(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
				
		try {
			RelatorioHelper helper = new RelatorioHelper();
			RelatorioDTO relatorio = null;
			Map parametros = 
				helper.preparaRelatorioFichaA(request);
			
			relatorio = helper.processar(getServlet().getServletContext(),
					Relatorio.FICHA_A, parametros);

			byte[] pdf = relatorio.getBytes();
			if (pdf != null && pdf.length > 0) {
				response.setContentType("application/pdf");
				response.setContentLength(pdf.length);
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(pdf, 0, pdf.length);
				outputStream.flush();
				outputStream.close();
				return null;
			}
			
		}catch (Exception e) {
			saveErrors(e,request);
			forward = mapping.findForward("ERROR_PAGE");
		}

		return forward;
	}
	
	public ActionForward imprimirFicha(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
		String ficha = request.getParameter("ficha");
		String nomeRelatorio = null;
				
		try {
			RelatorioHelper helper = new RelatorioHelper();
			RelatorioDTO relatorio = null;
			Map parametros = null;
			
			if ("fichaCrianca".equals(ficha)) {
				parametros = helper.preparaRelatorioFichaCrianca(request);
				nomeRelatorio = Relatorio.FICHA_CRIANCA;
			}else if ("fichaTb".equals(ficha)) {
				parametros = helper.preparaRelatorioFichaTb(request);
				nomeRelatorio = Relatorio.FICHA_TB;
			}else if ("fichaDia".equals(ficha)) {
				parametros = helper.preparaRelatorioFichaDia(request);
				nomeRelatorio = Relatorio.FICHA_DIA;
			}else if ("fichaHa".equals(ficha)) {
				parametros = helper.preparaRelatorioFichaHa(request);
				nomeRelatorio = Relatorio.FICHA_HA;
			}else if ("fichaHan".equals(ficha)) {
				parametros = helper.preparaRelatorioFichaHan(request);
				nomeRelatorio = Relatorio.FICHA_HAN;
			}else if ("fichaIdoso".equals(ficha)) {
				parametros = helper.preparaRelatorioFichaIdoso(request);
				nomeRelatorio = Relatorio.FICHA_IDOSO;
			}else if("fichaGes".equals(ficha)){
				parametros = helper.preparaRelatorioFichaGes(request);
				nomeRelatorio = Relatorio.FICHA_GES;
			}
			
			relatorio = helper.processar(getServlet().getServletContext(),
					nomeRelatorio, parametros);

			byte[] pdf = relatorio.getBytes();
			if (pdf != null && pdf.length > 0) {
				response.setContentType("application/pdf");
				response.setContentLength(pdf.length);
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(pdf, 0, pdf.length);
				outputStream.flush();
				outputStream.close();
				return null;
			}
			
		}catch (Exception e) {
			saveErrors(e,request);
			forward = mapping.findForward("ERROR_PAGE");
		}

		return forward;
	}


}
