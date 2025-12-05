package com.fdm.bouldering_tracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fdm.bouldering_tracker.model.FeatureTag;

public interface FeatureTagRepository extends JpaRepository<FeatureTag, Long>{

	Optional<FeatureTag> findByName(String name);
	List<FeatureTag> findAllByNameContainingIgnoreCase(String namePart);
	
}
