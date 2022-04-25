package com.mongo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {

	private String id;
	private String name;
	private String email;
	private DepartmentDto department;
	private SubjectDto subject;
}
