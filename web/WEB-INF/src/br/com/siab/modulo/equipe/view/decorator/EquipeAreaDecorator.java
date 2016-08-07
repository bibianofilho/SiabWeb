package br.com.siab.modulo.equipe.view.decorator;

import org.displaytag.decorator.TableDecorator;

import br.com.siab.biblioteca.utils.BBValueObject;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public class EquipeAreaDecorator extends TableDecorator {
	public String getCodigo() {
		BBValueObject vo = (BBValueObject) getCurrentRowObject();
		String codArea = vo.getString(0, "COD_AREA");
		String nome = vo.getString(0, "NOME_AREA");

		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"#\" onClick=\"javascript:returnValues('").append(
				codArea).append("&").append(nome).append("')\">").append(codArea).append("</a>");
		
		return sb.toString();

	}
	
	public String getNome(){
		BBValueObject vo = (BBValueObject) getCurrentRowObject();
		String nome = vo.getString(0, "NOME_AREA");
		
		return nome;
	}
}
