<%@ include file="/Paginas/Geral/cabecalho.jsp" %>

<html>
<head>
<title>SIAB - Módulo Web</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<frameset rows="133px,*" frameborder="NO" border="0" framespacing="0">

  <frame name="topo" scrolling="NO" src="<%=contexto%>/topo.do">

  <frameset cols="20%,*" frameborder="NO" border="0" framespacing="0">
    <frame name="menu"  scrolling="NO"  marginheight="0" src="<%=contexto%>/menuPrincipal.do">
    <frame name="conteudo" scrolling="YES" marginheight="0" src="<%=contexto%>/inicial.do" >
  </frameset>

</frameset>
<body>
	<noframes><p>Browser não suporta frames. Solicite suporte técnico!</p></noframes>
</body>
</html>