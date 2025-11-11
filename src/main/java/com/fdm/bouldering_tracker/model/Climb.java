package com.fdm.bouldering_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ForeignKey;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Entity
public class Climb {

	public enum Scales {V_SCALE, FONT_SCALE}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long climbId;
	
	@ManyToOne
	@JoinColumn(
	    name = "userId",
	    nullable = true,
	    foreignKey = @ForeignKey(name = "FK_climb_user")
	)
	private AppUser user;
	
	@ManyToOne
	@JoinColumn(
	    name = "locationId",
	    nullable = true,
	    foreignKey = @ForeignKey(name = "FK_climb_location")
	)
	private Location location;
		
	private String grade;
	private Scales scale;
	private ZonedDateTime datetime;
	private int rating;
	private boolean shared;
	private int attempts;
	
	@ManyToMany
	private List<FeatureTag> features;
	
	private String comment;
	private String boulderName;
	private String photoURL;
	
	public Climb() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Climb(AppUser user, Location location, String grade, Scales scale, ZonedDateTime datetime, int rating,
			boolean shared) {
		super();
		this.user = user;
		this.location = location;
		this.grade = grade;
		this.scale = scale;
		this.datetime = datetime;
		this.rating = rating;
		this.shared = shared;
	}

	public Climb(AppUser user, Location location, String grade, Scales scale, ZonedDateTime datetime, int rating,
			boolean shared, int attempts, List<FeatureTag> features, String comment, String boullderName,
			String photoURL) {
		super();
		this.user = user;
		this.location = location;
		this.grade = grade;
		this.scale = scale;
		this.datetime = datetime;
		this.rating = rating;
		this.shared = shared;
		this.attempts = attempts;
		this.features = features;
		this.comment = comment;
		this.boulderName = boullderName;
		this.photoURL = photoURL;
	}

	public Long getClimbId() {
		return climbId;
	}

	public AppUser getUser() {
		return user;
	}

	public Location getLocation() {
		return location;
	}

	public String getGrade() {
		return grade;
	}

	public Scales getScale() {
		return scale;
	}

	public ZonedDateTime getDatetime() {
		return datetime;
	}

	public int getRating() {
		return rating;
	}

	public boolean isShared() {
		return shared;
	}

	public int getAttempts() {
		return attempts;
	}

	public List<FeatureTag> getFeatures() {
		return features;
	}

	public String getComment() {
		return comment;
	}

	public String getBoulderName() {
		return boulderName;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public void setScale(Scales scale) {
		this.scale = scale;
	}

	public void setDatetime(ZonedDateTime datetime) {
		this.datetime = datetime;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public void setFeatures(List<FeatureTag> features) {
		this.features = features;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setBoulderName(String boulderName) {
		this.boulderName = boulderName;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	
}
