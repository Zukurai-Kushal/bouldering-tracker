package com.fdm.bouldering_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Entity
public class Climb {

	public enum Scales {V_SCALE, FONT_SCALE}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long climb_id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;
		
	private String grade;
	private Scales scale;
	private ZonedDateTime datetime;
	private int rating;
	private boolean is_shared;
	private int attempts;
	
	@ManyToMany
	private List<FeatureTag> features;
	
	private String comment;
	private String boulder_name;
	private String photo_url;
	
	public Climb() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Climb(User user, Location location, String grade, Scales scale, ZonedDateTime datetime, int rating,
			boolean is_shared) {
		super();
		this.user = user;
		this.location = location;
		this.grade = grade;
		this.scale = scale;
		this.datetime = datetime;
		this.rating = rating;
		this.is_shared = is_shared;
	}

	public Long getClimb_id() {
		return climb_id;
	}

	public User getUser() {
		return user;
	}

	public Location getLocation() {
		return location;
	}

	public List<FeatureTag> getFeatures() {
		return features;
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

	public boolean isIs_shared() {
		return is_shared;
	}

	public String getComment() {
		return comment;
	}

	public String getBoulder_name() {
		return boulder_name;
	}

	public String getPhoto_url() {
		return photo_url;
	}

	public int getAttempts() {
		return attempts;
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

	public void setFeatures(List<FeatureTag> features) {
		this.features = features;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setBoulder_name(String boulder_name) {
		this.boulder_name = boulder_name;
	}

	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}

	public void setDatetime(ZonedDateTime datetime) {
		this.datetime = datetime;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public void setIs_shared(boolean is_shared) {
		this.is_shared = is_shared;
	}
	
}
