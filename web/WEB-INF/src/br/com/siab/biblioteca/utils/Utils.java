package br.com.siab.biblioteca.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe utilit�ria da aplica��o
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class Utils {

	public static final SimpleDateFormat formata = new SimpleDateFormat(
			"dd/MM/yyyy");

	/**
	 * Formata uma string no formato dd/mm/yyyy a partir de um objeto do tipo
	 * date
	 * 
	 * @param data -
	 *            Ojeto do tipo java.util.Date
	 * @return
	 */
	public static String formataData(Date data) {
		return formata.format(data);
	}

	/**
	 * M�todo utilizado para criar um objeto date a partir de uma string
	 * dd/mm/yyyy
	 * 
	 * @param strdata
	 * @return
	 * @throws ParseException
	 */
	public static Date criaData(String strdata) throws ParseException {
		return formata.parse(strdata);
	}

	public static boolean isNumber(String str) {
		char[] arrChar = str.toCharArray();
		char character;
		int size = arrChar.length;

		if (size < 1) {
			return false;
		}
		for (int i = 0; i < arrChar.length; i++) {
			character = arrChar[i];
			if (!Character.isDigit(character))
				return false;
		}
		return true;
	}

	/**
	 * M�todo que formata os dados de um VO com os dados a serem exclu�dos. Este
	 * converte a coluna id_key para as colunas reais.
	 * 
	 * Ex: id_key = "a=10;b=45"
	 * 
	 * E seta no VO nas colunas A e B de cada linha o valor 10 e 45, neste caso
	 * na linha destes valores. Pois nas demais linhas os valores podem ou n�o
	 * serem diferentes.
	 * 
	 * @param vo
	 *            VO a ser formatado.
	 * @return SSValueObject Vo formatado.
	 */
	public static BBValueObject formatDeletedRecords(String idkey) {
		BBValueObject vo = new BBValueObject();

		// pega o nome das colunas.
		String[] keyColumns = idkey.split("=[^=;]+;?");
		String[] keys = null;

		// pega o valor das colunas.
		keys = idkey.split(";?[^=;]+=");
		for (int y = 0; y < keyColumns.length; y++) {
			vo.set(0, keyColumns[y], keys[y + 1]);
		}

		return vo;
	}

	/**
	 * M�todo respons�vel pelo c�lculo da idade.
	 * @param begin - data inicial
	 * @param end - data final
	 * @return
	 */
	public static int calculaIdade(Date begin, Date end) {
		Calendar dateOfBirth = Calendar.getInstance();
		Calendar today = Calendar.getInstance();

		dateOfBirth.setTime(begin);
		today.setTime(end);

		int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
		dateOfBirth.add(Calendar.YEAR, age);

		if (today.before(dateOfBirth)) {
			age--;
		}
		return age;
	}

	/**
	 * M�todo utilizado para formatar o tamanho do campo para os padr�es do
	 * siab.
	 * 
	 * @param campo
	 * @param tam
	 * @return
	 */
	public static String formataCampoSiab(String campo, int tam) {
		if (campo.length() < tam) {
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < tam; i++) {
				sb.append(" ");
			}
			sb.append(campo);

			return sb.toString();
		}

		return campo;
	}

	/**
	 * M�todo utilizado para formatar c�digo no padr�o dbf do siab
	 * 
	 * Ex.: Para tamanho igual a 2, passando o valor '1' ser� retornado '01'.
	 * Caso o valor j� tenha o tamanho correto o mesmo ser� retornado.
	 * 
	 * @param strCod -
	 *            c�digo
	 * @param tam -
	 *            tamanho do campo
	 * @return
	 */
	public static String formataCodigo(String strCod, int tam) {
		if (strCod.length() < tam) {
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < tam; i++) {
				sb.append("0");
			}
			sb.append(strCod);

			return sb.toString();
		}

		return strCod;
	}
}
