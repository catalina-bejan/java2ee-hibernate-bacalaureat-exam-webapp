package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "log")
public class Log {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
    private long id;
	
	@Column(name = "user", nullable = false)
    private String user;
	
	@Column(name = "rol", nullable = false)
    private String rol;
	
	@Column(name = "date", nullable = false)
    private Date date;
	
	@Column(name = "actiune", nullable = false)
    private String actiune;
	
	@Column(name = "descriere", nullable = false)
    private String descriere;

	

	public Log(long id, String user, String rol, Date date, String actiune, String descriere) {
		this.id = id;
		this.user = user;
		this.rol = rol;
		this.date = date;
		this.actiune = actiune;
		this.descriere = descriere;
	}

	public Log() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getActiune() {
		return actiune;
	}

	public void setActiune(String actiune) {
		this.actiune = actiune;
	}

	public String getDescriere() {
		return descriere;
	}

	public void setDescriere(String descriere) {
		this.descriere = descriere;
	}
}
