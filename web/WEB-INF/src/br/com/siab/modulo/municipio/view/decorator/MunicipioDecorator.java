package br.com.siab.modulo.municipio.view.decorator;

import org.displaytag.decorator.TableDecorator;

import br.com.siab.biblioteca.utils.BBValueObject;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public class MunicipioDecorator extends TableDecorator {
	public String getCodigo() {
		BBValueObject vo = (BBValueObject) getCurrentRowObject();
		String codSeg = vo.getString(0, "COD_SEG");
		String codZona = vo.getString(0, "COD_ZONA");
		String nome = vo.getString(0, "NOME");

		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"#\" onClick=\"javascript:returnValues('").append(
				codSeg).append("&").append(codZona).append("&").append(nome)
				.append("')\">").append(codSeg).append("</a>");
		
		return sb.toString();

	}
	
	public String getNome(){
		BBValueObject vo = (BBValueObject) getCurrentRowObject();
		String nome = vo.getString(0, "NOME");
		
		return nome;
	}
}
