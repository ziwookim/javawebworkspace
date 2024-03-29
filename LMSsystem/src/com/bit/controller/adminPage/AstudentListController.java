package com.bit.controller.adminPage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.model.dao.ClassDao;
import com.bit.model.dao.StudentDao;
import com.bit.model.dto.ClassDto;
import com.bit.model.dto.StudentDto;

@WebServlet("/admin/astudentlist.bit")
public class AstudentListController extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String temp1 = req.getParameter("search1");
		String temp2 = req.getParameter("search2");
		
		String search1 = "";
		String search2 = "";
		if(temp1 != "" && temp1 != null) {
			search1 = " and student.classIdx = " + temp1; // selectbox 반 선택 값
			
		}
		if(temp2 != "" && temp2 != null) {
			search2 = " and classTitle like '%" + temp2 + "%' or name like '%" + temp2 + "%' "; // 검색 값
		}
			try {
				StudentDao dao = new StudentDao();
				ArrayList<StudentDto> list = dao.selectJoinAll(search1, search2);
				
				ClassDao classdao = new ClassDao();
				ArrayList<ClassDto> classList = classdao.selectAll();
				req.setAttribute("list", list);
				req.setAttribute("classList", classList);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		req.getRequestDispatcher("../astudentlist.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		
	}

}
