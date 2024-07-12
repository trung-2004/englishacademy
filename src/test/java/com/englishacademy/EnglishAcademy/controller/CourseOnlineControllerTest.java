//package com.englishacademy.EnglishAcademy.controller;
//
//import com.englishacademy.EnglishAcademy.controllers.CourseOnlineController;
//import com.englishacademy.EnglishAcademy.dtos.course_online.CourseOnlineDTO;
//import com.englishacademy.EnglishAcademy.models.course_online.CreateCourseOnline;
//import com.englishacademy.EnglishAcademy.services.CourseOnlineService;
//import com.englishacademy.EnglishAcademy.services.impl.CourseOnlineServiceImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.hamcrest.Matchers.*;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(CourseOnlineController.class)
//@AutoConfigureMockMvc
//public class CourseOnlineControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @InjectMocks
//    private CourseOnlineServiceImpl courseOnlineService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private CreateCourseOnline createCourseOnline;
//
//    @BeforeEach
//    void setUp() {
//        createCourseOnline = new CreateCourseOnline(
//                "Java Basics",
//                "http://example.com/image.png",
//                100.0,
//                "A course about Java basics.",
//                1,
//                "English",
//                "http://example.com/trailer.mp4",
//                1L
//        );
//    }
//    @Test
//    void createCourseOnlineTest() throws Exception {
//        CourseOnlineDTO courseOnlineDTO = new CourseOnlineDTO();
//        // Set các trường của courseOnlineDTO nếu cần
//
//        Mockito.when(courseOnlineService.create(any(CreateCourseOnline.class))).thenReturn(courseOnlineDTO);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/any/course-online")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(createCourseOnline)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.success", is(true)))
//                .andExpect(jsonPath("$.status", is(200)))
//                .andExpect(jsonPath("$.message", is("ok")))
//                .andExpect(jsonPath("$.data").isNotEmpty());
//    }
//}
