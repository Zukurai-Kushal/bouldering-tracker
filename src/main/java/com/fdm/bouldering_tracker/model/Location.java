package com.fdm.bouldering_tracker.model;

import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Location {

	public enum Types {GYM_GENERATED, USER_GENERATED}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long location_id;
	
	@ManyToOne
	@JoinColumn(name = "creator_id")
	private User creator;
	
	private String name;
	private Types type;
	
	private String country;
	private String region;
	private double gps_lat;
	private double gps_long;
	
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Location(User creator, String name, Types type) {
		super();
		this.creator = creator;
		this.name = name;
		this.type = type;
	}

	public Long getLocation_id() {
		return location_id;
	}

	public User getCreator() {
		return creator;
	}

	public String getName() {
		return name;
	}

	public Types getType() {
		return type;
	}

	public String getCountry() {
		return country;
	}

	public String getRegion() {
		return region;
	}

	public double getGps_lat() {
		return gps_lat;
	}

	public double getGps_long() {
		return gps_long;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setGps_lat(double gps_lat) {
		this.gps_lat = gps_lat;
	}

	public void setGps_long(double gps_long) {
		this.gps_long = gps_long;
	}
	
}
