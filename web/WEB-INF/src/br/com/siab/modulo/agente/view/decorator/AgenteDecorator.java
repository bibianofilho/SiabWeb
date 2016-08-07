package br.com.siab.modulo.agente.view.decorator;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.decorator.TableDecorator;

import br.com.siab.modulo.agente.model.AgenteVO;


/**
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public class AgenteDecorator extends TableDecorator{
	
	public String getEditar(){
		AgenteVO agenteVO = (AgenteVO) getCurrentRowObject();
		Integer id = agenteVO.getCodigo();
		
		StringBuffer strUrl = new StringBuffer();
		
		HttpServletRequest request = (HttpServletRequest)this.getPageContext().getRequest();
        String contexto = request.getContextPath();
        
        strUrl.append(contexto).append("/agente.do?method=preparaEdicao&id=").append(id);
        
        return 
        "<a href='"+strUrl.toString()+"' title=\"Editar Agente\" >"+		
		"<img src='"+contexto+"/images/bt_editar.gif' border='0' >"+
		"</a>";
	}
}
