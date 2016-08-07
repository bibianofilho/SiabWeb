<%
	String contexto = request.getContextPath();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<%=contexto %>/css/menu.css">
<script language="JavaScript" src="<%=contexto %>/js/menu.js"></script>
</head>

<body>
<center>
<table style='width:100%;'>
<tr>
  <td align="center">
   <script type="text/javascript">

      var Link = new Array();
	  Link[0] = "0|Cadastros";
	  Link[1] = "1|Usuários Palm|<%=contexto%>/agente.do?method=preparaPesquisa|conteudo";
	  Link[2] = "2|Relatório SSA2|<%=contexto%>/relatorioSSA2.do?method=preparaPesquisa|conteudo";
	  Link[3] = "0|Processos";
	  Link[4] = "1|Sincronismo|<%=contexto%>/sincronismo.do?method=preparaSincronismo|conteudo";
	  Link[5] = "0|Fichas por agente";	
	  Link[6] = "1|Agente|<%=contexto%>/relFichaAgente.do|conteudo";
	  Link[7] = "0|Fichas individuais";							  							  
	  Link[8] = "1|Ficha A|<%=contexto%>/relFichaA.do|conteudo";	  
	  Link[9] = "2|Ficha Criança|<%=contexto%>/relFichaCrianca.do|conteudo";  	  
	  Link[10] = "3|Ficha Dia|<%=contexto%>/relFichaDia.do|conteudo";
	  Link[11] = "4|Ficha Ges|<%=contexto%>/relFichaGes.do|conteudo";
	  Link[12] = "5|Ficha Ha|<%=contexto%>/relFichaHa.do|conteudo";
	  Link[13] = "6|Ficha Han|<%=contexto%>/relFichaHan.do|conteudo";
  	  Link[14] = "7|Ficha Idoso|<%=contexto%>/relFichaIdoso.do|conteudo";
  	  Link[15] = "8|Ficha Tb|<%=contexto%>/relFichaTb.do|conteudo";
	  
      start(1);
                            
 </script>
 </td>
</tr>
</table>
</center>
</body>

</html>