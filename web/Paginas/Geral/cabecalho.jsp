<%@ taglib uri="tags/struts-bean" prefix="bean" %>
<%@ taglib uri="tags/struts-html" prefix="html" %>
<%@ taglib uri="tags/struts-logic" prefix="logic" %>
<%@ taglib uri="tags/struts-nested" prefix="nested" %>
<%@ taglib uri="tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="tags/c" prefix="c" %>
<%@ taglib uri="tags/fmt" prefix="fmt" %>
<%@ taglib uri="display/table" prefix="display"%>

<%@ taglib uri="tags/base" prefix="base" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="messages"%>

<%
	String contexto = request.getContextPath();
%>

<head>
<link rel="stylesheet" type="text/css" href="<%=contexto%>/css/style.css">
<link rel="stylesheet" type="text/css" href="<%=contexto%>/css/displaytag.css">
<script language="JavaScript" src="<%=contexto%>/js/base.js"></script>    
<script language="JavaScript" src="<%=contexto%>/js/json.js"></script>
<script language="JavaScript" src="<%=contexto%>/js/calendar.js"></script>
<script language="JavaScript" src="<%=contexto%>/scripts/Validator.jsp"></script>
</head>