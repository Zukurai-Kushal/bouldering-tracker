package com.fdm.bouldering_tracker.dto;

import com.fdm.bouldering_tracker.model.AppUser;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO for exposing and updating user data")
public class AppUserDTO {

    @Schema(description = "Unique ID of the user", example = "1")
    @NotNull
    private Long userId;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Schema(description = "Username of the user", example = "kushal")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Schema(description = "Email address of the user", example = "kushal@example.com")
    private String email;

    public AppUserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AppUserDTO(AppUser user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
	public void setUserId(Long userId) { this.userId = userId; }
    
}
