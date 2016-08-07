<%@ include file="/Paginas/Geral/Cabecalho.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Processos de licitação</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=contexto%>/css/all.css" rel="stylesheet" type="text/css">
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

  <tr> 
    <td width="68" height="77" align="left" valign="middle">
	<img src="<%=contexto%>/images/mg_info.gif" >
	</td>
    <td width="100%" align="left" valign="middle" class="alertscreen-text">   
		<UL>
		  <LI><%=request.getAttribute("INFO")%></LI>
		</UL>
    </td>
  </tr>
  
</TBODY>
</TABLE>

<table width="100%" cellspacing="0" cellpadding="5">
<tr> 
    <td> 
    	<div align="center">
    	 <input TYPE="button" class="formLogin" VALUE="VOLTAR" onClick="window.history.back();" >  
        </div>
    </td>
</tr>
</table>

</body>
</html>
