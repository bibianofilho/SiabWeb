<%@ include file="/Paginas/Geral/cabecalho.jsp" %>

<%

request.setAttribute("TITULO","Pesquisa Microarea");

%>

<script language="javascript">

function returnValues(str){
	var wObj = window.opener;
	var f = wObj.document.forms[0];
	
	f.codMicroa.value = str.split("&")[0];
	f.idModelo.value = str.split("&")[1];
	f.codUB.value = str.split("&")[2];
	f.descMicroa.value = str.split("&")[3];
		
	toGoFechar();
}

function toGoFechar() {
	window.close();
}

</script>

<html>
<body>

<%@ include file="/Paginas/Equipe/menu.jsp" %>

<html:form action="equipe" method="post">

<table width="100%" cellspacing="0" cellpadding="5">

  <tr> 
    <td width="100%" align="left">
    		
	 <display:table name="LISTA_MICROA" 
                   scope="session" 
                   class="displayTag"
                   pagesize="10"
                   decorator="br.com.siab.modulo.equipe.view.decorator.EquipeMicroaDecorator">
       <display:column property="codigo" title="Código" align="center" />
       <display:column property="nome" title="Descrição" />
    </display:table>
    
    </td>
  </tr>
  
</table>
<table width="100%" cellspacing="0" cellpadding="5">
<tr>
	<td><input name="Button" type="button" class="button" onClick="javascript:toGoFechar()" value="Fechar Janela"></td>
</tr>
</table>
</html:form>
</body>
</html>