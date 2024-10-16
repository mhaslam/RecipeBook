package com.malcolmhaslam.recipebook.controller;

import com.malcolmhaslam.recipebook.entity.Tag;
import com.malcolmhaslam.recipebook.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/{customerId}/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping
    public ResponseEntity<Tag> createTag(@PathVariable Long customerId, @RequestBody Tag tag) {
        Tag createdTag = tagService.createTag(customerId, tag);
        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long customerId, @PathVariable Long id) {
        tagService.deleteTag(customerId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags(@PathVariable Long customerId) {
        List<Tag> tags = tagService.getAllTags(customerId);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }
}
