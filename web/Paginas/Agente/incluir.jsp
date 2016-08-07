<%@ include file="/Paginas/Geral/cabecalho.jsp" %>

<%

request.setAttribute("TITULO","Inclusão de Usuários Palm");
request.setAttribute("PESQUISAR","PESQUISAR");

%>

<html>
<script language="JavaScript" type="text/JavaScript">
function toGoSalvar(){
	if(validaInclusao()){
		setParameters(document.agenteForm,'inserir','');
		document.agenteForm.submit();
	}
	
}

function validaInclusao(){
    var nome = document.agenteForm.nome.value;
    var login = document.agenteForm.login.value;
	var senha = document.agenteForm.senha.value;
	var resenha = document.agenteForm.resenha.value;
    
    if(nome == '') {
		alert("Digite o nome do agente!");
		document.agenteForm.nome.focus();
		return false;
	}
	
	if(login == ''){
		alert("Digite o login do agente!");
		document.agenteForm.login.focus();
		return false;
	}
    
    if(senha == ''){
		alert("Digite a senha do agente!");
		document.agenteForm.senha.focus();
		return false;
	}
	
	if(resenha == ''){
		alert("Digite a confirmação da senha do agente!");
		document.agenteForm.resenha.focus();
		return false;
	}
	
	if(senha != resenha) {
		alert("Senha não confere. Digite novamente!");
		document.agenteForm.senha.value = '';
		document.agenteForm.resenha.value = '';
		document.agenteForm.senha.focus();
		return false;		
	}
    
	return true;
}
</script>

<body>
<html:form action="agente" method="post">
  
  <%@ include file="/Paginas/Agente/menu.jsp" %>

  <table width="100%" cellspacing="0" cellpadding="5">
    <tr>
      <td align="left" class="ptext" >
        <fieldset style="width:80%">
        <legend>Dados do Agente</legend>
        <table width="100%" border="0">
          <!-- nome -->
		  <tr>
            <td width="30%"><div align="right">Nome:</div></td>
            <td colspan="2"><html:text name="agenteForm" property="nome" size="30" onblur="upperCase(this)" /></td>
          </tr>
		  <!-- login -->
          <tr>
            <td><div align="right">Login:</div></td>
            <td colspan="2">
              <html:text name="agenteForm" property="login" size="20" /></td>
          </tr>
		  <!-- senha -->
		  <tr>
            <td><div align="right">Senha:</div></td>
            <td colspan="2">
              <html:password name="agenteForm" property="senha" size="20" maxlength="10" /></td>
          </tr>
		  <!-- resenha -->
		  <tr>
            <td><div align="right">Redigite a senha:</div></td>
            <td colspan="2">
            	<html:password name="agenteForm" property="resenha" size="20" maxlength="10" />
			</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td width="40%"><input name="Button" type="button" class="button" onClick="javascript:toGoSalvar()" value="Salvar"></td>
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
