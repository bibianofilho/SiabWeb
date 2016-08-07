package br.com.siab.modulo.relatorio.view.decorator;

import org.displaytag.decorator.TableDecorator;

import br.com.siab.biblioteca.utils.BBValueObject;

/**
 * Classe responsável pela grid de relatorio de família
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public class RelatorioAgenteDecorator extends TableDecorator{
	
	public String getCodigo(){
		BBValueObject vo = (BBValueObject)getCurrentRowObject();
		Integer cdAgente = vo.getInteger(0,"CD_AGENTE");
		Long cdFamilia = vo.getLong(0,"CD_FAMILIA");
		
		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"javascript:imprimirFicha(").append(cdAgente).append(",").append(cdFamilia).append(")\">");
		sb.append(cdFamilia).append("</a>");
		
		return sb.toString();		
	}
	
	public String getEndereco(){
		BBValueObject vo = (BBValueObject)getCurrentRowObject();
		Integer cdAgente = vo.getInteger(0,"CD_AGENTE");
		Long cdFamilia = vo.getLong(0,"CD_FAMILIA");
		String endereco = vo.getString(0,"DS_ENDERECO");
		
		if(endereco == null){
			endereco = "-";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"javascript:imprimirFicha(").append(cdAgente).append(",").append(cdFamilia).append(")\">");
		sb.append(endereco).append("</a>");
		
		return sb.toString();
	}

}
