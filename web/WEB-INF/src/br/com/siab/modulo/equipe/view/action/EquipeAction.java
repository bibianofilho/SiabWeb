package br.com.siab.modulo.equipe.view.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.siab.biblioteca.struts.action.BaseAction;
import br.com.siab.modulo.equipe.Equipe;
import br.com.siab.modulo.equipe.EquipeImpl;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public class EquipeAction extends BaseAction {
	
	private final String FORWARD_POPUP_PESQUISA_AREA = "pesquisaPopupArea";
	private final String FORWARD_POPUP_PESQUISA_MICROA = "pesquisaPopupMicroa";
	
	public ActionForward pesquisaArea(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		HttpSession session = request.getSession(true);
		String codSeg = request.getParameter("codSeg");
		
		try {
			Equipe equipe = EquipeImpl.getInstance();
			List lstArea = equipe.retrieveAll(codSeg);
			session.setAttribute(Equipe.LISTA_AREAS,lstArea);
			
			forward = mapping.findForward(FORWARD_POPUP_PESQUISA_AREA);
			
		}catch (Exception e) {
			saveErrors(e, request);
			forward = mapping.findForward("ERROR_PAGE");
		}

		return forward;
	}
	
	public ActionForward pesquisaMicroa(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		HttpSession session = request.getSession(true);
		String codSeg = request.getParameter("codSeg");
		String codArea = request.getParameter("codArea");
		
		try {
			Equipe equipe = EquipeImpl.getInstance();
			List lstMicroa = equipe.retrieveAllMicroa(codSeg,codArea);
			session.setAttribute(Equipe.LISTA_MICROA,lstMicroa);
			
			forward = mapping.findForward(FORWARD_POPUP_PESQUISA_MICROA);
			
		}catch (Exception e) {
			saveErrors(e, request);
			forward = mapping.findForward("ERROR_PAGE");
		}

		return forward;
	}

}
