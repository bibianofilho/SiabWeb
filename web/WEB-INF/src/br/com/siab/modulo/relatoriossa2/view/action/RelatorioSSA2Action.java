package br.com.siab.modulo.relatoriossa2.view.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import br.com.siab.biblioteca.struts.action.BaseAction;
import br.com.siab.biblioteca.utils.BBValueObject;
import br.com.siab.modulo.equipe.Equipe;
import br.com.siab.modulo.equipe.EquipeImpl;
import br.com.siab.modulo.municipio.Municipio;
import br.com.siab.modulo.municipio.MunicipioImpl;
import br.com.siab.modulo.relatoriossa2.RelatorioSSA2;
import br.com.siab.modulo.relatoriossa2.RelatorioSSA2Impl;
import br.com.siab.modulo.relatoriossa2.model.RelatorioSSA2VO;
import br.com.siab.modulo.relatoriossa2.view.form.RelatorioSSA2Form;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public class RelatorioSSA2Action extends BaseAction {

	private final String FORWARD_LISTAR = "listar";
	
	private final String FORWARD_INCLUIR = "incluir";

	private final String FORWARD_EDITAR = "editar";
	
	private final String FORWARD_EXPORTAR = "exportar";

	public ActionForward preparaPesquisa(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.getInputForward();
	}
	
	public ActionForward pesquisar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession(true);
		RelatorioSSA2VO vo = new RelatorioSSA2VO();
		
		BeanUtils.copyProperties(vo,form);
		
		try {
			RelatorioSSA2 relatorio = RelatorioSSA2Impl.getInstance();
			List lista = relatorio.pesquisar(vo);
			session.setAttribute(RelatorioSSA2.LISTA_RELATORIO_SSA2, lista);

			forward = mapping.findForward(FORWARD_LISTAR);
			
		}catch (Exception e) {
			saveErrors(e, request);
			forward = mapping.findForward("ERROR_PAGE");
		}

		return forward;
		
	}

	public ActionForward preparaInsercao(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		generateToken(request);
		saveToken(request);
		return mapping.findForward(FORWARD_INCLUIR);
	}

	public ActionForward inserir(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward forward = null;
		RelatorioSSA2Form relForm = (RelatorioSSA2Form) form;
		RelatorioSSA2VO vo = new RelatorioSSA2VO();
		ActionMessages messages = new ActionMessages();

		popularVO(vo, relForm);

		try {

			if (isTokenValid(request)) {
				RelatorioSSA2 relatorio = RelatorioSSA2Impl.getInstance();
				relatorio.inserir(vo);

				messages.add("success", new ActionMessage(
						"message.insert.success"));
				saveMessages(request, messages);

				resetToken(request);

				relForm.reset(mapping, request);
			
			} 
			
			forward = mapping.getInputForward();

		} catch (Exception e) {
			saveErrors(e, request);
			forward = mapping.findForward(FORWARD_INCLUIR);
		}

		return forward;
	}
	
	public ActionForward preparaEdicao(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward forward = null;
		RelatorioSSA2Form relForm = (RelatorioSSA2Form) form;
		RelatorioSSA2VO vo = null;
		String codigo = request.getParameter("id");
		String view = request.getParameter("view");

		try {
			RelatorioSSA2 relatorio = RelatorioSSA2Impl.getInstance();
			vo = relatorio.getRelatorioById(codigo);
			BeanUtils.copyProperties(relForm, vo);

			// Obtendo a descrição do segmento
			BBValueObject bo = new BBValueObject();
			bo.set(0, "COD_SEG", relForm.getCodSeg());
			Municipio municipio = MunicipioImpl.getInstance();
			relForm.setDescSeg(municipio.retrieveSegById(bo).getString(0,
					"NOME"));

			// Obtendo a descrição da area
			Equipe equipe = EquipeImpl.getInstance();
			relForm.setDescArea(equipe.retrieveAreaById(relForm.getCodSeg(),
					relForm.getCodArea()).getString(0, "NOME_AREA"));

			// Obtendo a descrição da microarea
			relForm.setDescMicroa(equipe.retrieveMicroaById(
					relForm.getCodSeg(), relForm.getCodArea(),
					relForm.getCodMicroa()).getString(0, "NOME"));

			if(view != null){
				//Visualizar tela de exportação
				forward = mapping.findForward(FORWARD_EXPORTAR);
			} else {
				//Visualizar tela de edição
				forward = mapping.findForward(FORWARD_EDITAR);
			}
			
			generateToken(request);
			saveToken(request);
		} catch (Exception e) {
			saveErrors(e, request);
			forward = mapping.findForward("ERROR_PAGE");
		}

		return forward;
	}
	
	public ActionForward exportar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionForward forward = null;
		RelatorioSSA2Form relForm = (RelatorioSSA2Form) form;
		RelatorioSSA2VO vo = new RelatorioSSA2VO();
		ActionMessages messages = new ActionMessages();
		
		BeanUtils.copyProperties(vo,relForm);
		
		try {

			if (isTokenValid(request)) {
				RelatorioSSA2 relatorio = RelatorioSSA2Impl.getInstance();
				relatorio.exportar(vo);

				messages.add("success", new ActionMessage(
						"message.crud.default","Registro exportado para o siab com sucesso."));
				saveMessages(request, messages);

				resetToken(request);

				relForm.reset(mapping, request);	
			} 
			
			forward = mapping.getInputForward();

		} catch (Exception e) {
			saveErrors(e, request);
			forward = mapping.findForward(FORWARD_EXPORTAR);
		}

		return forward;

	}
	
	public ActionForward editar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward forward = null;
		RelatorioSSA2Form relForm = (RelatorioSSA2Form) form;
		RelatorioSSA2VO vo = new RelatorioSSA2VO();
		ActionMessages messages = new ActionMessages();

		popularVO(vo, relForm);

		try {

			if (isTokenValid(request)) {
				RelatorioSSA2 relatorio = RelatorioSSA2Impl.getInstance();
				relatorio.editar(vo);

				messages.add("success", new ActionMessage(
						"message.update.success"));
				saveMessages(request, messages);

				resetToken(request);

				relForm.reset(mapping, request);	
			} 
			
			forward = mapping.getInputForward();

		} catch (Exception e) {
			saveErrors(e, request);
			forward = mapping.findForward(FORWARD_EDITAR);
		}

		return forward;
	}

	/**
	 * Método utilizado para obter o código, modelo, unidade básica e a
	 * descrição da microarea. Usado pelo ajax.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMicroa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			StringBuilder sb = new StringBuilder();
			String codSeg = request.getParameter("codSeg");
			String codArea = request.getParameter("codArea");
			String codMicroa = request.getParameter("codMicroa");

			Equipe equipe = EquipeImpl.getInstance();
			BBValueObject vo = equipe.retrieveMicroaById(codSeg, codArea,
					codMicroa);

			sb.append("[{properties:[");
			sb.append("{property:'codMicroa',value:'").append(
					vo.get(0, "COD_MICROA")).append("'},");
			sb.append("{property:'idModelo',value:'").append(
					vo.get(0, "ID_MODELO")).append("'},");
			sb.append("{property:'codUB',value:'").append(vo.get(0, "COD_UB"))
					.append("'},");
			sb.append("{property:'nome',value:'").append(vo.get(0, "NOME"))
					.append("'}");
			sb.append("]}]");

			response.setContentType("text; charset=UTF-8");
			response.getOutputStream().write(sb.toString().getBytes());

		} catch (Exception e) {
			saveErrors(e, request);
			return mapping.findForward("ERROR_PAGE");
		}

		return null;
	}

	/**
	 * Método utilizado para obter o código e a descrição da area de um
	 * segmento. Método utilizado pelo ajax.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			StringBuilder sb = new StringBuilder();
			String codSeg = request.getParameter("codSeg");
			String codArea = request.getParameter("codArea");

			Equipe equipe = EquipeImpl.getInstance();
			BBValueObject vo = equipe.retrieveAreaById(codSeg, codArea);

			sb.append("[{properties:[");
			sb.append("{property:'codArea',value:'").append(
					vo.get(0, "COD_AREA")).append("'},");
			sb.append("{property:'nome',value:'")
					.append(vo.get(0, "NOME_AREA")).append("'}");
			sb.append("]}]");

			response.setContentType("text; charset=UTF-8");
			response.getOutputStream().write(sb.toString().getBytes());

		} catch (Exception e) {
			saveErrors(e, request);
			return mapping.findForward("ERROR_PAGE");
		}

		return null;
	}

	/**
	 * Método utilizado para obter o codigo da zona e a descrição do segmento.
	 * Método utilizado pelo ajax.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSegmento(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			StringBuilder sb = new StringBuilder();
			String codSeg = request.getParameter("codSeg");
			BBValueObject vo = new BBValueObject();
			vo.set(0, "COD_SEG", codSeg);

			Municipio municipio = MunicipioImpl.getInstance();
			BBValueObject rVO = municipio.retrieveSegById(vo);

			sb.append("[{properties:[");
			sb.append("{property:'codSeg',value:'").append(
					rVO.get(0, "COD_SEG")).append("'},");
			sb.append("{property:'codZona',value:'").append(
					rVO.get(0, "COD_ZONA")).append("'},");
			sb.append("{property:'nome',value:'").append(rVO.get(0, "NOME"))
					.append("'}");
			sb.append("]}]");

			response.setContentType("text; charset=UTF-8");
			response.getOutputStream().write(sb.toString().getBytes());

		} catch (Exception e) {
			saveErrors(e, request);
			return mapping.findForward("ERROR_PAGE");
		}

		return null;
	}

	/**
	 * Método interno utilizado para popular o bean
	 * 
	 * @param vo
	 * @param form
	 * @throws Exception
	 */
	private void popularVO(RelatorioSSA2VO vo, RelatorioSSA2Form form)
			throws Exception {

		vo.setCodSeg(Integer.valueOf(form.getCodSeg()));
		vo.setCodArea(Integer.valueOf(form.getCodArea()));
		vo.setCodMicroa(Integer.valueOf(form.getCodMicroa()));
		vo.setCodUB(Long.valueOf(form.getCodUB()));
		vo.setCodZona(Integer.valueOf(form.getCodZona()));
		vo.setIdModelo(Integer.valueOf(form.getIdModelo()));
		vo.setMes(form.getMes());
		vo.setAno(form.getAno());
		vo.setNfamicad(Integer.valueOf(form.getNfamicad()));
		vo.setNvisitas(Integer.valueOf(form.getNvisitas()));
		vo.setNgesCad(Integer.valueOf(form.getNgesCad()));
		vo.setNgesm20(Integer.valueOf(form.getNgesm20()));
		vo.setNgesac(Integer.valueOf(form.getNgesac()));
		vo.setNgesvac(Integer.valueOf(form.getNgesvac()));
		vo.setNgespre1(Integer.valueOf(form.getNgespre1()));
		vo.setNgespre2(Integer.valueOf(form.getNgespre2()));
		vo.setC_4meses(Integer.valueOf(form.getC_4meses()));
		vo.setC_mamand(Integer.valueOf(form.getC_mamand()));
		vo.setC_misto(Integer.valueOf(form.getC_misto()));
		vo.setC_0a11(Integer.valueOf(form.getC_0a11()));
		vo.setC_vacdia(Integer.valueOf(form.getC_vacdia()));
		vo.setC_0a112p(Integer.valueOf(form.getC_0a112p()));
		vo.setC_0a11gp(Integer.valueOf(form.getC_0a11gp()));
		vo.setC_1223(Integer.valueOf(form.getC_1223()));
		vo.setC_vacina(Integer.valueOf(form.getC_vacina()));
		vo.setC_12232p(Integer.valueOf(form.getC_12232p()));
		vo.setC_1223gp(Integer.valueOf(form.getC_1223gp()));
		vo.setC_diarre(Integer.valueOf(form.getC_diarre()));
		vo.setC_diasro(Integer.valueOf(form.getC_diasro()));
		vo.setC_ira(Integer.valueOf(form.getC_ira()));
		vo.setNascvivo(Integer.valueOf(form.getNascvivo()));
		vo.setPesados(Integer.valueOf(form.getPesados()));
		vo.setPeso2500(Integer.valueOf(form.getPeso2500()));
		vo.setO_dia0a28(Integer.valueOf(form.getO_dia0a28()));
		vo.setO_ira0a28(Integer.valueOf(form.getO_ira0a28()));
		vo.setO_cau0a28(Integer.valueOf(form.getO_cau0a28()));
		vo.setO_dia28a1(Integer.valueOf(form.getO_dia28a1()));
		vo.setO_ira28a1(Integer.valueOf(form.getO_ira28a1()));
		vo.setO_cau28a1(Integer.valueOf(form.getO_cau28a1()));
		vo.setObitodia(Integer.valueOf(form.getObitodia()));
		vo.setObitoira(Integer.valueOf(form.getObitoira()));
		vo.setObitocau(Integer.valueOf(form.getObitocau()));
		vo.setO_mul10a14(Integer.valueOf(form.getO_mul10a14()));
		vo.setObitomul(Integer.valueOf(form.getObitomul()));
		vo.setObitoadol(Integer.valueOf(form.getObitoadol()));
		vo.setObitoout(Integer.valueOf(form.getObitoout()));
		vo.setD_diabete(Integer.valueOf(form.getD_diabete()));
		vo.setD_diaac(Integer.valueOf(form.getD_diaac()));
		vo.setD_hiperten(Integer.valueOf(form.getD_hiperten()));
		vo.setD_hiperac(Integer.valueOf(form.getD_hiperac()));
		vo.setD_tubercul(Integer.valueOf(form.getD_tubercul()));
		vo.setD_tuberac(Integer.valueOf(form.getD_tuberac()));
		vo.setD_hansen(Integer.valueOf(form.getD_hansen()));
		vo.setD_hanseac(Integer.valueOf(form.getD_hanseac()));
		vo.setH_0a5pneu(Integer.valueOf(form.getH_0a5pneu()));
		vo.setH_0a5des(Integer.valueOf(form.getH_0a5des()));
		vo.setH_alcool(Integer.valueOf(form.getH_alcool()));
		vo.setH_psiqui(Integer.valueOf(form.getH_psiqui()));
		vo.setH_diabete(Integer.valueOf(form.getH_diabete()));
		vo.setH_outcau(Integer.valueOf(form.getH_outcau()));
		vo.setSigab(null);
	}
}
