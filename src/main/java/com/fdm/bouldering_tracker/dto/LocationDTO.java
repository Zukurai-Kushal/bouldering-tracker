package com.fdm.bouldering_tracker.dto;

import com.fdm.bouldering_tracker.model.Location;

public class LocationDTO {
    private Long locationId;
    private String name;
    private Location.Types type;
    private String country;
    private String region;
    private double gpsLat;
    private double gpsLong;
    private String locationPhotoUrl;

    public LocationDTO(Location location) {
        this.locationId = location.getLocationId();
        this.name = location.getName();
        this.type = location.getType();
        this.country = location.getCountry();
        this.region = location.getRegion();
        this.gpsLat = location.getGpsLat();
        this.gpsLong = location.getGpsLong();
        this.locationPhotoUrl = location.getLocationPhotoUrl();
    }

	public Long getLocationId() {
		return locationId;
	}

	public String getName() {
		return name;
	}

	public Location.Types getType() {
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

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(Location.Types type) {
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