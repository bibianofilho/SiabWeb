<%@ include file="/Paginas/Geral/cabecalho.jsp" %>

<%

request.setAttribute("TITULO","Incluir Relatório SSA2");
request.setAttribute("PESQUISAR","PESQUISAR");

%>

<script language="javascript">

function pesquisarSegmento() {
    var codSeg = document.relatorioSSA2Form.codSeg.value;
    var url = 'relatorioSSA2.do?method=getSegmento&codSeg=' + codSeg;
	
	if(codSeg != '') {
		var jsonData = syncJsonRequestGetData(url);
		var codZona = jasonGetValueByProperty('codZona',jsonData[0]);
		var nome = jasonGetValueByProperty('nome',jsonData[0]);
		codSeg = jasonGetValueByProperty('codSeg',jsonData[0]);
		
		if(nome != 'null') {
			document.relatorioSSA2Form.codZona.value = codZona;
			document.relatorioSSA2Form.codSeg.value = codSeg;
			document.relatorioSSA2Form.descSeg.value = nome;
		} else {
			document.relatorioSSA2Form.codSeg.value = '';
			document.relatorioSSA2Form.descSeg.value = 'Não Encontrado';
			document.relatorioSSA2Form.codZona.value = '';
		}
	} else {
		limparSegmento();
		limparArea();
		limparMicroa();
	}
}

function limparSegmento(){
	document.relatorioSSA2Form.codSeg.value = '';
	document.relatorioSSA2Form.codZona.value = '';
	document.relatorioSSA2Form.descSeg.value = '';
}

function pesquisarSegmentoPopup() {
	window.open("<%=contexto%>/municipio.do?method=preparaPesquisa","","height=300,width=500,toolbar=no,scrollbars=yes");
}



function pesquisarArea() {
    var codSeg = document.relatorioSSA2Form.codSeg.value;
    var codArea = document.relatorioSSA2Form.codArea.value;
    var url = 'relatorioSSA2.do?method=getArea&codSeg=' + codSeg + '&codArea=' + codArea;
    
    if(codSeg == '') {
    	alert('Selecione o código do segmento.');
    	return;
    }
	
	if(codArea != '') {
		var jsonData = syncJsonRequestGetData(url);
		var nome = jasonGetValueByProperty('nome',jsonData[0]);
		codArea = jasonGetValueByProperty('codArea',jsonData[0]);
		
		if(nome != 'null') {
			document.relatorioSSA2Form.codArea.value = codArea;
			document.relatorioSSA2Form.descArea.value = nome;
		} else {
			document.relatorioSSA2Form.codArea.value = '';
			document.relatorioSSA2Form.descArea.value = 'Não Encontrado';
		}
	} else {
		limparArea();
		limparMicroa();
	}
}

function limparArea() {
	document.relatorioSSA2Form.codArea.value = '';
	document.relatorioSSA2Form.descArea.value = '';
}

function pesquisarAreaPopup() {
	var codSeg = document.relatorioSSA2Form.codSeg.value;
	
	if(codSeg == '') {
    	alert('Selecione o código do segmento.');
    	return;
    }
    
	window.open("<%=contexto%>/equipe.do?method=pesquisaArea&codSeg=" + codSeg ,"","height=300,width=500,toolbar=no,scrollbars=yes");
}

function pesquisarMicroa() {
    var codSeg = document.relatorioSSA2Form.codSeg.value;
    var codArea = document.relatorioSSA2Form.codArea.value;
    var codMicroa = document.relatorioSSA2Form.codMicroa.value;
    var url = 'relatorioSSA2.do?method=getMicroa&codSeg=' + codSeg + '&codArea=' + codArea + '&codMicroa=' + codMicroa;
    
    if(codSeg == '') {
    	alert('Selecione o código do segmento.');
    	return;
    }
    
    if(codArea == '') {
    	alert('Selecione o código da area');
    	return;
    }
	
	if(codMicroa != '') {
		var jsonData = syncJsonRequestGetData(url);
		codMicroa = jasonGetValueByProperty('codMicroa',jsonData[0]);
		var idModelo = jasonGetValueByProperty('idModelo',jsonData[0]);
		var codUB = jasonGetValueByProperty('codUB',jsonData[0]);
		var nome = jasonGetValueByProperty('nome',jsonData[0]);
		
		if(nome != 'null') {
			document.relatorioSSA2Form.codMicroa.value = codMicroa;
			document.relatorioSSA2Form.idModelo.value = idModelo;
			document.relatorioSSA2Form.codUB.value = codUB;
			document.relatorioSSA2Form.descMicroa.value = nome;
		} else {
			document.relatorioSSA2Form.codMicroa.value = '';
			document.relatorioSSA2Form.idModelo.value = '';
			document.relatorioSSA2Form.codUB.value = '';
			document.relatorioSSA2Form.descMicroa.value = 'Não Encontrado';
		}
	} else {
		limparMicroa();
	}
}

function limparMicroa() {
	document.relatorioSSA2Form.codMicroa.value = '';
	document.relatorioSSA2Form.descMicroa.value = '';
	document.relatorioSSA2Form.idModelo.value = '';
	document.relatorioSSA2Form.codUB.value = '';
}

function pesquisarMicroaPopup() {
	var codSeg = document.relatorioSSA2Form.codSeg.value;
	var codArea = document.relatorioSSA2Form.codArea.value;
	
	if(codSeg == '') {
    	alert('Selecione o código do segmento.');
    	return;
    }
    
    if(codArea == ''){
    	alert('Selecione o código da area');
    	return
    }
    
	window.open("<%=contexto%>/equipe.do?method=pesquisaMicroa&codSeg=" + codSeg + "&codArea=" + codArea ,"","height=300,width=500,toolbar=no,scrollbars=yes");
}

function inserirRelatorio() {
	if(!validateRelatorioSSA2Form(document.relatorioSSA2Form)) {
		return;
	}
	
	if(validaInclusao()) {
		setParameters(document.relatorioSSA2Form,'inserir','');
		document.relatorioSSA2Form.submit();
	}
}

function validaInclusao(){
    var ano = document.relatorioSSA2Form.ano.value;
    
    if(!isNumber(ano)){
		alert("Ano deve ser numérico!");
		document.relatorioSSA2Form.ano.focus();
		return false;
	}
	
	if(ano.length != 4){
		alert("Ano deve ter 4 digitos!");
		document.relatorioSSA2Form.ano.focus();
		return false;
	}
    
	return true;
}

</script>

<html>

<body>
<html:form action="relatorioSSA2" method="post">

	<html:hidden property="codZona" />
	<html:hidden property="idModelo" />
	<html:hidden property="codUB" />

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
            <td colspan="2"><html:text property="codSeg" size="2" maxlength="2" onblur="javascript:pesquisarSegmento()" />&nbsp;<img id="lupaSeg" src="<%=contexto%>/images/lupa.gif" onClick="javascript:pesquisarSegmentoPopup()" align="absmiddle" border="0" style="cursor:pointer;">&nbsp;<html:text property="descSeg" size="20" readonly="true" /></td>
          </tr>
		  <!-- area -->
          <tr>
            <td><div align="right">Area:</div></td>
            <td colspan="2"><html:text property="codArea" size="2" maxlength="3" onblur="javascript:pesquisarArea()" />&nbsp;<img id="lupaArea" src="<%=contexto%>/images/lupa.gif" onClick="javascript:pesquisarAreaPopup()" align="absmiddle" border="0" style="cursor:pointer;">&nbsp;<html:text property="descArea" size="20" readonly="true" /></td>
          </tr>
		  <!-- microarea -->
		  <tr>
            <td><div align="right">Microarea:</div></td>
            <td colspan="2"><html:text property="codMicroa" size="2" maxlength="2" onblur="javascript:pesquisarMicroa()" />&nbsp;<img id="lupaMicroa" src="<%=contexto%>/images/lupa.gif" onClick="javascript:pesquisarMicroaPopup()" align="absmiddle" border="0" style="cursor:pointer;">&nbsp;<html:text property="descMicroa" size="40" readonly="true" /></td>
          </tr>
		  <!-- mes -->
		  <tr>
            <td><div align="right">Mês:</div></td>
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
            <td colspan="2"><html:text property="ano" maxlength="4" size="10" /></td>
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
        <legend>Crianças</legend>
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
            <td><div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tiveram diarréia e usaram TRO:</div></td>
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
            <td><div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Consulta de pre-natal no mês:</div></td>
            <td colspan="2"><html:text property="ngespre1" maxlength="6" /></td>
          </tr>
		  <tr>
            <td><div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pre-natal iniciado 1º trimestre:</div></td>
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
        <legend>Doenças</legend>
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
        <legend>Hospitalizações</legend>
		<table width="100%" border="0">
		  <tr>
            <td width="50%"><div align="left">Menores de 5 anos por pneumonia:</div></td>
            <td colspan="2"><html:text property="h_0a5pneu" maxlength="6" /></td>
          </tr>
		   <tr>
            <td><div align="left">Menores de 5 anos por desidratação:</div></td>
            <td colspan="2"><html:text property="h_0a5des" maxlength="6" /></td>
          </tr>
		   <tr>
            <td><div align="left">Por abuso de alcool:</div></td>
            <td colspan="2"><html:text property="h_alcool" maxlength="6" /></td>
          </tr>
		   <tr>
            <td><div align="left">Por complicações do diabetes:</div></td>
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
            <td><div align="left">Pessoas internadas em hospital psiquiátrico:</div></td>
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
            <td><div align="left">10 a 19 anos por violência:</div></td>
            <td colspan="2"><html:text property="obitoadol" maxlength="6" /></td>
		  </tr>
        </table>
	 </fieldset>
	 <br />
	 <fieldset style="width:80%">
        <legend>Famílias</legend>
		<table width="100%" border="0">
		  <tr>
            <td width="50%"><div align="left">Total de Acompanhadas:</div></td>
            <td colspan="2"><html:text property="nfamicad" maxlength="6" /></td>
          </tr>
           <tr>
            <td><div align="left">Número de Visitas:</div></td>
            <td colspan="2"><html:text property="nvisitas" maxlength="6"/></td>
          </tr>
        </table>
	 </fieldset>
	 <table width="100%" cellspacing="0" cellpadding="5">
	  <tr>
		<td>&nbsp;</td>
		<td width="40%"><div align="center">
		      <input name="bntSalvar" type="button" class="button" onClick="javascript:inserirRelatorio()" value="Salvar"> 
		      <input name="button" type="button" class="button" onClick="history.back(-1);" value="Voltar" >
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
