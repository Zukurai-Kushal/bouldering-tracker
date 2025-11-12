package com.fdm.bouldering_tracker.service;

import com.fdm.bouldering_tracker.model.FeatureTag;
import com.fdm.bouldering_tracker.repository.FeatureTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeatureTagService {

    private final FeatureTagRepository featureTagRepository;

    @Autowired
    public FeatureTagService(FeatureTagRepository featureTagRepository) {
        this.featureTagRepository = featureTagRepository;
    }

    public List<FeatureTag> findAllByIds(List<Long> ids) {
        return featureTagRepository.findAllById(ids);
    }

    public Optional<FeatureTag> findByName(String name) {
        return featureTagRepository.findByName(name);
    }

    public List<FeatureTag> searchByName(String namePart) {
        return featureTagRepository.findAllByNameContainingIgnoreCase(namePart);
    }

    public FeatureTag save(FeatureTag tag) {
        return featureTagRepository.save(tag);
    }

    public void delete(FeatureTag tag) {
        featureTagRepository.delete(tag);
    }
    
    public List<FeatureTag> findAll() {
    	return featureTagRepository.findAll();
    }
    
	public List<FeatureTag> findAllSorted() {
	    return featureTagRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}
    
}