package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import persistence.PersonDao;

@Entity
public class Log {

	// ATRIBUTOS
	@Id
	@GeneratedValue
	private Integer id;
	@NotNull
	private String uid;
	@Temporal( TemporalType.TIMESTAMP)
	private Date acessRegistry = new Date();

	// PROPRIEDADES
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAcessRegistry() {
		DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatDate.format(this.acessRegistry);
	}

}
