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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "examen")
public class Examen {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codex", nullable = false)
    private long codExamen;
	
	@ManyToOne()
	@JoinColumn(name = "code", nullable = false)
    private Elev elev;
	
	@ManyToOne()
	@JoinColumn(name = "codpr", nullable = false)
    private Proba proba;
	
	@OneToOne(fetch = FetchType.EAGER,mappedBy = "examen",  cascade = CascadeType.ALL)
	private Rezultat rezultat;

	public Examen(long codExamen, Elev elev, Proba proba, Rezultat rezultat) {
		this.codExamen = codExamen;
		this.elev = elev;
		this.proba = proba;
		this.rezultat = rezultat;
	}

	public Examen() {
	}

	public long getCodProba() {
		return codExamen;
	}

	public void setCodExamen(long codExamen) {
		this.codExamen = codExamen;
	}
	
	public long getCodExamen() {
		return codExamen;
	}

	public Elev getElev() {
		return elev;
	}

	public void setElev(Elev elev) {
		this.elev = elev;
	}

	public Proba getProba() {
		return proba;
	}

	public void setProba(Proba proba) {
		this.proba = proba;
	}

	public Rezultat getRezultat() {
		return rezultat;
	}

	public void setRezultat(Rezultat rezultat) {
		this.rezultat = rezultat;
	}
	
}
