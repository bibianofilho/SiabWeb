<%@ include file="/Paginas/Geral/cabecalho.jsp" %>

<%

request.setAttribute("TITULO","Pesquisa Usuários Palm");

%>

<html>
<script language="JavaScript" type="text/JavaScript">
function toGoPesquisar(){
	if(validaPesquisa()){
		setParameters(document.agenteForm,'pesquisar','');
		document.agenteForm.submit();
	}
	
	return false;	
}

function validaPesquisa(){
    var codigo = document.agenteForm.codigo.value;
    var nome = document.agenteForm.nome.value;
    var login = document.agenteForm.login.value;
    
    /*if(codigo == '' && nome == '' && login == ''){
    	alert("Preencha pelo menos um critério para pesquisa!");
    	return false;
    }*/
    
    if(codigo != ''){
    	if(!isNumber(codigo)){
    		alert("Código informado deve ser numérico!");
    		document.agenteForm.codigo.focus();
    		return false;
    	}
    }
    
	return true;
}
</script>

<body>
<html:form action="agente" method="post" focus="codigo">
  
  <%@ include file="/Paginas/Agente/menu.jsp" %>

  <table width="100%" cellspacing="0" cellpadding="5">
    <tr>
      <td align="left" class="ptext" >
        <fieldset style="width:80%">
        <legend>Formul&aacute;rio</legend>
        <table width="100%" border="0">
          <tr>
            <td width="30%"><div align="right">C&oacute;digo:</div></td>
            <td colspan="2">
              <html:text name="agenteForm" property="codigo" size="15" />
            </td>
          </tr>
          <tr>
            <td><div align="right">Nome:</div></td>
            <td colspan="2"><html:text name="agenteForm" property="nome" size="20" /></td>
          </tr>
          <tr>
            <td><div align="right">Login:</div></td>
            <td colspan="2">
              <html:text name="agenteForm" property="login" size="20" /></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td width="40%"><input name="Button" type="submit" class="button" onClick="return toGoPesquisar()" value="Consultar"></td>
            <td width="41%">&nbsp;</td>
          </tr>
        </table>
      </fieldset></td>
    </tr>
  </table>
</html:form>

<messages:message />

</body>
</html>
