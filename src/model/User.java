package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author catal
 */


@Entity
@Table(name = "users")
public class User{
	
	@Column(name = "nume", nullable = false)
    private String nume;
	
	@Column(name = "prenume", nullable = false)
	private String prenume;
	
	@Id
	@Column(name = "cnp", nullable = false)
	private String cnp;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "parola", nullable = false)
	private String parola;
	
	@Column(name = "functie", nullable = false)
	private String functie;

    public User(String nume, String prenume, String cnp, String username, String parola, String functie) {
        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
        this.username = username;
        this.parola = parola;
        this.functie = functie;
    }

    public User() {
    }
    
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }
	
}
