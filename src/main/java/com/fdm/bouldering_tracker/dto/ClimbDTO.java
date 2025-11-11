package com.fdm.bouldering_tracker.dto;

import com.fdm.bouldering_tracker.model.Climb;
import java.time.ZonedDateTime;
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

}
