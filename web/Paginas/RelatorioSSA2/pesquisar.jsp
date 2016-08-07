<%@ include file="/Paginas/Geral/cabecalho.jsp" %>

<%

request.setAttribute("TITULO","Pesquisa Relatório SSA2");
request.setAttribute("INCLUIR","INCLUIR");

%>

<html>
<script language="JavaScript" type="text/JavaScript">
function toGoPesquisar(){
	if(validaPesquisa()){
		setParameters(document.relatorioSSA2Form,'pesquisar','');
		document.relatorioSSA2Form.submit();
	}
	
	return false;	
}

function validaPesquisa(){
    var mes = document.relatorioSSA2Form.mes.value;
    var ano = document.relatorioSSA2Form.ano.value;

	if(mes != '') {
		if(!isNumber(mes)) {
			alert("Mês informado deve ser numérico!");
			document.relatorioSSA2Form.mes.focus();
			return false;
		}
	}
	
	if(ano != '') {
		if(!isNumber(ano)) {
			alert("Ano informado deve ser numérico!");
			document.relatorioSSA2Form.ano.focus();
			return false;
		}
	}
       
	return true;
}
</script>

<body>
<html:form action="relatorioSSA2" method="post" focus="codSeg">
  
  <%@ include file="/Paginas/RelatorioSSA2/menu.jsp" %>

  <table width="100%" cellspacing="0" cellpadding="5">
    <tr>
      <td align="left" class="ptext" >
        <fieldset style="width:80%">
        <legend>Formul&aacute;rio</legend>
        <table width="100%" border="0">
		 <!-- segmento -->
          <tr>
            <td><div align="right">Segmento:</div></td>
            <td colspan="2"><html:text property="codSeg" size="10" maxlength="2" /></td>
          </tr>
		  <!-- area -->
		   <tr>
            <td><div align="right">Area:</div></td>
            <td colspan="2"><html:text property="codArea" size="10" maxlength="2" /></td>
          </tr>
		  <!-- microarea -->
		   <tr>
            <td><div align="right">Microarea:</div></td>
            <td colspan="2"><html:text property="codMicroa" size="10" maxlength="2" /></td>
          </tr>
		<!-- mes -->
          <tr>
            <td width="30%"><div align="right">Mês:</div></td>
            <td colspan="2">
             <html:select property="mes">
				<option value=""></option>
				<html:option value="01">Janeiro</html:option>
				<html:option value="02">Fevereiro</html:option>
				<html:option value="03">Março</html:option>
				<html:option value="04">Abril</html:option>
				<html:option value="05">Maio</html:option>
				<html:option value="06">Junho</html:option>
				<html:option value="07">Julho</html:option>
				<html:option value="08">Agosto</html:option>
				<html:option value="09">Setembro</html:option>
				<html:option value="10">Outubro</html:option>
				<html:option value="11">Novembro</html:option>
				<html:option value="12">Dezembro</html:option>
			</html:select>
            </td>
          </tr>
		  <!-- ano -->
          <tr>
            <td><div align="right">Ano:</div></td>
            <td colspan="2"><html:text name="relatorioSSA2Form" property="ano" size="10" maxlength="4" /></td>
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
