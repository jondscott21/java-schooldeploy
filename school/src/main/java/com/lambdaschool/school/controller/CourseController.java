package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.ErrorDetail;
import com.lambdaschool.school.service.CourseService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/courses")
public class CourseController
{
    @Autowired
    private CourseService courseService;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integr", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})


    @ApiOperation(value = "Lists all Courses", response = Course.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Courses Found", response = Course.class),
            @ApiResponse(code = 404, message = "Courses Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/courses", produces = {"application/json"})
    public ResponseEntity<?> listAllCourses()
    {
        ArrayList<Course> myCourses = courseService.findAll();
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    @ApiOperation(value = "Lists all Courses Pageable", response = Course.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Courses Found", response = Course.class),
            @ApiResponse(code = 404, message = "Courses Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/courses/pagable", produces = {"application/json"})
    public ResponseEntity<?> listAllCourses(@PageableDefault(page = 0, size= 3, sort = "coursename", direction = Sort.Direction.DESC)
                                                    Pageable pageable)
    {
        ArrayList<Course> myCourses = courseService.findAllPageable(pageable);
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    @ApiOperation(value = "Student Count By Course", response = Course.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Student Count Found", response = Course.class),
            @ApiResponse(code = 404, message = "Student Count Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/studcount", produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses()
    {
        return new ResponseEntity<>(courseService.getCountStudentsInCourse(), HttpStatus.OK);
    }

    @ApiOperation(value = "Create a Course", response = Course.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Student Course Created", response = Course.class),
            @ApiResponse(code = 500, message = "Course Creation Failed", response = ErrorDetail.class)})
    @PostMapping(value = "/course/add", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewCourse(@Valid
                                          @RequestBody Course newCourse) throws URISyntaxException
    {
        newCourse = courseService.save(newCourse);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCourseURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Courseid}").buildAndExpand(newCourse.getCourseid()).toUri();
        responseHeaders.setLocation(newCourseURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete Courses By Course Id", response = void.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Course Deleted", response = void.class),
            @ApiResponse(code = 500, message = "Course Delete Failed", response = ErrorDetail.class)})
    @DeleteMapping("/courses/{courseid}")
    public ResponseEntity<?> deleteCourseById(@PathVariable long courseid)
    {
        courseService.delete(courseid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
