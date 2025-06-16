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

// http://localhost:8080/phonebook/PhonebookController

@WebServlet("/PhonebookController")
public class PhonebookController extends HttpServlet {
	//필드
	private static final long serialVersionUID = 1L;
    //생성자
    public PhonebookController() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("PhonebookController");
		
		//db데이터 가져온다 > list
		PhonebookDAO phonebookDAO = new PhonebookDAO();
		List<PersonVO> personList =  phonebookDAO.personSelect();
		
		//list.jsp에게 후반일 html을 만들고 응답문서 만들어 보낸다
		//1)request객체에 데이타를 넣는다
		request.setAttribute("pList", personList);
		
		//2)list.jsp에 request 객체와 response 객체를 보낸다(*포워드)
		RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
		rd.forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
