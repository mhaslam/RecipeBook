package com.malcolmhaslam.recipebook.service;

import com.malcolmhaslam.recipebook.entity.Customer;
import com.malcolmhaslam.recipebook.entity.Tag;
import com.malcolmhaslam.recipebook.exception.ResourceNotFoundException;
import com.malcolmhaslam.recipebook.repository.CustomerRepository;
import com.malcolmhaslam.recipebook.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Tag createTag(Long customerId, Tag tag) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        // Perform any additional logic or validation here

        return tagRepository.save(tag);
    }

    public void deleteTag(Long customerId, Long tagId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));

        // Ensure the tag is not being used by any recipes before deleting
        if (!tag.getRecipes().isEmpty()) {
            throw new IllegalStateException("Tag is being used by recipes and cannot be deleted");
        }

        tagRepository.delete(tag);
    }

    public List<Tag> getAllTags(Long customerId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return tagRepository.findAll();
    }
}
