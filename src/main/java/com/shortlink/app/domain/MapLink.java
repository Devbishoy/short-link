package com.shortlink.app.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
/**
 * Entity for map_link table to store url and keys
 * @author Prog
 *
 */
@Entity
@Table(name = "map_link")
public class MapLink implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4137104218476487733L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequence_generator")
	private Long id;

	@NotNull
	@Column(name = "key", nullable = false)
	private String key;

	@NotNull
	@Column(name = "url")
	private String url;

	@Column(name = "opened", nullable = false)	
	private Long opened;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getOpened() {
		return opened;
	}

	public void setOpened(Long opened) {
		this.opened = opened;
	}

}
