<%@ include file="/Paginas/Geral/cabecalho.jsp" %>

<html>
<script language="JavaScript" type="text/JavaScript">
function processarRelatorio(){	
   var valido =  validaPesquisa();		         
   if( valido==true ){		
		document.relatorioForm.acton='<%=contexto%>/relatorioAgente.do?method=pesquisarAgente';
		document.relatorioForm.submit();
	}
	
	return false;
}

function validaPesquisa(){
    var ano = document.relatorioForm.ano.value;
	var agente = document.relatorioForm.cdAgente.value;
	
	if(agente == '') {
		alert('Digite o código do agente!');
		document.relatorioForm.cdAgente.focus();
		return false;
	}
	
	if(ano == '') {
		alert('Digite o ano!');
		document.relatorioForm.ano.focus();
		return false;
	}
	
	if(!isNumber(agente)) {
		alert('O código do agente deve ser numérico!');
		document.relatorioForm.cdAgente.focus();
		return false;
	}
	
	if ( !isNumber(ano)){		  
	   alert('O ano deve ser numérico!');
	   document.relatorioForm.ano.focus();
	   return false;
	} 	
	 
	return true; 	
}

</script>

<body>
<html:form action="/relatorioAgente.do?method=pesquisarAgente" method="POST" focus="cdAgente">
<TABLE cellpadding="0" width="100%"  >
<tr>
 <td width="740" height="18" class="tituloFormulario">
		FICHAS POR AGENTE
  </td>
 <tr>
</TABLE>
<table width="100%" cellspacing="0" cellpadding="5">
<tr>
  <td align="left" class="ptext" >
  <fieldset style="width:80%">
  	<legend>Formulário de Pesquisa</legend>
  		<table width="100%" border="0">
		 <tr> 
            <td width="30%"><div align="right">Agente:</div></td>
            <td colspan="2"> <html:text name="relatorioForm" maxlength="20" property="cdAgente" size="15" />
            </td>
		  </tr>
          <tr> 
            <td><div align="right">Ano:</div></td>
            <td colspan="2"> <html:text name="relatorioForm" maxlength="4" property="ano" size="7" /><div align="center">
              </div></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td width="40%"><input name="Button" type="submit" class="button" onClick="return processarRelatorio()" value="Consultar"></td>
            <td width="41%">&nbsp;</td>
          </tr>
        </table>
	</fieldset>
  </td>
</tr>
</table>
	<html:hidden name="relatorioForm" property="repname" value="RelatorioFichaCrianca"/>
</html:form>
</body>
</html>
