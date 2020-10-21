package com.bit.controller.home;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit.model.dao.RecruitmentBoardDao;
import com.bit.model.dao.ResumeDao;
import com.bit.model.dto.RecruitmentBoardDto;
import com.bit.model.dto.ResumeDto;
import com.bit.model.dto.StudentDto;

@WebServlet("/recruitdetail.bit")
public class RecruitDetailController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int recruitmentboardIdx = Integer.parseInt(req.getParameter("boardIdx"));
//		System.out.println(recruitmentboardIdx);
		RecruitmentBoardDao dao = new RecruitmentBoardDao();
		RecruitmentBoardDto bean = dao.selectOne(recruitmentboardIdx);
		req.setAttribute("bean", bean);

		req.getRequestDispatcher("recruitdetail.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int recruitmentboardIdx = Integer.parseInt(req.getParameter("boardIdx"));
		// �α��� �˿� (�����ϱ�)
		HttpSession session = req.getSession();
		StudentDto student = (StudentDto)session.getAttribute("stuBean");
		int studentIdx = 0;
		if(student != null) {
			studentIdx = student.getStudentIdx();
		}
		Object admin = session.getAttribute("empBean");
		if(student != null) {
			ResumeDao resumedao = new ResumeDao();
			ArrayList<ResumeDto> resumeList = (ArrayList<ResumeDto>)resumedao.getResumeList(studentIdx);
			if(resumeList.size() <= 0 || resumeList == null) { // �����ϱ�
				// �̷¼� ���� ��� // �̷¼� �ۼ� ���������� ��ũ ÷��
				resp.setContentType("text/html; charset=UTF-8");
				PrintWriter out = resp.getWriter();
				out.println("<script>alert('����� �̷¼��� �������� �ʽ��ϴ�.\\n�̷¼� �ۼ��������� �̵��մϴ�.');location.href='myResume.bit?studentIdx=" + studentIdx + "'</script>");
				out.flush();
			}else { // �̷¼� �ִ� ���, // �̷¼� ��� �����ϱ�
				resp.setContentType("text/html; charset=UTF-8");
				PrintWriter out = resp.getWriter();
				out.println("<script>location.href='recruitselectresume.bit?boardIdx=" + recruitmentboardIdx + "'</script>");
				out.flush();
				return;
			}
		} else if(admin != null) {
			// ������ ȸ��
			//����� �� ���� �޴� �˸�
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<script>alert('�����ڴ� ����� �� ���� ����Դϴ�.');location.href='recruitdetail.bit?boardIdx=" + recruitmentboardIdx + "'</script>");
			out.flush();
			return;
		} else{
//			System.out.println("�α��� ��û");
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<script>alert('�α����� �ʿ��� �޴��Դϴ�.');location.href='recruitdetail.bit?boardIdx=" + recruitmentboardIdx + "'</script>");
			out.flush();		
			return;
		}
	}
}