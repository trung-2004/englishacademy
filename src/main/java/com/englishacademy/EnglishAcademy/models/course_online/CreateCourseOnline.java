package com.englishacademy.EnglishAcademy.models.course_online;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateCourseOnline {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Image URL is mandatory")
    @Pattern(regexp = "^(http|https)://.*$", message = "Image URL should be a valid URL")
    private String image;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Level is mandatory")
    @Min(value = 0, message = "Level must be at least 0")
    @Max(value = 3, message = "Level must be at most 3")
    private Integer level;

    @NotBlank(message = "Language is mandatory")
    private String language;

    @NotBlank(message = "Trailer URL is mandatory")
    @Pattern(regexp = "^(http|https)://.*$", message = "Trailer URL should be a valid URL")
    private String trailer;

    @NotNull(message = "Category ID is mandatory")
    private Long categoryId;

    // Public constructor
    public CreateCourseOnline(
            @NotBlank(message = "Name is mandatory") String name,
            @NotBlank(message = "Image URL is mandatory") @Pattern(regexp = "^(http|https)://.*$", message = "Image URL should be a valid URL") String image,
            @NotNull(message = "Price is mandatory") @Positive(message = "Price must be greater than zero") Double price,
            @NotBlank(message = "Description is mandatory") @Size(max = 1000, message = "Description cannot exceed 1000 characters") String description,
            @NotNull(message = "Level is mandatory") @Min(value = 0, message = "Level must be at least 0") @Max(value = 3, message = "Level must be at most 3") Integer level,
            @NotBlank(message = "Language is mandatory") String language,
            @NotBlank(message = "Trailer URL is mandatory") @Pattern(regexp = "^(http|https)://.*$", message = "Trailer URL should be a valid URL") String trailer,
            @NotNull(message = "Category ID is mandatory") Long categoryId
    ) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.level = level;
        this.language = language;
        this.trailer = trailer;
        this.categoryId = categoryId;
    }

    // Getters and setters
    // ...
}
