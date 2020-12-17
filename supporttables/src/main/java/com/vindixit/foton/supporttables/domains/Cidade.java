package com.vindixit.foton.supporttables.domains;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


@Table(name="TBL_CIDADE")
@SequenceGenerator(name="tbl_cidade_id_cidade_seq", sequenceName="tbl_cidade_id_cidade_seq",initialValue=1, allocationSize=1)
@Entity
public class Cidade implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "tbl_cidade_id_cidade_seq")
	@Column(name="id_cidade")
	Integer idCidade;
	
	@Column(name="dc_temperatura")
	Double dcTemperatura;
	
	@Column(name="v10m")
	Double v10m;
	
	@Column(name="dc_nome")
	String dcNome;

	public Cidade() {
		super();
		
	}
	
	public Cidade(Integer idCidade, Double dcTemperatura, Double v10m, String dcNome) {
		super();
		this.idCidade = idCidade;
		this.dcTemperatura = dcTemperatura;
		this.v10m = v10m;
		this.dcNome = dcNome;
	}
	

	public Integer getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(Integer idCidade) {
		this.idCidade = idCidade;
	}

	public Double getDcTemperatura() {
		return dcTemperatura;
	}

	public void setDcTemperatura(Double dcTemperatura) {
		this.dcTemperatura = dcTemperatura;
	}

	public Double getV10m() {
		return v10m;
	}

	public void setV10m(Double v10m) {
		this.v10m = v10m;
	}

	public String getDcNome() {
		return dcNome;
	}

	public void setDcNome(String dcNome) {
		this.dcNome = dcNome;
	}

	@Override
	public String toString() {
		return "Cidade [idCidade=" + idCidade + ", dcTemperatura=" + dcTemperatura + ", v10m=" + v10m + ", dcNome="
				+ dcNome + "]";
	}	
	

}
