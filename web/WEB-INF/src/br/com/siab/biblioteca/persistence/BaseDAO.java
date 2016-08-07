package br.com.siab.biblioteca.persistence;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import br.com.siab.biblioteca.exception.BaseException;

/**
 * Classe de persistência
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class BaseDAO {

	public static final ResourceBundle bundle =
        ResourceBundle.getBundle("Configuracoes");

	/** Constante para o tipo de conexão JNDI */
	public static final String CONN_JNDI = "CONN_JNDI";
	
	/** Constante para o tipo de conexão DBF */
	public static final String CONN_DBF = "CONN_DBF";

	/**
	 * Obtem uma conexão com a base de dados em dbf
	 * @return
	 * @throws BaseException
	 */
	public Connection getConnectionDBF() throws BaseException {
		Connection connection = null;
				
        try {
            //Registrando o driver:
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
			//Estabelecendo a conexão através do ODBC criado no Painel de Controle:
            connection = DriverManager.getConnection("jdbc:odbc:siab","","");
		} catch (Exception e) {
			throw new BaseException(e.getMessage());
		} 
		
		return connection;
	}
	
	/**
	 * Obtem uma conexão com o banco de dados
	 * 
	 * @return Connection
	 * @throws BaseException
	 */
	public Connection getConnection() throws BaseException {
		Connection connection = null;
		String datasource = bundle.getString("datasource");
		
		try {
			Context initContext = new InitialContext();

			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup(datasource);

			connection = ds.getConnection();

			return connection;
		} catch(NamingException e ){
			throw new BaseException("O contexto da aplicação web é inválido.");
		   		
		}catch(SQLException e ){
			throw new BaseException("Os parâmetros para conexão com o banco de dados estão incorretos."); 
		}	
	}
	
	/**
	 * Inicia uma transação
	 * 
	 * @return connection
	 * @throws Exception
	 */
	public Connection beginTransaction(String connType) throws Exception {
		Connection connection = null;
		
		
		if(BaseDAO.CONN_JNDI.equals(connType)){
			connection = getConnection();
		}else if(BaseDAO.CONN_DBF.equals(connType)){
			connection = getConnectionDBF();
		}
		
		if (connection != null) {
			connection.setAutoCommit(false);
		}

		return connection;
	}

	/**
	 * Efetiva uma transação
	 * 
	 * @param connection
	 * @throws SQLException
	 */
	public void commitTransaction(Connection connection) throws SQLException {
		try {
			if (connection != null) {
				connection.commit();
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (connection != null) {
				connection.setAutoCommit(true);
				connection.close();
			}
		}
	}

	/**
	 * Aborta uma transação
	 * 
	 * @param connection
	 * @throws Exception
	 */
	public void rollbackTransaction(Connection connection) throws Exception {
		try {
			if (connection != null) {
				connection.rollback();
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			closeConnection(connection);
		}
	}

	/**
	 * Método utilizado para liberar uma conexão
	 * 
	 * @param connection
	 * @throws BaseException
	 */
	public void closeConnection(Connection connection) throws BaseException {
		try {
			if (connection == null) {
				return;
			}
			connection.setAutoCommit(true);
			connection.close();
		} catch (SQLException e) {
			throw new BaseException(e);
		}
	}

	protected static void setInteger(int index, PreparedStatement ps, Integer i)
			throws SQLException {
		if (i == null) {
			ps.setNull(index, Types.INTEGER);
		} else {
			ps.setInt(index, i.intValue());
		}
	}

	protected static void setInt(int index, PreparedStatement ps, int i)
			throws SQLException {
		ps.setInt(index, i);
	}

	protected static void setLong(int index, PreparedStatement ps, Long i)
			throws SQLException {
		if (i == null) {
			ps.setNull(index, Types.BIGINT);
		} else {
			ps.setLong(index, i.longValue());
		}
	}

	protected static void setInteger(int index, CallableStatement cstmt,
			Integer i) throws SQLException {
		if (i == null) {
			cstmt.setNull(index, Types.INTEGER);
		} else {
			cstmt.setInt(index, i.intValue());
		}
	}

	protected static void setDate(int index, CallableStatement cstmt,
			java.util.Date date) throws SQLException {
		if (date == null) {
			cstmt.setNull(index, Types.DATE);
		} else {
			cstmt.setDate(index, new java.sql.Date(date.getTime()));
		}
	}

	protected static void setDate(int index, PreparedStatement ps,
			java.util.Date date) throws SQLException {
		if (date == null) {
			ps.setNull(index, Types.DATE);
		} else {
			ps.setDate(index, new java.sql.Date(date.getTime()));
		}
	}

	protected static void setString(int index, CallableStatement cstmt,
			String str) throws SQLException {
		if (str == null) {
			cstmt.setNull(index, Types.VARCHAR);
		} else {
			cstmt.setString(index, str);
		}
	}

	protected static void setString(int index, PreparedStatement ps, String str)
			throws SQLException {
		if (str == null) {
			ps.setNull(index, Types.VARCHAR);
		} else {
			ps.setString(index, str);
		}
	}

	protected static void setDouble(int index, CallableStatement cstmt, Double d)
			throws SQLException {
		if (d == null) {
			cstmt.setNull(index, Types.DOUBLE);
		} else {
			cstmt.setDouble(index, d.doubleValue());
		}
	}

	protected static void setDouble(int index, PreparedStatement ps, double val)
			throws SQLException {
		ps.setDouble(index, val);
	}

	protected static void setObject(int index, PreparedStatement ps,
			Object object) throws SQLException {
		if (object == null) {
			ps.setNull(index, Types.JAVA_OBJECT);
		} else {
			ps.setObject(index, object);
		}
	}

	protected static void setBinaryStream(int index, PreparedStatement ps,
			InputStream inputStream) throws SQLException {
		if (inputStream == null) {
			ps.setNull(index, Types.BLOB);
		} else {
			try {
				ps.setBinaryStream(index, inputStream, inputStream.available());
			} catch (Exception e) {
				throw new SQLException(e.getMessage());
			}
		}
	}

	protected static void setShort(int index, PreparedStatement ps, Short object)
			throws SQLException {
		if (object == null) {
			ps.setNull(index, Types.SMALLINT);
		} else {
			ps.setShort(index, object.shortValue());
		}
	}
}
