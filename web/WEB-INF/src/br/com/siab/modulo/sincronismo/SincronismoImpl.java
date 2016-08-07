package br.com.siab.modulo.sincronismo;

import javax.servlet.ServletContext;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.modulo.sincronismo.service.SincronismoService;
import br.com.siab.modulo.sincronismo.service.SincronismoServiceImpl;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public class SincronismoImpl implements Sincronismo {

	private static SincronismoImpl instance;
	
	private SincronismoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public static SincronismoImpl getInstance(){
		if(instance == null){
			instance = new SincronismoImpl();
		}
		return instance;
	}
	
	public void realizarSincronismo(String ano,ServletContext context) throws BaseException {
		SincronismoService service = SincronismoServiceImpl.getInstance();
		service.realizarSincronismo(ano,context);
		
	}

}
