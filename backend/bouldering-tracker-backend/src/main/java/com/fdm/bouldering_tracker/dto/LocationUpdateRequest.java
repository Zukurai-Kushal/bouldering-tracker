package com.fdm.bouldering_tracker.dto;

import com.fdm.bouldering_tracker.model.Location.Types;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to update an existing location")
public class LocationUpdateRequest {

    @Size(max = 100, message = "Location name must be at most 100 characters")
    @Schema(description = "Updated name of the location", example = "Beacon Hill")
    private String name;

    @Schema(description = "Updated type of location", example = "INDOOR")
    private Types type;

    @Size(max = 100, message = "Country name must be at most 100 characters")
    private String country;

    @Size(max = 100, message = "Region name must be at most 100 characters")
    private String region;

    private Double gpsLat;
    private Double gpsLong;

    @Size(max = 255, message = "Photo URL must be at most 255 characters")
    private String locationPhotoUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Double getGpsLat() {
		return gpsLat;
	}

	public void setGpsLat(Double gpsLat) {
		this.gpsLat = gpsLat;
	}

	public Double getGpsLong() {
		return gpsLong;
	}

	public void setGpsLong(Double gpsLong) {
		this.gpsLong = gpsLong;
	}

	public String getLocationPhotoUrl() {
		return locationPhotoUrl;
	}

	public void setLocationPhotoUrl(String locationPhotoUrl) {
		this.locationPhotoUrl = locationPhotoUrl;
	}

}