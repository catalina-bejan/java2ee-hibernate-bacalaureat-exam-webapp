package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "proba")
public class Proba {
	@Id
	@Column(name = "codpr", nullable = false, updatable = false)
    private long codProba;

	@Column(name = "denpr", nullable = false)
    private String denumire;
	
	@Column(name = "tip_proba", nullable = false)
    private long tipProba;
	
	@ManyToOne()
	@JoinColumn(name = "codp", nullable = false)
    private Profil profil;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "proba")
	private List<Examen> examene= new ArrayList();

	public Proba(long codProba, String denumire, long tipProba, Profil profil, List<Examen> examene) {
		this.codProba = codProba;
		this.denumire = denumire;
		this.tipProba = tipProba;
		this.profil = profil;
		this.examene = examene;
	}

	public Proba() {
	}

	public long getCodProba() {
		return codProba;
	}

	public void setCodProba(long codProba) {
		this.codProba = codProba;
	}

	public String getDenumire() {
		return denumire;
	}

	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

	public long getTipProba() {
		return tipProba;
	}

	public void setTipProba(long tipProba) {
		this.tipProba = tipProba;
	}

	public Profil getProfil() {
		return profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}

	public List<Examen> getExamene() {
		return examene;
	}

	public void setExamene(List<Examen> examene) {
		this.examene = examene;
	}
	
	
	
	
}
