package com.fdm.bouldering_tracker.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class FeatureTag {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long feature_id;
	
	private String name;
	
	@ManyToMany(mappedBy = "features")
	private List<Climb> climbs;

	public FeatureTag(String name) {
		super();
		this.name = name;
	}

	public FeatureTag() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getFeature_id() {
		return feature_id;
	}

	public String getName() {
		return name;
	}

	public List<Climb> getClimbs() {
		return climbs;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setClimbs(List<Climb> climbs) {
		this.climbs = climbs;
	}
	
}
