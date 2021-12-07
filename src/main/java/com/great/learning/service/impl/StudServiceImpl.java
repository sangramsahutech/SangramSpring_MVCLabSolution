package com.great.learning.service.impl;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.great.learning.model.Student;
import com.great.learning.service.StudentService;

@Repository
public class StudServiceImpl implements StudentService {
	private SessionFactory sessionFactory;
	private Session session;
	
	@Autowired
	StudServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException ex) {
			session = sessionFactory.openSession();
		}
	}

	@Transactional
	public List<Student> findAll() {
		// TODO Auto-generated method stub
		List<Student> students = session.createQuery("from Student").list();
		return students;
	}

	@Transactional
	public Student findById(int studentId) {
//		Student myStudent = session.get(Student.class, studentId);
		Student myStudent = (Student) session.get(Student.class, studentId);
		return myStudent;
	}

	@Transactional
	public void save(Student myStudent) {
		// TODO Auto-generated method stub
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(myStudent);
		transaction.commit();
	}

	@Transactional
	public void deleteById(int studentId) {
		// TODO Auto-generated method stub
		Transaction transaction = session.beginTransaction();
		Student myStudent = (Student) session.get(Student.class, studentId);
		session.delete(myStudent);
		transaction.commit();
	}
	
	@Transactional
	public void print(List<Student> students) {
		for (Student student : students) {
			System.out.println(student);
		}
	}
}
