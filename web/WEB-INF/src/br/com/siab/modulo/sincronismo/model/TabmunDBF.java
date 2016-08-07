package br.com.siab.modulo.sincronismo.model;

/**
 * Classe representativa da entidade tabmun do modelo dbf
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 */
public class TabmunDBF {
	private String codEsta;
	private String codRegi;
	private String codMuni;
	private String codSeg;
	private String codZona;
	private String nome;
	
	public String getCodEsta() {
		return codEsta;
	}
	public void setCodEsta(String codEsta) {
		this.codEsta = codEsta;
	}
	public String getCodMuni() {
		return codMuni;
	}
	public void setCodMuni(String codMuni) {
		this.codMuni = codMuni;
	}
	public String getCodRegi() {
		return codRegi;
	}
	public void setCodRegi(String codRegi) {
		this.codRegi = codRegi;
	}
	public String getCodSeg() {
		return codSeg;
	}
	public void setCodSeg(String codSeg) {
		this.codSeg = codSeg;
	}
	public String getCodZona() {
		return codZona;
	}
	public void setCodZona(String codZona) {
		this.codZona = codZona;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
