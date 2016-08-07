package br.com.siab.modulo.equipe.view.decorator;

import org.displaytag.decorator.TableDecorator;

import br.com.siab.biblioteca.utils.BBValueObject;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public class EquipeMicroaDecorator extends TableDecorator {
	public String getCodigo() {
		BBValueObject vo = (BBValueObject) getCurrentRowObject();
		String codMicroa = vo.getString(0, "COD_MICROA");
		String idModelo = vo.getString(0,"ID_MODELO");
		String codUB = vo.getString(0,"COD_UB");
		String nome = vo.getString(0, "NOME");

		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"#\" onClick=\"javascript:returnValues('");
		sb.append(codMicroa).append("&").append(idModelo).append("&").append(codUB).append("&").append(nome).append("')\">");
		sb.append(codMicroa).append("</a>");
		
		return sb.toString();

	}
	
	public String getNome(){
		BBValueObject vo = (BBValueObject) getCurrentRowObject();
		String nome = vo.getString(0, "NOME");
		
		return nome;
	}
}
