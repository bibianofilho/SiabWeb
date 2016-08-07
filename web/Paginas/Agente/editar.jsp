<%@ include file="/Paginas/Geral/cabecalho.jsp" %>

<%

request.setAttribute("TITULO","Edi��o de Usu�rios Palm");
request.setAttribute("PESQUISAR","PESQUISAR");

%>

<html>
<script language="JavaScript" type="text/JavaScript">
function toGoSalvar(){
	if(validaEdicao()){
		setParameters(document.agenteForm,'editar','');
		document.agenteForm.submit();
	}
	
}

function toGoGerarDados() {
	var codigo = document.agenteForm.codigo.value;

	if(callConfirm("Deseja confirmar a gera��o de dados para o agente?")){
		setParameters(document.agenteForm,'gerarDados','id='+codigo);
		document.agenteForm.submit();
	}
}

function validaEdicao(){
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
		alert("Digite a confirma��o da senha do agente!");
		document.agenteForm.resenha.focus();
		return false;
	}
	
	if(senha != resenha) {
		alert("Senha n�o confere. Digite novamente!");
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

  <html:hidden name="agenteForm" property="codigo" />
  
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
            <td><html:text name="agenteForm" property="nome" size="30" onblur="upperCase(this)" /></td>
          </tr>
		  <!-- login -->
          <tr>
            <td><div align="right">Login:</div></td>
            <td>
              <html:text name="agenteForm" property="login" size="20" />
			</td>
          </tr>
		  <!-- senha -->
		  <tr>
            <td><div align="right">Senha:</div></td>
            <td>
              <html:password name="agenteForm" property="senha" size="20" maxlength="10" /></td>
          </tr>
		  <!-- resenha -->
		  <tr>
            <td><div align="right">Redigite a senha:</div></td>
            <td>
            	<html:password name="agenteForm" property="resenha" size="20" maxlength="10" />
			</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td><input name="Button" type="button" class="button" onClick="javascript:toGoSalvar()" value="Salvar">
            <input name="Button2" type="button" class="button" onClick="javascript:toGoGerarDados()" value=" Gerar Dados ">            <input name="button" type="button" class="button" onClick="history.back(-1);" value="Voltar" ></td>
          </tr>
        </table>
      </fieldset></td>
    </tr>
  </table>
</html:form>

<messages:message />

</body>
</html>
