package com.section_09.jpahibernate_advanced.demo;

import com.section_09.jpahibernate_advanced.demo.dao.AppDAO;
import com.section_09.jpahibernate_advanced.demo.entity.Course;
import com.section_09.jpahibernate_advanced.demo.entity.Instructor;
import com.section_09.jpahibernate_advanced.demo.entity.InstructorDetail;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

			createInstructorWithCourses(appDAO);
		};
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
