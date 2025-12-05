package com.fdm.bouldering_tracker.dto;

import com.fdm.bouldering_tracker.model.Location;

import io.swagger.v3.oas.annotations.media.Schema;

public class LocationDTO {
	
	@Schema(description = "Unique ID of the location", example = "101")
	private Long locationId;

	@Schema(description = "Name of the location", example = "The Arch Climbing Wall")
	private String name;

	@Schema(description = "Type of location", example = "INDOOR")
	private Location.Types type;

	@Schema(description = "Country where the location is situated", example = "UK")
	private String country;

	@Schema(description = "Region of the location", example = "London")
	private String region;

	@Schema(description = "GPS latitude of the location", example = "51.5074")
	private double gpsLat;

	@Schema(description = "GPS longitude of the location", example = "-0.1278")
	private double gpsLong;

	@Schema(description = "Photo URL of the location", example = "https://example.com/location.jpg")
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