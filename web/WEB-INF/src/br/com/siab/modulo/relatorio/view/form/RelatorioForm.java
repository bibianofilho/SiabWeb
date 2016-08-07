package br.com.siab.modulo.relatorio.view.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.com.siab.biblioteca.utils.Utils;

/**
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public class RelatorioForm extends ActionForm {
    
    private String metodo;
    private String repname;
    /** Ano */
    private String ano;
    /** Código do agente */
    private String cdAgente;
    
    /** Código da família */
    private String cdFamilia;
    
	public String getMetodo() {
		return metodo;
	}
	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}
	public String getRepname() {
		return repname;
	}
	public void setRepname(String repname) {
		this.repname = repname;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public String getCdAgente() {
		return cdAgente;
	}
	public void setCdAgente(String cdAgente) {
		this.cdAgente = cdAgente;
	}
	public String getCdFamilia() {
		return cdFamilia;
	}
	public void setCdFamilia(String cdFamilia) {
		this.cdFamilia = cdFamilia;
	} 
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		Date date = new Date();
		String strDate = Utils.formataData(date);
		this.ano = strDate.substring(6,strDate.length()); 
	}
		
}
