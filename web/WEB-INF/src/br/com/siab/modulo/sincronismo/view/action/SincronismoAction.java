package br.com.siab.modulo.sincronismo.view.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import br.com.siab.biblioteca.struts.action.BaseAction;
import br.com.siab.modulo.sincronismo.Sincronismo;
import br.com.siab.modulo.sincronismo.SincronismoImpl;
import br.com.siab.modulo.sincronismo.view.form.SincronismoForm;

/**
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public class SincronismoAction extends BaseAction {

	public ActionForward preparaSincronismo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.getInputForward();

	}

	public ActionForward sincronismo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward forward = null;
		SincronismoForm sincronismoForm = (SincronismoForm) form;
		ActionMessages messages = new ActionMessages();

		try {
			ServletContext context = getServlet().getServletContext();
			Sincronismo sincronismo = SincronismoImpl.getInstance();
			sincronismo.realizarSincronismo(sincronismoForm.getAno(),context);
			
			messages.add("success", new ActionMessage("message.crud.default",
					"Sincronismo realizado com sucesso."));
			saveMessages(request, messages);
			
			forward = mapping.getInputForward();

		} catch (Exception e) {
			saveErrors(e, request);
			forward = mapping.findForward("ERROR_PAGE");
		}

		return forward;

	}

}
