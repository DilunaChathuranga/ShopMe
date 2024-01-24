package com.shopme.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role extends IdBasedEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(length = 40, nullable = false, unique = true)
	private String name;
	
	@Column(length = 150, nullable = false)
	private String description;
	
	public Role() {}
	
	public Role(Integer id) {
		this.id = id;
	}

	public Role(String name) {
		this.name = name;
	}	
	
	public Role(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	@Override
	public String toString() {
		return this.name;
	}

	//getter setter trick
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
	
	
	

}
