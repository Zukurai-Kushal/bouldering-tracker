package com.fdm.bouldering_tracker.model;

import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ForeignKey;

@Entity
public class Location {

	public enum Types {GYM_GENERATED, USER_GENERATED}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long locationId;
	
	@ManyToOne
	@JoinColumn(
	    name = "creatorId",
	    nullable = true,
	    foreignKey = @ForeignKey(name = "FK_location_creator")
	)
	private AppUser creator;
	
	private String name;
	private Types type;
	
	private String country;
	private String region;
	private double gpsLat;
	private double gpsLong;
	private String locationPhotoUrl;
	
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Location(AppUser creator, String name, Types type) {
		super();
		this.creator = creator;
		this.name = name;
		this.type = type;
	}

	public Location(AppUser creator, String name, Types type, String country, String region, double gpsLat,
			double gpsLong, String locationPhotoUrl) {
		super();
		this.creator = creator;
		this.name = name;
		this.type = type;
		this.country = country;
		this.region = region;
		this.gpsLat = gpsLat;
		this.gpsLong = gpsLong;
		this.locationPhotoUrl = locationPhotoUrl;
	}

	public Long getLocationId() {
		return locationId;
	}

	public AppUser getCreator() {
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

	public double getGpsLat() {
		return gpsLat;
	}

	public double getGpsLong() {
		return gpsLong;
	}

	public String getLocationPhotoUrl() {
		return locationPhotoUrl;
	}

	public void setCreator(AppUser creator) {
		this.creator = creator;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setGpsLat(double gpsLat) {
		this.gpsLat = gpsLat;
	}

	public void setGpsLong(double gpsLong) {
		this.gpsLong = gpsLong;
	}

	public void setLocationPhotoUrl(String locationPhotoUrl) {
		this.locationPhotoUrl = locationPhotoUrl;
	}

}
