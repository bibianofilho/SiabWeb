<%@ include file="/Paginas/Geral/cabecalho.jsp" %>

<%

request.setAttribute("TITULO","Editar Relat�rio SSA2");
request.setAttribute("PESQUISAR","PESQUISAR");

%>

<script language="javascript">


function editarRelatorio() {
	if(!validateRelatorioSSA2Form(document.relatorioSSA2Form)) {
		return;
	}
	
	setParameters(document.relatorioSSA2Form,'editar','');
	document.relatorioSSA2Form.submit();
}

function selecionarMes() {
	var mes = document.relatorioSSA2Form.mes.value;
	var desc = "";
	
	switch(mes) {
		case "01":
			desc = "Janeiro";
			break;
		case "02":
			desc = "Fevereiro";
			break;
		case "03":
			desc = "Mar�o";
			break;
		case "04":
			desc = "Abril";
			break;
		case "05":
			desc = "Maio";
			break;
		case "06":
			desc = "Junho";
			break;
		case "07":
			desc = "Julho";
			break;
		case "08":
			desc = "Agosto";
			break;
		case "09":
			desc = "Setembro";
			break;
		case "10":
			desc = "Outubro";
			break;
		case "11":
			desc = "Novembro";
			break;
		case "12":
			desc = "Dezembro";
			break;
	}
	
	document.relatorioSSA2Form.strMes.value = desc;
}

</script>

<html>

<body>
<html:form action="relatorioSSA2" method="post">

	<html:hidden property="codZona" />
	<html:hidden property="idModelo" />
	<html:hidden property="codUB" />
	<html:hidden property="mes" />
	<html:hidden property="exportado" />

 <%@ include file="/Paginas/RelatorioSSA2/menu.jsp" %>
 
 <table width="100%" cellspacing="0" cellpadding="5">
    <tr>
      <td align="left" class="ptext" >
        <fieldset style="width:80%">
        <legend>Dados Gerais</legend>
        <table width="100%" border="0">
          <!-- segmento -->
		  <tr>
            <td width="20%"><div align="right">Segmento:</div></td>
            <td colspan="2"><html:text property="codSeg" size="2" maxlength="2" readonly="true" />&nbsp;<html:text property="descSeg" size="20" readonly="true" /></td>
          </tr>
		  <!-- area -->
          <tr>
            <td><div align="right">Area:</div></td>
            <td colspan="2"><html:text property="codArea" size="2" maxlength="3" readonly="true" />&nbsp;<html:text property="descArea" size="20" readonly="true" /></td>
          </tr>
		  <!-- microarea -->
		  <tr>
            <td><div align="right">Microarea:</div></td>
            <td colspan="2"><html:text property="codMicroa" size="2" maxlength="2" readonly="true" />&nbsp;<html:text property="descMicroa" size="40" readonly="true" /></td>
          </tr>
		  <!-- mes -->
		  <tr>
            <td><div align="right">M�s:</div></td>
            <td colspan="2">
			<input type="text" name="strMes" readonly="true" size="10">
			<script>selecionarMes();</script>
			</td>
          </tr>
		  <!-- ano -->
		  <tr>
            <td><div align="right">Ano:</div></td>
            <td colspan="2"><html:text property="ano" maxlength="4" size="10" readonly="true" /></td>
          </tr>
        </table>
      </fieldset>
	  <br />
	 <fieldset style="width:80%">
        <legend>Recem-Nascidos</legend>
		<table width="100%" border="0">
		  <tr>
            <td width="50%"><div align="left">Nascidos vivos no mes:</div></td>
            <td colspan="2"><html:text property="nascvivo" maxlength="6" /></td>
          </tr>
          <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;RN pesados ao nascer:</div></td>
            <td colspan="2"><html:text property="pesados" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RN com peso < 2500g:</div></td>
            <td colspan="2"><html:text property="peso2500" maxlength="6" /></td>
          </tr>
        </table>
	 </fieldset>
	  <br />
	 <fieldset style="width:80%">
        <legend>Crian�as</legend>
		<table width="100%" border="0">
		  <tr>
            <td width="50%"><div align="left">De 0 a 3 meses e 29 dias:</div></td>
            <td colspan="2"><html:text property="c_4meses" /></td>
          </tr>
          <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Aleitamento Exclusivo:</div></td>
            <td colspan="2"><html:text property="c_mamand" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Aleitamento Misto:</div></td>
            <td colspan="2"><html:text property="c_misto" maxlength="6" /></td>
          </tr>
		   <tr>
            <td><div align="left">De 0 a 11 meses e 29 dias:</div></td>
            <td colspan="2"><html:text property="c_0a11" maxlength="6" /></td>
          </tr>
          <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Com vacinas em dia:</div></td>
            <td colspan="2"><html:text property="c_vacdia" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Pesadas:</div></td>
            <td colspan="2"><html:text property="c_0a112p" maxlength="6" /></td>
          </tr>
		   <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Desnutridas:</div></td>
            <td colspan="2"><html:text property="c_0a11gp" maxlength="6" /></td>
          </tr>
		   <tr>
            <td><div align="left">De 12 a 23 meses e 29 dias:</div></td>
            <td colspan="2"><html:text property="c_1223" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Com vacinas em dia:</div></td>
            <td colspan="2"><html:text property="c_vacina" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Pesadas:</div></td>
            <td colspan="2"><html:text property="c_12232p" maxlength="6" /></td>
          </tr>
		   <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Desnutridas:</div></td>
            <td colspan="2"><html:text property="c_1223gp" maxlength="6" /></td>
          </tr>
		   <tr>
            <td width="30%"><div align="left"><strong>Menores de 2 anos</strong></div></td>
            <td colspan="2"></td>
          </tr>
		   <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Que tiveram diarreia:</div></td>
            <td colspan="2"><html:text property="c_diarre" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tiveram diarr�ia e usaram TRO:</div></td>
            <td colspan="2"><html:text property="c_diasro" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Que tiveram IRA:</div></td>
            <td colspan="2"><html:text property="c_ira" maxlength="6" /></td>
          </tr>
        </table>
	 </fieldset>
	   <br />
	 <fieldset style="width:80%">
        <legend>Gestantes</legend>
		<table width="100%" border="0">
		  <tr>
            <td width="50%"><div align="left">Cadastradas:</div></td>
            <td colspan="2"><html:text property="ngesCad" maxlength="6" /></td>
          </tr>
          <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Acompanhadas:</div></td>
            <td colspan="2"><html:text property="ngesac" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Com vacina em dia:</div></td>
            <td colspan="2"><html:text property="ngesvac" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Consulta de pre-natal no m�s:</div></td>
            <td colspan="2"><html:text property="ngespre1" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pre-natal iniciado 1� trimestre:</div></td>
            <td colspan="2"><html:text property="ngespre2" maxlength="6"/></td>
          </tr>
		   <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Menores de 20 anos cadastradas:</div></td>
            <td colspan="2"><html:text property="ngesm20" maxlength="6" /></td>
          </tr>
        </table>
	 </fieldset>
	   <br />
	 <fieldset style="width:80%">
        <legend>Doen�as</legend>
		<table width="100%" border="0">
		  <tr>
            <td width="50%"><div align="left"><strong>Diabeticos</strong></div></td>
            <td colspan="2"></td>
          </tr>
          <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Cadastrados:</div></td>
            <td colspan="2"><html:text property="d_diabete" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Acompanhados:</div></td>
            <td colspan="2"><html:text property="d_diaac" maxlength="6" /></td>
          </tr>
		  <tr>
            <td width="30%"><div align="left"><strong>Hipertensos</strong></div></td>
            <td colspan="2"></td>
          </tr>
          <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Cadastrados:</div></td>
            <td colspan="2"><html:text property="d_hiperten" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Acompanhados:</div></td>
            <td colspan="2"><html:text property="d_hiperac" maxlength="6" /></td>
          </tr>
		  <tr>
            <td width="30%"><div align="left"><strong>Pessoas com tuberculose</strong></div></td>
            <td colspan="2"></td>
          </tr>
          <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Cadastrados:</div></td>
            <td colspan="2"><html:text property="d_tubercul" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Acompanhados:</div></td>
            <td colspan="2"><html:text property="d_tuberac" maxlength="6" /></td>
          </tr>
		  <tr>
            <td width="30%"><div align="left"><strong>Pessoas com hanseniase</strong></div></td>
            <td colspan="2"></td>
          </tr>
          <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Cadastrados:</div></td>
            <td colspan="2"><html:text property="d_hansen" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Acompanhados:</div></td>
            <td colspan="2"><html:text property="d_hanseac" maxlength="6" /></td>
          </tr>
        </table>
	 </fieldset>
	  <br />
	 <fieldset style="width:80%">
        <legend>Hospitaliza��es</legend>
		<table width="100%" border="0">
		  <tr>
            <td width="50%"><div align="left">Menores de 5 anos por pneumonia:</div></td>
            <td colspan="2"><html:text property="h_0a5pneu" maxlength="6" /></td>
          </tr>
		   <tr>
            <td><div align="left">Menores de 5 anos por desidrata��o:</div></td>
            <td colspan="2"><html:text property="h_0a5des" maxlength="6" /></td>
          </tr>
		   <tr>
            <td><div align="left">Por abuso de alcool:</div></td>
            <td colspan="2"><html:text property="h_alcool" maxlength="6" /></td>
          </tr>
		   <tr>
            <td><div align="left">Por complica��es do diabetes:</div></td>
            <td colspan="2"><html:text property="h_diabete" maxlength="6" /></td>
          </tr>
		   <tr>
            <td><div align="left">Por outras causas:</div></td>
            <td colspan="2"><html:text property="h_outcau" maxlength="6" /></td>
          </tr>
		   <tr>
            <td>&nbsp;</td>
            <td colspan="2">&nbsp;</td>
          </tr>
		   <tr>
            <td><div align="left">Pessoas internadas em hospital psiqui�trico:</div></td>
            <td colspan="2"><html:text property="h_psiqui" maxlength="6" /></td>
          </tr>
        </table>
	 </fieldset>
	  <br />
	 <fieldset style="width:80%">
        <legend>Obitos</legend>
		<table width="100%" border="0">
		  <tr>
            <td width="50%"><div align="left"><strong>Obitos de &lt; 28 dias </strong></div></td>
            <td colspan="2">&nbsp;</td>
		  </tr>
          <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Diarreia:</div></td>
            <td colspan="2"><html:text property="o_dia0a28" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;IRA:</div></td>
            <td colspan="2"><html:text property="o_ira0a28" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Outras causas:</div></td>
            <td colspan="2"><html:text property="o_cau0a28" maxlength="6" /></td>
          </tr>
		  <tr>
            <td width="30%"><div align="left"><strong>Obitos de 28 dias a 11 meses e 29 dias</strong></div></td>
            <td colspan="2">&nbsp;</td>
		  </tr>
          <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Diarreia:</div></td>
            <td colspan="2"><html:text property="o_dia28a1" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;IRA:</div></td>
            <td colspan="2"><html:text property="o_ira28a1" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Outras causas:</div></td>
            <td colspan="2"><html:text property="o_cau28a1" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left"><strong>Obitos de menores de 1 ano</strong></div></td>
            <td colspan="2">&nbsp;</td>
		  </tr>
          <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Diarreia:</div></td>
            <td colspan="2"><html:text property="obitodia" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;IRA:</div></td>
            <td colspan="2"><html:text property="obitoira" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;Outras causas:</div></td>
            <td colspan="2"><html:text property="obitocau" maxlength="6" /></td>
          </tr>
		  <tr>
            <td>&nbsp;</td>
            <td colspan="2">&nbsp;</td>
          </tr>
		   <tr>
            <td><div align="left">Mulheres de 10 a 14:</div></td>
            <td colspan="2"><html:text property="o_mul10a14" maxlength="6" /></td>
		  </tr>
		  <tr>
            <td><div align="left">Mulheres de 15 a 49:</div></td>
            <td colspan="2"><html:text property="obitomul" maxlength="6" /></td>
		  </tr>
		  <tr>
            <td><div align="left">Outros Obitos:</div></td>
            <td colspan="2"><html:text property="obitoout" maxlength="6" /></td>
		  </tr>
		  <tr>
            <td><div align="left">10 a 19 anos por viol�ncia:</div></td>
            <td colspan="2"><html:text property="obitoadol" maxlength="6" /></td>
		  </tr>
        </table>
	 </fieldset>
	 <br />
	 <fieldset style="width:80%">
        <legend>Fam�lias</legend>
		<table width="100%" border="0">
		  <tr>
            <td width="50%"><div align="left">Total de Acompanhadas:</div></td>
            <td colspan="2"><html:text property="nfamicad" maxlength="6" /></td>
          </tr>
           <tr>
            <td><div align="left">N�mero de Visitas:</div></td>
            <td colspan="2"><html:text property="nvisitas" maxlength="6"/></td>
          </tr>
        </table>
	 </fieldset>
	 <table width="100%" cellspacing="0" cellpadding="5">
	  <tr>
		<td>&nbsp;</td>
		<td width="40%"><div align="center">
		<logic:equal name="relatorioSSA2Form" property="exportado" value="N">
			  <input name="btnSalvar" type="button" class="button" onClick="javascript:editarRelatorio()" value="Salvar">
		</logic:equal>
		      <input name="btnVoltar" type="button" class="button" onClick="history.back(-1);" value="Voltar" >
		  </div></td>
		<td width="41%">&nbsp;</td>
	  </tr>
	 </table>
	  </td>
    </tr>
  </table>
</html:form>

<messages:message />
</body>

<html:javascript formName="relatorioSSA2Form" staticJavascript="false" cdata="false" />

</html>
