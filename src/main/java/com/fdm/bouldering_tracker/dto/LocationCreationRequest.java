package com.fdm.bouldering_tracker.dto;

import com.fdm.bouldering_tracker.model.Location.Types;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to create a new location")
public class LocationCreationRequest {

    @NotBlank(message = "Location name is required")
    @Size(max = 100, message = "Location name must be at most 100 characters")
    @Schema(description = "Name of the location", example = "Shek O Boulders")
    private String name;

    @Schema(description = "Type of location", example = "OUTDOOR")
    private Types type;

    @Size(max = 100, message = "Country name must be at most 100 characters")
    @Schema(description = "Country of the location", example = "Hong Kong")
    private String country;

    @Size(max = 100, message = "Region name must be at most 100 characters")
    @Schema(description = "Region of the location", example = "Island East")
    private String region;

    @Schema(description = "GPS latitude", example = "22.2485")
    private Double gpsLat;

    @Schema(description = "GPS longitude", example = "114.2321")
    private Double gpsLong;

    @Size(max = 255, message = "Photo URL must be at most 255 characters")
    @Schema(description = "Photo URL of the location", example = "https://example.com/photo.jpg")
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