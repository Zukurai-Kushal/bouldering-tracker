package com.fdm.bouldering_tracker.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.util.List;

public class ClimbUpdateRequest {

    @Size(max = 10, message = "Grade must be at most 10 characters")
    private String grade;

    @Size(max = 10, message = "Scale must be at most 10 characters")
    private String scale;

    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 3, message = "Rating must be at most 3")
    private Integer rating;

    @Min(value = 0, message = "Attempts must be at least 0")
    private Integer attempts;

    @Size(max = 500, message = "Comment must be at most 500 characters")
    private String comment;

    @Size(max = 100, message = "Boulder name must be at most 100 characters")
    private String boulderName;

    @Size(max = 255, message = "Photo URL must be at most 255 characters")
    private String photoURL;

    private Boolean shared;

    private Long locationId;

    private List<Long> featureTagIds;

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

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Integer getAttempts() {
		return attempts;
	}

	public void setAttempts(Integer attempts) {
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

	public Boolean getShared() {
		return shared;
	}

	public void setShared(Boolean shared) {
		this.shared = shared;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public List<Long> getFeatureTagIds() {
		return featureTagIds;
	}

	public void setFeatureTagIds(List<Long> featureTagIds) {
		this.featureTagIds = featureTagIds;
	}

}