package br.com.siab.modulo.municipio.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.biblioteca.persistence.BaseDAO;
import br.com.siab.biblioteca.utils.BBValueObject;
import br.com.siab.biblioteca.utils.Utils;

/**
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public class MunicipioDAOImpl extends BaseDAO implements MunicipioDAO {

	private static MunicipioDAOImpl instance;
	
	private MunicipioDAOImpl() {}
	
	public static MunicipioDAOImpl getInstance(){
		if(instance == null){
			instance = new MunicipioDAOImpl();
		}
		return instance;
	}
	
	private static final String SQL_RETRIEVE_SEG_BY_ID = 
		"select cod_seg,cod_zona, nome from tabmun where cod_seg not in ('00') and cod_seg = ";
	
	private static final String SQL_RETRIEVE_ALL = 
		"select * from tabmun where cod_seg not in ('00')";
	
	public BBValueObject retrieveSegById(BBValueObject vo) throws BaseException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		BBValueObject resultVO = new BBValueObject();
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			
			StringBuffer sb = new StringBuffer();
			sb.append(SQL_RETRIEVE_SEG_BY_ID).append("'").append(Utils.formataCodigo(vo.getString(0,"COD_SEG"),2)).append("'");
			
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				resultVO.set(0,"COD_SEG",rs.getString("COD_SEG"));
				resultVO.set(0,"COD_ZONA",rs.getString("COD_ZONA"));
				resultVO.set(0,"NOME",rs.getString("NOME"));
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

	public List<BBValueObject> retrieveAll() throws BaseException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<BBValueObject> lstSeg = new ArrayList<BBValueObject>();
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(SQL_RETRIEVE_ALL);
			while(rs.next()){
				BBValueObject vo = new BBValueObject();
				vo.set(0,"COD_SEG",rs.getString("COD_SEG"));
				vo.set(0,"COD_ZONA",rs.getString("COD_ZONA"));
				vo.set(0,"NOME",rs.getString("NOME"));
				
				lstSeg.add(vo);
			}
			rs.close();
			stmt.close();
			
		}catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}

		return lstSeg;
	}

}
