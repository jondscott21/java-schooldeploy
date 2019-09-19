package com.lambdaschool.school.service;

import com.lambdaschool.school.model.Instructor;

public interface InstructorService
{
    Instructor findInstructorById(long id);

    Instructor save(Instructor instructor);

    Instructor update(Instructor instructor, long id);

    void delete(long id);
}
