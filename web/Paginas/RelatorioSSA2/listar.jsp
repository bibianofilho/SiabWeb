<%@ include file="/Paginas/Geral/cabecalho.jsp" %>

<%

request.setAttribute("TITULO","Pesquisa Relatório SSA2");
request.setAttribute("PESQUISAR","PESQUISAR");

%>

<html>
<body>

<html:form action="relatorioSSA2" method="post">

<%@ include file="/Paginas/RelatorioSSA2/menu.jsp" %>

<table width="100%" cellspacing="0" cellpadding="5">

  <tr> 
    <td width="100%" align="left">
    		
	 <display:table name="LISTA_RELATORIO_SSA2" 
                   scope="session" 
                   class="displayTag"
                   pagesize="8"
                   decorator="br.com.siab.modulo.relatoriossa2.view.decorator.RelatorioSSA2Decorator">
       <display:column property="editar" title="-" align="center" />
	   <display:column property="exportar" title="-" align="center" />	   
	   <display:column property="exportado" align="center" title="Situação" />
	   <display:column property="codSeg" align="center" title="Segmento" />
       <display:column property="codArea" align="center" title="Area" />
       <display:column property="codMicroa" align="center" title="Microarea" />   
	   <display:column property="mes" align="center" title="Mês" />
	   <display:column property="ano" align="center" title="Ano" />    
    </display:table>
    
    </td>
  </tr>
  
</table>
</html:form>
</body>
</html>
