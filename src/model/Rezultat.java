/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author catal
 */
@Entity
@Table(name = "rezultat")
public class Rezultat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;
	
	@OneToOne()
    @JoinColumn(name = "codex", nullable = false)
    private Examen examen;
	
	@Column(name = "nota_init", nullable = true)
    private double notaInitiala;
	
	@Column(name = "nota_cont", nullable = true)
    private double notaContestatie;

	public Rezultat(long id, Examen examen, double notaInitiala, double notaContestatie) {
		this.id = id;
		this.examen = examen;
		this.notaInitiala = notaInitiala;
		this.notaContestatie = notaContestatie;
	}

	public Rezultat() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

	public double getNotaInitiala() {
		return notaInitiala;
	}

	public void setNotaInitiala(double notaInitiala) {
		this.notaInitiala = notaInitiala;
	}

	public double getNotaContestatie() {
		return notaContestatie;
	}

	public void setNotaContestatie(double notaContestatie) {
		this.notaContestatie = notaContestatie;
	}

}
