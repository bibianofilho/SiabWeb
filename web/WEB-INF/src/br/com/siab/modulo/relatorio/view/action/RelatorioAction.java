package br.com.siab.modulo.relatorio.view.action;

import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.siab.biblioteca.struts.action.BaseAction;
import br.com.siab.modulo.relatorio.Relatorio;
import br.com.siab.modulo.relatorio.helpers.RelatorioHelper;
import br.com.siab.modulo.relatorio.model.RelatorioDTO;
import br.com.siab.modulo.relatorio.view.form.RelatorioForm;

/**
 * Action responsável pelo tratramento dos relatórios
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public class RelatorioAction extends BaseAction {

	/**
	 * Método responsável pela geração de relatórios da aplicação
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RelatorioForm relatorioForm = (RelatorioForm) form;
		String nomeRelatorio = relatorioForm.getRepname();

		ActionForward forward = null;

		try {
			RelatorioHelper helper = new RelatorioHelper();
			RelatorioDTO relatorio = null;
			Map parametros = null;

			if (Relatorio.FICHA_CRIANCA.equals(nomeRelatorio)) {
				parametros = helper.preparaRelatorioFichaCrianca(request);
			}else if (Relatorio.FICHA_TB.equals(nomeRelatorio)) {
				parametros = helper.preparaRelatorioFichaTb(request);
			}else if (Relatorio.FICHA_DIA.equals(nomeRelatorio)) {
				parametros = helper.preparaRelatorioFichaDia(request);
			}else if (Relatorio.FICHA_HA.equals(nomeRelatorio)) {
				parametros = helper.preparaRelatorioFichaHa(request);
			}else if (Relatorio.FICHA_HAN.equals(nomeRelatorio)) {
				parametros = helper.preparaRelatorioFichaHan(request);
			}else if (Relatorio.FICHA_IDOSO.equals(nomeRelatorio)) {
				parametros = helper.preparaRelatorioFichaIdoso(request);
			}else if (Relatorio.FICHA_A.equals(nomeRelatorio)) {
				parametros = helper.preparaRelatorioFichaA(request);
			}else if (Relatorio.FICHA_GES.equals(nomeRelatorio)){
				parametros = helper.preparaRelatorioFichaGes(request);
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

		} catch (Exception e) {
			saveErrors(e,request);
			forward = mapping.findForward("ERROR_PAGE");
		}

		return forward;
	}
}
