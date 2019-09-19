package com.lambdaschool.school.service;

import com.lambdaschool.school.SchoolApplication;
import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolApplication.class)
public class CourseServiceImplUnitTest
{
    @Autowired
    CourseService courseService;
    @Autowired
    InstructorService instructorService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void findAll()
    {
    }

    @Test
    public void findCourseById()
    {
        assertEquals("JavaScript", courseService.findCourseById(2).getCoursename());
    }

    @Test
    public void getCountStudentsInCourse()
    {
    }


    @Test
    public void save()
    {
        Instructor i1 = instructorService.findInstructorById(2);
        Course c1 = new Course("Getting Mathy With it", i1);
        courseService.save(c1);
        assertEquals(6, courseService.findAll().size());
    }

    @Test
    public void delete()
    {
        courseService.delete(5);
        assertEquals(5, courseService.findAll().size());
    }
    @Test(expected = ResourceNotFoundException.class)
    public void deleteNotFound()
    {
        courseService.delete(50);
        assertEquals(5, courseService.findAll().size());
    }
}