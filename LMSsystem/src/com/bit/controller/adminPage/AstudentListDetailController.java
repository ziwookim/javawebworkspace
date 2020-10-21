package com.bit.controller.adminPage;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.model.dao.ClassDao;
import com.bit.model.dao.StudentDao;
import com.bit.model.dto.ClassDto;
import com.bit.model.dto.StudentDto;

@WebServlet("/admin/AstudentListDetailController")
public class AstudentListDetailController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int studentIdx = Integer.parseInt(request.getParameter("studentIdx"));
		
		StudentDao studentDao;
		try {
			studentDao = new StudentDao();
			StudentDto studentDto= studentDao.selectOne(studentIdx);
			
			ClassDao classDao = new ClassDao();
			// ���������� �ش� classdto�� classTitle ������.
			ClassDto classDto = classDao.selectOne(studentDto.getClassIdx());
			request.setAttribute("classbean", classDto);
			request.setAttribute("studentbean", studentDto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		
		int studentIdx = Integer.parseInt(request.getParameter("studentIdx"));
		String memo = new String(request.getParameter("memo").getBytes("iso-8859-1"), "utf-8");
		StudentDao studentDao;
		try {
			studentDao = new StudentDao();
			//jsp���� ���� ���� ����, ����ó
			StudentDto studentDto=studentDao.selectOne(studentIdx);
			int result = studentDao.updateOne(studentDto.getStudentIdx(), studentDto.getPassword(), studentDto.getName(), 
								studentDto.getContact(), studentDto.getEmail(), studentDto.getClassIdx(), memo, studentDto.getDeleted());
			if(result>0){
				response.sendRedirect("astudentlist.bit");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}