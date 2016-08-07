package br.com.siab.modulo.equipe.dao;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.biblioteca.persistence.BaseDAO;
import br.com.siab.biblioteca.utils.BBValueObject;
import br.com.siab.biblioteca.utils.Utils;

/*
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */

public class EquipeDAOImpl extends BaseDAO implements EquipeDAO {

	private static EquipeDAOImpl instance;
	
	public EquipeDAOImpl() { }
	
	
	private static final String SQL_RETRIEVE_ALL = 
		"select distinct cod_area, nome_area from equipe where cod_seg = ";
	
	private static final String SQL_RETRIEVE_AREA_BY_ID = 
		"select distinct cod_area, nome_area from equipe where "; 
	
	private static final String SQL_RETRIEVE_ALL_MICROA = 
		"select cod_microa, id_modelo, cod_ub, nome from equipe, agente where cod_prof = cod_age and cod_microa is not null ";
	
	private static final String SQL_RETRIEVE_MICROA_BY_ID = 
		"select cod_microa, id_modelo, cod_ub, nome " +
		"from equipe, agente " +
		"where cod_prof = cod_age ";
	
	public static EquipeDAOImpl getInstance(){
		if(instance == null){
			instance = new EquipeDAOImpl();
		}
		return instance;
	}
	
	public List<BBValueObject> retrieveAll(String codSeg) throws BaseException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<BBValueObject> lstArea = new ArrayList<BBValueObject>();
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_RETRIEVE_ALL).append("'").append(Utils.formataCodigo(codSeg, 2)).append("'");
			
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				BBValueObject vo = new BBValueObject();
				vo.set(0,"COD_AREA",rs.getString("COD_AREA"));
				vo.set(0,"NOME_AREA",rs.getString("NOME_AREA"));
								
				lstArea.add(vo);
			}
			rs.close();
			stmt.close();
			
		}catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}

		return lstArea;
	}

	public BBValueObject retrieveAreaById(String codSeg, String codArea) throws BaseException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		BBValueObject resultVO = new BBValueObject();
		
		try {
			conn = getConnectionDBF();
			stmt = conn.createStatement();
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_RETRIEVE_AREA_BY_ID).append("cod_seg = '").append(Utils.formataCodigo(codSeg, 2)).append("' ");
			sb.append("and cod_area = '").append(Utils.formataCodigo(codArea, 3)).append("'");
			
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				resultVO.set(0,"COD_AREA",rs.getString("COD_AREA"));
				resultVO.set(0,"NOME_AREA",rs.getString("NOME_AREA"));
			}
			rs.close();
			stmt.close();
			
		}catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
		
		return resultVO;
	}

	public List<BBValueObject> retrieveAllMicroa(String codSeg, String codArea) throws BaseException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<BBValueObject> lstMicroa = new ArrayList<BBValueObject>();
		
		try {
			conn = getConnectionDBF();
			stmt = conn.createStatement();
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_RETRIEVE_ALL_MICROA).append("and cod_seg ='").append(Utils.formataCodigo(codSeg, 2)).append("' ");
			sb.append("and cod_area = '").append(Utils.formataCodigo(codArea, 3)).append("' ");
			sb.append("order by cod_microa");
			
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				BBValueObject vo = new BBValueObject();
				vo.set(0,"COD_MICROA",rs.getString("COD_MICROA"));
				vo.set(0,"ID_MODELO",rs.getString("ID_MODELO"));
				vo.set(0,"COD_UB",rs.getString("COD_UB"));
				vo.set(0,"NOME",rs.getString("NOME"));
								
				lstMicroa.add(vo);
			}
			rs.close();
			stmt.close();
			
		}catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}

		return lstMicroa;
	}

	public BBValueObject retrieveMicroaById(String codSeg, String codArea, String codMicroa) throws BaseException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		BBValueObject vo = new BBValueObject();
		
		try {
			conn = getConnectionDBF();
			stmt = conn.createStatement();
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_RETRIEVE_MICROA_BY_ID).append("and cod_seg = '").append(Utils.formataCodigo(codSeg, 2)).append("' ");
			sb.append("and cod_area = '").append(Utils.formataCodigo(codArea, 3)).append("' ");
			sb.append("and cod_microa = '").append(Utils.formataCodigo(codMicroa, 2)).append("' ");
			
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				vo.set(0,"COD_MICROA",rs.getString("COD_MICROA"));
				vo.set(0,"ID_MODELO",rs.getString("ID_MODELO"));
				vo.set(0,"COD_UB",rs.getString("COD_UB"));
				vo.set(0,"NOME",rs.getString("NOME"));
			}
			rs.close();
			stmt.close();
			
		}catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
		
		return vo;
	}

}
