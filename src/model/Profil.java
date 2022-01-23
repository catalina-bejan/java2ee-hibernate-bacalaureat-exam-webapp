/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author catal
 */
@Entity
@Table(name = "profil")
public class Profil {
	@Id
	@Column(name = "codp", nullable = false, updatable = false)
    private long codProfil;

	@Column(name = "denp", nullable = false)
    private String denumire;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "profil")
	private List<LiceuProfil> liceeprofil = new ArrayList();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "profil")
	private List<Proba> probe = new ArrayList();

	public Profil(long codProfil, String denumire, List<LiceuProfil> liceeprofil, List<Proba> probe) {
		this.codProfil = codProfil;
		this.denumire = denumire;
		this.liceeprofil = liceeprofil;
		this.probe = probe;
	}

	public Profil() {
	}

	public long getCodProfil() {
		return codProfil;
	}

	public void setCodProfil(long codProfil) {
		this.codProfil = codProfil;
	}

	public String getDenumire() {
		return denumire;
	}

	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

	public List<LiceuProfil> getLiceeprofil() {
		return liceeprofil;
	}

	public void setLiceeprofil(List<LiceuProfil> liceeprofil) {
		this.liceeprofil = liceeprofil;
	}

	public List<Proba> getProbe() {
		return probe;
	}

	public void setProbe(List<Proba> probe) {
		this.probe = probe;
	}
	
	
	 
}
