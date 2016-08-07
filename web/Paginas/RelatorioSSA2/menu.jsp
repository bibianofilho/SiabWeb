<html>
<script language="JavaScript" type="text/JavaScript">
function pesquisar() {
	toGo('<%=contexto%>/relatorioSSA2.do?method=preparaPesquisa');
}

function incluir() {
	toGo('<%=contexto%>/relatorioSSA2.do?method=preparaInsercao');
}

</script>

<body>
<TABLE cellpadding="0" width="100%"  >
<tr>
  <td width="740" height="18" class="tituloFormulario"> <%=request.getAttribute("TITULO")%> </td>
<tr>
</TABLE>

<table>
<tr>
<% if( request.getAttribute("PESQUISAR") != null ) { %>
 <!-- PESQUISAR -->
 <td width="5px"><img src="<%=contexto%>/images/bt_pesquisar.gif" border="0" ></td>
 <td width="56px"><a href="javascript:pesquisar()">Pesquisar</a></td>
 <td width="5px"></td>
<% } %>

<% if( request.getAttribute("INCLUIR") != null ) { %> 
  <!-- INCLUIR -->
 <td width="5px"><img src="<%=contexto%>/images/bt_incluir.gif" border="0" ></td>
 <td width="56px"><a href="javascript:incluir()">Incluir</a></td>
 <td width="5px"></td>
<% } %>

</tr>
</table>
</body>
</html>
