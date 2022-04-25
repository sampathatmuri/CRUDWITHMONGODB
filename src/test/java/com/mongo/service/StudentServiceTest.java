package com.mongo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import com.mongo.documents.Department;
import com.mongo.documents.Student;
import com.mongo.documents.Subject;
import com.mongo.repository.StudentRepository;

class StudentServiceTest {

	private StudentRepository repo;

	private StudentService service;

	private Student student;

	@BeforeEach
	void setup() {
		repo = Mockito.mock(StudentRepository.class);
		service = new StudentServiceImpl(repo);
		student = Student.builder().id("1").name("Sampath Atmuri").email("sam@gmail.com")
				.department(new Department("CSE", "A-BLOCK")).subject(new Subject("JAVA", "100")).build();
	}

	@DisplayName("Testing save student method")
	@Test
	void saveStudentTest() {
		given(repo.save(student)).willReturn(student);
		Student studentSave = service.saveStudent(student);
		assertThat(studentSave).isNotNull();
	}

	@DisplayName("Test saving multiple Students")
	@Test
	void saveStudentsTest() {
		List<Student> students = new ArrayList<>();
		students.add(new Student("2", "Bhanu", "bhanu@gmail.com", new Department("ECE", "D-BLOCK"),
				new Subject("C++", "100")));
		students.add(new Student("3", "Hemanth", "hem@gmail.com", new Department("GEO", "G-BLOCK"),
				new Subject("Graphics", "20")));
		given(repo.saveAll((students))).willReturn(students);
		int countOfStudents = service.saveStudents(students).size();
		assertEquals(2, countOfStudents);
	}

	@DisplayName("Testing Delete student method")
	@Test
	void deleteAllStudentTest() {
		List<Student> students = new ArrayList<>();
		students.add(new Student("2", "Bhanu", "bhanu@gmail.com", new Department("ECE", "D-BLOCK"),
				new Subject("C++", "100")));
		students.add(new Student("3", "Hemanth", "hem@gmail.com", new Department("GEO", "G-BLOCK"),
				new Subject("Graphics", "20")));
		given(repo.findAll()).willReturn(students);
		BDDMockito.willDoNothing().given(repo).deleteAll();
		service.deleteAllStudents();
		Mockito.verify(repo, times(1)).deleteAll();
	}
	
	@DisplayName("Testing Delete student method negative scenario")
	@Test
	void deleteAllStudentNegativeTest() {
		ArrayList<Student> studentEm = new ArrayList<Student>();
		given(repo.findAll()).willReturn(studentEm);
		String result = service.deleteAllStudents();
		assertEquals("FAILURE",result);
	}

	@DisplayName("Test Deleting a particular student based on their name and department name")
	@Test
	void deleteStudentTest() {
		String studentName = "Sampath Atmuri";
		String departmentName = "CSE";
		given(repo.findByName(studentName)).willReturn(student);
		BDDMockito.willDoNothing().given(repo).deleteByNameAndDepartmentDepartmentName(studentName, departmentName);
		Student findStudentResult = service.findByStudentName(studentName);
		String deleteStudentResult = service.deleteStudentByName(studentName, departmentName);
		assertThat(findStudentResult).isNotNull();
		Mockito.verify(repo, times(1)).deleteByNameAndDepartmentDepartmentName(studentName, departmentName);
		assertEquals("SUCCESS", deleteStudentResult);

	}
	
	@DisplayName("Test Deleting a particular student based on their name and department name which doesn't exists")
	@Test
	void deleteStudentNegativeTest() {
		String studentName = "Sampath Atmuri";
		String departmentName = "CSE";
		given(repo.findByName(studentName)).willReturn(null);
		Student findStudentResult = service.findByStudentName(studentName);
		assertThat(findStudentResult).isNull();
		Mockito.verify(repo, times(0)).deleteByNameAndDepartmentDepartmentName(studentName, departmentName);
		String result = service.deleteStudentByName(studentName, departmentName);
		assertEquals("FAILURE", result);

	}

	@DisplayName("Test finding a Student by Name")
	@Test
	void findStudentByName() {
		String studentName = "Sampath Atmuri";
		given(repo.findByName(studentName)).willReturn(student);
		Student studentFound = service.findByStudentName(studentName);
		assertThat(studentFound).isNotNull();
	}

	@DisplayName("Test updating a Student")
	@Test
	void updatesStudentTest() {
		given(repo.save(student)).willReturn(student);
		Student studentUpdated = service.upateStudent(student);
		assertThat(studentUpdated).isNotNull();
	}

	@DisplayName("Test getting all students info")
	@Test
	void findAllStudentsTest() {
		List<Student> students = new ArrayList<>();
		students.add(new Student("2", "Bhanu", "bhanu@gmail.com", new Department("ECE", "D-BLOCK"),
				new Subject("C++", "100")));
		students.add(new Student("3", "Hemanth", "hem@gmail.com", new Department("GEO", "G-BLOCK"),
				new Subject("Graphics", "20")));
		given(repo.findAllExcludeId()).willReturn(students);
		int studentCount = service.findAllStudents().size();
		assertThat(studentCount).isEqualTo(2);
	}

	@DisplayName("Test updating a Student by name and email")
	@Test
	void updatesStudentByNameAndEmailTest() {
		String studentName = "Sampath Atmuri";
		String departmentName = "CSE";
		given(repo.update(studentName, departmentName)).willReturn(1L);
		Long count = service.updateStudentByName(studentName, departmentName);
		assertThat(count).isEqualTo(1);
	}

	@DisplayName("Finding a Student based on Mongo Tempalte")
	@Test
	void findStudentByMongoTemplateTest() {
		List<Student> students = new ArrayList<>();
		students.add(new Student("2", "Bhanu", "bhanu@gmail.com", new Department("ECE", "D-BLOCK"),
				new Subject("C++", "100")));
		students.add(new Student("3", "Hemanth", "hem@gmail.com", new Department("GEO", "G-BLOCK"),
				new Subject("Graphics", "20")));
		String studentName = "Sampath Atmuri";
		String email = "sampathatmuri@gmail.com";
		String departmentName = "CSE";
		given(repo.findStudentByNameAndEmailAndDepartmentName(studentName, email, departmentName)).willReturn(students);
		int studentCount = service.findStudentByMongoTemplate(studentName, email, departmentName).size();
		assertThat(studentCount).isEqualTo(2);
	}

}
