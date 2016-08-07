package br.com.siab.modulo.relatorio.helpers;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JasperRunManager;
import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.modulo.relatorio.Relatorio;
import br.com.siab.modulo.relatorio.model.RelatorioDTO;
import br.com.siab.modulo.relatorio.service.RelatorioService;

/**
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public class RelatorioHelper {

	public static final ResourceBundle bundleRels = ResourceBundle
			.getBundle("Relatorios");

	/**
	 * Método responsável pelo processamento do relatório
	 * 
	 * @param servcontext
	 * @param repname
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public RelatorioDTO processar(ServletContext servcontext, String repname,
			Map parameters) throws Exception {

		String pathRel = bundleRels.getString(repname);
		File reportFile = new File(servcontext.getRealPath(pathRel));
		RelatorioService relatorioService = new RelatorioService();
		Connection connection = relatorioService.getCurrentConnection();
		RelatorioDTO relatorio = new RelatorioDTO();

		try {
			/* PDF */
			byte[] bytes = null;
			bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),
					parameters, connection);
			relatorio.setBytes(bytes);
			relatorio.setTipo(Relatorio.TIPO_PDF);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;		
		}finally{
			relatorioService.closeCurrentConnection(connection);
		}		
        return relatorio;

	}

	/**
	 * Método responsável pela preparação dos parametros do relatório 
	 * (Ficha Criança)
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	public Map preparaRelatorioFichaCrianca(HttpServletRequest request)
			throws BaseException {
		Map<String,Object> parametros = new HashMap<String,Object>();

		String ano = request.getParameter("ano");
		String cdAgente = request.getParameter("cdAgente");
		String subRelatorio = request.getSession().getServletContext()
				.getRealPath(bundleRels.getString("RelatorioFichaCriancaSub"));
		
		parametros.put("cd_agente",new Long(cdAgente));
		parametros.put("cd_ano",new Integer(ano));
		parametros.put("sub_relatorio",subRelatorio);

		return parametros;
	}
	
	/**
	 * Método responsável pela preparação dos parametros do relatório 
	 * (Ficha TB)
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	public Map preparaRelatorioFichaTb(HttpServletRequest request)
			throws BaseException {
		Map<String,Object> parametros = new HashMap<String,Object>();

		String ano = request.getParameter("ano");
		String cdAgente = request.getParameter("cdAgente");
		String subRelatorio = request.getSession().getServletContext()
				.getRealPath(bundleRels.getString("RelatorioFichaTbSub"));
		
		parametros.put("cd_agente",new Long(cdAgente));
		parametros.put("cd_ano",new Integer(ano));
		parametros.put("sub_relatorio",subRelatorio);

		return parametros;
	}
	
	/**
	 * Método responsável pela preparação dos parametros do relatório 
	 * (Ficha DIA)
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	public Map preparaRelatorioFichaDia(HttpServletRequest request)
			throws BaseException {
		Map<String,Object> parametros = new HashMap<String,Object>();

		String ano = request.getParameter("ano");
		String cdAgente = request.getParameter("cdAgente");
		String subRelatorio = request.getSession().getServletContext()
				.getRealPath(bundleRels.getString("RelatorioFichaDiaSub"));
		
		parametros.put("cd_agente",new Long(cdAgente));
		parametros.put("cd_ano",new Integer(ano));
		parametros.put("sub_relatorio",subRelatorio);

		return parametros;
	}
	
	/**
	 * Método responsável pela preparação dos parametros do relatório 
	 * (Ficha HA)
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	public Map preparaRelatorioFichaHa(HttpServletRequest request)
			throws BaseException {
		Map<String,Object> parametros = new HashMap<String,Object>();

		String ano = request.getParameter("ano");
		String cdAgente = request.getParameter("cdAgente");
		String subRelatorio = request.getSession().getServletContext()
				.getRealPath(bundleRels.getString("RelatorioFichaHaSub"));
		
		parametros.put("cd_agente",new Long(cdAgente));
		parametros.put("cd_ano",new Integer(ano));
		parametros.put("sub_relatorio",subRelatorio);

		return parametros;
	}
	
	/**
	 * Método responsável pela preparação dos parametros do relatório 
	 * (Ficha HAN)
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	public Map preparaRelatorioFichaHan(HttpServletRequest request)
			throws BaseException {
		Map<String,Object> parametros = new HashMap<String,Object>();

		String ano = request.getParameter("ano");
		String cdAgente = request.getParameter("cdAgente");
		String subRelatorio = request.getSession().getServletContext()
				.getRealPath(bundleRels.getString("RelatorioFichaHanSub"));
		
		parametros.put("cd_agente",new Long(cdAgente));
		parametros.put("cd_ano",new Integer(ano));
		parametros.put("sub_relatorio",subRelatorio);

		return parametros;
	}
	/**
	 * Método responsável pela preparação dos parametros do relatório 
	 * (Ficha IDOSO)
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	public Map preparaRelatorioFichaIdoso(HttpServletRequest request)
			throws BaseException {
		Map<String,Object> parametros = new HashMap<String,Object>();

		String ano = request.getParameter("ano");
		String cdAgente = request.getParameter("cdAgente");
		String subRelatorio = request.getSession().getServletContext()
				.getRealPath(bundleRels.getString("RelatorioFichaIdosoSub"));
		
		parametros.put("cd_agente",new Long(cdAgente));
		parametros.put("cd_ano",new Integer(ano));
		parametros.put("sub_relatorio",subRelatorio);

		return parametros;
	}
	/**
	 * Método responsável pela preparação dos parametros do relatório 
	 * (Ficha A)
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	public Map preparaRelatorioFichaA(HttpServletRequest request)
			throws BaseException {
		Map<String,Object> parametros = new HashMap<String,Object>();

		String cd_familia = request.getParameter("cdFamilia");
		String cdAgente = request.getParameter("cdAgente");
		String subRelatorio = request.getSession().getServletContext()
				.getRealPath(bundleRels.getString("RelatorioFichaASub1"));
		String subRelatorio2 = request.getSession().getServletContext()
		.getRealPath(bundleRels.getString("RelatorioFichaASub2"));
		
		parametros.put("cd_agente",new Long(cdAgente));
		parametros.put("cd_familia",new Long(cd_familia));
		parametros.put("sub_relatorio",subRelatorio);
		parametros.put("sub_relatorio2",subRelatorio2);

		return parametros;
	}
	/**
	 * Método responsável pela preparação dos parametros do relatório 
	 * (Ficha GES)
	 * @param request
	 * @return
	 * @throws BaseException
	 */
	public Map preparaRelatorioFichaGes(HttpServletRequest request)
			throws BaseException {
		Map<String,Object> parametros = new HashMap<String,Object>();

		String ano = request.getParameter("ano");
		String cdAgente = request.getParameter("cdAgente");
		String subRelatorio = request.getSession().getServletContext()
				.getRealPath(bundleRels.getString("RelatorioFichaGesSub"));
		
		parametros.put("cd_agente",new Long(cdAgente));
		parametros.put("cd_ano",new Integer(ano));
		parametros.put("sub_relatorio",subRelatorio);

		return parametros;
	}
}
