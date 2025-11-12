package com.fdm.bouldering_tracker.dto;

import com.fdm.bouldering_tracker.model.Climb;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public class ClimbDTO {
    private Long climbId;
    private String grade;
    private String scale;
    private ZonedDateTime datetime;
    private int rating;
    private boolean shared;
    private int attempts;
    private String comment;
    private String boulderName;
    private String photoURL;
    
    @Schema(description = "Location details for the climb")
    private LocationDTO location;

    @Schema(description = "Feature tags associated with the climb")
    private List<FeatureTagDTO> features;

    public ClimbDTO(Climb climb) {
        this.climbId = climb.getClimbId();
        this.grade = climb.getGrade();
        this.scale = Optional.ofNullable(climb.getScale()).map(Enum::name).orElse(null);
        this.datetime = climb.getDatetime();
        this.rating = climb.getRating();
        this.shared = climb.isShared();
        this.attempts = climb.getAttempts();
        this.comment = climb.getComment();
        this.boulderName = climb.getBoulderName();
        this.photoURL = climb.getPhotoURL();
        this.location = climb.getLocation() != null ? new LocationDTO(climb.getLocation()) : null;
        this.features = climb.getFeatures() != null
            ? climb.getFeatures().stream().map(FeatureTagDTO::new).toList()
            : List.of();
    }

	public Long getClimbId() {
		return climbId;
	}

	public void setClimbId(Long climbId) {
		this.climbId = climbId;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public ZonedDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(ZonedDateTime datetime) {
		this.datetime = datetime;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getBoulderName() {
		return boulderName;
	}

	public void setBoulderName(String boulderName) {
		this.boulderName = boulderName;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	public List<FeatureTagDTO> getFeatures() {
		return features;
	}

	public void setFeatures(List<FeatureTagDTO> features) {
		this.features = features;
	}

}
