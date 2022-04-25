package com.mongo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongo.documents.Department;
import com.mongo.documents.Student;
import com.mongo.documents.Subject;
import com.mongo.dto.DepartmentDto;
import com.mongo.dto.StudentDto;
import com.mongo.dto.SubjectDto;
import com.mongo.service.StudentService;

@RunWith(SpringRunner.class)
@ComponentScan(basePackages = "com.mongo")
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService service;

	@Autowired
	private ObjectMapper mapper;

	private StudentDto studentDto;

	@BeforeEach
	void setup() {
		studentDto = StudentDto.builder().id("1").name("Sampath Atmuri").email("sam@gmail.com")
				.department(new DepartmentDto("CSE", "A-BLOCK")).subject(new SubjectDto("JAVA", "100")).build();

	}

	@DisplayName("Testing save Student controller")
	@Test
	void saveStudentTest() throws Exception {

		mockMvc.perform(
				post("/save").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(studentDto)))
				.andExpect(status().isOk());

		Mockito.verify(service, times(1)).saveStudent(any(Student.class));
	}

	@DisplayName("Testing save list of Students controller")
	@Test
	void saveStudentsTest() throws Exception {

		List<StudentDto> students = new ArrayList<>();
		students.add(new StudentDto("2", "Bhanu", "bhanu@gmail.com", new DepartmentDto("ECE", "D-BLOCK"),
				new SubjectDto("C++", "100")));
		students.add(new StudentDto("3", "Hemanth", "hem@gmail.com", new DepartmentDto("GEO", "G-BLOCK"),
				new SubjectDto("Graphics", "20")));

		mockMvc.perform(
				post("/saveAll").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(students)))
				.andExpect(status().isOk());
	}

	@DisplayName("Testing Deleting All Students controller")
	@Test
	void deleteAllStudentsTest() throws Exception {

		mockMvc.perform(delete("/delete").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@DisplayName("Testing Deleting A Student by name controller")
	@Test
	void deleteStudentByNameTest() throws Exception {

		BDDMockito.given(service.deleteStudentByName("Sampath Atmuri", "CSE")).willReturn("SUCCESS");

		ResultActions response = mockMvc
				.perform(delete("/delete/Sampath Atmuri/CSE").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		assertEquals("SUCCESS", response.andReturn().getResponse().getContentAsString());
	}

	@DisplayName("Testing Deleting A Student by name controller negative case")
	@Test
	void deleteStudentByNameNegativeTest() throws Exception {

		BDDMockito.given(service.deleteStudentByName("Sampath Atmuri", "CSE")).willReturn("FAILURE");

		ResultActions response = mockMvc
				.perform(delete("/delete/Sampath Atmuri/CSE").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		assertEquals("FAILURE", response.andReturn().getResponse().getContentAsString());
	}

	@DisplayName("Testing Finding A Student by name controller")
	@Test
	void findByNameTest() throws Exception {

		Student studentRes = Student.builder().id("1").name("Sampath Atmuri").email("sam@gmail.com")
				.department(new Department("CSE", "A-BLOCK")).subject(new Subject("JAVA", "100")).build();

		BDDMockito.given(service.findByStudentName("Sampath Atmuri")).willReturn(studentRes);

		mockMvc.perform(get("/findByName/Sampath Atmuri").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		Mockito.verify(service, times(1)).findByStudentName("Sampath Atmuri");
	}

	@DisplayName("Testing Update Student controller")
	@Test
	void updateStudentTest() throws Exception {

		Student studentRes = Student.builder().id("1").name("Sampath Atmuri").email("sam@gmail.com")
				.department(new Department("CSE", "A-BLOCK")).subject(new Subject("JAVA", "100")).build();

		BDDMockito.given(service.upateStudent(studentRes)).willReturn(studentRes);

		mockMvc.perform(
				put("/update").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(studentDto)))
				.andExpect(status().isOk());

		Mockito.verify(service, times(1)).upateStudent(any(Student.class));
	}

	@DisplayName("Testing Find All Student controller")
	@Test
	void findAllTest() throws Exception {

		List<Student> students = new ArrayList<>();
		students.add(new Student("2", "Bhanu", "bhanu@gmail.com", new Department("ECE", "D-BLOCK"),
				new Subject("C++", "100")));
		students.add(new Student("3", "Hemanth", "hem@gmail.com", new Department("GEO", "G-BLOCK"),
				new Subject("Graphics", "20")));

		BDDMockito.given(service.findAllStudents()).willReturn(students);

		mockMvc.perform(get("/findAll").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		Mockito.verify(service, times(1)).findAllStudents();
	}

	@DisplayName("Testing Update Student By Name controller")
	@Test
	void updateStudentByNameTest() throws Exception {

		BDDMockito.given(service.updateStudentByName("Sampath Atmuri", "sampathatmuri@gmail.com")).willReturn(1L);

		mockMvc.perform(
				put("/updateByName/Sampath Atmuri/sampathatmuri@gmail.com").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		Mockito.verify(service, times(1)).updateStudentByName("Sampath Atmuri", "sampathatmuri@gmail.com");
	}

	@DisplayName("Testing Find by name, email and department name controller")
	@Test
	void findByNameAndEmailAndDepartmentNameTest() throws Exception {
		
		List<Student> students = new ArrayList<>();
		students.add(new Student("2", "Bhanu", "bhanu@gmail.com", new Department("ECE", "D-BLOCK"),
				new Subject("C++", "100")));
		students.add(new Student("3", "Hemanth", "hem@gmail.com", new Department("GEO", "G-BLOCK"),
				new Subject("Graphics", "20")));

		BDDMockito.given(service.findStudentByMongoTemplate("Sampath Atmuri", "sampathatmuri@gmail.com","CSE")).willReturn(students);

		mockMvc.perform(
				get("/find/Sampath Atmuri/sampathatmuri@gmail.com/CSE").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		Mockito.verify(service, times(1)).findStudentByMongoTemplate("Sampath Atmuri","sampathatmuri@gmail.com","CSE");
	}

}
