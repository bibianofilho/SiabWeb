package br.com.siab.modulo.agente.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.biblioteca.persistence.BaseDAO;
import br.com.siab.biblioteca.utils.BBValueObject;
import br.com.siab.modulo.agente.model.AgenteVO;

/**
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class AgenteDAOImpl extends BaseDAO implements AgenteDAO {

	private static AgenteDAOImpl instance;

	private AgenteDAOImpl() {
	}

	public static AgenteDAOImpl getInstance() {
		if (instance == null) {
			instance = new AgenteDAOImpl();
		}

		return instance;
	}

	private static final String SQL_PESQUISA = "select * from agente a "
			+ "where 1 = 1";

	private static final String SQL_PESQUISA_LOGIN = "select * from agente a where a.ds_login = ?";

	private static final String SQL_PESQUISA_AGENTE = "select * from agente a where a.cd_agente = ?";

	private static final String SQL_EDITAR_AGENTE = "update agente "
			+ "set nm_agente = ?, " + "ds_login = ?, " + "ds_senha = ? "
			+ "where cd_agente = ?";
	
	private static final String SQL_PESQUISAR_RELATORIO_AGENTE = 
		"select f.cd_familia, f.ds_endereco from fichacadfam f " +
		"where f.cd_agente = ? " +
		"and   substr(f.dt_cadastro,7,10) = ? " +
		"order by f.cd_familia asc";
	
	private static final String SQL_PESQUISA_VALIDAR_QTD_AGENTE = 
		"select count(*) - ( " +
		"	       select case when ds_senha is null then 0 else 1 end " +
		"	       from agente b " +
		"	       where b.cd_agente = ?) " +
		"from agente a " +
		"where a.ds_senha is not null " + 
		"or length(a.ds_senha) > 0";
		
	/**
	 * Método utilizado para gerar dados para o usuário do palm
	 */
	public void gerarDados(Integer codigo) throws BaseException {
		Connection conn = null;

		try {

			if (validarEdicaoUsuario(codigo)) {
				conn = getConnection();

				CallableStatement csGerar = conn
						.prepareCall("{call func_gerar_dados_agente(?)}");

				setInteger(1, csGerar, codigo);

				csGerar.execute();
				csGerar.close();
			} else {
				throw new BaseException(
						"Número máximo de agentes ultrapassado. "
								+ "Entre em contato com o suporte pelo e-mail siabmobile@gmail.com");
			}

		} catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
	}
	
	/**
	 * Método responsável pela validação da quantidade de usuários
	 * @return
	 * @throws BaseException
	 */
	private boolean validarEdicaoUsuario(Integer codigoAgente) throws BaseException {
		Connection conn = null;
		boolean valido = true;
		
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_PESQUISA_VALIDAR_QTD_AGENTE);
			setInteger(1,ps,codigoAgente);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				int qtd = rs.getInt(1);
				if(qtd >= 110){
					valido = false;
				}
			}
			rs.close();
			ps.close();
			
			return valido;
			
		}catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
	}

	public AgenteVO inserir(AgenteVO agenteVO) throws BaseException {
		Connection conn = null;

		try {
			conn = getConnection();

			if (existeLogin(conn, agenteVO)) {
				throw new BaseException(
						"Já existe um agente cadastrado com o login informado.");
			}

			CallableStatement csInserir = conn
					.prepareCall("{? = call func_inserir_agente(?,?,?)}");

			csInserir.registerOutParameter(1, Types.INTEGER);
			setString(2, csInserir, agenteVO.getNome().trim());
			setString(3, csInserir, agenteVO.getLogin().toLowerCase().trim());
			setString(4, csInserir, agenteVO.getSenha().trim());

			csInserir.execute();

			Integer codigo = csInserir.getInt(1);
			agenteVO.setCodigo(codigo);

			csInserir.close();

			return agenteVO;

		} catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
	}

	public void editar(AgenteVO agenteVO) throws BaseException {
		Connection conn = null;

		try {
			
			if(validarEdicaoUsuario(agenteVO.getCodigo())) {
				conn = getConnection();

				PreparedStatement stmt = conn.prepareStatement(SQL_EDITAR_AGENTE);
				setString(1, stmt, agenteVO.getNome().trim());
				setString(2, stmt, agenteVO.getLogin().toLowerCase().trim());
				setString(3, stmt, agenteVO.getSenha().trim());
				setInteger(4, stmt, agenteVO.getCodigo());

				stmt.executeUpdate();

				stmt.close();

			} else {
				throw new BaseException("Número máximo de agentes ultrapassado. " +
						"Entre em contato com o suporte pelo e-mail siabmobile@gmail.com");
			}
			
		} catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}

	}

	public List<AgenteVO> pesquisar(AgenteVO agenteVO) throws BaseException {
		Connection conn = null;
		String query = null;
		try {
			query = preparaPesquisa(SQL_PESQUISA, agenteVO);
			conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			List<AgenteVO> lstAgentes = new ArrayList<AgenteVO>();
			AgenteVO agente = null;

			while (rs.next()) {
				agente = new AgenteVO();
				agente.setCodigo(rs.getInt("cd_agente"));
				agente.setNome(rs.getString("nm_agente"));
				agente.setLogin(rs.getString("ds_login"));
				agente.setSenha(rs.getString("ds_senha"));

				lstAgentes.add(agente);
			}

			rs.close();
			stmt.close();

			return lstAgentes;

		} catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
	}

	/**
	 * Método auxiliar utilizado para verificar se existe um agente cadastrado.
	 * 
	 * @param agenteVO
	 * @return
	 * @throws BaseException
	 */
	private boolean existeLogin(Connection conn, AgenteVO agenteVO)
			throws BaseException {
		boolean retorno = false;
		String login = agenteVO.getLogin();
		try {
			PreparedStatement stmt = conn.prepareStatement(SQL_PESQUISA_LOGIN);
			setString(1, stmt, login);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				retorno = true;
			}

			rs.close();
			stmt.close();
		} catch (Exception e) {
			throw new BaseException(e);
		}

		return retorno;
	}

	private String preparaPesquisa(String sql, AgenteVO agenteVO) {
		Integer codigo = agenteVO.getCodigo();
		String nome = agenteVO.getNome();
		String login = agenteVO.getLogin();

		if (codigo != null) {
			sql += " and a.cd_agente = " + codigo;
		}

		if (nome != null && !"".equals(nome)) {
			sql += " and a.nm_agente ilike '%" + nome + "%' ";
		}

		if (login != null && !"".equals(login)) {
			sql += " and a.ds_login ilike '%" + login + "%' ";
		}
		
		sql += " order by nm_agente";

		return sql;
	}

	public AgenteVO getAgenteById(AgenteVO agenteVO) throws BaseException {
		Connection conn = null;
		AgenteVO retorno = new AgenteVO();
		try {
			conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQL_PESQUISA_AGENTE);
			setInteger(1, stmt, agenteVO.getCodigo());

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				retorno.setCodigo(rs.getInt("cd_agente"));
				retorno.setNome(rs.getString("nm_agente"));
				retorno.setLogin(rs.getString("ds_login"));
				retorno.setSenha(rs.getString("ds_senha"));
			}

			rs.close();
			stmt.close();

		} catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
		return retorno;
	}

	public List<BBValueObject> pesquisarRelAgente(Integer cdAgente, String ano) throws BaseException {
		Connection conn = null;
		PreparedStatement ps = null;
		List<BBValueObject> lista = new ArrayList<BBValueObject>();
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(SQL_PESQUISAR_RELATORIO_AGENTE);
			setInteger(1,ps,cdAgente);
			setString(2,ps,ano);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				BBValueObject vo = new BBValueObject();
				vo.set(0,"CD_FAMILIA",rs.getLong("cd_familia"));
				vo.set(0,"DS_ENDERECO",rs.getString("ds_endereco"));
				vo.set(0,"CD_AGENTE",cdAgente);
				vo.set(0,"ANO",ano);
				lista.add(vo);
			}
			
			return lista;
			
		}catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
	}

}
