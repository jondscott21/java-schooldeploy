package com.lambdaschool.school.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.service.CourseService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CourseController.class, secure = false)

public class CourseControllerUnitTest
{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    private ArrayList<Course> courseList = new ArrayList<>();
    private ArrayList<Instructor> instructList = new ArrayList<>();

//    @Sql(scripts = "data.sql")
    @Before
    public void setUp() throws Exception
    {
//        System.out.println(courseRepository.findAll());
//        courseList.addAll(courseService.findAll());
        Instructor i1 = new Instructor("Sally");
        i1.setInstructid(0);
        Instructor i2 = new Instructor("Lucy");
        i2.setInstructid(1);
        Instructor i3 = new Instructor("Charlie");
        i3.setInstructid(2);

        Course c1 = new Course("Data Science", i1);
        c1.setCourseid(0);
        Course c2 = new Course("JavaScript", i1);
        c2.setCourseid(1);
        Course c3 = new Course("Node.js", i1);
        c3.setCourseid(2);
        Course c4 = new Course("Java Back End", i2);
        c4.setCourseid(3);
        Course c5 = new Course("Mobile IOS", i2);
        c5.setCourseid(4);
        Course c6 = new Course("Mobile Android", i3);
        c6.setCourseid(5);

        courseList.add(c1);
        courseList.add(c2);
        courseList.add(c3);
        courseList.add(c4);
        courseList.add(c5);
        courseList.add(c6);

        instructList.add(i1);
        instructList.add(i2);
        instructList.add(i3);

        System.out.println("CourseList" + courseList);
    }

    @After
    public void tearDown() throws Exception
    {
    }


    @Test
    public void listAllCourses() throws Exception
    {
        String apiUrl ="/courses/courses";
        Mockito.when(courseService.findAll()).thenReturn(courseList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andDo(MockMvcResultHandlers.print()).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(courseList);

        System.out.println("Expected " + er);
        System.out.println("Actual   " + tr);
        assertEquals("Rest Api Returns List", er, tr);
    }

    @Test
    public void addNewCourse() throws Exception
    {
        String apiUrl ="/courses/course/add";
        Instructor i1 = instructList.get(0);
        Course c1 = new Course("New Course", i1);
        Mockito.when(courseService.save(any(Course.class))).thenReturn(c1);
        ObjectMapper mapper = new ObjectMapper();
        String postBody = mapper.writeValueAsString(c1);
        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl).content(postBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
       mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        System.out.println(postBody);
    }
}