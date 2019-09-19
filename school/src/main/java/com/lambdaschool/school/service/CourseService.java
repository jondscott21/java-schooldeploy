package com.lambdaschool.school.service;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.view.CountStudentsInCourses;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collection;

public interface CourseService
{

    ArrayList<Course> findAll();
    ArrayList<Course> findAllPageable(Pageable pageable);

    ArrayList<CountStudentsInCourses> getCountStudentsInCourse();

    Course findCourseById(long id);

    Course save(Course course);

    void delete(long id);

}
