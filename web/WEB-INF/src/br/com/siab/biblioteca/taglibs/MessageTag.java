package br.com.siab.biblioteca.taglibs;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.Resources;

import br.com.siab.biblioteca.exception.BaseException;

/**
 * Tag responsável pela renderização das mensagens de erro.
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class MessageTag extends TagSupport {

	public MessageTag() {
		super();
	}
	
	public int doEndTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			StringBuffer erro = getMessagem();

			if (erro != null) {
				out.print(erro.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * Obtém a mensagem de erro da requisição.
	 * 
	 * @return - mensagem
	 */
	private StringBuffer getMessagem() throws JspException {

		Object oErrors = null;
		ActionMessages listaErros = null;
		ActionMessage erro = null;
		StringBuffer mensagens = new StringBuffer();
		oErrors = pageContext.getRequest().getAttribute(
				org.apache.struts.Globals.ERROR_KEY);
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

		if (oErrors instanceof ActionMessages) {
			listaErros = (ActionMessages) oErrors;
			Iterator iterErrors = listaErros.get();

			while (iterErrors.hasNext()) {
				erro = (ActionMessage) iterErrors.next();
				Object[] arrerros = erro.getValues();

				/*
				 * Para erro de banco
				 */
				if ("error".equals(erro.getKey())) {
					Exception exception = (Exception) arrerros[0];

					if (exception instanceof BaseException) {
						mensagens.append(((BaseException) exception)
								.getFormatedMessage());
					} else {
						mensagens.append(exception.getMessage());
					}
				} else {
					/*
					 * Para erro de validação de dados do formulário
					 */
					MessageResources resources = Resources.getMessageResources(request);
					String mensagem = resources.getMessage(erro.getKey(),erro.getValues());
					mensagens.append(mensagem);
				}
			}
		}
		return mensagens;

	}
}
