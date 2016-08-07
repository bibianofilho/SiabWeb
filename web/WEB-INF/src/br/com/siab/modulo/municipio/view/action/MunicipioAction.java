package br.com.siab.modulo.municipio.view.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.siab.biblioteca.struts.action.BaseAction;
import br.com.siab.modulo.municipio.Municipio;
import br.com.siab.modulo.municipio.MunicipioImpl;

/**
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public class MunicipioAction extends BaseAction {

	public ActionForward preparaPesquisa(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(true);
		
		try {
			Municipio municipio = MunicipioImpl.getInstance();
			List lstSeg = municipio.retrieveAll();
			session.setAttribute(Municipio.LISTA_SEGMENTOS,lstSeg);
			
		}catch (Exception e) {
			saveErrors(e, request);
			return mapping.findForward("ERROR_PAGE");
		}

		return mapping.getInputForward();
	}
	
}
