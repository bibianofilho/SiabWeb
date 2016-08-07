<%@ include file="/Paginas/Geral/cabecalho.jsp" %>

<%

request.setAttribute("TITULO","Sincronismo SIAB");

%>

<html>
<script language="JavaScript" type="text/JavaScript">
function toGoImportar(){
	if(validaInclusao()){
		
		if(callConfirm('Feche o aplicativo siab antes de efetuar o sincronismo. Deseja continuar?')) {
			document.sincronismoForm.btnSincronismo.disabled = true;
			document.getElementById('PopupDiv').style.display = "block";
			setParameters(document.sincronismoForm,'sincronismo','');
			document.sincronismoForm.submit();
		}	
	}
	
	return false;
	
}

function validaInclusao(){
    var ano = document.sincronismoForm.ano.value;
    
    if(ano == '') {
		alert("Digite o ano!");
		document.sincronismoForm.ano.focus();
		return false;
	}
	
	if(!isNumber(ano)){
		alert("Ano deve ser numérico!");
		document.sincronismoForm.ano.focus();
		return false;
	}
	
	if(ano.length != 4){
		alert("Ano deve ter 4 digitos!");
		document.sincronismoForm.ano.focus();
		return false;
	}
    
	return true;
}
</script>

<body>
<html:form action="sincronismo" method="post">
  
  <%@ include file="/Paginas/Sincronismo/menu.jsp" %>

  <table width="100%" cellspacing="0" cellpadding="5">
    <tr>
      <td align="left" class="ptext" >
        <fieldset style="width:80%">
        <legend>Dados Gerais</legend>
        <table width="100%" border="0">
          <!-- ano -->
		  <tr>
            <td width="30%"><div align="right">Ano:</div></td>
            <td colspan="2"><html:text name="sincronismoForm" property="ano" size="10" maxlength="4" readonly="true" /></td>
          </tr>
		   <tr>
            <td>&nbsp;</td>
            <td width="40%"><input name="btnSincronismo" type="submit" class="button" onClick="return toGoImportar()" value="Efetuar Sincronismo"></td>
            <td width="41%">&nbsp;</td>
          </tr>
        </table>
      </fieldset></td>
    </tr>
  </table>
</html:form>

<!-- DIV AGUARDE -->
<div id="PopupDiv" style="position:absolute; width:300px; height:105px; top:100px; left:20%; background-color:#FFFFFF; display:none; color:#000000; z-index:100"> 
  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr> 
      <td height="1" bgcolor="#666666"></td>
    </tr>
  </table>
  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr> 
      <td width="1" bgcolor="#666666"></td>
      <td> <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td height="105"> <div align="right"><img src="<%=contexto%>/images/search.gif" width="50" height="50">&nbsp;&nbsp;</div></td>
            <td><span class="texto-form-preto">Sincronizando Dados</span><br> <span class="texto-form-preto">Aguarde...</span></td>
          </tr>
        </table></td>
      <td width="1" bgcolor="#666666"></td>
    </tr>
  </table>
  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr> 
      <td height="1" bgcolor="#666666"></td>
    </tr>
  </table>
</div>

<messages:message />

</body>
</html>
