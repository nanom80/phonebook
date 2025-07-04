package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhonebookDAO;
import com.javaex.vo.PersonVO;

//http://localhost:8080/phonebook/PhonebookController
//http://localhost:8080/phonebook/pbc?action=list
//http://localhost:8080/phonebook/pbc?action=wform
//http://localhost:8080/phonebook/pbc?action=delete&pId=3

//http://localhost:8080/phonebook/pbc?action=mform&pId=3
//http://localhost:8080/phonebook/pbc?action=modify

@WebServlet("/pbc")
public class PhonebookController extends HttpServlet {
	//필드
	private static final long serialVersionUID = 1L;
    //생성자
    public PhonebookController() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("PhonebookController");
		
		String action = request.getParameter("action");
		
		System.out.println("action="+action);
		
		if("list".equals(action)) {
			System.out.println("!리스트!");
			
			PhonebookDAO phonebookDAO = new PhonebookDAO();
			List<PersonVO> personList =  phonebookDAO.personSelect();
			
			//list.jsp에게 후반일 html을 만들고 응답문서 만들어 보낸다
			//1)request객체에 데이타를 넣는다
			request.setAttribute("pList", personList);
			
			//2)list.jsp에 request 객체와 response 객체를 보낸다(*포워드)
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
		
		}else if("wform".equals(action)) {
			System.out.println("!등록폼!");
			
			//등록폼을 응답해야한다
			//db관련 할 일이 없다
			
			//jsp에게 화면을 그리게 한다(포워드)
			//writeForm.jsp 포워드
			RequestDispatcher rd = request.getRequestDispatcher("/writeForm.jsp");
			rd.forward(request, response);
			
		}else if("write".equals(action)) {
			System.out.println("!등록!");
			
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			//데이터를 묶는다
			PersonVO personVO = new PersonVO(name, hp, company);
			System.out.println(personVO);
			
			//DAO를 통해서 저장시키기
			PhonebookDAO phonebookDAO = new PhonebookDAO();
			phonebookDAO.personInsert(personVO);
			
			//리다이렉트
			System.out.println("!리다이렉트!");
			response.sendRedirect("http://192.168.0.99:8080/phonebook/pbc?action=list");
			
			/*
			//응답(리스트)
			List<PersonVO> personList = phonebookDAO.personSelect();
			
			//request의 어트리뷰트 영역에 데이타 넣기
			//1)request객체에 데이타를 넣는다
			request.setAttribute("pList", personList);
			
			//2)list.jsp에 request 객체와 response 객체를 보낸다(*포워드)
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
			*/
			
		}else if("delete".equals(action)) {
			System.out.println("!삭제!");
			
			int pId = Integer.parseInt(request.getParameter("pId"));
			
			PhonebookDAO phonebookDAO = new PhonebookDAO();
			phonebookDAO.personDelete(pId);
			
			//리다이렉트
			System.out.println("!리다이렉트!");
			response.sendRedirect("http://192.168.0.99:8080/phonebook/pbc?action=list");
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
