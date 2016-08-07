package br.com.siab.modulo.sincronismo.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import br.com.siab.biblioteca.exception.BaseException;
import br.com.siab.biblioteca.persistence.BaseDAO;
import br.com.siab.biblioteca.utils.BBValueObject;
import br.com.siab.biblioteca.utils.Utils;
import br.com.siab.modulo.sincronismo.model.AgenteDBF;
import br.com.siab.modulo.sincronismo.model.EquipeDBF;
import br.com.siab.modulo.sincronismo.model.TabmunDBF;
import br.com.softsite.sfc.tini.persistence.Table;

public class SincronismoDAOImpl extends BaseDAO implements SincronismoDAO {

	private static SincronismoDAOImpl instance;

	private SincronismoDAOImpl() {

	}

	public static SincronismoDAOImpl getInstance() {
		if (instance == null) {
			instance = new SincronismoDAOImpl();
		}

		return instance;
	}
	
	private final ResourceBundle bundleConfig = ResourceBundle.getBundle("Configuracoes");

	// TABMUN
	private static final String SQL_DBF_TABMUN_SELECT = "select * from tabmun";

	private static final String SQL_TABMUN_DELETE = "DELETE FROM TABMUN";

	private static final String SQL_TABMUN_INSERT = "INSERT INTO TABMUN (COD_ESTA,COD_REGI,COD_MUNI,COD_SEG,COD_ZONA,NOME) VALUES (?,?,?,?,?,?)";

	// EQUIPE
	private static final String SQL_DBF_EQUIPE_SELECT = "select * from equipe";

	private static final String SQL_EQUIPE_DELETE = "DELETE FROM EQUIPE";

	private static final String SQL_EQUIPE_INSERT = "INSERT INTO EQUIPE(COD_SEG,COD_AREA,ID_MODELO,COD_ATP,"
			+ "COD_PROF,COD_MICROA,COD_UB,NOME_AREA,ORDEM,SITUACAO,ID_BUCAL,ID_POPULA,ASSEN,QUILO,GERAL) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	// AGENTE
	private static final String SQL_DBF_AGENTE_SELECT = "select cod_age,nome from agente";

	private static final String SQL_AGENTE_INSERT_TMP = "INSERT INTO AGENTE_TMP(CD_AGENTE,NM_AGENTE) VALUES(?,?)";

	private static final String SQL_AGENTE_UPDATE = "UPDATE AGENTE "
			+ "SET NM_AGENTE = TMP.NM_AGENTE " + "FROM AGENTE_TMP TMP "
			+ "WHERE AGENTE.CD_AGENTE = TMP.CD_AGENTE";

	private static final String SQL_AGENTE_INSERT = "INSERT INTO AGENTE (CD_AGENTE,NM_AGENTE) "
			+ "	SELECT CD_AGENTE,NM_AGENTE "
			+ "	FROM AGENTE_TMP TMP "
			+ "	WHERE TMP.CD_AGENTE "
			+ "	NOT IN ( SELECT CD_AGENTE FROM AGENTE )";

	// FAMILIA	
	private static final String SQL_FUNC_SYNC_DELETE_FICHACADFAM = 
		"SELECT * FROM FUNC_SYNC_DELETE_FICHACADFAM() AS (ID_KEY varchar, CD_SINC_USUARIO INT4)";
	private static final String SQL_FUNC_SYNC_DELETE_PACFAMILIA = 
		"SELECT * FROM FUNC_SYNC_DELETE_PACFAMILIA() AS (ID_KEY varchar, CD_SINC_USUARIO INT4) ";
	
	private static final String SQL_FUNC_SYNC_UPDATE_FICHACADFAM = 
		"SELECT * FROM FUNC_SYNC_UPDATE_FICHACADFAM() AS "+ 
		"(cod_seg text, cod_area text, cod_microa text, id_modelo varchar(1),  nfamilia text,  "+ 
		"npessoas varchar,  fem1 varchar, fem2 varchar, fem3 varchar, fem4 varchar,  "+ 
		"fem5 varchar, fem6 varchar, fem7 varchar, fem8 varchar, fem9 varchar, fem10 varchar,  "+ 
		"masc1 varchar, masc2 varchar, masc3 varchar, masc4 varchar, masc5 varchar, masc6 varchar,  "+ 
		"masc7 varchar, masc8 varchar, masc9 varchar, masc10 varchar, escola varchar, alfabe varchar,  "+ 
		"alc1 varchar, alc2 varchar, cha1 varchar, cha2 varchar, def1 varchar, def2 varchar, dia1 varchar,  "+ 
		"dia2 varchar, dme1 varchar, dme2 varchar, epi1 varchar, epi2 varchar, ges1 varchar, ges2 varchar,  "+ 
		"han1 varchar, han2 varchar, ha1 varchar, ha2 varchar, mal1 varchar, mal2 varchar, tbc1 varchar,  "+ 
		"tbc2 varchar, id_tipo text, id_elet text, qtd_como text, id_lixo text, id_trata text,  "+ 
		"id_agua text, id_urina text, id_doen text, id_meio text, id_grupo text,  "+ 
		"id_tran text, cobertura varchar, ano text, cd_sinc_usuario int4)";
	
	private static final String SQL_FUNC_SYNC_UPDATE_PACFAMILIA_MAIOR = 
		"SELECT * FROM func_sync_update_pacfamilia_maior() AS " +
		"(COD_SEG TEXT, COD_AREA TEXT, COD_MICROA TEXT, NFAMILIA TEXT,DTNASC CHAR(10)," +
		"IDADE TEXT, SEXO CHAR(1), ALFA CHAR(1), COD_OCUP TEXT, NOME_OCUP varchar(10)," +
		"ALC TEXT, CHA TEXT, DEF TEXT, DIA TEXT, DME TEXT, EPI TEXT, GES TEXT, HAN TEXT," +
		"HA TEXT, MAL TEXT, TBC TEXT, ANO TEXT,ROWID_DBF INT8, CD_SINC_USUARIO INT4)";
	
	private static final String SQL_FUNC_SYNC_UPDATE_PACFAMILIA_MENOR = 
		"SELECT * FROM func_sync_update_pacfamilia_menor() AS " +
		"(COD_SEG TEXT, COD_AREA TEXT, COD_MICROA TEXT, NFAMILIA TEXT,DTNASC CHAR(10)," +
		"IDADE TEXT, SEXO CHAR(1), ESCOLA CHAR(1), COD_OCUP TEXT, NOME_OCUP varchar(10)," +
		"ALC TEXT, CHA TEXT, DEF TEXT, DIA TEXT, DME TEXT, EPI TEXT, GES TEXT, HAN TEXT," +
		"HA TEXT, MAL TEXT, TBC TEXT, ANO TEXT, ROWID_DBF INT8, CD_SINC_USUARIO INT4)";
	
	/*private static final String SQL_FUNC_SYNC_INSERT_FICHACADFAM = 
		"SELECT * from func_sync_insert_fichacadfam() AS " +
		"(cod_seg text, cod_area text, cod_microa text, id_modelo varchar(1),  nfamilia text, " +
		"npessoas text,  fem1 text, fem2 text, fem3 text, fem4 text, " +
		"fem5 text, fem6 text, fem7 text, fem8 text, fem9 text, fem10 text, " +
		"masc1 text, masc2 text, masc3 text, masc4 text, masc5 text, masc6 text, " +
		"masc7 text, masc8 text, masc9 text, masc10 text, escola text, alfabe text, " +
		"alc1 text, alc2 text, cha1 text, cha2 text, def1 text, def2 text, dia1 text, " +
		"dia2 text, dme1 text, dme2 text, epi1 text, epi2 text, ges1 text, ges2 text, " +
		"han1 text, han2 text, ha1 text, ha2 text, mal1 text, mal2 text, tbc1 text, " +
		"tbc2 text, id_tipo text, id_elet text, qtd_como text, id_lixo text, id_trata text, " +
		"id_agua text, id_urina text, id_doen text, id_meio text, id_grupo text, " +
		"id_tran text, cobertura text, ano text, cd_sinc_usuario int4)";*/
	private static final String SQL_FUNC_SYNC_INSERT_FICHACADFAM = 
	"SELECT * from func_sync_insert_fichacadfam() AS "+ 
			"(cod_seg text, cod_area text, cod_microa text, id_modelo varchar(1),  nfamilia text, "+
			"npessoas varchar(2),  fem1 varchar(2), fem2 varchar(2), fem3 varchar(2), fem4 varchar(2), "+
			"fem5 varchar(2), fem6 varchar(2), fem7 varchar(2), fem8 varchar(2), fem9 varchar(2), fem10 varchar(2),"+ 
			"masc1 varchar(2), masc2 varchar(2), masc3 varchar(2), masc4 varchar(2), masc5 varchar(2), masc6 varchar(2),"+ 
			"masc7 varchar(2), masc8 varchar(2), masc9 varchar(2), masc10 varchar(2), escola varchar(2), alfabe varchar(2),"+
			"alc1 varchar(2), alc2 varchar(2), cha1 varchar(2), cha2 varchar(2), def1 varchar(2), def2 varchar(2), dia1 varchar(2),"+ 
			"dia2 varchar(2), dme1 varchar(2), dme2 varchar(2), epi1 varchar(2), epi2 varchar(2), ges1 varchar(2), ges2 varchar(2), "+
			"han1 varchar(2), han2 varchar(2), ha1 varchar(2), ha2 varchar(2), mal1 varchar(2), mal2 varchar(2), tbc1 varchar(2), "+
			"tbc2 varchar(2), id_tipo text, id_elet text, qtd_como text, id_lixo text, id_trata text, "+
			"id_agua text, id_urina text, id_doen text, id_meio text, id_grupo text, "+
			"id_tran text, cobertura varchar(2), ano text, cd_sinc_usuario int4)";
	
	private static final String SQL_FUNC_SYNC_INSERT_PACFAMILIA_MAIOR = 
		"SELECT * FROM func_sync_insert_pacfamilia_maior() AS " +
		"(CD_FAMILIA int8,COD_SEG TEXT, COD_AREA TEXT, COD_MICROA TEXT, NFAMILIA TEXT,DTNASC CHAR(10)," +
		"IDADE TEXT, SEXO CHAR(1), ALFA CHAR(1), COD_OCUP TEXT, NOME_OCUP VARCHAR(60)," +
		"ALC TEXT, CHA TEXT, DEF TEXT, DIA TEXT, DME TEXT, EPI TEXT, GES TEXT, HAN TEXT," +
		"HA TEXT, MAL TEXT, TBC TEXT, ANO TEXT, ROWID_DBF INT8, CD_SINC_USUARIO INT4)";
	
	private static final String SQL_FUNC_SYNC_INSERT_PACFAMILIA_MENOR = 
		"SELECT * FROM func_sync_insert_pacfamilia_menor() AS " +
		"(CD_FAMILIA int8, COD_SEG TEXT, COD_AREA TEXT, COD_MICROA TEXT, NFAMILIA TEXT,DTNASC CHAR(10)," +
		"IDADE TEXT, SEXO CHAR(1), ESCOLA CHAR(1), COD_OCUP TEXT, NOME_OCUP VARCHAR(60)," +
		"ALC TEXT, CHA TEXT, DEF TEXT, DIA TEXT, DME TEXT, EPI TEXT, GES TEXT, HAN TEXT," +
		"HA TEXT, MAL TEXT, TBC TEXT, ANO TEXT, ROWID_DBF INT8, CD_SINC_USUARIO INT4)";
	
	private static final String SQL_FUNC_SYNC_IMPORT_PACFAMILIA_INSERT = 
		"INSERT INTO PACFAMILIA VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String SQL_SELECT_CD_PACIENTE = 
		"SELECT case when  max(cd_paciente) is null then 1 else max(cd_paciente)+1 end as cd_paciente " +
		"from 	pacfamilia " +
		"where 	cd_paciente < 500 " +
		"and  	cd_familia = ? "+
		"and    cd_ano=? "; 
	
	private static final String SQL_FUNC_SYNC_IMPORT_PACFAMILIA_SELECT = 
		" select tmp.cod_seg as cd_segmento, " +
		"		tmp.cod_area as cd_area, " +
		"		tmp.cod_microa as cd_microarea, " +
		"		tmp.nr_familia as nr_familia, " +
		"      	99999 as cd_paciente, "+
		"       tmp.cod_ano,"+
		"		null as nm_paciente, "+
		"		tmp.dt_nascimento as dt_nascimento, "+
		"		tmp.num_idade as num_idade, "+
		"		tmp.fl_sexo as fl_sexo, "+
        "       tmp.fl_menorquinze as fl_menorquinze, "+
		"		tmp.fl_alfabetizado as fl_alfabetizado, "+
		"		tmp.fl_freqescola as fl_freqescola, "+
		"		tmp.cd_ocupacao  as cd_ocupacao, "+
		"		tmp.fl_alc as fl_alc, "+
		"		tmp.fl_cha as fl_cha, "+
		"		tmp.fl_def as fl_def, "+
		"		tmp.fl_dia as fl_dia, "+
		"		tmp.fl_dme as fl_dme, "+
		"		tmp.fl_epi as fl_epi, "+
		"		tmp.fl_ges as fl_ges, "+
		"		tmp.fl_han as fl_han, "+
		"		tmp.fl_ha as fl_ha, "+
		"		tmp.fl_mal as fl_mal, "+
		"		tmp.fl_tbc as fl_tbc, " +
		"		tmp.fl_maior as fl_maior, "+
		"		tmp.rowid_dbf as rowid_dbf  "+
		" from pacfamilia_tmp as tmp "+
		" where not exists( "+
		"		select * from pacfamilia as pac "+
		"		where pac.cd_segmento = tmp.cod_seg " +
		"		and pac.cd_area = tmp.cod_area " +
		"		and pac.cd_microarea = tmp.cod_microa" +
		"		and pac.nr_familia = tmp.nr_familia "+
		//"		and pac.dt_nascimento=tmp.dt_nascimento "+
        "       and pac.rowid_dbf=tmp.rowid_dbf " +
        "		and pac.cd_ano=tmp.cod_ano )";	
	
	// LOG ALTERACAO USUARIO
	private static final String SQL_LOG_ALTERACAO_USUARIO_EXPORTACAO = 
		"UPDATE LOG_ALTERACAO_USUARIO " +
		"SET	DT_SINCRONISMO = CURRENT_TIMESTAMP " +
		"WHERE  DT_SINCRONISMO IS NULL " +
		"AND    CD_SISTEMA_EXT = 'WEB' " +
		"AND    CD_SINC_USUARIO <= ? " +
		"AND    NM_TABELA = ? " +
		"AND    TP_OPERACAO = ?";
	
	// ROWID ADULTO
	private static final String SQL_UPDATE_ROWID_PACFAMILIA_ADULTO = 
		"update pacfamilia set rowid_dbf = ?"+
		"		where rowid_dbf is null" +
		"		and cd_familia = ?" +
		"		and cd_paciente in (select min(cd_paciente) from pacfamilia where cd_familia = ? and  rowid_dbf is null" + 
		"		and cast(substr(age(to_date(dt_nascimento,'dd/mm/yyyy')),1,2)as int2) > 14)"; 
	
	// ROWID CRIANC
	private static final String SQL_UPDATE_ROWID_PACFAMILIA_CRIANC = 
		"update pacfamilia set rowid_dbf = ?"+
		"		where rowid_dbf is null" +
		"		and cd_familia = ?" +
		"		and cd_paciente in (select min(cd_paciente) from pacfamilia where cd_familia = ? and  rowid_dbf is null" + 
		"		and cast(substr(age(to_date(dt_nascimento,'dd/mm/yyyy')),1,2)as int2) <= 14)"; 

	public void realizarSincronismo(String ano,ServletContext context) throws BaseException {
		this.exportacao(ano);
		this.importacao(ano,context);
		
	}
	
	/**
	 * Método responsável pela exportação dos dados para o siab
	 * 
	 * @throws BaseException
	 */
	private void exportacao(String ano) throws BaseException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			conn = getConnection();
			Long cdSincUsuario = new Long(0);
			List<BBValueObject> listaDelete = new ArrayList<BBValueObject>();

			/* FAMILIA DELETE */
			stmt = conn.createStatement();
			
			// Obtendo registros a serem excluídos
			rs = stmt.executeQuery(SQL_FUNC_SYNC_DELETE_PACFAMILIA);
			while(rs.next()){
				String idKey = rs.getString("ID_KEY");
				cdSincUsuario = rs.getLong("CD_SINC_USUARIO");
				BBValueObject vo = getVOCdFamilia(idKey);
				vo.set(0,"ANO",ano.substring(2,4));
				listaDelete.add(vo);
			}
			
			if(listaDelete.size() > 0){
				
				//Excluindo registros siab
				exportacaoDeleteFamiliaSiab(listaDelete,"PACFAMILIA");
				
				ps = conn.prepareStatement(SQL_LOG_ALTERACAO_USUARIO_EXPORTACAO);
				setLong(1,ps,cdSincUsuario);
				setString(2,ps,"PACFAMILIA");
				setString(3,ps,"D");
				ps.executeUpdate();
			}
			
			cdSincUsuario = new Long(0);
			listaDelete = new ArrayList<BBValueObject>();

			rs = stmt.executeQuery(SQL_FUNC_SYNC_DELETE_FICHACADFAM);
			while (rs.next()) {
				String idKey = rs.getString("ID_KEY");
				cdSincUsuario = rs.getLong("CD_SINC_USUARIO");
				BBValueObject vo = getVOCdFamilia(idKey);
				vo.set(0,"ANO",ano.substring(2,4));
				listaDelete.add(vo);
			}
			
			if(listaDelete.size() > 0){
				//Excluindo registros siab
				exportacaoDeleteFamiliaSiab(listaDelete,"FICHACADFAM");
				
				//Atualizar log sincronismo
				ps = conn.prepareStatement(SQL_LOG_ALTERACAO_USUARIO_EXPORTACAO);
				setLong(1,ps,cdSincUsuario);
				setString(2,ps,"FICHACADFAM");
				setString(3,ps,"D");
				ps.executeUpdate();
			}
			
			/* FAMILIA UPDATE */
			List<BBValueObject> listaUpdate = new ArrayList<BBValueObject>();
			
			//SANMUN
			rs = stmt.executeQuery(SQL_FUNC_SYNC_UPDATE_FICHACADFAM);
			while(rs.next()){
				cdSincUsuario = rs.getLong("CD_SINC_USUARIO");
				
				BBValueObject vo = new BBValueObject();
				vo.set(0,"COD_SEG",rs.getString("COD_SEG"));
				vo.set(0,"COD_AREA",rs.getString("COD_AREA"));
				vo.set(0,"COD_MICROA",rs.getString("COD_MICROA"));
				vo.set(0,"ID_MODELO",rs.getString("ID_MODELO"));
				vo.set(0,"NFAMILIA",rs.getString("NFAMILIA"));
				vo.set(0,"NPESSOAS",rs.getString("NPESSOAS"));
				vo.set(0,"FEM1",rs.getString("FEM1").equals("0")? "" : rs.getString("FEM1"));
				vo.set(0,"FEM2",rs.getString("FEM2").equals("0")? "" : rs.getString("FEM2"));
				vo.set(0,"FEM3",rs.getString("FEM3").equals("0")? "" : rs.getString("FEM3"));
				vo.set(0,"FEM4",rs.getString("FEM4").equals("0")? "" : rs.getString("FEM4"));
				vo.set(0,"FEM5",rs.getString("FEM5").equals("0")? "" : rs.getString("FEM5"));
				vo.set(0,"FEM6",rs.getString("FEM6").equals("0")? "" : rs.getString("FEM6"));
				vo.set(0,"FEM7",rs.getString("FEM7").equals("0")? "" : rs.getString("FEM7"));
				vo.set(0,"FEM8",rs.getString("FEM8").equals("0")? "" : rs.getString("FEM8"));
				vo.set(0,"FEM9",rs.getString("FEM9").equals("0")? "" : rs.getString("FEM9"));
				vo.set(0,"FEM10",rs.getString("FEM10").equals("0")? "" : rs.getString("FEM10"));
				vo.set(0,"MASC1",rs.getString("MASC1").equals("0")? "" : rs.getString("MASC1"));
				vo.set(0,"MASC2",rs.getString("MASC2").equals("0")? "" : rs.getString("MASC2"));
				vo.set(0,"MASC3",rs.getString("MASC3").equals("0")? "" : rs.getString("MASC3"));
				vo.set(0,"MASC4",rs.getString("MASC4").equals("0")? "" : rs.getString("MASC4"));
				vo.set(0,"MASC5",rs.getString("MASC5").equals("0")? "" : rs.getString("MASC5"));
				vo.set(0,"MASC6",rs.getString("MASC6").equals("0")? "" : rs.getString("MASC6"));
				vo.set(0,"MASC7",rs.getString("MASC7").equals("0")? "" : rs.getString("MASC7"));
				vo.set(0,"MASC8",rs.getString("MASC8").equals("0")? "" : rs.getString("MASC8"));
				vo.set(0,"MASC9",rs.getString("MASC9").equals("0")? "" : rs.getString("MASC9"));
				vo.set(0,"MASC10",rs.getString("MASC10").equals("0")? "" : rs.getString("MASC10"));
				vo.set(0,"ESCOLA",rs.getString("ESCOLA").equals("0")? "" : rs.getString("ESCOLA"));
				vo.set(0,"ALFABE",rs.getString("ALFABE").equals("0")? "" : rs.getString("ALFABE"));
				vo.set(0,"ALC1",rs.getString("ALC1").equals("0")? "" : rs.getString("ALC1"));
				vo.set(0,"ALC2",rs.getString("ALC2").equals("0")? "" : rs.getString("ALC2"));
				vo.set(0,"CHA1",rs.getString("CHA1").equals("0")? "" : rs.getString("CHA1"));
				vo.set(0,"CHA2",rs.getString("CHA2").equals("0")? "" : rs.getString("CHA2"));
				vo.set(0,"DEF1",rs.getString("DEF1").equals("0")? "" : rs.getString("DEF1"));
				vo.set(0,"DEF2",rs.getString("DEF2").equals("0")? "" : rs.getString("DEF2"));
				vo.set(0,"DIA1",rs.getString("DIA1").equals("0")? "" : rs.getString("DIA1"));
				vo.set(0,"DIA2",rs.getString("DIA2").equals("0")? "" : rs.getString("DIA2"));
				vo.set(0,"DME1",rs.getString("DME1").equals("0")? "" : rs.getString("DME1"));
				vo.set(0,"DME2",rs.getString("DME2").equals("0")? "" : rs.getString("DME2"));
				vo.set(0,"EPI1",rs.getString("EPI1").equals("0")? "" : rs.getString("EPI1"));
				vo.set(0,"EPI2",rs.getString("EPI2").equals("0")? "" : rs.getString("EPI2"));
				vo.set(0,"GES1",rs.getString("GES1").equals("0")? "" : rs.getString("GES1"));
				vo.set(0,"GES2",rs.getString("GES2").equals("0")? "" : rs.getString("GES2"));
				vo.set(0,"HAN1",rs.getString("HAN1").equals("0")? "" : rs.getString("HAN1"));
				vo.set(0,"HAN2",rs.getString("HAN2").equals("0")? "" : rs.getString("HAN2"));
				vo.set(0,"HA1",rs.getString("HA1").equals("0")? "" : rs.getString("HA1"));
				vo.set(0,"HA2",rs.getString("HA2").equals("0")? "" : rs.getString("HA2"));
				vo.set(0,"MAL1",rs.getString("MAL1").equals("0")? "" : rs.getString("MAL1"));
				vo.set(0,"MAL2",rs.getString("MAL2").equals("0")? "" : rs.getString("MAL2"));
				vo.set(0,"TBC1",rs.getString("TBC1").equals("0")? "" : rs.getString("TBC1"));
				vo.set(0,"TBC2",rs.getString("TBC2").equals("0")? "" : rs.getString("TBC2"));
				vo.set(0,"ID_TIPO",rs.getString("ID_TIPO"));
				vo.set(0,"ID_ELET",rs.getString("ID_ELET"));
				vo.set(0,"QTD_COMO",rs.getString("QTD_COMO"));
				vo.set(0,"ID_LIXO",rs.getString("ID_LIXO"));
				vo.set(0,"ID_TRATA",rs.getString("ID_TRATA"));
				vo.set(0,"ID_AGUA",rs.getString("ID_AGUA"));
				vo.set(0,"ID_URINA",rs.getString("ID_URINA"));
				vo.set(0,"ID_DOEN",rs.getString("ID_DOEN"));
				vo.set(0,"ID_MEIO",rs.getString("ID_MEIO"));
				vo.set(0,"ID_GRUPO",rs.getString("ID_GRUPO").equals("0")? "" : rs.getString("ID_GRUPO"));
				vo.set(0,"ID_TRAN",rs.getString("ID_TRAN"));
				vo.set(0,"COBERTURA",rs.getString("COBERTURA").equals("0")? "" : rs.getString("COBERTURA"));
				vo.set(0,"ANO",rs.getString("ANO"));
				
				listaUpdate.add(vo);
			}
			
			if(listaUpdate.size() > 0){
				exportacaoUpdateFamiliaSiab(listaUpdate,"SANMUN");
				
				//Atualizar log sincronismo
				ps = conn.prepareStatement(SQL_LOG_ALTERACAO_USUARIO_EXPORTACAO);
				setLong(1,ps,cdSincUsuario);
				setString(2,ps,"FICHACADFAM");
				setString(3,ps,"U");
				ps.executeUpdate();
			}
			
			//ADULTO
			listaUpdate = new ArrayList<BBValueObject>();
			Long cdSincUsuarioAdulto = new Long(0);
			
			rs = stmt.executeQuery(SQL_FUNC_SYNC_UPDATE_PACFAMILIA_MAIOR);
			while(rs.next()){
				BBValueObject vo = new BBValueObject();
				vo.set(0,"COD_SEG",rs.getString("COD_SEG"));
				vo.set(0,"COD_AREA",rs.getString("COD_AREA"));
				vo.set(0,"COD_MICROA",rs.getString("COD_MICROA"));
				vo.set(0,"NFAMILIA",rs.getString("NFAMILIA"));
				vo.set(0,"DTNASC",rs.getString("DTNASC"));
				vo.set(0,"IDADE",rs.getString("IDADE"));
				vo.set(0,"SEXO",rs.getString("SEXO"));
				vo.set(0,"ALFA",rs.getString("ALFA"));
				vo.set(0,"COD_OCUP",rs.getString("COD_OCUP"));
				vo.set(0,"NOME_OCUP",rs.getString("NOME_OCUP"));
				vo.set(0,"ALC",rs.getString("ALC"));
				vo.set(0,"CHA",rs.getString("CHA"));
				vo.set(0,"DEF",rs.getString("DEF"));
				vo.set(0,"DIA",rs.getString("DIA"));
				vo.set(0,"DME",rs.getString("DME"));
				vo.set(0,"EPI",rs.getString("EPI"));
				vo.set(0,"GES",rs.getString("GES"));
				vo.set(0,"HAN",rs.getString("HAN"));
				vo.set(0,"HA",rs.getString("HA"));
				vo.set(0,"MAL",rs.getString("MAL"));
				vo.set(0,"TBC",rs.getString("TBC"));
				vo.set(0,"ANO",rs.getString("ANO"));
				vo.set(0,"ROWID_DBF",rs.getString("ROWID_DBF"));
				cdSincUsuarioAdulto = rs.getLong("CD_SINC_USUARIO");
				
				listaUpdate.add(vo);
			}
			
			if(listaUpdate.size() > 0){
				exportacaoUpdateFamiliaSiab(listaUpdate,"ADULTO");	
			}
			
			//CRIANC
			listaUpdate = new ArrayList<BBValueObject>();
			Long cdSincUsuarioMenor = new Long(0);
			
			rs = stmt.executeQuery(SQL_FUNC_SYNC_UPDATE_PACFAMILIA_MENOR);
			while(rs.next()){
				BBValueObject vo = new BBValueObject();				
				vo.set(0,"COD_SEG",rs.getString("COD_SEG"));
				vo.set(0,"COD_AREA",rs.getString("COD_AREA"));
				vo.set(0,"COD_MICROA",rs.getString("COD_MICROA"));
				vo.set(0,"NFAMILIA",rs.getString("NFAMILIA"));
				vo.set(0,"DTNASC",rs.getString("DTNASC"));
				vo.set(0,"IDADE",rs.getString("IDADE"));
				vo.set(0,"SEXO",rs.getString("SEXO"));
				vo.set(0,"ESCOLA",rs.getString("ESCOLA"));
				vo.set(0,"COD_OCUP",rs.getString("COD_OCUP"));
				vo.set(0,"NOME_OCUP",rs.getString("NOME_OCUP"));
				vo.set(0,"ALC",rs.getString("ALC"));
				vo.set(0,"CHA",rs.getString("CHA"));
				vo.set(0,"DEF",rs.getString("DEF"));
				vo.set(0,"DIA",rs.getString("DIA"));
				vo.set(0,"DME",rs.getString("DME"));
				vo.set(0,"EPI",rs.getString("EPI"));
				vo.set(0,"GES",rs.getString("GES"));
				vo.set(0,"HAN",rs.getString("HAN"));
				vo.set(0,"HA",rs.getString("HA"));
				vo.set(0,"MAL",rs.getString("MAL"));
				vo.set(0,"TBC",rs.getString("TBC"));
				vo.set(0,"ANO",rs.getString("ANO"));
				vo.set(0,"ROWID_DBF",rs.getString("ROWID_DBF"));
				
				cdSincUsuarioMenor = rs.getLong("CD_SINC_USUARIO");
				
				listaUpdate.add(vo);
			}
			
			if(listaUpdate.size() > 0){
				exportacaoUpdateFamiliaSiab(listaUpdate,"CRIANC");	
			}
			
			cdSincUsuario = (cdSincUsuarioAdulto > cdSincUsuarioMenor)? cdSincUsuarioAdulto: cdSincUsuarioMenor;
			
			//Atualizar log sincronismo
			if(cdSincUsuario != 0){
				ps = conn.prepareStatement(SQL_LOG_ALTERACAO_USUARIO_EXPORTACAO);
				setLong(1,ps,cdSincUsuario);
				setString(2,ps,"PACFAMILIA");
				setString(3,ps,"U");
				ps.executeUpdate();
			}
			
			/* FAMILIA INSERT */
			
			List<BBValueObject> listaInsert = new ArrayList<BBValueObject>();
			
			//SANMUN
			rs = stmt.executeQuery(SQL_FUNC_SYNC_INSERT_FICHACADFAM);
			while(rs.next()){
				cdSincUsuario = rs.getLong("CD_SINC_USUARIO");
				
				BBValueObject vo = new BBValueObject();
				vo.set(0,"COD_SEG",rs.getString("COD_SEG"));
				vo.set(0,"COD_AREA",rs.getString("COD_AREA"));
				vo.set(0,"COD_MICROA",rs.getString("COD_MICROA"));
				vo.set(0,"COD_ZONA",rs.getString("COD_SEG")); //COD_ZONA = COD_SEG
				vo.set(0,"ID_MODELO",rs.getString("ID_MODELO"));
				vo.set(0,"NFAMILIA",rs.getString("NFAMILIA"));
				vo.set(0,"NPESSOAS",rs.getString("NPESSOAS"));
				vo.set(0,"FEM1",rs.getString("FEM1"));
				vo.set(0,"FEM2",rs.getString("FEM2"));
				vo.set(0,"FEM3",rs.getString("FEM3"));
				vo.set(0,"FEM4",rs.getString("FEM4"));
				vo.set(0,"FEM5",rs.getString("FEM5"));
				vo.set(0,"FEM6",rs.getString("FEM6"));
				vo.set(0,"FEM7",rs.getString("FEM7"));
				vo.set(0,"FEM8",rs.getString("FEM8"));
				vo.set(0,"FEM9",rs.getString("FEM9"));
				vo.set(0,"FEM10",rs.getString("FEM10"));
				vo.set(0,"MASC1",rs.getString("MASC1"));
				vo.set(0,"MASC2",rs.getString("MASC2"));
				vo.set(0,"MASC3",rs.getString("MASC3"));
				vo.set(0,"MASC4",rs.getString("MASC4"));
				vo.set(0,"MASC5",rs.getString("MASC5"));
				vo.set(0,"MASC6",rs.getString("MASC6"));
				vo.set(0,"MASC7",rs.getString("MASC7"));
				vo.set(0,"MASC8",rs.getString("MASC8"));
				vo.set(0,"MASC9",rs.getString("MASC9"));
				vo.set(0,"MASC10",rs.getString("MASC10"));
				vo.set(0,"ESCOLA",rs.getString("ESCOLA"));
				vo.set(0,"ALFABE",rs.getString("ALFABE"));
				vo.set(0,"ALC1",rs.getString("ALC1"));
				vo.set(0,"ALC2",rs.getString("ALC2"));
				vo.set(0,"CHA1",rs.getString("CHA1"));
				vo.set(0,"CHA2",rs.getString("CHA2"));
				vo.set(0,"DEF1",rs.getString("DEF1"));
				vo.set(0,"DEF2",rs.getString("DEF2"));
				vo.set(0,"DIA1",rs.getString("DIA1"));
				vo.set(0,"DIA2",rs.getString("DIA2"));
				vo.set(0,"DME1",rs.getString("DME1"));
				vo.set(0,"DME2",rs.getString("DME2"));
				vo.set(0,"EPI1",rs.getString("EPI1"));
				vo.set(0,"EPI2",rs.getString("EPI2"));
				vo.set(0,"GES1",rs.getString("GES1"));
				vo.set(0,"GES2",rs.getString("GES2"));
				vo.set(0,"HAN1",rs.getString("HAN1"));
				vo.set(0,"HAN2",rs.getString("HAN2"));
				vo.set(0,"HA1",rs.getString("HA1"));
				vo.set(0,"HA2",rs.getString("HA2"));
				vo.set(0,"MAL1",rs.getString("MAL1"));
				vo.set(0,"MAL2",rs.getString("MAL2"));
				vo.set(0,"TBC1",rs.getString("TBC1"));
				vo.set(0,"TBC2",rs.getString("TBC2"));
				vo.set(0,"ID_TIPO",rs.getString("ID_TIPO"));
				vo.set(0,"ID_ELET",rs.getString("ID_ELET"));
				vo.set(0,"QTD_COMO",rs.getString("QTD_COMO"));
				vo.set(0,"ID_LIXO",rs.getString("ID_LIXO"));
				vo.set(0,"ID_TRATA",rs.getString("ID_TRATA"));
				vo.set(0,"ID_AGUA",rs.getString("ID_AGUA"));
				vo.set(0,"ID_URINA",rs.getString("ID_URINA"));
				vo.set(0,"ID_DOEN",rs.getString("ID_DOEN"));
				vo.set(0,"ID_MEIO",rs.getString("ID_MEIO"));
				vo.set(0,"ID_GRUPO",rs.getString("ID_GRUPO"));
				vo.set(0,"ID_TRAN",rs.getString("ID_TRAN"));
				vo.set(0,"COBERTURA",rs.getString("COBERTURA"));
				vo.set(0,"ANO",rs.getString("ANO"));
				
				listaInsert.add(vo);
			}
			
			if(listaInsert.size() > 0){
				exportacaoInsertFamiliaSiab(listaInsert,"SANMUN");
				
				//Atualizar log sincronismo
				ps = conn.prepareStatement(SQL_LOG_ALTERACAO_USUARIO_EXPORTACAO);
				setLong(1,ps,cdSincUsuario);
				setString(2,ps,"FICHACADFAM");
				setString(3,ps,"I");
				ps.executeUpdate();
			}
			
			//ADULTO
			listaInsert = new ArrayList<BBValueObject>();
			cdSincUsuarioAdulto = new Long(0);
			
			rs = stmt.executeQuery(SQL_FUNC_SYNC_INSERT_PACFAMILIA_MAIOR);
			while(rs.next()){
				BBValueObject vo = new BBValueObject();
				vo.set(0,"CD_FAMILIA",rs.getLong("CD_FAMILIA"));
				vo.set(0,"COD_SEG",rs.getString("COD_SEG"));
				vo.set(0,"COD_AREA",rs.getString("COD_AREA"));
				vo.set(0,"COD_MICROA",rs.getString("COD_MICROA"));
				vo.set(0,"NFAMILIA",rs.getString("NFAMILIA"));
				vo.set(0,"DTNASC",rs.getString("DTNASC"));
				vo.set(0,"IDADE",rs.getString("IDADE"));
				vo.set(0,"SEXO",rs.getString("SEXO"));
				vo.set(0,"ALFA",rs.getString("ALFA"));
				vo.set(0,"COD_OCUP",rs.getString("COD_OCUP"));
				vo.set(0,"NOME_OCUP",rs.getString("NOME_OCUP"));
				vo.set(0,"ALC",rs.getString("ALC"));
				vo.set(0,"CHA",rs.getString("CHA"));
				vo.set(0,"DEF",rs.getString("DEF"));
				vo.set(0,"DIA",rs.getString("DIA"));
				vo.set(0,"DME",rs.getString("DME"));
				vo.set(0,"EPI",rs.getString("EPI"));
				vo.set(0,"GES",rs.getString("GES"));
				vo.set(0,"HAN",rs.getString("HAN"));
				vo.set(0,"HA",rs.getString("HA"));
				vo.set(0,"MAL",rs.getString("MAL"));
				vo.set(0,"TBC",rs.getString("TBC"));
				vo.set(0,"ANO",rs.getString("ANO"));
				
				cdSincUsuarioAdulto = rs.getLong("CD_SINC_USUARIO");
				
				listaInsert.add(vo);
			}
			
			if(listaInsert.size() > 0){
				exportacaoInsertFamiliaSiab(listaInsert,"ADULTO");	
			}
			
			//CRIANC
			listaInsert = new ArrayList<BBValueObject>();
			cdSincUsuarioMenor = new Long(0);
			
			rs = stmt.executeQuery(SQL_FUNC_SYNC_INSERT_PACFAMILIA_MENOR);
			while(rs.next()){
				BBValueObject vo = new BBValueObject();
				vo.set(0,"CD_FAMILIA",rs.getLong("CD_FAMILIA"));
				vo.set(0,"COD_SEG",rs.getString("COD_SEG"));
				vo.set(0,"COD_AREA",rs.getString("COD_AREA"));
				vo.set(0,"COD_MICROA",rs.getString("COD_MICROA"));
				vo.set(0,"NFAMILIA",rs.getString("NFAMILIA"));
				vo.set(0,"DTNASC",rs.getString("DTNASC"));
				vo.set(0,"IDADE",rs.getString("IDADE"));
				vo.set(0,"SEXO",rs.getString("SEXO"));
				vo.set(0,"ESCOLA",rs.getString("ESCOLA"));
				vo.set(0,"COD_OCUP",rs.getString("COD_OCUP"));
				vo.set(0,"NOME_OCUP",rs.getString("NOME_OCUP"));
				vo.set(0,"ALC",rs.getString("ALC"));
				vo.set(0,"CHA",rs.getString("CHA"));
				vo.set(0,"DEF",rs.getString("DEF"));
				vo.set(0,"DIA",rs.getString("DIA"));
				vo.set(0,"DME",rs.getString("DME"));
				vo.set(0,"EPI",rs.getString("EPI"));
				vo.set(0,"GES",rs.getString("GES"));
				vo.set(0,"HAN",rs.getString("HAN"));
				vo.set(0,"HA",rs.getString("HA"));
				vo.set(0,"MAL",rs.getString("MAL"));
				vo.set(0,"TBC",rs.getString("TBC"));
				vo.set(0,"ANO",rs.getString("ANO"));
				
				cdSincUsuarioMenor = rs.getLong("CD_SINC_USUARIO");
				
				listaInsert.add(vo);
			}
			
			if(listaInsert.size() > 0){
				exportacaoInsertFamiliaSiab(listaInsert,"CRIANC");	
			}
			
			cdSincUsuario = (cdSincUsuarioAdulto > cdSincUsuarioMenor)? cdSincUsuarioAdulto: cdSincUsuarioMenor;
			
			//Atualizar log sincronismo
			
			if(cdSincUsuario != 0){
				ps = conn.prepareStatement(SQL_LOG_ALTERACAO_USUARIO_EXPORTACAO);
				setLong(1,ps,cdSincUsuario);
				setString(2,ps,"PACFAMILIA");
				setString(3,ps,"I");
				ps.executeUpdate();
			}

			rs.close();
			if(ps != null){
				ps.close();
			}
			stmt.close();
	

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
				throw new BaseException(e);
			}
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
	}
	
	/**
	 * Método auxiliar utilizado para atualizar o rowid_dbf de um registro da
	 * tabela pacfamilia após a inserção de um novo dado na base dbf
	 * 
	 * @param vo
	 * @param query - query de atualização da tabela pacfamilia
	 * @throws BaseException
	 */
	private void exportacaoUpdateRowId(BBValueObject vo, String query)
			throws BaseException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			int index = 1;
			setLong(index++,ps,vo.getLong(0,"ROWID_DBF"));
			setLong(index++,ps,vo.getLong(0,"CD_FAMILIA"));
			setLong(index++,ps,vo.getLong(0,"CD_FAMILIA"));
			
			ps.executeUpdate();
			ps.close();
		}catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
	}
	
	/**
	 * Método utilizado para inserir registro de familia do siab
	 * @param lista
	 * @param tabela
	 * @throws BaseException
	 */
	private void exportacaoInsertFamiliaSiab(List<BBValueObject> lista,
			String tabela) throws BaseException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnectionDBF();

			for (BBValueObject vo : lista) {
				if ("ADULTO".equals(tabela)) {
					String ano = vo.getString(0, "ANO");

					StringBuilder sqlInsert = new StringBuilder();
					sqlInsert.append("insert into adulto").append(
							ano.substring(2, ano.length())).append(" values(");
					sqlInsert.append("?,?,?,?,?,?,?,?,?,?,");
					sqlInsert.append("?,?,?,?,?,?,?,?,?,?,?)");

					int indice = 1;
					ps = conn.prepareStatement(sqlInsert.toString());
					setString(indice++, ps, vo.getString(0, "COD_SEG"));
					setString(indice++, ps, vo.getString(0, "COD_AREA"));
					setString(indice++, ps, vo.getString(0, "COD_MICROA"));
					setString(indice++, ps, vo.getString(0, "NFAMILIA"));
					setString(indice++, ps, vo.getString(0, "DTNASC"));
					setString(indice++, ps, vo.getString(0, "IDADE"));
					setString(indice++, ps, vo.getString(0, "SEXO"));
					setString(indice++, ps, vo.getString(0, "ALFA"));
					setString(indice++, ps, vo.getString(0, "COD_OCUP"));
					setString(indice++, ps, vo.getString(0, "NOME_OCUP"));
					setString(indice++, ps, vo.getString(0, "ALC"));
					setString(indice++, ps, vo.getString(0, "CHA"));
					setString(indice++, ps, vo.getString(0, "DEF"));
					setString(indice++, ps, vo.getString(0, "DIA"));
					setString(indice++, ps, vo.getString(0, "DME"));
					setString(indice++, ps, vo.getString(0, "EPI"));
					setString(indice++, ps, vo.getString(0, "GES"));
					setString(indice++, ps, vo.getString(0, "HAN"));
					setString(indice++, ps, vo.getString(0, "HA"));
					setString(indice++, ps, vo.getString(0, "MAL"));
					setString(indice++, ps, vo.getString(0, "TBC"));

					ps.execute();

					// Obtendo o rowid após a inserção do registro no dbf
					Table table = new Table(bundleConfig.getString("pastaSiab")
							+ "ADULTO" + ano.substring(2, ano.length())
							+ ".DBF");
					int row = table.getNumberOfRecords();
					table.close();
					vo.set(0, "ROWID_DBF", new Long(row));

					exportacaoUpdateRowId(vo,
							SQL_UPDATE_ROWID_PACFAMILIA_ADULTO);

				} else if ("CRIANC".equals(tabela)) {
					String ano = vo.getString(0, "ANO");

					StringBuilder sqlInsert = new StringBuilder();
					sqlInsert.append("insert into crianc").append(
							ano.substring(2, ano.length())).append(" values(");
					sqlInsert.append("?,?,?,?,?,?,?,?,?,?,");
					sqlInsert.append("?,?,?,?,?,?,?,?,?,?,?)");

					int indice = 1;
					ps = conn.prepareStatement(sqlInsert.toString());
					setString(indice++, ps, vo.getString(0, "COD_SEG"));
					setString(indice++, ps, vo.getString(0, "COD_AREA"));
					setString(indice++, ps, vo.getString(0, "COD_MICROA"));
					setString(indice++, ps, vo.getString(0, "NFAMILIA"));
					setString(indice++, ps, vo.getString(0, "DTNASC"));
					setString(indice++, ps, vo.getString(0, "IDADE"));
					setString(indice++, ps, vo.getString(0, "SEXO"));
					setString(indice++, ps, vo.getString(0, "ESCOLA"));
					setString(indice++, ps, vo.getString(0, "COD_OCUP"));
					setString(indice++, ps, vo.getString(0, "NOME_OCUP"));
					setString(indice++, ps, vo.getString(0, "ALC"));
					setString(indice++, ps, vo.getString(0, "CHA"));
					setString(indice++, ps, vo.getString(0, "DEF"));
					setString(indice++, ps, vo.getString(0, "DIA"));
					setString(indice++, ps, vo.getString(0, "DME"));
					setString(indice++, ps, vo.getString(0, "EPI"));
					setString(indice++, ps, vo.getString(0, "GES"));
					setString(indice++, ps, vo.getString(0, "HAN"));
					setString(indice++, ps, vo.getString(0, "HA"));
					setString(indice++, ps, vo.getString(0, "MAL"));
					setString(indice++, ps, vo.getString(0, "TBC"));

					ps.execute();

					// Obtendo o rowid após a inserção do registro no dbf
					Table table = new Table(bundleConfig.getString("pastaSiab")
							+ "CRIANC" + ano.substring(2, ano.length())
							+ ".DBF");
					int row = table.getNumberOfRecords();
					table.close();
					vo.set(0, "ROWID_DBF", new Long(row));

					exportacaoUpdateRowId(vo,
							SQL_UPDATE_ROWID_PACFAMILIA_CRIANC);

				} else if ("SANMUN".equals(tabela)) {
					String ano = vo.getString(0, "ANO");

					StringBuffer sqlSelect = new StringBuffer();
					sqlSelect.append("select * from sanmun").append(
							ano.substring(2, ano.length())).append(" where ");
					sqlSelect
							.append("cod_seg=? and cod_area=? and cod_microa=? and nfamilia=?");

					ps = conn.prepareStatement(sqlSelect.toString());
					setString(1, ps, vo.getString(0, "COD_SEG"));
					setString(2, ps, vo.getString(0, "COD_AREA"));
					setString(3, ps, vo.getString(0, "COD_MICROA"));
					setString(4, ps, vo.getString(0, "NFAMILIA"));

					rs = ps.executeQuery();
					if (rs.next()) {
						continue;
					}
					rs.close();

					StringBuilder sqlInsert = new StringBuilder();
					sqlInsert.append("insert into sanmun").append(
							ano.substring(2, ano.length())).append(" values(");
					sqlInsert.append("?,?,?,?,?,?,?,?,?,?,");
					sqlInsert.append("?,?,?,?,?,?,?,?,?,?,");
					sqlInsert.append("?,?,?,?,?,?,?,?,?,?,");
					sqlInsert.append("?,?,?,?,?,?,?,?,?,?,");
					sqlInsert.append("?,?,?,?,?,?,?,?,?,?,");
					sqlInsert.append("?,?,?,?,?,?,?,?,?,?,");
					sqlInsert.append("?,?,?,?)");

					int indice = 1;
					String strTmp = "";
					ps = conn.prepareStatement(sqlInsert.toString());
					setString(indice++, ps, vo.getString(0, "COD_SEG"));
					setString(indice++, ps, vo.getString(0, "COD_AREA"));
					setString(indice++, ps, vo.getString(0, "COD_MICROA"));
					setString(indice++, ps, vo.getString(0, "COD_ZONA"));
					setString(indice++, ps, vo.getString(0, "ID_MODELO"));
					setString(indice++, ps, vo.getString(0, "NFAMILIA"));
					setString(indice++, ps, vo.getString(0, "NPESSOAS"));
					strTmp = vo.getString(0, "FEM1");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "FEM2");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "FEM3");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "FEM4");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "FEM5");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "FEM6");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "FEM7");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "FEM8");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "FEM9");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "FEM10");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "MASC1");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "MASC2");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "MASC3");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "MASC4");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "MASC5");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "MASC6");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "MASC7");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "MASC8");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "MASC9");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "MASC10");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "ESCOLA");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "ALFABE");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "ALC1");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "ALC2");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "CHA1");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "CHA2");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "DEF1");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "DEF2");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "DIA1");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "DIA2");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "DME1");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "DME2");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "EPI1");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "EPI2");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "GES1");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "GES2");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "HAN1");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "HAN2");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "HA1");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "HA2");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "MAL1");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "MAL2");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "TBC1");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					strTmp = vo.getString(0, "TBC2");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					setString(indice++, ps, vo.getString(0, "ID_TIPO"));
					setString(indice++, ps, vo.getString(0, "ID_ELET"));
					setString(indice++, ps, vo.getString(0, "QTD_COMO"));
					setString(indice++, ps, vo.getString(0, "ID_LIXO"));
					setString(indice++, ps, vo.getString(0, "ID_TRATA"));
					setString(indice++, ps, vo.getString(0, "ID_AGUA"));
					setString(indice++, ps, vo.getString(0, "ID_URINA"));
					setString(indice++, ps, vo.getString(0, "ID_DOEN"));
					setString(indice++, ps, vo.getString(0, "ID_MEIO"));
					setString(indice++, ps, vo.getString(0, "ID_GRUPO"));
					setString(indice++, ps, vo.getString(0, "ID_TRAN"));
					strTmp = vo.getString(0, "COBERTURA");
					setString(indice++, ps, "0".equals(strTmp) ? "" : strTmp);
					setString(indice++, ps, ""); //SIGAB

					ps.execute();
				}
			}

			ps.close();

		} catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
	}
	
	/**
	 * Método utilizado para atualizar registro de familia do siab
	 * @param lista
	 * @param tabela
	 * @throws BaseException
	 */
	private void exportacaoUpdateFamiliaSiab(List<BBValueObject> lista,
			String tabela) throws BaseException {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = getConnectionDBF();
			
			for (BBValueObject vo : lista) {
				if("ADULTO".equals(tabela) || "CRIANCA".equals(tabela)){
					String ano = vo.getString(0,"ANO");
					String dtNasc = vo.getString(0,"DTNASC");
					
					int idade = Utils.calculaIdade(Utils.criaData(dtNasc),new Date());
					String codSeg = Utils.formataCampoSiab(vo.getString(0, "COD_SEG"),2);
					String codArea = Utils.formataCampoSiab(vo.getString(0, "COD_AREA"),3);
					String codMicroa = Utils.formataCampoSiab(vo.getString(0, "COD_MICROA"),2);
					String nFamilia = Utils.formataCampoSiab(vo.getString(0, "NFAMILIA"),3);
					
					
					if(idade == 14 || idade == 15){
						Table tableAdulto = 
							new Table(bundleConfig.getString("pastaSiab") + "ADULTO" + ano.substring(2,ano.length()) + ".DBF");
						int row = Integer.parseInt(vo.getString(0,"ROWID_DBF"));
						tableAdulto.goTo(row);
						if(codSeg.equals(tableAdulto.getFieldString("cod_seg")) &&
								codArea.equals(tableAdulto.getFieldString("cod_area")) && 
								codMicroa.equals(tableAdulto.getFieldString("cod_microa")) &&
								nFamilia.equals(tableAdulto.getFieldString("nfamilia"))){
							tableAdulto.setFieldString("idade",vo.getString(0,"IDADE"));
							tableAdulto.setFieldString("sexo",vo.getString(0,"SEXO"));
							tableAdulto.setFieldString("alfa",vo.getString(0,"ALFA"));
							tableAdulto.setFieldString("cod_ocup",vo.getString(0,"COD_OCUP"));
							tableAdulto.setFieldString("nome_ocup",vo.getString(0,"NOME_OCUP"));
							tableAdulto.setFieldString("alc",vo.getString(0,"ALC"));
							tableAdulto.setFieldString("cha",vo.getString(0,"CHA"));
							tableAdulto.setFieldString("def",vo.getString(0,"DEF"));
							tableAdulto.setFieldString("dia",vo.getString(0,"DIA"));
							tableAdulto.setFieldString("dme",vo.getString(0,"DME"));
							tableAdulto.setFieldString("epi",vo.getString(0,"EPI"));
							tableAdulto.setFieldString("ges",vo.getString(0,"GES"));
							tableAdulto.setFieldString("han",vo.getString(0,"HAN"));
							tableAdulto.setFieldString("ha",vo.getString(0,"HA"));
							tableAdulto.setFieldString("mal",vo.getString(0,"MAL"));
							tableAdulto.setFieldString("tbc",vo.getString(0,"TBC"));
						}
						tableAdulto.close();
						
						Table tableCrianca = 
							new Table(bundleConfig.getString("pastaSiab") + "CRIANC" + ano.substring(2,ano.length()) + ".DBF");
						row = Integer.parseInt(vo.getString(0,"ROWID_DBF"));
						tableCrianca.goTo(row);
						if(codSeg.equals(tableCrianca.getFieldString("cod_seg")) &&
								codArea.equals(tableCrianca.getFieldString("cod_area")) && 
								codMicroa.equals(tableCrianca.getFieldString("cod_microa")) &&
								nFamilia.equals(tableCrianca.getFieldString("nfamilia"))){
							tableCrianca.setFieldString("idade",vo.getString(0,"IDADE"));
							tableCrianca.setFieldString("sexo",vo.getString(0,"SEXO"));
							tableCrianca.setFieldString("escola",vo.getString(0,"ESCOLA"));
							tableCrianca.setFieldString("cod_ocup",vo.getString(0,"COD_OCUP"));
							tableCrianca.setFieldString("nome_ocup",vo.getString(0,"NOME_OCUP"));
							tableCrianca.setFieldString("alc",vo.getString(0,"ALC"));
							tableCrianca.setFieldString("cha",vo.getString(0,"CHA"));
							tableCrianca.setFieldString("def",vo.getString(0,"DEF"));
							tableCrianca.setFieldString("dia",vo.getString(0,"DIA"));
							tableCrianca.setFieldString("dme",vo.getString(0,"DME"));
							tableCrianca.setFieldString("epi",vo.getString(0,"EPI"));
							tableCrianca.setFieldString("ges",vo.getString(0,"GES"));
							tableCrianca.setFieldString("han",vo.getString(0,"HAN"));
							tableCrianca.setFieldString("ha",vo.getString(0,"HA"));
							tableCrianca.setFieldString("mal",vo.getString(0,"MAL"));
							tableCrianca.setFieldString("tbc",vo.getString(0,"TBC"));
						}
						tableCrianca.close();
					}else if(idade<14){
						Table tableCrianca = 
							new Table(bundleConfig.getString("pastaSiab") + "CRIANC" + ano.substring(2,ano.length()) + ".DBF");
						int row = Integer.parseInt(vo.getString(0,"ROWID_DBF"));
						tableCrianca.goTo(row);
						if(codSeg.equals(tableCrianca.getFieldString("cod_seg")) &&
								codArea.equals(tableCrianca.getFieldString("cod_area")) && 
								codMicroa.equals(tableCrianca.getFieldString("cod_microa")) &&
								nFamilia.equals(tableCrianca.getFieldString("nfamilia"))){
							tableCrianca.setFieldString("idade",vo.getString(0,"IDADE"));
							tableCrianca.setFieldString("sexo",vo.getString(0,"SEXO"));
							tableCrianca.setFieldString("escola",vo.getString(0,"ESCOLA"));
							tableCrianca.setFieldString("cod_ocup",vo.getString(0,"COD_OCUP"));
							tableCrianca.setFieldString("nome_ocup",vo.getString(0,"NOME_OCUP"));
							tableCrianca.setFieldString("alc",vo.getString(0,"ALC"));
							tableCrianca.setFieldString("cha",vo.getString(0,"CHA"));
							tableCrianca.setFieldString("def",vo.getString(0,"DEF"));
							tableCrianca.setFieldString("dia",vo.getString(0,"DIA"));
							tableCrianca.setFieldString("dme",vo.getString(0,"DME"));
							tableCrianca.setFieldString("epi",vo.getString(0,"EPI"));
							tableCrianca.setFieldString("ges",vo.getString(0,"GES"));
							tableCrianca.setFieldString("han",vo.getString(0,"HAN"));
							tableCrianca.setFieldString("ha",vo.getString(0,"HA"));
							tableCrianca.setFieldString("mal",vo.getString(0,"MAL"));
							tableCrianca.setFieldString("tbc",vo.getString(0,"TBC"));
						}
						tableCrianca.close();
					}else if(idade>15){
						Table tableAdulto = 
							new Table(bundleConfig.getString("pastaSiab") + "ADULTO" + ano.substring(2,ano.length()) + ".DBF");
						int row = Integer.parseInt(vo.getString(0,"ROWID_DBF"));
						tableAdulto.goTo(row);
						if(codSeg.equals(tableAdulto.getFieldString("cod_seg")) &&
								codArea.equals(tableAdulto.getFieldString("cod_area")) && 
								codMicroa.equals(tableAdulto.getFieldString("cod_microa")) &&
								nFamilia.equals(tableAdulto.getFieldString("nfamilia"))){
							tableAdulto.setFieldString("idade",vo.getString(0,"IDADE"));
							tableAdulto.setFieldString("sexo",vo.getString(0,"SEXO"));
							tableAdulto.setFieldString("alfa",vo.getString(0,"ALFA"));
							tableAdulto.setFieldString("cod_ocup",vo.getString(0,"COD_OCUP"));
							tableAdulto.setFieldString("nome_ocup",vo.getString(0,"NOME_OCUP"));
							tableAdulto.setFieldString("alc",vo.getString(0,"ALC"));
							tableAdulto.setFieldString("cha",vo.getString(0,"CHA"));
							tableAdulto.setFieldString("def",vo.getString(0,"DEF"));
							tableAdulto.setFieldString("dia",vo.getString(0,"DIA"));
							tableAdulto.setFieldString("dme",vo.getString(0,"DME"));
							tableAdulto.setFieldString("epi",vo.getString(0,"EPI"));
							tableAdulto.setFieldString("ges",vo.getString(0,"GES"));
							tableAdulto.setFieldString("han",vo.getString(0,"HAN"));
							tableAdulto.setFieldString("ha",vo.getString(0,"HA"));
							tableAdulto.setFieldString("mal",vo.getString(0,"MAL"));
							tableAdulto.setFieldString("tbc",vo.getString(0,"TBC"));
						}
						tableAdulto.close();
					}

					
				} else if("SANMUN".equals(tabela)){
					String ano = vo.getString(0,"ANO");
					
					StringBuilder sqlUpdate = new StringBuilder();
					sqlUpdate.append("update sanmun").append(
							ano.substring(2, ano.length())).append(" set ");
					sqlUpdate.append("id_modelo = '").append(vo.getString(0,"ID_MODELO")).append("', ");
					sqlUpdate.append("npessoas = '").append(vo.getString(0,"NPESSOAS")).append("', ");
					sqlUpdate.append("fem1 = '").append(vo.getString(0,"FEM1")).append("', ");
					sqlUpdate.append("fem2 = '").append(vo.getString(0,"FEM2")).append("', ");
					sqlUpdate.append("fem3 = '").append(vo.getString(0,"FEM3")).append("', ");
					sqlUpdate.append("fem4 = '").append(vo.getString(0,"FEM4")).append("', ");
					sqlUpdate.append("fem5 = '").append(vo.getString(0,"FEM5")).append("', ");
					sqlUpdate.append("fem6 = '").append(vo.getString(0,"FEM6")).append("', ");
					sqlUpdate.append("fem7 = '").append(vo.getString(0,"FEM7")).append("', ");
					sqlUpdate.append("fem8 = '").append(vo.getString(0,"FEM8")).append("', ");
					sqlUpdate.append("fem9 = '").append(vo.getString(0,"FEM9")).append("', ");
					sqlUpdate.append("fem10 = '").append(vo.getString(0,"FEM10")).append("', ");
					sqlUpdate.append("masc1 = '").append(vo.getString(0,"MASC1")).append("', ");
					sqlUpdate.append("masc2 = '").append(vo.getString(0,"MASC2")).append("', ");
					sqlUpdate.append("masc3 = '").append(vo.getString(0,"MASC3")).append("', ");
					sqlUpdate.append("masc4 = '").append(vo.getString(0,"MASC4")).append("', ");
					sqlUpdate.append("masc5 = '").append(vo.getString(0,"MASC5")).append("', ");
					sqlUpdate.append("masc6 = '").append(vo.getString(0,"MASC6")).append("', ");
					sqlUpdate.append("masc7 = '").append(vo.getString(0,"MASC7")).append("', ");
					sqlUpdate.append("masc8 = '").append(vo.getString(0,"MASC8")).append("', ");
					sqlUpdate.append("masc9 = '").append(vo.getString(0,"MASC9")).append("', ");
					sqlUpdate.append("masc10 = '").append(vo.getString(0,"MASC10")).append("', ");
					sqlUpdate.append("escola = '").append(vo.getString(0,"ESCOLA")).append("', ");
					sqlUpdate.append("alfabe = '").append(vo.getString(0,"ALFABE")).append("', ");
					sqlUpdate.append("alc1 = '").append(vo.getString(0,"ALC1")).append("', ");
					sqlUpdate.append("alc2 = '").append(vo.getString(0,"ALC2")).append("', ");
					sqlUpdate.append("cha1 = '").append(vo.getString(0,"CHA1")).append("', ");
					sqlUpdate.append("cha2 = '").append(vo.getString(0,"CHA2")).append("', ");
					sqlUpdate.append("def1 = '").append(vo.getString(0,"DEF1")).append("', ");
					sqlUpdate.append("def2 = '").append(vo.getString(0,"DEF2")).append("', ");
					sqlUpdate.append("dia1 = '").append(vo.getString(0,"DIA1")).append("', ");
					sqlUpdate.append("dia2 = '").append(vo.getString(0,"DIA2")).append("', ");
					sqlUpdate.append("dme1 = '").append(vo.getString(0,"DME1")).append("', ");
					sqlUpdate.append("dme2 = '").append(vo.getString(0,"DME2")).append("', ");
					sqlUpdate.append("epi1 = '").append(vo.getString(0,"EPI1")).append("', ");
					sqlUpdate.append("epi2 = '").append(vo.getString(0,"EPI2")).append("', ");
					sqlUpdate.append("ges1 = '").append(vo.getString(0,"GES1")).append("', ");
					sqlUpdate.append("ges2 = '").append(vo.getString(0,"GES2")).append("', ");
					sqlUpdate.append("han1 = '").append(vo.getString(0,"HAN1")).append("', ");
					sqlUpdate.append("han2 = '").append(vo.getString(0,"HAN2")).append("', ");
					sqlUpdate.append("ha1 = '").append(vo.getString(0,"HA1")).append("', ");
					sqlUpdate.append("ha2 = '").append(vo.getString(0,"HA2")).append("', ");
					sqlUpdate.append("mal1 = '").append(vo.getString(0,"MAL1")).append("', ");
					sqlUpdate.append("mal2 = '").append(vo.getString(0,"MAL2")).append("', ");
					sqlUpdate.append("tbc1 = '").append(vo.getString(0,"TBC1")).append("', ");
					sqlUpdate.append("tbc2 = '").append(vo.getString(0,"TBC2")).append("', ");
					sqlUpdate.append("id_tipo = '").append(vo.getString(0,"ID_TIPO")).append("', ");
					sqlUpdate.append("id_elet = '").append(vo.getString(0,"ID_ELET")).append("', ");
					sqlUpdate.append("qtd_como = '").append(vo.getString(0,"QTD_COMO")).append("', ");
					sqlUpdate.append("id_lixo = '").append(vo.getString(0,"ID_LIXO")).append("', ");
					sqlUpdate.append("id_trata = '").append(vo.getString(0,"ID_TRATA")).append("', ");
					sqlUpdate.append("id_agua = '").append(vo.getString(0,"ID_AGUA")).append("', ");
					sqlUpdate.append("id_urina = '").append(vo.getString(0,"ID_URINA")).append("', ");
					sqlUpdate.append("id_doen = '").append(vo.getString(0,"ID_DOEN")).append("', ");
					sqlUpdate.append("id_meio = '").append(vo.getString(0,"ID_MEIO")).append("', ");
					sqlUpdate.append("id_grupo = '").append(vo.getString(0,"ID_GRUPO")).append("', ");
					sqlUpdate.append("id_tran = '").append(vo.getString(0,"ID_TRAN")).append("', ");
					sqlUpdate.append("cobertura = '").append(vo.getString(0,"COBERTURA")).append("' ");
					sqlUpdate.append("where cod_seg = '").append(vo.getString(0,"COD_SEG")).append("' ");
					sqlUpdate.append("and	cod_area = '").append(vo.getString(0,"COD_AREA")).append("' ");
					sqlUpdate.append("and	cod_microa = '").append(vo.getString(0,"COD_MICROA")).append("' ");
					sqlUpdate.append("and	nfamilia = '").append(vo.getString(0,"NFAMILIA")).append("'");
					
					stmt = conn.createStatement();
					stmt.executeUpdate(sqlUpdate.toString());
					stmt.close();
					
				}
				
			}
			
		}catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
	}
	
	/**
	 * Método utilizado para excluir registro de famílias do siab
	 * @param lista
	 * @param tabela 
	 * @throws BaseException
	 */
	private void exportacaoDeleteFamiliaSiab(List<BBValueObject> lista,
			String tabela) throws BaseException {
		Connection conn = null;
		Statement stmt = null;
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR,-15);

		try {
			conn = getConnectionDBF();
			for (BBValueObject vo : lista) {
				String ano = vo.getString(0, "ANO");

				String codSeg = Utils.formataCampoSiab(vo.getString(0, "CODSEG"),2);
				String codArea = Utils.formataCampoSiab(vo.getString(0, "CODAREA"),3);
				String codMicroa = Utils.formataCampoSiab(vo.getString(0, "CODMICROA"),2);
				String nFamilia = Utils.formataCampoSiab(vo.getString(0, "NFAMILIA"),3);
				String dtNasc = vo.getString(0,"DT_NASCIMENTO");

				if ("PACFAMILIA".equals(tabela)) {
					int rowId = Integer.parseInt(vo.getString(0,"ROWID_DBF"));
					Date date = Utils.criaData(dtNasc);
					
					int idade = Utils.calculaIdade(date,new Date());
					
					if(idade == 14 || idade == 15){
						// CRIANC
						Table tableCrianc = 
							new Table(bundleConfig.getString("pastaSiab") + "CRIANC" + ano.substring(2,ano.length()) + ".DBF");
						tableCrianc.goTo(rowId);
						
						if(codSeg.equals(tableCrianc.getFieldString("cod_seg")) &&
								codArea.equals(tableCrianc.getFieldString("cod_area")) && 
								codMicroa.equals(tableCrianc.getFieldString("cod_microa")) &&
								nFamilia.equals(tableCrianc.getFieldString("nfamilia"))){
							tableCrianc.deleteRecord(rowId);
						}
						
						tableCrianc.close();
						
						// ADULTO
						Table tableAdulto = 
							new Table(bundleConfig.getString("pastaSiab") + "ADULTO" + ano.substring(2,ano.length()) + ".DBF");
						tableAdulto.goTo(rowId);
						
						if(codSeg.equals(tableAdulto.getFieldString("cod_seg")) &&
								codArea.equals(tableAdulto.getFieldString("cod_area")) && 
								codMicroa.equals(tableAdulto.getFieldString("cod_microa")) &&
								nFamilia.equals(tableAdulto.getFieldString("nfamilia"))){
							tableAdulto.deleteRecord(rowId);
						}
						tableAdulto.close();
					} else if (idade < 14) {
						// CRIANC
						Table tableCrianc = 
							new Table(bundleConfig.getString("pastaSiab") + "CRIANC" + ano.substring(2,ano.length()) + ".DBF");
						tableCrianc.goTo(rowId);
						
						if(codSeg.equals(tableCrianc.getFieldString("cod_seg")) &&
								codArea.equals(tableCrianc.getFieldString("cod_area")) && 
								codMicroa.equals(tableCrianc.getFieldString("cod_microa")) &&
								nFamilia.equals(tableCrianc.getFieldString("nfamilia"))){
							tableCrianc.deleteRecord(rowId);
						}
						
						tableCrianc.close();
					} else if (idade > 15){
						// ADULTO
						Table tableAdulto = 
							new Table(bundleConfig.getString("pastaSiab") + "ADULTO" + ano.substring(2,ano.length()) + ".DBF");
						tableAdulto.goTo(rowId);
						
						if(codSeg.equals(tableAdulto.getFieldString("cod_seg")) &&
								codArea.equals(tableAdulto.getFieldString("cod_area")) && 
								codMicroa.equals(tableAdulto.getFieldString("cod_microa")) &&
								nFamilia.equals(tableAdulto.getFieldString("nfamilia"))){
							tableAdulto.deleteRecord(rowId);
						}
						tableAdulto.close();
					}


				} else if ("FICHACADFAM".equals(tabela)) {
					// SANMUN
					StringBuilder sqlDelSanmun = new StringBuilder();
					sqlDelSanmun.append("delete from sanmun").append(ano).append(" ");
					sqlDelSanmun.append("where 	cod_seg = '").append(codSeg).append("' ");
					sqlDelSanmun.append("and 	cod_area = '").append(codArea).append("' ");
					sqlDelSanmun.append("and 	cod_microa = '").append(codMicroa).append("' ");
					sqlDelSanmun.append("and 	nfamilia = '").append(nFamilia).append("' ");

					stmt = conn.createStatement();
					stmt.executeUpdate(sqlDelSanmun.toString());
					stmt.close();
				}

			}

		} catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
		
	}

	private void importacao(String ano, ServletContext context) throws BaseException {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement ps = null;

		List<TabmunDBF> lstTabMun = new ArrayList<TabmunDBF>();
		List<EquipeDBF> lstEquipe = new ArrayList<EquipeDBF>();
		List<AgenteDBF> lstAgente = new ArrayList<AgenteDBF>();

		try {
			Date inicio = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss:S");
			
			// Popula lista de registros importados dos arquivos dbf
			populateList(ano, lstTabMun, lstEquipe, lstAgente);

			// =============# Estabelecendo conexão com o banco
			conn = getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

			/* TABMUN */

			// Excluindo registros
			stmt.executeUpdate(SQL_TABMUN_DELETE);

			// Inserindo registros
			ps = conn.prepareStatement(SQL_TABMUN_INSERT);
			for (TabmunDBF tabmunDBF : lstTabMun) {
				setString(1, ps, tabmunDBF.getCodEsta());
				setString(2, ps, tabmunDBF.getCodRegi());
				setString(3, ps, tabmunDBF.getCodMuni());
				setString(4, ps, tabmunDBF.getCodSeg());
				setString(5, ps, tabmunDBF.getCodZona());
				setString(6, ps, tabmunDBF.getNome());

				ps.executeUpdate();
			}
			ps.close();

			/* EQUIPE */

			// Excluindo registros
			stmt.executeUpdate(SQL_EQUIPE_DELETE);

			// Inserindo registros
			ps = conn.prepareStatement(SQL_EQUIPE_INSERT);
			for (EquipeDBF equipeDBF : lstEquipe) {
				setString(1, ps, equipeDBF.getCodSeg());
				setString(2, ps, equipeDBF.getCodArea());
				setString(3, ps, equipeDBF.getIdModelo());
				setString(4, ps, equipeDBF.getCodAtp());
				setString(5, ps, equipeDBF.getCodProf());
				setString(6, ps, equipeDBF.getCodMicroa());
				setString(7, ps, equipeDBF.getCodUB());
				setString(8, ps, equipeDBF.getNomeArea());
				setString(9, ps, equipeDBF.getOrdem());
				setString(10, ps, equipeDBF.getSituacao());
				setString(11, ps, equipeDBF.getIdBucal());
				setString(12, ps, equipeDBF.getIdPopula());
				setString(13, ps, equipeDBF.getAssen());
				setString(14, ps, equipeDBF.getQuilo());
				setString(15, ps, equipeDBF.getGeral());

				ps.executeUpdate();
			}
			ps.close();

			/* AGENTE */
			
			//Excluindo registros da tabela temporaria
			stmt.executeUpdate("truncate AGENTE_TMP");

			// Inserindo registros na tabela temporária
			ps = conn.prepareStatement(SQL_AGENTE_INSERT_TMP);
			for (AgenteDBF agenteDBF : lstAgente) {
				setInt(1, ps, Integer.parseInt(agenteDBF.getCodAgente()));
				setString(2, ps, agenteDBF.getNome());

				ps.executeUpdate();
			}
			ps.close();
			
			// Atualizando registros
			stmt.executeUpdate(SQL_AGENTE_UPDATE);

			// Inserindo registros
			stmt.executeUpdate(SQL_AGENTE_INSERT);
			
			/* FAMILIA E PACIENTE */
			stmt.executeUpdate("truncate pacfamilia_tmp");
			stmt.executeUpdate("truncate sanmun2_tmp");
			
			//Selecionando o cadastro de familias do siab e inserindo na tabela temporaria do servidor web
			
			File file = new File(context.getRealPath(bundleConfig.getString("pastaImportacaoTxt") + "sanmun.txt"));
			FileOutputStream outputStream = new FileOutputStream(file);
			
			Table tableSanmun = new Table(bundleConfig.getString("pastaSiab") + "sanmun" + ano.substring(2,ano.length()) + ".dbf");
			tableSanmun.setReadDeletedData(false);
			tableSanmun.goTop();
			
			
			String strTemp="";
			String strSeg,strArea,strMicroa,strFamilia,strDtNasc = "";
			do {
				StringBuffer sb = new StringBuffer();
				strSeg = tableSanmun.getFieldString("cod_seg");
				strArea = tableSanmun.getFieldString("cod_area");
				strMicroa = tableSanmun.getFieldString("cod_microa");
				strFamilia = tableSanmun.getFieldString("nfamilia");
				
				if("".equals(strSeg) || "".equals(strArea) || "".equals(strMicroa) || "".equals(strFamilia)){
					break;
				}
				
				sb.append(strSeg).append("\t");
				sb.append(strArea).append("\t");
				sb.append(strMicroa).append("\t");
				sb.append(strFamilia).append("\t");
				sb.append(tableSanmun.getFieldString("cod_zona")).append("\t");
				sb.append(ano).append("\t");
				sb.append(tableSanmun.getFieldString("id_modelo")).append("\t");
				strTemp = tableSanmun.getFieldString("id_tipo");
				sb.append((strTemp!=null && strTemp.length()>0)? strTemp.trim().substring(0,1):"0").append("\t");
				strTemp = tableSanmun.getFieldString("id_elet");
				sb.append( strTemp != null ? "S" : "N").append("\t");
				strTemp = tableSanmun.getFieldString("qtd_como");
				sb.append((strTemp!=null && strTemp.trim().length()>0)? strTemp : "0").append("\t");
				strTemp = tableSanmun.getFieldString("id_lixo");
				sb.append((strTemp!=null && strTemp.length()>0)? strTemp.trim().substring(0,1):"0").append("\t");
				strTemp = tableSanmun.getFieldString("id_trata");
				sb.append((strTemp!=null && strTemp.length()>0)? strTemp.trim().substring(0,1):"0").append("\t");
				strTemp = tableSanmun.getFieldString("id_agua");
				sb.append((strTemp!=null && strTemp.length()>0)? strTemp.trim().substring(0,1):"0").append("\t");
				strTemp = tableSanmun.getFieldString("id_urina");
				sb.append((strTemp!=null && strTemp.length()>0)? strTemp.trim().substring(0,1):"0").append("\t");
				strTemp = tableSanmun.getFieldString("id_doen");
				sb.append((strTemp!=null && strTemp.length()>0)? strTemp.trim().substring(0,1):"0").append("\t");
				strTemp = tableSanmun.getFieldString("id_meio");
				sb.append((strTemp!=null && strTemp.length()>0)? strTemp.trim().substring(0,1):"0").append("\t");
				strTemp = tableSanmun.getFieldString("id_grupo");
				sb.append((strTemp!=null && strTemp.length()>0)? strTemp.trim().substring(0,1):"0").append("\t");
				strTemp = tableSanmun.getFieldString("id_tran");
				sb.append((strTemp!=null && strTemp.length()>0)? strTemp.trim().substring(0,1):"0").append("\t");
				strTemp = tableSanmun.getFieldString("cobertura");
				sb.append((strTemp!=null && strTemp.length()>0)? strTemp.trim().substring(0,1):"0");
				sb.append("\n");
				
				outputStream.write(sb.toString().getBytes());
				
			}while(tableSanmun.nextRecord());
			tableSanmun.close();
			
			String copy = "copy sanmun2_tmp from '" + context.getRealPath(bundleConfig.getString("pastaImportacaoTxt")) +
			"/sanmun.txt' WITH DELIMITER '\t'";
			copy = copy.replaceAll("\\\\","/");
			stmt.execute(copy);
			
			//Selecionando adultos do siab e inserindo na tabela temporária do servidor web
			
			file = new File(context.getRealPath(bundleConfig.getString("pastaImportacaoTxt") + "adulto.txt"));
			outputStream = new FileOutputStream(file);
			
			Table tableAdulto = new Table(bundleConfig.getString("pastaSiab") + "adulto" + ano.substring(2,ano.length()) + ".dbf");
			tableAdulto.setReadDeletedData(false);
			tableAdulto.goTop();
			
			strTemp="";
			do {
				StringBuffer sb = new StringBuffer();
				strSeg = tableAdulto.getFieldString("cod_seg");
				strArea = tableAdulto.getFieldString("cod_area");
				strMicroa = tableAdulto.getFieldString("cod_microa");
				strFamilia = tableAdulto.getFieldString("nfamilia");
				strDtNasc = tableAdulto.getFieldString("dtnasc");
				if("".equals(strSeg) || "".equals(strArea) || "".equals(strMicroa) || "".equals(strFamilia)){
					break;
				}
				if(strDtNasc==null || "".equals(strDtNasc) || "/  /".equals(strDtNasc.trim())){
					continue;
				}
				
				sb.append(strSeg).append("\t"); // cod_seg
				sb.append(strArea).append("\t"); // cod_area
				sb.append(strMicroa).append("\t"); // cod_microa
				sb.append(strFamilia).append("\t"); //nfamilia
				sb.append(ano).append("\t"); // cod_ano
				sb.append(strDtNasc).append("\t"); //dt_nascimento
				strTemp=tableAdulto.getFieldString("idade"); // num_idade
				sb.append((strTemp!= "" && strTemp.trim().length() > 0) ? strTemp.trim(): "0").append("\t");
				sb.append(tableAdulto.getFieldString("sexo")).append("\t"); // sexo
				sb.append("N").append("\t"); // fl_menorquinze
				strTemp=tableAdulto.getFieldString("alfa"); // fl_alfabetizado
				sb.append((strTemp!= "" && "S".equals(strTemp.trim())) ? "S" : "N").append("\t");
				sb.append("").append("\t"); // fl_freqescola
				strTemp=tableAdulto.getFieldString("cod_ocup"); // cd_ocupacao
				sb.append(( strTemp!= "" && strTemp.trim().length() > 0) ? strTemp.trim() : "0").append("\t");
				strTemp=tableAdulto.getFieldString("alc");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableAdulto.getFieldString("cha");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableAdulto.getFieldString("def");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableAdulto.getFieldString("dia");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableAdulto.getFieldString("dme");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableAdulto.getFieldString("epi");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableAdulto.getFieldString("ges");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableAdulto.getFieldString("han");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableAdulto.getFieldString("ha");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableAdulto.getFieldString("mal");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableAdulto.getFieldString("tbc");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				sb.append("1").append("\t");
				sb.append(tableAdulto.getRecordNumber()); // rowid_dbf
				sb.append("\n");
				
				outputStream.write(sb.toString().getBytes());
				
			}while(tableAdulto.nextRecord());
			tableAdulto.close();
			
			copy = "copy pacfamilia_tmp from '" + context.getRealPath(bundleConfig.getString("pastaImportacaoTxt")) +
			"/adulto.txt' WITH DELIMITER '\t'";
			copy = copy.replaceAll("\\\\","/");
			stmt.execute(copy);

			
			//Selecionando crianças do siab e inserindo na tabela temporária do servidor web
			file = new File(context.getRealPath(bundleConfig.getString("pastaImportacaoTxt") + "crianc.txt"));
			outputStream = new FileOutputStream(file);
			
			Table tableCrianc = new Table(bundleConfig.getString("pastaSiab") + "crianc" + ano.substring(2,ano.length()) + ".dbf");
			tableCrianc.setReadDeletedData(false);
			tableCrianc.goTop();
			
			strTemp="";
			do {
				StringBuffer sb = new StringBuffer();
				strSeg = tableCrianc.getFieldString("cod_seg");
				strArea = tableCrianc.getFieldString("cod_area");
				strMicroa = tableCrianc.getFieldString("cod_microa");
				strFamilia = tableCrianc.getFieldString("nfamilia");
				strDtNasc = tableCrianc.getFieldString("dtnasc");
				
				if("".equals(strSeg) || "".equals(strArea) || "".equals(strMicroa) || "".equals(strFamilia)){
					break;
				}
				if(strDtNasc==null || "".equals(strDtNasc) || "/  /".equals(strDtNasc.trim())){
					continue;
				}
				
				sb.append(strSeg).append("\t"); // cod_seg
				sb.append(strArea).append("\t"); // cod_area
				sb.append(strMicroa).append("\t"); // cod_microa
				sb.append(strFamilia).append("\t"); //cd_familia
				sb.append(ano).append("\t"); // cod_ano
				sb.append(strDtNasc).append("\t"); //dt_nascimento
				strTemp=tableCrianc.getFieldString("idade"); // num_idade
				sb.append((strTemp!= "" && strTemp.trim().length() > 0) ? strTemp.trim(): "0").append("\t");
				sb.append(tableCrianc.getFieldString("sexo")).append("\t"); // sexo
				sb.append("S").append("\t"); // fl_menorquinze
				sb.append("").append("\t");  // fl_alfabetizado
				strTemp=tableCrianc.getFieldString("escola"); // fl_freqescola
				sb.append((strTemp!= "" && "S".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableCrianc.getFieldString("cod_ocup"); // cd_ocupacao
				sb.append(( strTemp!= "" && strTemp.trim().length() > 0) ? strTemp.trim() : "0").append("\t");
				strTemp=tableCrianc.getFieldString("alc");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableCrianc.getFieldString("cha");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableCrianc.getFieldString("def");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableCrianc.getFieldString("dia");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableCrianc.getFieldString("dme");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableCrianc.getFieldString("epi");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableCrianc.getFieldString("ges");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableCrianc.getFieldString("han");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableCrianc.getFieldString("ha");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableCrianc.getFieldString("mal");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				strTemp=tableCrianc.getFieldString("tbc");
				sb.append(( strTemp!= "" && "X".equals(strTemp.trim())) ? "S" : "N").append("\t");
				sb.append("0").append("\t");
				sb.append(tableCrianc.getRecordNumber()); // rowid_dbf
				sb.append("\n");
				
				outputStream.write(sb.toString().getBytes());
				
			}while(tableCrianc.nextRecord());
			tableCrianc.close();
			
			copy = "copy pacfamilia_tmp from '" + context.getRealPath(bundleConfig.getString("pastaImportacaoTxt")) +
			"/crianc.txt' WITH DELIMITER '\t'";
			copy = copy.replaceAll("\\\\","/");
			stmt.execute(copy);
			
			conn.commit();
			
			//EXCLUINDO E ATUALIZANDO REGISTROS DE FAMILIA NO SERVIDOR WEB
			CallableStatement cs = conn.prepareCall("{call func_sync_import_familia(?)}");
			setString(1,cs,ano);
			cs.execute();
			cs.close();
			
			int  temp=0;
			//INSERINDO REGISTROS DE FAMILIA NO SERVIDOR WEB
			ResultSet rs = stmt.executeQuery(SQL_FUNC_SYNC_IMPORT_PACFAMILIA_SELECT);
			
			while(rs.next()){
				PreparedStatement psPaciente = conn.prepareStatement(SQL_SELECT_CD_PACIENTE);
				setLong(1,psPaciente,rs.getLong("cd_familia"));
				setInteger(2,psPaciente,Integer.parseInt(ano));
				
				ResultSet rsCdPaciente = psPaciente.executeQuery();
				Long cdPaciente = new Long(0);
				while(rsCdPaciente.next()){
					cdPaciente = rsCdPaciente.getLong("cd_paciente");
				}
				rsCdPaciente.close();
				psPaciente.close();
				
				PreparedStatement psIncluir = 
					conn.prepareStatement(SQL_FUNC_SYNC_IMPORT_PACFAMILIA_INSERT);
				int index = 1;
				setLong(index++, psIncluir, rs.getLong("cd_familia"));
				setLong(index++, psIncluir, cdPaciente);
				setLong(index++, psIncluir, rs.getLong("cd_agente"));
				setInteger(index++,psIncluir,rs.getInt("cod_ano"));
				setString(index++, psIncluir, rs.getString("nm_paciente"));
				setString(index++, psIncluir, rs.getString("dt_nascimento"));
				setInteger(index++, psIncluir, rs.getInt("num_idade"));
				setString(index++, psIncluir, rs.getString("fl_sexo"));
				setString(index++, psIncluir, rs.getString("fl_menorquinze"));
				setString(index++, psIncluir, rs.getString("fl_alfabetizado"));
				setString(index++, psIncluir, rs.getString("fl_freqescola"));
				setInteger(index++, psIncluir, rs.getInt("cd_ocupacao"));
				setString(index++, psIncluir, rs.getString("fl_alc"));
				setString(index++, psIncluir, rs.getString("fl_cha"));
				setString(index++, psIncluir, rs.getString("fl_def"));
				setString(index++, psIncluir, rs.getString("fl_dia"));
				setString(index++, psIncluir, rs.getString("fl_dme"));
				setString(index++, psIncluir, rs.getString("fl_epi"));
				setString(index++, psIncluir, rs.getString("fl_ges"));
				setString(index++, psIncluir, rs.getString("fl_han"));
				setString(index++, psIncluir, rs.getString("fl_ha"));
				setString(index++, psIncluir, rs.getString("fl_mal"));
				setString(index++, psIncluir, rs.getString("fl_tbc"));
				setLong(index++, psIncluir, rs.getLong("rowid_dbf"));
				psIncluir.executeUpdate();
				psIncluir.close();
				if(temp%50==0){
					System.out.println("insert paciente: "+temp);
				}
				temp++;
			}
			rs.close();
			
			stmt.close();

			conn.commit();
			
			 Date fim = new Date();
			 System.out.println(dateFormat.format(inicio)+"-"+dateFormat.format(fim));

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
				throw new BaseException(e);
			}
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}
	}

	/**
	 * Método interno utilizado para popular lista de objetos com os dados dos
	 * arquivos dbf
	 * 
	 * @param lstTabMun
	 * @param lstEquipe
	 */
	private void populateList(String ano, List<TabmunDBF> lstTabMun,
			List<EquipeDBF> lstEquipe, List<AgenteDBF> lstAgente)
			throws BaseException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// =============# Estabelecendo conexão com os arquivos dbf
			conn = getConnectionDBF();

			stmt = conn.createStatement();

			/*
			 * Importando arquivo tabmun
			 */
			rs = stmt.executeQuery(SQL_DBF_TABMUN_SELECT);
			while (rs.next()) {
				TabmunDBF tabmunDBF = new TabmunDBF();
				tabmunDBF.setCodEsta(rs.getString("COD_ESTA"));
				tabmunDBF.setCodRegi(rs.getString("COD_REGI"));
				tabmunDBF.setCodMuni(rs.getString("COD_MUNI"));
				tabmunDBF.setCodSeg(rs.getString("COD_SEG"));
				tabmunDBF.setCodZona(rs.getString("COD_ZONA"));
				tabmunDBF.setNome(rs.getString("NOME"));

				lstTabMun.add(tabmunDBF);
			}
			rs.close();

			/*
			 * Importando arquivo equipe
			 */
			rs = stmt.executeQuery(SQL_DBF_EQUIPE_SELECT);
			while (rs.next()) {
				EquipeDBF equipeDBF = new EquipeDBF();
				equipeDBF.setCodSeg(rs.getString("cod_seg"));
				equipeDBF.setCodArea(rs.getString("cod_area"));
				equipeDBF.setIdModelo(rs.getString("id_modelo"));
				equipeDBF.setCodAtp(rs.getString("cod_atp"));
				equipeDBF.setCodProf(rs.getString("cod_prof"));
				equipeDBF.setCodMicroa(rs.getString("cod_microa"));
				equipeDBF.setCodUB(rs.getString("cod_ub"));
				equipeDBF.setNomeArea(rs.getString("nome_area"));
				equipeDBF.setOrdem(rs.getString("ordem"));
				equipeDBF.setSituacao(rs.getString("situacao"));
				equipeDBF.setIdBucal(rs.getString("id_bucal"));
				equipeDBF.setIdPopula(rs.getString("id_popula"));
				equipeDBF.setAssen(rs.getString("assen"));
				equipeDBF.setQuilo(rs.getString("quilo"));
				equipeDBF.setGeral(rs.getString("geral"));

				lstEquipe.add(equipeDBF);
			}
			rs.close();

			/*
			 * Importando arquivo agente
			 */
			rs = stmt.executeQuery(SQL_DBF_AGENTE_SELECT);
			while (rs.next()) {
				AgenteDBF agenteDBF = new AgenteDBF();
				agenteDBF.setCodAgente(rs.getString("cod_age"));
				agenteDBF.setNome(rs.getString("nome"));

				lstAgente.add(agenteDBF);
			}
			rs.close();

			stmt.close();

		} catch (Exception e) {
			throw new BaseException(e);
		} finally {
			closeConnection(conn);
		}

	}

	/**
	 * Retorna um vo com os dados da chave da tabela fichacadfam
	 * 
	 * @param idKey
	 * @return
	 */
	private BBValueObject getVOCdFamilia(String idKey) {
		BBValueObject vo = Utils.formatDeletedRecords(idKey);
		String cdFamilia = vo.getString(0, "CD_FAMILIA");
		//String ano = vo.getString(0, "ANO");
		int size = cdFamilia.length();

		//BBValueObject ret = new BBValueObject();

		if (size == 9) {
			vo.set(0, "NFAMILIA", cdFamilia.substring(6, size));
			vo.set(0, "CODMICROA", cdFamilia.substring(4, size - 3));
			vo.set(0, "CODAREA", cdFamilia.substring(1, size - 5));
			vo.set(0, "CODSEG", "0" + cdFamilia.substring(0, size - 8));
		} else if (size == 10) {
			vo.set(0, "NFAMILIA", cdFamilia.substring(7, size));
			vo.set(0, "CODMICROA", cdFamilia.substring(5, size - 3));
			vo.set(0, "CODAREA", cdFamilia.substring(2, size - 5));
			vo.set(0, "CODSEG", cdFamilia.substring(0, size - 8));
		}

		return vo;
	}

}
