package com.mongo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongo.documents.Student;

public class StudentCustomRepositoryImpl implements StudentCustomRepository {

	@Autowired
	private MongoTemplate template;

	public StudentCustomRepositoryImpl(MongoTemplate template) {
		this.template = template;
	}

	private static final String EMAIL = "email";

	@Override
	public long update(String name, String email) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		Update update = new Update();
		update.set(EMAIL, email);
		return template.updateFirst(query, update, Student.class).getModifiedCount();
	}

	public List<Student> findStudentByNameAndEmailAndDepartmentName(String name, String email, String departmentName) {
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(Criteria.where("name").is(name), Criteria.where(EMAIL).is(email),
				Criteria.where("department.departmentName").is(departmentName)));
		query.fields().include("name", "department.departmentName", EMAIL, "subject.subjectName").exclude("id");
		return template.find(query, Student.class);
	}
}
