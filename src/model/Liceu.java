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
@Table(name = "liceu")
public class Liceu {
	@Id
	@Column(name = "codl", nullable = false, updatable = false)
    private long codLiceu;
	
	@Column(name = "denl", nullable = false)
    private String denumire;
	
	@Column(name = "adresal", nullable = false)
    private String adresa;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "liceu")
	private List<LiceuProfil> liceeprofil= new ArrayList();

    public Liceu(long codLiceu, String denumire, String adresa, List<LiceuProfil> liceeprofil) {
		this.codLiceu = codLiceu;
		this.denumire = denumire;
		this.adresa = adresa;
		this.liceeprofil = liceeprofil;
	}

	public Liceu() {
    }

	public long getCodLiceu() {
		return codLiceu;
	}

	public void setCodLiceu(long codLiceu) {
		this.codLiceu = codLiceu;
	}

	public String getDenumire() {
		return denumire;
	}

	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public List<LiceuProfil> getLiceeprofil() {
		return liceeprofil;
	}

	public void setLiceeprofil(List<LiceuProfil> liceeprofil) {
		this.liceeprofil = liceeprofil;
	}

	
}
