package com.mongo.validations;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mongo.dto.StudentDto;

@Component("StudentValidations")
public class StudentValidations {

	private final Logger log = LoggerFactory.getLogger(StudentValidations.class);
	public Boolean error = false;
	public String errorMessage = "";

	public void validatePayLoad() {
		if (this.error) {
			throw new StudentInputDataException(this.errorMessage);
		}
	}

	public void validateSaveStudent(@Valid StudentDto student) {
		validateStudentDetails(student);
		validateDepartmentDetails(student);
		validateSubjectDetails(student);
		validatePayLoad();
	}

	public void validateStudentDetails(StudentDto student) {
		if (student.getName().isBlank()) {
			log.info("Student Name should not be null ");
			this.error = true;
			this.errorMessage += " Student Name should not be null";
		}

		if (student.getEmail().isBlank()) {
			log.info("Student Email should not be null ");
			this.error = true;
			this.errorMessage += ", Student email should not be null";
		}
	}

	public void validateDepartmentDetails(StudentDto student) {
		if (student.getDepartment().getDepartmentName().isBlank()) {
			log.info("Department Name should not be null ");
			this.error = true;
			this.errorMessage += " Department Name should not be null";
		}

		if (student.getDepartment().getLocation().isBlank()) {
			log.info("Department Location should not be null ");
			this.error = true;
			this.errorMessage += ", Department Location should not be null";
		}
	}

	public void validateSubjectDetails(StudentDto student) {
		if (student.getSubject().getSubjectName().isBlank()) {
			log.info("Subject Name should not be null ");
			this.error = true;
			this.errorMessage += " Subject Name should not be null";
		}

		if (student.getSubject().getMarksObtained().isBlank()) {
			log.info("Department Location should not be null ");
			this.error = true;
			this.errorMessage += ", Department Location should not be null";
		}
	}
	
	public void validateListOfStudents(List<StudentDto> student) {
	
		for(StudentDto res : student) {
			validateStudentDetails(res);
			validateDepartmentDetails(res);
			validateSubjectDetails(res);
		}
		validatePayLoad();
	}
	

}
