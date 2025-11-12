package com.fdm.bouldering_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to create a new feature tag")
public class FeatureTagCreationRequest {

    @NotBlank(message = "Feature tag name is required")
    @Size(max = 50, message = "Feature tag name must be at most 50 characters")
    @Schema(description = "Name of the feature tag", example = "Crimp")
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}