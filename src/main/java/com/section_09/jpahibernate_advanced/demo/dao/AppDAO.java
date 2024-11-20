package com.section_09.jpahibernate_advanced.demo.dao;

import com.section_09.jpahibernate_advanced.demo.entity.Course;
import com.section_09.jpahibernate_advanced.demo.entity.Instructor;
import com.section_09.jpahibernate_advanced.demo.entity.InstructorDetail;

import java.util.List;

public interface AppDAO {
    void save(Instructor theInstructor);

    Instructor findById(int theId);

    void deleteInstructorById(int theId);

    InstructorDetail findInstructorDetailById(int theId);

    void deleteInstructorDetailById(int theId);

    List<Course> findCoursesByInstructorId(int theId);

    Instructor findInstructorByIdJoinFetch(int theId);

    void update(Instructor theInstructor);

    void updateCourse(Course tempCourse);

    Course findCourseById(int theId);

    void deleteCourseById(int theId);

    void saveCourse(Course theCourse);

    Course findCourseAndReviewsByCourseId(int theId);
}
