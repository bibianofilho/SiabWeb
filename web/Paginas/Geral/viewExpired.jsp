<%
String contexto = request.getContextPath();
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Sistema de Patrimônio</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=contexto%>/css/all.css" rel="stylesheet" type="text/css">
<link rel=stylesheet href="<%=contexto%>/css/estilo.css" type=text/css >
<script language="JavaScript">

function closeWindow(){
window.returnvalue = "0"; 
window.close();

}

</script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<TABLE>
<TBODY>


  <tr > 
    <td width="68" height="77" align="left" valign="middle">
	<img src="<%=contexto%>/images/mg_erro.gif" >
	</td>
    <td width="100%" align="left" valign="middle" class="alertscreen-text">   
		<UL>
		<LI>A sessão do usuário foi desativada. Para retornar ao funcionamento normal saia do sistema e efetue o login novamente.</LI>
		</UL>
    </td>
  </tr>
  
</TBODY>
</TABLE>



</body>
</html>
