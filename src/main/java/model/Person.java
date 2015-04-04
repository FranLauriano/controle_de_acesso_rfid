package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Person implements Serializable{
	
	//ATRIBUTOS
	@Id
	@GeneratedValue
	private Integer id;
	@NotNull
	private String uId;
	@NotNull
	private String name;
	@NotNull
	private String enrollment;
	@NotNull
	private String phone;
	@Temporal( TemporalType.TIMESTAMP)
	private Date createdAt = new Date();
	@NotNull
	private String cpf;
	
	
	//PROPRIEDADES

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCreatedAt() {
		DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatDate.format(this.createdAt);
	}
	
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	
	public String getEnrollment() {
		return this.enrollment;
	}
	public void setEnrollment(String enrollment) {
		this.enrollment = enrollment;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	

}
