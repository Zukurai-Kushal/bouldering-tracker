package com.fdm.bouldering_tracker.dto;

import com.fdm.bouldering_tracker.model.FeatureTag;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Feature tag details")
public class FeatureTagDTO {

    @Schema(description = "Unique ID of the feature tag", example = "1")
    private Long featureId;

    @Schema(description = "Name of the feature tag", example = "Crimp")
    private String name;

    public FeatureTagDTO(FeatureTag tag) {
        this.featureId = tag.getFeatureId();
        this.name = tag.getName();
    }

    public Long getFeatureId() {
        return featureId;
    }

    public String getName() {
        return name;
    }
}