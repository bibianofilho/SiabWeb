package br.com.siab.modulo.sincronismo.view.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.com.siab.biblioteca.utils.Utils;

/**
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public class SincronismoForm extends ActionForm {

	private static final long serialVersionUID = 8375460813368432100L;
	
	private String ano;

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		Date date = new Date();
		String strDate = Utils.formataData(date);
		this.ano = strDate.substring(6,strDate.length()); 
	}
	
	
}
