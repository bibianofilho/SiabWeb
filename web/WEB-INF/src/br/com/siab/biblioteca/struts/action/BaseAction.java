package br.com.siab.biblioteca.struts.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class BaseAction extends DispatchAction {
	
	/**
	 * Método responsável pela obtenção do erro da aplicação.
	 * @param e
	 * @param request
	 */
	public void saveErrors(Exception e, HttpServletRequest request) {
		ActionMessages errors = new ActionMessages();
		errors.add("ActionErrors.GLOBAL_ERROR", new ActionMessage(
				"error.action.exception", e));
		request.setAttribute(org.apache.struts.Globals.ERROR_KEY, errors);
	}
}
