<%@ include file="/Paginas/Geral/cabecalho.jsp" %>

<html>

<body>

<script>
function pesquisar() {
	toGo('<%=contexto%>/relFichaAgente.do');
}

function imprimirFichaA() {
	document.relatorioForm.target = 'iFrameFamilia';
	document.relatorioForm.action = '<%=contexto%>/relatorioAgente.do?method=listarFamilia';
	document.relatorioForm.submit();
	
	prepareDiv('divPesquisa', 'show', '15px', '20px');
}

function imprimirFichaCrianca(agente,ano) {
	var url = '<%=contexto%>/relatorioAgente.do?method=imprimirFicha&ficha=fichaCrianca&cdAgente=' + agente + '&ano=' + ano;
	callTelaRelatorio(url);
}

function imprimirFichaDia(agente,ano) {
	var url = '<%=contexto%>/relatorioAgente.do?method=imprimirFicha&ficha=fichaDia&cdAgente=' + agente + '&ano=' + ano;
	callTelaRelatorio(url);
}

function imprimirFichaGes(agente,ano) {
	var url = '<%=contexto%>/relatorioAgente.do?method=imprimirFicha&ficha=fichaGes&cdAgente=' + agente + '&ano=' + ano;
	callTelaRelatorio(url);
}

function imprimirFichaHa(agente,ano) {
	var url = '<%=contexto%>/relatorioAgente.do?method=imprimirFicha&ficha=fichaHa&cdAgente=' + agente + '&ano=' + ano;
	callTelaRelatorio(url);
}

function imprimirFichaHan(agente,ano) {
	var url = '<%=contexto%>/relatorioAgente.do?method=imprimirFicha&ficha=fichaHan&cdAgente=' + agente + '&ano=' + ano;
	callTelaRelatorio(url);
}

function imprimirFichaIdoso(agente,ano) {
	var url = '<%=contexto%>/relatorioAgente.do?method=imprimirFicha&ficha=fichaIdoso&cdAgente=' + agente + '&ano=' + ano;
	callTelaRelatorio(url);
}

function imprimirFichaTb(agente,ano) {
	var url = '<%=contexto%>/relatorioAgente.do?method=imprimirFicha&ficha=fichaTb&cdAgente=' + agente + '&ano=' + ano;
	callTelaRelatorio(url);
}
</script>


<html:form action="relatorioAgente" method="POST" >
<TABLE cellpadding="0" width="100%"  >
<tr>
 <td width="740" height="18" class="tituloFormulario">
		FICHAS POR AGENTE
  </td>
 <tr>
</TABLE>
<table>
<tr>
 <td width="5px"><img src="<%=contexto%>/images/bt_pesquisar.gif" border="0" ></td>
 <td width="56px"><a href="javascript:pesquisar()">Pesquisar</a></td>
 <td width="5px"></td>
</tr>
</table>
<table width="100%" cellspacing="0" cellpadding="5">
<tr>
  <td align="left" class="ptext" >
  	 <fieldset style="width:80%">
        <legend>Fichas</legend>
		<base:treeagente />
	 </fieldset>
  </td>
</tr>
</table>
<div class="divPesquisa" id="divPesquisa" style="height:220px; width:580px; display:none;"> 
 <iframe name="iFrameFamilia" src="" width="580" height="220" frameborder="0" scrolling="yes"> 
            Seu navegador n&atilde;o suporta Iframes </iframe>
</div>	
</html:form>
</body>
</html>
