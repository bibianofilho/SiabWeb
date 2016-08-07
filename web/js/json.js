/**
 * Realiza de modo sincronizado uma requisi??o para o JsonRequestAction. Onde ser? retornada uma estrutura Json.
 * url: Action e method que ser? chamado pela requisi??o.
 */
function syncJsonRequestGetData(url) {
	var postStr = jasonGetPostedElementsFromForm();
	var xmlhttp = getRefXmlHttpRequest();
	xmlhttp.open("GET",url + postStr,false);
	xmlhttp.send(null);
	var data;
	try {
		data = eval(xmlhttp.responseText);
	}
	catch(ex) {
		data = null;
	}
	return data;
}

/**
 * Retorna objeto de XmlHttpRequest do browser que ser? utilizado para realizar a requisi??o.
 */
function getRefXmlHttpRequest() {
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	}
	else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	return xmlhttp;
}

/**
 * Retorna valor da propriedade passada. Se for nulo, o valor nao foi encontrado.
 * property: String do nome da propriedade.
 * obj: Objeto JSON.
 */
function jasonGetValueByProperty(property,obj) {
	var array = obj.properties;
	for (var ct = 0; ct < array.length; ct++) {
		if (array[ct].property == property) {
			return array[ct].value;
		}
	}
	return null;
}

/**
 * Atribui para os campos do array 'fields' os valores contidos no objeto 'obj' utilizandos a propriedades em 'fieldsProperty'.
 * fieldsName: Array com os nomes dos campos a serem atribuidos.
 * fieldsProperty: Array de propriedades dos campos 'fieldsName'.
 * obj: Objeto JSON.
 */
function jasonSetFields(fieldsName,fieldsProperty,obj) {
	var elementName;
	var elementProperty;
	var elementValue;
	for (var i = 0; i < fieldsName.length; i++) {
		elementName = fieldsName[i];
		elementProperty = fieldsProperty[i];
		elementValue = jasonGetValueByProperty(elementProperty,obj);
		if (elementValue == null) {
			document.forms[0].elements[elementName].value = "";
		}
		else {
			document.forms[0].elements[elementName].value = elementValue;
		}
	}
}

/**
 * Limpa os valores dos campos contido no Array 'fieldsName'.
 * fieldsName: Array que cont?m os nomes dos campos a terem seus valores limpados.
 */
function jasonClearFields(fieldsName) {
	var elementName;
	for (var i = 0; i < fieldsName.length; i++) {
		elementName = fieldsName[i];
		document.forms[0].elements[elementName].value = "";
	}
}

/**
 * Retorna string de par?metros do form que ser? concatenada com a url da requisi??o.
 */
function jasonGetPostedElementsFromForm() {
	var postStr = "";
	for (var ct = 0; ct < document.forms[0].elements.length; ct++) {
		postStr += "&" + document.forms[0].elements[ct].name + "=" + document.forms[0].elements[ct].value;
	}
	return postStr;
}

/**
 * Abre DIV de pesquisa.
 */
function jsonCloseSearchLookup(idDiv,idIframe) {
	var searchDiv = document.getElementById(idDiv);
	var searchIframe = document.getElementById(idIframe);
	searchIframe.src = "";
	searchDiv.style.display = 'none';
}

/**
 * Fecha DIV de pesquisa.
 */
function jsonOpenSearchLookup(idDiv,idIframe) {
	var searchDiv = document.getElementById(idDiv);
	var searchIframe = document.getElementById(idIframe);
	searchIframe.src = "";
	searchDiv.style.display = "block";
}

/**
 * Cria uma tabela de pesquisa apartir de uma lista de beans. A tabela ? gerada dinamicamente por JavaScript e ? populada com os dados contidos no array de beans.
 */
function jasonCreateTableSearch(beanArray,selectObjectFunction,idDiv,idIframe,strTitle,strCodCol,strDescCol,strCodProperty,strDescProperty) {
	var htmlTable = "";
	var maxCols = 2;
	var i;
	var j;
	htmlTable += "<html>";
	htmlTable += "<head>";
	htmlTable += "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/global.css\"/>";
	htmlTable += "</head>";
	htmlTable += "<body class=\"lupa\">";
	htmlTable += "<div class=\"formulario\">";
	htmlTable += "<div class=\"interno\">";
	//Cabe?alho da tabela.
	htmlTable += "<div class=\"cabecalho\">";
	htmlTable += "	<label>" + strTitle + "</label>";
	htmlTable += "	<img src=\"images/fecha_pesquisa.gif\" border=\"0\" onclick=\"parent.jsonCloseSearchLookup('" + idDiv + "','" + idIframe + "')\"/>";
	htmlTable += "</div>";
	htmlTable += "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
	htmlTable += "	<thead>";
	htmlTable += "		<tr>";
	for(i = 0; i < maxCols; i++) {
		htmlTable += "			<th>"
		switch(i) {
			case 0: htmlTable += strCodCol;
					break;
			case 1:	htmlTable += strDescCol;
					break;
		}
		htmlTable += "</th>";
	}
	htmlTable += "		</tr>";
	htmlTable += "	</thead>";
	//Corpo da tabela.
	htmlTable += "	<tbody>";
	for(i = 0; i < beanArray.length; i++) {
		if (i % 2 == 0) {
			htmlTable += "		<tr class=\"linhaEscura\">";
		}
		else {
			htmlTable += "		<tr class=\"linhaClara\">";
		}
		for (j = 0; j < maxCols;j++) {
			htmlTable += "			<td><a href=\"javascript:parent." + selectObjectFunction + "(" + i + ");parent.jsonCloseSearchLookup('" + idDiv + "','" + idIframe + "');\">";
			switch(j) {
				case 0: htmlTable += jasonGetValueByProperty(strCodProperty,beanArray[i]);
						break;
				case 1:	htmlTable += jasonGetValueByProperty(strDescProperty,beanArray[i]);
						break;
			}
			htmlTable += "</a></td>";
		}
		htmlTable += "		</tr>";
	}
	htmlTable += "	</tbody>";
	htmlTable += "</table>";
	htmlTable += "</div>";
	htmlTable += "</div>";
	htmlTable += "</body>";	
	htmlTable += "</html>";
	return htmlTable;
}