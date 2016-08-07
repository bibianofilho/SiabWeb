package br.com.siab.modulo.relatoriossa2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.biblioteca.persistence.BaseDAO;
import br.com.siab.biblioteca.utils.Utils;
import br.com.siab.modulo.relatoriossa2.model.RelatorioSSA2VO;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public class RelatorioSSA2DAOImpl extends BaseDAO implements RelatorioSSA2DAO {

	private static RelatorioSSA2DAOImpl instance;

	private RelatorioSSA2DAOImpl() {
	}

	private static final String SQL_INSERT_RELATORIO = "INSERT INTO SAUMUN VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
			+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_UPDATE_RELATORIO = "UPDATE SAUMUN "
			+ "SET nfamicad = ?, nvisitas = ?, ngescad = ?, ngesm20 = ?, ngesac = ?, ngesvac = ? "
			+ ", ngespre1 = ?, ngespre2 = ?, c_4meses = ?, c_mamand = ?, c_misto = ?, c_0a11 = ? "
			+ ", c_vacdia = ?, c_0a112p = ?, c_0a11gp = ?, c_1223 = ?, c_vacina = ?, c_12232p = ? "
			+ ", c_1223gp = ?, c_diarre = ?, c_diasro = ?, c_ira = ?, nascvivo = ?, pesados = ? "
			+ ", peso2500 = ?, o_dia0a28 = ?, o_ira0a28 = ?, o_cau0a28 = ?, o_dia28a1 = ? "
			+ ", o_ira28a1 = ?, o_cau28a1 = ?, obitodia = ?, obitoira = ?, obitocau = ? "
			+ ", o_mul10a14 = ?, obitomul = ?, obitoadol = ?, obitoout = ?, d_diabete = ? "
			+ ", d_diaac = ?, d_hiperten = ?, d_hiperac = ?, d_tubercul = ?, d_tuberac = ? "
			+ ", d_hansen = ?, d_hanseac = ?, h_0a5pneu = ?, h_0a5des = ?, h_alcool = ?, h_psiqui = ? "
			+ ", h_diabete = ?, h_outcau = ? " + "WHERE cod_seg = ? "
			+ "AND   cod_area = ? " + "AND   cod_microa = ? "
			+ "AND   mes = ? " + "AND   ano = ?";

	private static final String SQL_PESQUISAR_RELATORIO = "SELECT * FROM SAUMUN WHERE 1 = 1";
	
	private static final String SQL_PESQUISAR_RELATORIO_ID = 
		"SELECT * FROM SAUMUN WHERE 1 = 1 " +
		"AND COD_SEG = ? " +
		"AND COD_AREA = ? " +
		"AND COD_MICROA = ? " +
		"AND MES = ? " +
		"AND ANO = ? ";
	
	private static final String SQL_UPDATE_RELATORIO_EXPORTADO = 
		"UPDATE SAUMUN " +
		"set exportado = 'S' " +
		"WHERE cod_seg = ? " +
		"AND   cod_area = ? " +
		"AND   cod_microa = ? " +
		"AND   mes = ? " +
		"AND   ano = ? ";

	public static RelatorioSSA2DAOImpl getInstance() {
		if (instance == null) {
			instance = new RelatorioSSA2DAOImpl();
		}

		return instance;
	}

	public List<RelatorioSSA2VO> pesquisar(RelatorioSSA2VO vo)
			throws BaseException {
		Connection conn = null;
		String query = null;

		try {
			query = preparaPesquisa(SQL_PESQUISAR_RELATORIO, vo);
			conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			List<RelatorioSSA2VO> lstRel = new ArrayList<RelatorioSSA2VO>();

			while (rs.next()) {
				RelatorioSSA2VO rel = new RelatorioSSA2VO();
				rel.setCodSeg(rs.getInt("cod_seg"));
				rel.setCodArea(rs.getInt("cod_area"));
				rel.setCodMicroa(rs.getInt("cod_microa"));
				rel.setMes(rs.getString("mes"));
				rel.setAno(rs.getString("ano"));
				rel.setExportado(rs.getString("exportado"));

				lstRel.add(rel);
			}

			return lstRel;

		} catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
	}

	public RelatorioSSA2VO getRelatorioById(String codigo) throws BaseException {
		Integer codSeg = null;
		Integer codArea = null;
		Integer codMicroa = null;
		String mes = null;
		String ano = null;

		Connection conn = null;
		PreparedStatement ps = null;
		RelatorioSSA2VO vo = null;

		try {

			if (codigo.length() == 12) {
				ano = codigo.substring(8, codigo.length());
				mes = codigo.substring(6, codigo.length() - 4);
				codMicroa = new Integer(codigo
						.substring(4, codigo.length() - 6));
				codArea = new Integer(codigo.substring(1, codigo.length() - 8));
				codSeg = new Integer(codigo.substring(0, codigo.length() - 11));
			} else if (codigo.length() == 13) {
				ano = codigo.substring(9, codigo.length());
				mes = codigo.substring(7, codigo.length() - 4);
				codMicroa = new Integer(codigo
						.substring(5, codigo.length() - 6));
				codArea = new Integer(codigo.substring(2, codigo.length() - 8));
				codSeg = new Integer(codigo.substring(0, codigo.length() - 11));
			}
			
			conn = getConnection();
			ps = conn.prepareStatement(SQL_PESQUISAR_RELATORIO_ID);
			int index = 1;
			setInteger(index++,ps,codSeg);
			setInteger(index++,ps,codArea);
			setInteger(index++,ps,codMicroa);
			setString(index++,ps,mes);
			setString(index++,ps,ano);
			
			ResultSet rs = ps.executeQuery();
			index = 1;
			if(rs.next()){
				vo = new RelatorioSSA2VO();
				vo.setCodSeg(rs.getInt(index++));
				vo.setCodArea(rs.getInt(index++));
				vo.setCodMicroa(rs.getInt(index++));
				vo.setCodUB(rs.getLong(index++));
				vo.setCodZona(rs.getInt(index++));
				vo.setIdModelo(rs.getInt(index++));
				vo.setMes(rs.getString(index++));
				vo.setAno(rs.getString(index++));
				vo.setNfamicad(rs.getInt(index++));
				vo.setNvisitas(rs.getInt(index++));
				vo.setNgesCad(rs.getInt(index++));
				vo.setNgesm20(rs.getInt(index++));
				vo.setNgesac(rs.getInt(index++));
				vo.setNgesvac(rs.getInt(index++));
				vo.setNgespre1(rs.getInt(index++));
				vo.setNgespre2(rs.getInt(index++));
				vo.setC_4meses(rs.getInt(index++));
				vo.setC_mamand(rs.getInt(index++));
				vo.setC_misto(rs.getInt(index++));
				vo.setC_0a11(rs.getInt(index++));
				vo.setC_vacdia(rs.getInt(index++));
				vo.setC_0a112p(rs.getInt(index++));
				vo.setC_0a11gp(rs.getInt(index++));
				vo.setC_1223(rs.getInt(index++));
				vo.setC_vacina(rs.getInt(index++));
				vo.setC_12232p(rs.getInt(index++));
				vo.setC_1223gp(rs.getInt(index++));
				vo.setC_diarre(rs.getInt(index++));
				vo.setC_diasro(rs.getInt(index++));
				vo.setC_ira(rs.getInt(index++));
				vo.setNascvivo(rs.getInt(index++));
				vo.setPesados(rs.getInt(index++));
				vo.setPeso2500(rs.getInt(index++));
				vo.setO_dia0a28(rs.getInt(index++));
				vo.setO_ira0a28(rs.getInt(index++));
				vo.setO_cau0a28(rs.getInt(index++));
				vo.setO_dia28a1(rs.getInt(index++));
				vo.setO_ira28a1(rs.getInt(index++));
				vo.setO_cau28a1(rs.getInt(index++));
				vo.setObitodia(rs.getInt(index++));
				vo.setObitoira(rs.getInt(index++));
				vo.setObitocau(rs.getInt(index++));
				vo.setO_mul10a14(rs.getInt(index++));
				vo.setObitomul(rs.getInt(index++));
				vo.setObitoadol(rs.getInt(index++));
				vo.setObitoout(rs.getInt(index++));
				vo.setD_diabete(rs.getInt(index++));
				vo.setD_diaac(rs.getInt(index++));
				vo.setD_hiperten(rs.getInt(index++));
				vo.setD_hiperac(rs.getInt(index++));
				vo.setD_tubercul(rs.getInt(index++));
				vo.setD_tuberac(rs.getInt(index++));
				vo.setD_hansen(rs.getInt(index++));
				vo.setD_hanseac(rs.getInt(index++));
				vo.setH_0a5pneu(rs.getInt(index++));
				vo.setH_0a5des(rs.getInt(index++));
				vo.setH_alcool(rs.getInt(index++));
				vo.setH_psiqui(rs.getInt(index++));
				vo.setH_diabete(rs.getInt(index++));
				vo.setH_outcau(rs.getInt(index++));
				vo.setSigab(rs.getString(index++));
				vo.setExportado(rs.getString(index++));
			}
			rs.close();
			ps.close();
			
			return vo;

		} catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
	}

	private String preparaPesquisa(String sql, RelatorioSSA2VO vo) {
		Integer codSeg = vo.getCodSeg();
		Integer codArea = vo.getCodArea();
		Integer codMicroa = vo.getCodMicroa();
		String mes = vo.getMes();
		String ano = vo.getAno();

		if (codSeg != null && codSeg != 0) {
			sql += " AND COD_SEG = " + codSeg;
		}
		if (codArea != null && codArea != 0) {
			sql += " AND COD_AREA = " + codArea;
		}
		if (codMicroa != null && codMicroa != 0) {
			sql += " AND COD_MICROA = " + codMicroa;
		}
		if (mes != null && !"".equals(mes)) {
			sql += " AND MES = '" + mes + "'";
		}
		if (ano != null && !"".equals(ano)) {
			sql += " AND ANO = '" + ano + "'";
		}

		sql += " ORDER BY COD_SEG, COD_AREA, COD_MICROA, MES, ANO ";

		return sql;
	}

	/**
	 * Editando o registro no servidor
	 */
	public void editar(RelatorioSSA2VO vo) throws BaseException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = getConnection();

			ps = conn.prepareStatement(SQL_UPDATE_RELATORIO);
			int index = 1;
			setInteger(index++, ps, vo.getNfamicad());
			setInteger(index++, ps, vo.getNvisitas());
			setInteger(index++, ps, vo.getNgesCad());
			setInteger(index++, ps, vo.getNgesm20());
			setInteger(index++, ps, vo.getNgesac());
			setInteger(index++, ps, vo.getNgesvac());
			setInteger(index++, ps, vo.getNgespre1());
			setInteger(index++, ps, vo.getNgespre2());
			setInteger(index++, ps, vo.getC_4meses());
			setInteger(index++, ps, vo.getC_mamand());
			setInteger(index++, ps, vo.getC_misto());
			setInteger(index++, ps, vo.getC_0a11());
			setInteger(index++, ps, vo.getC_vacdia());
			setInteger(index++, ps, vo.getC_0a112p());
			setInteger(index++, ps, vo.getC_0a11gp());
			setInteger(index++, ps, vo.getC_1223());
			setInteger(index++, ps, vo.getC_vacina());
			setInteger(index++, ps, vo.getC_12232p());
			setInteger(index++, ps, vo.getC_1223gp());
			setInteger(index++, ps, vo.getC_diarre());
			setInteger(index++, ps, vo.getC_diasro());
			setInteger(index++, ps, vo.getC_ira());
			setInteger(index++, ps, vo.getNascvivo());
			setInteger(index++, ps, vo.getPesados());
			setInteger(index++, ps, vo.getPeso2500());
			setInteger(index++, ps, vo.getO_dia0a28());
			setInteger(index++, ps, vo.getO_ira0a28());
			setInteger(index++, ps, vo.getO_cau0a28());
			setInteger(index++, ps, vo.getO_dia28a1());
			setInteger(index++, ps, vo.getO_ira28a1());
			setInteger(index++, ps, vo.getO_cau28a1());
			setInteger(index++, ps, vo.getObitodia());
			setInteger(index++, ps, vo.getObitoira());
			setInteger(index++, ps, vo.getObitocau());
			setInteger(index++, ps, vo.getO_mul10a14());
			setInteger(index++, ps, vo.getObitomul());
			setInteger(index++, ps, vo.getObitoadol());
			setInteger(index++, ps, vo.getObitoout());
			setInteger(index++, ps, vo.getD_diabete());
			setInteger(index++, ps, vo.getD_diaac());
			setInteger(index++, ps, vo.getD_hiperten());
			setInteger(index++, ps, vo.getD_hiperac());
			setInteger(index++, ps, vo.getD_tubercul());
			setInteger(index++, ps, vo.getD_tuberac());
			setInteger(index++, ps, vo.getD_hansen());
			setInteger(index++, ps, vo.getD_hanseac());
			setInteger(index++, ps, vo.getH_0a5pneu());
			setInteger(index++, ps, vo.getH_0a5des());
			setInteger(index++, ps, vo.getH_alcool());
			setInteger(index++, ps, vo.getH_psiqui());
			setInteger(index++, ps, vo.getH_diabete());
			setInteger(index++, ps, vo.getH_outcau());

			setInteger(index++, ps, vo.getCodSeg());
			setInteger(index++, ps, vo.getCodArea());
			setInteger(index++, ps, vo.getCodMicroa());
			setString(index++, ps, vo.getMes());
			setString(index++, ps, vo.getAno());

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
	}

	/**
	 * Inserindo registro no servidor
	 */
	public void inserir(RelatorioSSA2VO vo) throws BaseException {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = getConnection();

			ps = conn.prepareStatement(SQL_INSERT_RELATORIO);
			int index = 1;
			setInteger(index++, ps, vo.getCodSeg());
			setInteger(index++, ps, vo.getCodArea());
			setInteger(index++, ps, vo.getCodMicroa());
			setLong(index++, ps, vo.getCodUB());
			setInteger(index++, ps, vo.getCodZona());
			setInteger(index++, ps, vo.getIdModelo());
			setString(index++, ps, vo.getMes());
			setString(index++, ps, vo.getAno());
			setInteger(index++, ps, vo.getNfamicad());
			setInteger(index++, ps, vo.getNvisitas());
			setInteger(index++, ps, vo.getNgesCad());
			setInteger(index++, ps, vo.getNgesm20());
			setInteger(index++, ps, vo.getNgesac());
			setInteger(index++, ps, vo.getNgesvac());
			setInteger(index++, ps, vo.getNgespre1());
			setInteger(index++, ps, vo.getNgespre2());
			setInteger(index++, ps, vo.getC_4meses());
			setInteger(index++, ps, vo.getC_mamand());
			setInteger(index++, ps, vo.getC_misto());
			setInteger(index++, ps, vo.getC_0a11());
			setInteger(index++, ps, vo.getC_vacdia());
			setInteger(index++, ps, vo.getC_0a112p());
			setInteger(index++, ps, vo.getC_0a11gp());
			setInteger(index++, ps, vo.getC_1223());
			setInteger(index++, ps, vo.getC_vacina());
			setInteger(index++, ps, vo.getC_12232p());
			setInteger(index++, ps, vo.getC_1223gp());
			setInteger(index++, ps, vo.getC_diarre());
			setInteger(index++, ps, vo.getC_diasro());
			setInteger(index++, ps, vo.getC_ira());
			setInteger(index++, ps, vo.getNascvivo());
			setInteger(index++, ps, vo.getPesados());
			setInteger(index++, ps, vo.getPeso2500());
			setInteger(index++, ps, vo.getO_dia0a28());
			setInteger(index++, ps, vo.getO_ira0a28());
			setInteger(index++, ps, vo.getO_cau0a28());
			setInteger(index++, ps, vo.getO_dia28a1());
			setInteger(index++, ps, vo.getO_ira28a1());
			setInteger(index++, ps, vo.getO_cau28a1());
			setInteger(index++, ps, vo.getObitodia());
			setInteger(index++, ps, vo.getObitoira());
			setInteger(index++, ps, vo.getObitocau());
			setInteger(index++, ps, vo.getO_mul10a14());
			setInteger(index++, ps, vo.getObitomul());
			setInteger(index++, ps, vo.getObitoadol());
			setInteger(index++, ps, vo.getObitoout());
			setInteger(index++, ps, vo.getD_diabete());
			setInteger(index++, ps, vo.getD_diaac());
			setInteger(index++, ps, vo.getD_hiperten());
			setInteger(index++, ps, vo.getD_hiperac());
			setInteger(index++, ps, vo.getD_tubercul());
			setInteger(index++, ps, vo.getD_tuberac());
			setInteger(index++, ps, vo.getD_hansen());
			setInteger(index++, ps, vo.getD_hanseac());
			setInteger(index++, ps, vo.getH_0a5pneu());
			setInteger(index++, ps, vo.getH_0a5des());
			setInteger(index++, ps, vo.getH_alcool());
			setInteger(index++, ps, vo.getH_psiqui());
			setInteger(index++, ps, vo.getH_diabete());
			setInteger(index++, ps, vo.getH_outcau());
			setString(index++, ps, vo.getSigab());
			setString(index++,ps,"N");

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}

	}

	public void exportar(RelatorioSSA2VO vo) throws BaseException {
		Connection conn = null;
		PreparedStatement ps = null;
		String ano = vo.getAno();
		
		//Exportando dados para o siab
		try {
			conn = getConnectionDBF();
			
			StringBuilder sb = new StringBuilder();
			sb.append("insert into saumun").append(ano.substring(2, ano.length())).append(" values (");
			sb.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,");
			sb.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )");
			
			ps = conn.prepareStatement(sb.toString());
			int index =1;
			setString(index++,ps,Utils.formataCodigo(""+vo.getCodSeg(),2));
			setString(index++,ps,Utils.formataCodigo(""+vo.getCodArea(),3));
			setString(index++,ps,Utils.formataCodigo(""+vo.getCodMicroa(),2));
			setString(index++,ps,vo.getCodUB().toString());
			setString(index++,ps,Utils.formataCodigo(""+vo.getCodZona(),2));
			setString(index++,ps,vo.getIdModelo().toString());
			setString(index++,ps,vo.getMes());
			setString(index++,ps,Utils.formataCampoSiab(vo.getNfamicad().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getNvisitas().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getNgesCad().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getNgesm20().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getNgesac().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getNgesvac().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getNgespre1().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getNgespre2().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getC_4meses().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getC_mamand().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getC_misto().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getC_0a11().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getC_vacdia().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getC_0a112p().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getC_0a11gp().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getC_1223().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getC_vacina().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getC_12232p().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getC_1223gp().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getC_diarre().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getC_diasro().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getC_ira().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getNascvivo().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getPesados().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getPeso2500().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getO_dia0a28().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getO_ira0a28().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getO_cau0a28().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getO_dia28a1().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getO_ira28a1().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getO_cau28a1().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getObitodia().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getObitoira().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getObitocau().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getO_mul10a14().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getObitomul().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getObitoadol().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getObitoout().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getD_diabete().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getD_diaac().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getD_hiperten().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getD_hiperac().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getD_tubercul().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getD_tuberac().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getD_hansen().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getD_hanseac().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getH_0a5pneu().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getH_0a5des().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getH_alcool().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getH_psiqui().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getH_diabete().toString(),6));
			setString(index++,ps,Utils.formataCampoSiab(vo.getH_outcau().toString(),6));
			setString(index++,ps,vo.getSigab());
			
			ps.executeUpdate();
			ps.close();
			
		}catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
		
		//Atualizando exportacao no servidor web
		try {
			conn = getConnection();
			ps = conn.prepareStatement(SQL_UPDATE_RELATORIO_EXPORTADO);
			int index = 1;
			setInteger(index++,ps,vo.getCodSeg());
			setInteger(index++,ps,vo.getCodArea());
			setInteger(index++,ps,vo.getCodMicroa());
			setString(index++,ps,vo.getMes());
			setString(index++,ps,vo.getAno());
			
			ps.executeUpdate();
			ps.close();
			
		}catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
		
	}

}
