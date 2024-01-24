package com.shopme.common.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class IdBasedEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;

	
	
	//getter setter trick
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
