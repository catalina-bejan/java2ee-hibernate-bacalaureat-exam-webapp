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
@Table(name = "liceu_profil")
public class LiceuProfil {
	@Id
	@Column(name = "codlp", nullable = false, updatable = false)
    private long codLiceuProfil;
	
	@ManyToOne()
	@JoinColumn(name = "codl", nullable = false)
    private Liceu liceu;
	
	@ManyToOne()
	@JoinColumn(name = "codp", nullable = false)
    private Profil profil;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "liceuProfil")
	private List<Elev> elevi= new ArrayList();

	public LiceuProfil(long codLiceuProfil, Liceu liceu, Profil profil, List<Elev> elevi) {
		this.codLiceuProfil = codLiceuProfil;
		this.liceu = liceu;
		this.profil = profil;
		this.elevi = elevi;
	}

	public LiceuProfil() {
		super();
	}


	public long getCodLiceuProfil() {
		return codLiceuProfil;
	}



	public void setCodLiceuProfil(long codLiceuProfil) {
		this.codLiceuProfil = codLiceuProfil;
	}



	public Liceu getLiceu() {
		return liceu;
	}



	public void setLiceu(Liceu liceu) {
		this.liceu = liceu;
	}



	public Profil getProfil() {
		return profil;
	}



	public void setProfil(Profil profil) {
		this.profil = profil;
	}



	public List<Elev> getElevi() {
		return elevi;
	}



	public void setElevi(List<Elev> elevi) {
		this.elevi = elevi;
	}

	@Override
	public String toString() {
		return "LiceuProfil [codLiceuProfil=" + codLiceuProfil + ", liceu=" + liceu + ", profil=" + profil + ", elevi="
				+ elevi + "]";
	}
	

}
