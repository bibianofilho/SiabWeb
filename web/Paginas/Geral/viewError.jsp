<%@ include file="/Paginas/Geral/cabecalho.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
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
		<LI><base:error /></LI>
		</UL>
    </td>
  </tr>
  
</TBODY>
</TABLE>

<table width="100%" cellspacing="0" cellpadding="5">
<tr> 
    <td> 
    	<div id="error" align="center">
    	 <input type="button" class="button" value="Voltar" onClick="history.back(-1);" >        
        </div>
    </td>
</tr>
</table>
</body>
</html>

