package com.section_09.jpahibernate_advanced.demo;

import com.section_09.jpahibernate_advanced.demo.dao.AppDAO;
import com.section_09.jpahibernate_advanced.demo.entity.Course;
import com.section_09.jpahibernate_advanced.demo.entity.Instructor;
import com.section_09.jpahibernate_advanced.demo.entity.InstructorDetail;
import com.section_09.jpahibernate_advanced.demo.entity.Review;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO) {

		return runner -> {
//			createInstructor(appDAO);

//			findInstructorById(appDAO);

//			deleteInstructor(appDAO);

//			findInstructorDetailById(appDAO);

//			deleteInstructorDetail(appDAO);

//			createInstructorWithCourses(appDAO);

//			findInstructorWithCourses(appDAO);

//			findCoursesByInstructorId(appDAO);

//			findInstructorByIdJoinFetch(appDAO);

//			updateInstructor(appDAO);

//			updateCourse(appDAO);

//			deleteCourse(appDAO);

//			createCourse(appDAO);

//			findCourseAndReviewById(appDAO);

			deleteCourseAndReviews(appDAO);
		};
	}

	private void deleteCourseAndReviews(AppDAO appDAO) {
		int theId = 12;
		appDAO.deleteCourseById(theId);
		System.out.println("Deleted the Course with id: " + theId);
		// will delete all the review because the cascade.all
	}

	private void findCourseAndReviewById(AppDAO appDAO) {
		int theId = 12;
		Course course = appDAO.findCourseAndReviewsByCourseId(theId);

		System.out.println("Course: "  +course);
		System.out.println("Reviews: "  +course.getReviews());
	}

	private void createCourse(AppDAO appDAO) {
		Course course = new Course("Javascript NodeJS and ReactJS");

		Review newReview = new Review("Great course");
		Review newReview2 = new Review("Recommend u guys should test it out!");
		Review newReview3 = new Review("What a dumb course, u are an idiot!");

		course.addReview(newReview);
		course.addReview(newReview2);
		course.addReview(newReview3);

		System.out.println("Course: " + course);
		System.out.println("Reviews: " + course.getReviews());

		appDAO.saveCourse(course);
	}

	private void deleteCourse(AppDAO appDAO) {
		int theId = 11;
		appDAO.deleteCourseById(theId);
	}

	private void updateCourse(AppDAO appDAO) {
		int theId = 10;
		Course tempCourse = appDAO.findCourseById(theId);
		if(tempCourse != null){
			tempCourse.setTitle("C# Crash Course");
		}

		appDAO.updateCourse(tempCourse);
	}

	private void updateInstructor(AppDAO appDAO) {
		int theId = 1;
		Instructor tempInstructors = appDAO.findById(theId);
		System.out.println("tempInstructor: " + tempInstructors);

		tempInstructors.setFirstName("Test update ok");

		appDAO.update(tempInstructors);
	}

	private void findInstructorByIdJoinFetch(AppDAO appDAO) {
		int theId = 1;
		Instructor tempInstructors = appDAO.findInstructorByIdJoinFetch(theId);
		System.out.println("tempInstructor: " + tempInstructors);
		System.out.println("Courses: " + tempInstructors.getCourses());

		// select i1_0.id,c1_0.instructor_id,c1_0.id,c1_0.title,i1_0.email,i1_0.first_name,id1_0.id,id1_0.hobby,id1_0.youtube_channel,i1_0.last_name from instructor i1_0 join course c1_0 on i1_0.id=c1_0.instructor_id join instructor_detail id1_0 on id1_0.id=i1_0.instructor_detail_id where i1_0.id=?
	}

	private void findCoursesByInstructorId(AppDAO appDAO) {
		int theId = 1;
		Instructor tempInstructors = appDAO.findById(theId);
		System.out.println("tempInstructor: " + tempInstructors);

		// fetch = FetchType.LAZY
		List<Course> courses = appDAO.findCoursesByInstructorId(theId);
		tempInstructors.setCourses(courses);
		System.out.println("the associated course: " + tempInstructors.getCourses());
	}

	private void findInstructorWithCourses(AppDAO appDAO) {
		int theId = 1;
		Instructor tempInstructors = appDAO.findById(theId);
		System.out.println("tempInstructor: " + tempInstructors);

		// fetch = FetchType.EAGER
		System.out.println("the associated course: " + tempInstructors.getCourses());
	}

	private void createInstructorWithCourses(AppDAO appDAO) {
		Instructor tempInstructor = new Instructor(
				"Susan", "Pem", "susanpem@gmail.com"
		);

		InstructorDetail tempInstructorDetail = new InstructorDetail(
				"http://www.susanpem.com/youtube", "Video Games"
		);

		tempInstructor.setInstructorDetail(tempInstructorDetail);
		// NOTE: This will also save the details object because of CascadeType.ALL

		Course course = new Course("Java Spring");
		Course course2 = new Course("PHP Laravel");

		// add courses to instructor
		tempInstructor.add(course);
		tempInstructor.add(course2);

		// NOTE: This will also save the courses because of CascadeType.PERSIST
		System.out.println("Save the instructor with instructor detail and courses");
		System.out.println(tempInstructor);
		System.out.println("Courses: " + tempInstructor.getCourses());
		appDAO.save(tempInstructor);
	}

	private void deleteInstructorDetail(AppDAO appDAO) {
		int theId = 4;
		appDAO.deleteInstructorDetailById(theId);
		System.out.println("Done");
		// delete from instructor where id=?
		// delete from instructor_detail where id=?

		// update instructor set email=?,first_name=?,instructor_detail_id=?,last_name=? where id=?
		// delete from instructor_detail where id=?
	}

	private void findInstructorDetailById(AppDAO appDAO) {
		int theId = 2;
		InstructorDetail tempInstructorDetail = appDAO.findInstructorDetailById(theId);
		System.out.println(tempInstructorDetail);
		System.out.println(tempInstructorDetail.getInstructor());
	}

	private void deleteInstructor(AppDAO appDAO) {
		int theId = 1;
		appDAO.deleteInstructorById(theId);
		System.out.println("Done");
		// delete from instructor where id=?
		// delete from instructor_detail where id=?
	}

	private void findInstructorById(AppDAO appDAO) {
		int theId = 1;
		Instructor tempInstructor = appDAO.findById(theId);
		System.out.println("Instructor" + tempInstructor);
		System.out.println("Instructor Details: " + tempInstructor.getInstructorDetail());
	}

	private void createInstructor(AppDAO appDAO) {

		Instructor tempInstructor = new Instructor(
				"John", "Smith", "johnsmith@gmail.com"
		);

		InstructorDetail tempInstructorDetail = new InstructorDetail(
				"http://www.johnsmith.com/youtube", "Football"
		);

//		Instructor tempInstructor = new Instructor(
//				"Mary", "Jane", "maryjane@gmail.com"
//		);
//
//		InstructorDetail tempInstructorDetail = new InstructorDetail(
//				"http://www.maryjane.com/youtube", "Baseball"
//		);

		tempInstructor.setInstructorDetail(tempInstructorDetail);
		// NOTE: This will also save the details object because of CascadeType.ALL
		appDAO.save(tempInstructor);
	}
}
