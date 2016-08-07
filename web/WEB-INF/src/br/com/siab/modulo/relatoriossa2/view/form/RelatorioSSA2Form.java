package br.com.siab.modulo.relatoriossa2.view.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * 
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public class RelatorioSSA2Form extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7857689172323644888L;

	private String codSeg;
	
	private String descSeg;

	private String codArea;
	
	private String descArea;

	private String codMicroa;
	
	private String descMicroa;

	private String codUB;

	private String codZona;

	private String idModelo;

	private String mes;

	private String ano;

	private String nfamicad;

	private String nvisitas;

	private String ngesCad;

	private String ngesm20;

	private String ngesac;

	private String ngesvac;

	private String ngespre1;

	private String ngespre2;

	private String c_4meses;

	private String c_mamand;

	private String c_misto;

	private String c_0a11;

	private String c_vacdia;

	private String c_0a112p;

	private String c_0a11gp;

	private String c_1223;

	private String c_vacina;

	private String c_12232p;

	private String c_1223gp;

	private String c_diarre;

	private String c_diasro;

	private String c_ira;

	private String nascvivo;

	private String pesados;

	private String peso2500;

	private String o_dia0a28;

	private String o_ira0a28;

	private String o_cau0a28;

	private String o_dia28a1;

	private String o_ira28a1;

	private String o_cau28a1;

	private String obitodia;

	private String obitoira;

	private String obitocau;

	private String o_mul10a14;

	private String obitomul;

	private String obitoadol;

	private String obitoout;

	private String d_diabete;

	private String d_diaac;

	private String d_hiperten;

	private String d_hiperac;

	private String d_tubercul;

	private String d_tuberac;

	private String d_hansen;

	private String d_hanseac;

	private String h_0a5pneu;

	private String h_0a5des;

	private String h_alcool;

	private String h_psiqui;

	private String h_diabete;

	private String h_outcau;
	
	private String exportado;

	public String getExportado() {
		return exportado;
	}

	public void setExportado(String exportado) {
		this.exportado = exportado;
	}

	public String getAno() {
		return ano;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getC_0a11() {
		return c_0a11;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setC_0a11(String c_0a11) {
		this.c_0a11 = c_0a11;
	}

	public String getC_0a112p() {
		return c_0a112p;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setC_0a112p(String c_0a112p) {
		this.c_0a112p = c_0a112p;
	}

	public String getC_0a11gp() {
		return c_0a11gp;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setC_0a11gp(String c_0a11gp) {
		this.c_0a11gp = c_0a11gp;
	}

	public String getC_1223() {
		return c_1223;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setC_1223(String c_1223) {
		this.c_1223 = c_1223;
	}

	public String getC_12232p() {
		return c_12232p;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setC_12232p(String c_12232p) {
		this.c_12232p = c_12232p;
	}

	public String getC_1223gp() {
		return c_1223gp;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setC_1223gp(String c_1223gp) {
		this.c_1223gp = c_1223gp;
	}

	public String getC_4meses() {
		return c_4meses;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setC_4meses(String c_4meses) {
		this.c_4meses = c_4meses;
	}

	public String getC_diarre() {
		return c_diarre;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setC_diarre(String c_diarre) {
		this.c_diarre = c_diarre;
	}

	public String getC_diasro() {
		return c_diasro;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setC_diasro(String c_diasro) {
		this.c_diasro = c_diasro;
	}

	public String getC_ira() {
		return c_ira;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setC_ira(String c_ira) {
		this.c_ira = c_ira;
	}

	public String getC_mamand() {
		return c_mamand;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setC_mamand(String c_mamand) {
		this.c_mamand = c_mamand;
	}

	public String getC_misto() {
		return c_misto;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setC_misto(String c_misto) {
		this.c_misto = c_misto;
	}

	public String getC_vacdia() {
		return c_vacdia;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setC_vacdia(String c_vacdia) {
		this.c_vacdia = c_vacdia;
	}

	public String getC_vacina() {
		return c_vacina;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setC_vacina(String c_vacina) {
		this.c_vacina = c_vacina;
	}

	public String getCodArea() {
		return codArea;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setCodArea(String codArea) {
		this.codArea = codArea;
	}

	public String getCodMicroa() {
		return codMicroa;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setCodMicroa(String codMicroa) {
		this.codMicroa = codMicroa;
	}

	public String getCodSeg() {
		return codSeg;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setCodSeg(String codSeg) {
		this.codSeg = codSeg;
	}

	public String getCodUB() {
		return codUB;
	}

	public void setCodUB(String codUB) {
		this.codUB = codUB;
	}

	public String getCodZona() {
		return codZona;
	}

	public void setCodZona(String codZona) {
		this.codZona = codZona;
	}

	public String getD_diaac() {
		return d_diaac;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setD_diaac(String d_diaac) {
		this.d_diaac = d_diaac;
	}

	public String getD_diabete() {
		return d_diabete;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setD_diabete(String d_diabete) {
		this.d_diabete = d_diabete;
	}

	public String getD_hanseac() {
		return d_hanseac;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setD_hanseac(String d_hanseac) {
		this.d_hanseac = d_hanseac;
	}

	public String getD_hansen() {
		return d_hansen;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setD_hansen(String d_hansen) {
		this.d_hansen = d_hansen;
	}

	public String getD_hiperac() {
		return d_hiperac;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setD_hiperac(String d_hiperac) {
		this.d_hiperac = d_hiperac;
	}

	public String getD_hiperten() {
		return d_hiperten;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setD_hiperten(String d_hiperten) {
		this.d_hiperten = d_hiperten;
	}

	public String getD_tuberac() {
		return d_tuberac;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setD_tuberac(String d_tuberac) {
		this.d_tuberac = d_tuberac;
	}

	public String getD_tubercul() {
		return d_tubercul;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setD_tubercul(String d_tubercul) {
		this.d_tubercul = d_tubercul;
	}

	public String getH_0a5des() {
		return h_0a5des;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setH_0a5des(String h_0a5des) {
		this.h_0a5des = h_0a5des;
	}

	public String getH_0a5pneu() {
		return h_0a5pneu;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setH_0a5pneu(String h_0a5pneu) {
		this.h_0a5pneu = h_0a5pneu;
	}

	public String getH_alcool() {
		return h_alcool;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setH_alcool(String h_alcool) {
		this.h_alcool = h_alcool;
	}

	public String getH_diabete() {
		return h_diabete;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setH_diabete(String h_diabete) {
		this.h_diabete = h_diabete;
	}

	public String getH_outcau() {
		return h_outcau;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setH_outcau(String h_outcau) {
		this.h_outcau = h_outcau;
	}

	public String getH_psiqui() {
		return h_psiqui;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setH_psiqui(String h_psiqui) {
		this.h_psiqui = h_psiqui;
	}

	public String getIdModelo() {
		return idModelo;
	}

	public void setIdModelo(String idModelo) {
		this.idModelo = idModelo;
	}

	public String getMes() {
		return mes;
	}

	/**
	 * @struts.validator type="required"
	 */
	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getNascvivo() {
		return nascvivo;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setNascvivo(String nascvivo) {
		this.nascvivo = nascvivo;
	}

	public String getNfamicad() {
		return nfamicad;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setNfamicad(String nfamicad) {
		this.nfamicad = nfamicad;
	}

	public String getNgesac() {
		return ngesac;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setNgesac(String ngesac) {
		this.ngesac = ngesac;
	}

	public String getNgesCad() {
		return ngesCad;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setNgesCad(String ngesCad) {
		this.ngesCad = ngesCad;
	}

	public String getNgesm20() {
		return ngesm20;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setNgesm20(String ngesm20) {
		this.ngesm20 = ngesm20;
	}

	public String getNgespre1() {
		return ngespre1;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setNgespre1(String ngespre1) {
		this.ngespre1 = ngespre1;
	}

	public String getNgespre2() {
		return ngespre2;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setNgespre2(String ngespre2) {
		this.ngespre2 = ngespre2;
	}

	public String getNgesvac() {
		return ngesvac;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setNgesvac(String ngesvac) {
		this.ngesvac = ngesvac;
	}

	public String getNvisitas() {
		return nvisitas;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setNvisitas(String nvisitas) {
		this.nvisitas = nvisitas;
	}

	public String getO_cau0a28() {
		return o_cau0a28;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setO_cau0a28(String o_cau0a28) {
		this.o_cau0a28 = o_cau0a28;
	}

	public String getO_cau28a1() {
		return o_cau28a1;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setO_cau28a1(String o_cau28a1) {
		this.o_cau28a1 = o_cau28a1;
	}

	public String getO_dia0a28() {
		return o_dia0a28;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setO_dia0a28(String o_dia0a28) {
		this.o_dia0a28 = o_dia0a28;
	}

	public String getO_dia28a1() {
		return o_dia28a1;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setO_dia28a1(String o_dia28a1) {
		this.o_dia28a1 = o_dia28a1;
	}

	public String getO_ira0a28() {
		return o_ira0a28;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setO_ira0a28(String o_ira0a28) {
		this.o_ira0a28 = o_ira0a28;
	}

	public String getO_ira28a1() {
		return o_ira28a1;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setO_ira28a1(String o_ira28a1) {
		this.o_ira28a1 = o_ira28a1;
	}

	public String getO_mul10a14() {
		return o_mul10a14;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setO_mul10a14(String o_mul10a14) {
		this.o_mul10a14 = o_mul10a14;
	}

	public String getObitoadol() {
		return obitoadol;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setObitoadol(String obitoadol) {
		this.obitoadol = obitoadol;
	}

	public String getObitocau() {
		return obitocau;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setObitocau(String obitocau) {
		this.obitocau = obitocau;
	}

	public String getObitodia() {
		return obitodia;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setObitodia(String obitodia) {
		this.obitodia = obitodia;
	}

	public String getObitoira() {
		return obitoira;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setObitoira(String obitoira) {
		this.obitoira = obitoira;
	}

	public String getObitomul() {
		return obitomul;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setObitomul(String obitomul) {
		this.obitomul = obitomul;
	}

	public String getObitoout() {
		return obitoout;
	}
	
	/**
	 * @struts.validator type="required,integer"
	 */
	public void setObitoout(String obitoout) {
		this.obitoout = obitoout;
	}

	public String getPesados() {
		return pesados;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setPesados(String pesados) {
		this.pesados = pesados;
	}

	public String getPeso2500() {
		return peso2500;
	}

	/**
	 * @struts.validator type="required,integer"
	 */
	public void setPeso2500(String peso2500) {
		this.peso2500 = peso2500;
	}

	public String getDescArea() {
		return descArea;
	}

	
	public void setDescArea(String descArea) {
		this.descArea = descArea;
	}

	public String getDescMicroa() {
		return descMicroa;
	}


	public void setDescMicroa(String descMicroa) {
		this.descMicroa = descMicroa;
	}

	public String getDescSeg() {
		return descSeg;
	}

	
	public void setDescSeg(String descSeg) {
		this.descSeg = descSeg;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.codSeg = null;
		this.codArea = null;
		this.codMicroa = null;
		this.mes = null;
		this.ano = null;
	}
	
	
}
