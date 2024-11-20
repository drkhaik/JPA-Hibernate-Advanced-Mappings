package com.section_09.jpahibernate_advanced.demo.dao;

import com.section_09.jpahibernate_advanced.demo.entity.Course;
import com.section_09.jpahibernate_advanced.demo.entity.Instructor;
import com.section_09.jpahibernate_advanced.demo.entity.InstructorDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO{

    private EntityManager entityManager;

    @Autowired
    public AppDAOImpl(EntityManager theEntityManager){
        this.entityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void save(Instructor theInstructor) {
        // this will also save the details object because of CascadeType.ALL
        entityManager.persist(theInstructor);
    }

    @Override
    public Instructor findById(int theId) {
        return entityManager.find(Instructor.class, theId);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int theId) {
        Instructor tempInstructor = entityManager.find(Instructor.class, theId);
        if(tempInstructor != null){

            // get the courses
            List<Course> courses = tempInstructor.getCourses();

            // break the association of all courses for the instructor
            for(Course tempCourse : courses){
                tempCourse.setInstructor(null);
                // we only remove the instructor, not the courses base on our cascade.types
            }

            // delete the instructor
            entityManager.remove(tempInstructor);
        }
    }

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        return entityManager.find(InstructorDetail.class, theId);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int theId) {
        InstructorDetail tempInstructorDetail = entityManager.find(InstructorDetail.class, theId);
        if(tempInstructorDetail != null){

            // remove the associated object reference if u set except cascade remove, u have to break the bi-direction link between them
            tempInstructorDetail.getInstructor().setInstructorDetail(null);

            entityManager.remove(tempInstructorDetail);
        }
    }

    @Override
    public List<Course> findCoursesByInstructorId(int theId) {
        TypedQuery<Course> query = entityManager.createQuery("from Course where instructor.id = :data", Course.class);
        query.setParameter("data", theId);

        List<Course> courses;
        courses = query.getResultList();
        return courses;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int theId) {
        TypedQuery<Instructor> query = entityManager.createQuery(
                "select i from Instructor i " // add whitespace before double quotes
                + "JOIN FETCH i.courses " // "courses" is name of Instructor properties
                + "JOIN FETCH i.instructorDetail "
                + "where i.id = :data", Instructor.class);
        query.setParameter("data", theId);
        // select i1_0.id,c1_0.instructor_id,c1_0.id,c1_0.title,i1_0.email,i1_0.first_name,id1_0.id,id1_0.hobby,id1_0.youtube_channel,i1_0.last_name from instructor i1_0 join course c1_0 on i1_0.id=c1_0.instructor_id join instructor_detail id1_0 on id1_0.id=i1_0.instructor_detail_id where i1_0.id=?
        Instructor instructor;
        instructor = query.getSingleResult();
        return instructor;
    }

    @Override
    @Transactional
    public void update(Instructor theInstructor) {
        entityManager.merge(theInstructor);
    }

    @Override
    @Transactional
    public void updateCourse(Course tempCourse) {
        entityManager.merge(tempCourse);
    }

    @Override
    public Course findCourseById(int theId) {
        Course course = entityManager.find(Course.class, theId);
        if(course != null){
            return course;
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteCourseById(int theId) {
        Course tempCourse = entityManager.find(Course.class, theId);
        entityManager.remove(tempCourse);
    }

    @Override
    @Transactional
    public void saveCourse(Course theCourse) {
        entityManager.persist(theCourse);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int theId) {
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c "
                        + "JOIN FETCH c.reviews "
                        + "where c.id= :data", Course.class);
        query.setParameter("data", theId);

        Course course;
        course = query.getSingleResult();
        return course;
    }
}
