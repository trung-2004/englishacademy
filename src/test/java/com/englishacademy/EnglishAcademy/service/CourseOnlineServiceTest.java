package com.englishacademy.EnglishAcademy.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.englishacademy.EnglishAcademy.dtos.course_online.CourseOnlineDTO;
import com.englishacademy.EnglishAcademy.entities.Category;
import com.englishacademy.EnglishAcademy.entities.CourseOnline;
import com.englishacademy.EnglishAcademy.exceptions.AppException;
import com.englishacademy.EnglishAcademy.mappers.CourseOnlineMapper;
import com.englishacademy.EnglishAcademy.models.course_online.CreateCourseOnline;
import com.englishacademy.EnglishAcademy.repositories.CategoryRepository;
import com.englishacademy.EnglishAcademy.repositories.CourseOnlineRepository;
import com.englishacademy.EnglishAcademy.services.impl.CourseOnlineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CourseOnlineServiceTest {

    @Mock
    private CourseOnlineRepository courseOnlineRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CourseOnlineMapper courseOnlineMapper;

    @InjectMocks
    private CourseOnlineServiceImpl courseOnlineService;

    private CreateCourseOnline createCourseOnline;

    @BeforeEach
    void setUp() {
        createCourseOnline = new CreateCourseOnline(
                "Java Basics",
                "http://example.com/image.png",
                100.0,
                "A course about Java basics.",
                1,
                "English",
                "http://example.com/trailer.mp4",
                1L
        );
    }

    @Test
    void whenDuplicateCourseOnline_thenThrowException() {
        CourseOnline existingCourseOnline = new CourseOnline();
        existingCourseOnline.setName("Java Basics");
        existingCourseOnline.setSlug("java-basics");

        when(courseOnlineRepository.findBySlug("java-basics")).thenReturn(existingCourseOnline);

        assertThrows(AppException.class, () -> {
            courseOnlineService.create(createCourseOnline);
        });
    }

    @Test
    void whenCategoryNotFound_thenThrowException() {
        when(courseOnlineRepository.findBySlug("java-basics")).thenReturn(null);
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AppException.class, () -> {
            courseOnlineService.create(createCourseOnline);
        });
    }

    @Test
    void whenValidInput_thenCreateCourseOnline() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Programming");

        CourseOnline courseOnline = CourseOnline.builder()
                .name("Java Basics")
                .slug("java-basics")
                .image("http://example.com/image.png")
                .price(100.0)
                .description("A course about Java basics.")
                .level(1)
                .language("English")
                .status(0)
                .star(0.0)
                .trailer("http://example.com/trailer.mp4")
                .category(category)
                .build();
        courseOnline.setCreatedBy("Demo");
        courseOnline.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        courseOnline.setModifiedBy("Demo");
        courseOnline.setModifiedDate(new Timestamp(System.currentTimeMillis()));

        CourseOnlineDTO courseOnlineDTO = new CourseOnlineDTO();
        // Set fields of courseOnlineDTO as needed

        when(courseOnlineRepository.findBySlug("java-basics")).thenReturn(null);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(courseOnlineRepository.save(any(CourseOnline.class))).thenReturn(courseOnline);
        when(courseOnlineMapper.toCourseOnlineDTO(courseOnline)).thenReturn(courseOnlineDTO);

        CourseOnlineDTO result = courseOnlineService.create(createCourseOnline);
        // Add assertions to verify the result
    }
}
