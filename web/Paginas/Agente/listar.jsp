<%@ include file="/Paginas/Geral/cabecalho.jsp" %>

<%

request.setAttribute("TITULO","Pesquisa Usuários Palm");
request.setAttribute("PESQUISAR","PESQUISAR");

%>

<html>
<body>

<%@ include file="/Paginas/Agente/menu.jsp" %>

<html:form action="agente" method="post">

<table width="100%" cellspacing="0" cellpadding="5">
  <tr> 
    <td width="100%" align="left">   		
	 <display:table name="LISTA_AGENTES" 
                   scope="session" 
                   class="displayTag"
                   pagesize="8"
                   decorator="br.com.siab.modulo.agente.view.decorator.AgenteDecorator">
       <display:column property="editar" title="-" align="center" />
	   <display:column property="codigo" align="center" title="Cód." sortable="true" />
       <display:column property="login" align="center" title="Login" sortable="true"/>
       <display:column property="nome" title="Nome Usuário" sortable="true"/>       
    </display:table>
    </td>
  </tr>
</table>
</html:form>
</body>
</html>