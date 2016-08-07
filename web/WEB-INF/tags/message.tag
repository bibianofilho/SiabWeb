<%@tag isELIgnored="false" %>
<%@ taglib uri="tags/struts-bean" prefix="bean" %>
<%@ taglib uri="tags/struts-html" prefix="html" %>
<%@ taglib uri="tags/struts-logic" prefix="logic" %>
<%@ taglib uri="tags/base" prefix="base" %>

<logic:messagesPresent message="true">
	<html:messages id="message" message="true">
		<div id="success" style="position:absolute; z-index:5001; top:30px; left:325px; width:264px;">
			 <table border="0" cellpadding="0" cellspacing="0" width="264px" onMouseOver="MM_dragLayer('success','',0,0,0,0,true,false,-1,-1,-1,-1,false,false,0,'',false,'')" style="border:1px black solid;">
			  <tr>
			  	<td background="images/msgTitleBack.jpg" colspan="2" align="right">
					<img src="images/bt_fechar_msg.gif" title="Fechar Janela" style="cursor:pointer;" onClick="prepareDiv('success','','','');" />
				</td>
			  </tr>
			  <tr> 
			    <td width="68" height="77" align="left" bgcolor="#FFFFFF" valign="middle">
				<img src="images/mg_info.gif" >
				</td>
			    <td width="100%" align="left" valign="middle" bgcolor="#FFFFFF" class="alertscreen-text">   
						<bean:write name="message" /></br>
			    </td>
			  </tr>
			</table>
		</div>
	</html:messages>
</logic:messagesPresent>

<logic:messagesPresent message="false">
	<div id="error" style="position:absolute; z-index:5001; top:30px; left:325px; width:264px;">
		 <table border="0" cellpadding="0" cellspacing="0" width="264px" onMouseOver="MM_dragLayer('error','',0,0,0,0,true,false,-1,-1,-1,-1,false,false,0,'',false,'')" style="border:1px black solid;">
		  <tr>
		  	<td background="images/msgTitleBack.jpg" colspan="2" align="right">
				<img src="images/bt_fechar_msg.gif" title="Fechar Janela" style="cursor:pointer;" onClick="prepareDiv('error','','','');" />
			</td>
		  </tr>
		  <tr> 
		    <td width="68" height="77" align="left" bgcolor="#FFFFFF" valign="middle">
			<img src="images/mg_erro.gif" >
			</td>
		    <td width="100%" align="left" valign="middle" bgcolor="#FFFFFF" class="alertscreen-text">   
				<FONT COLOR='CC0000' ><base:error /></FONT>
		    </td>
		  </tr>
		</table>
	</div>
</logic:messagesPresent>
