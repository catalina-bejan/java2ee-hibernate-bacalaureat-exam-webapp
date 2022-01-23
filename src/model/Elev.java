/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author catal
 */
@Entity
@Table(name = "elev")
public class Elev {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "code", nullable = false)
    private long codElev;
	
	@Column(name = "nume", nullable = false)
    public String nume;
	
	@Column(name = "init_tata", nullable = false)
    private String initialaTata;
	
	@Column(name = "prenume", nullable = false)
    public String prenume;

	@Column(name = "cnp", unique = true, nullable = false)
    private String cnp;
	
	// liceu mm profil
	// liceu 1m liceu_profil m1 profil
	// proba m1 profil
	// proba 1m examen
	//elev m1 liceu_profil
	@ManyToOne()
    @JoinColumn(name = "codlp", nullable = false)
    private LiceuProfil liceuProfil;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "elev", cascade = CascadeType.ALL)
	private List<Examen> examene= new ArrayList();

	public Elev(long codElev, String nume, String initialaTata, String prenume, String cnp, LiceuProfil liceuProfil,
			List<Examen> examene) {
		this.codElev = codElev;
		this.nume = nume;
		this.initialaTata = initialaTata;
		this.prenume = prenume;
		this.cnp = cnp;
		this.liceuProfil = liceuProfil;
		this.examene = examene;
	}

	public Elev() {
	}

	public long getCodElev() {
		return codElev;
	}

	public void setCodElev(long codElev) {
		this.codElev = codElev;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getInitialaTata() {
		return initialaTata;
	}

	public void setInitialaTata(String initialaTata) {
		this.initialaTata = initialaTata;
	}

	public String getPrenume() {
		return prenume;
	}

	public void setPrenume(String prenume) {
		this.prenume = prenume;
	}

	public String getCnp() {
		return cnp;
	}

	public void setCnp(String cnp) {
		this.cnp = cnp;
	}

	public LiceuProfil getLiceuProfil() {
		return liceuProfil;
	}

	public void setLiceuProfil(LiceuProfil liceuProfil) {
		this.liceuProfil = liceuProfil;
	}

	public List<Examen> getExamene() {
		return examene;
	}

	public void setExamene(List<Examen> examene) {
		this.examene = examene;
	}

}
