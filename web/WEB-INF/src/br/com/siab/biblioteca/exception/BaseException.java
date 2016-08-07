package br.com.siab.biblioteca.exception;

import java.sql.SQLException;
import java.util.StringTokenizer;

/**
 * Classe base de tratamento de erro
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class BaseException extends Exception {
	private String message;

	private Exception exception;

	public BaseException(){
		super();
	}
	
	public BaseException(Exception e) {
		this.exception = e;
		this.message = e.getClass().getName() + " - " + e.getMessage();
	}
	
	public BaseException(String message){
		this.message = message;
	}
	
	public String toString(){
		return message;
	}
	
	public String getMessage(){
		return message;
	}

	public String getLocalizedMessage() {
		return super.getLocalizedMessage();
	}
	
	/**
	 * Obtem a mensagem de erro formatada
	 * @return
	 */
	public String getFormatedMessage(){
		String message = "";
    	
    	if( exception instanceof SQLException ){
    		message = formatedMessage();
    	}else{
    		message = this.getMessage(); 
    	}   	
    	return message;
	}
	
	/**
	 * Formata mensagem de erro removendo a formatação padrão do 
	 * oracle.
	 * @return Mensagem Formatada
	 */
	private String formatedMessage(){
		String message = exception.toString();
    	
    	StringTokenizer tokenizer = new StringTokenizer(message, "\n");
    	if (tokenizer.hasMoreTokens()) {
    		message = tokenizer.nextToken();
    		
    		int pos = message.indexOf("ORA-");    		
    		pos = pos + 10;
    		message = message.substring( pos, message.length() );
		}    	
    	return message;
	}
	
}
