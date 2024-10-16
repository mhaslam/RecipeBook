package com.malcolmhaslam.recipebook.config;

import com.malcolmhaslam.recipebook.dto.BaseDto;
import com.malcolmhaslam.recipebook.dto.RecipeDto;
import com.malcolmhaslam.recipebook.dto.UserDto;
import com.malcolmhaslam.recipebook.entity.BaseEntity;
import com.malcolmhaslam.recipebook.entity.Recipe;
import com.malcolmhaslam.recipebook.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Base property map for common properties
        PropertyMap<BaseEntity, BaseDto> basePropertyMap = new PropertyMap<BaseEntity, BaseDto>() {
            protected void configure() {
                map().setCreated(source.getCreatedAt());
                map().setUpdated(source.getUpdatedAt());
                using(ctx -> modelMapper.map(source.getCreatedBy(), UserDto.class))
                        .map(source, destination.getCreatedBy());
                using(ctx -> modelMapper.map(source.getUpdatedBy(), UserDto.class))
                        .map(source, destination.getUpdatedBy());
            }
        };

        // Add the base property map to the model mapper
        modelMapper.addMappings(basePropertyMap);

        // Custom mapping for Recipe to RecipeDto
        modelMapper.typeMap(Recipe.class, RecipeDto.class).addMappings(mapper -> {
            // Add specific mappings if necessary
        });

        // User to UserDto mapping
        modelMapper.typeMap(User.class, UserDto.class).addMappings(mapper -> {
            // Add specific mappings if necessary
        });

        return modelMapper;
    }
}
