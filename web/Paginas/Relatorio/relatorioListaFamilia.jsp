<%@ include file="/Paginas/Geral/cabecalho.jsp" %>

<html>
<body>
<script>
function imprimirFicha(agente,familia){
	var url = '<%=contexto%>/relatorioAgente.do?method=fichaA&cdAgente=' + agente + '&cdFamilia=' + familia;
	callTelaRelatorio(url);
}	
</script>
<html:form action="relatorioAgente" method="post">

<table width="100%" cellspacing="0" cellpadding="5">
  <tr> 
    <td width="100%" align="left"> 
	<a href="#" onClick="javascript:parent.prepareDiv('divPesquisa')">Fechar Janela</a> <br/>  		
	 <display:table name="LISTA_AGENTE_FAMILIAS" 
                   scope="session" 
                   class="displayTag"
                   decorator="br.com.siab.modulo.relatorio.view.decorator.RelatorioAgenteDecorator">
             <display:column property="codigo" align="center" title="Código" /> 
			<display:column property="endereco" align="center" title="Endereço" />       
    </display:table>
	<a href="#" onClick="javascript:parent.prepareDiv('divPesquisa')">Fechar Janela</a> <br/>
    </td>
  </tr>
</table>

</html:form>

</body>
</html>