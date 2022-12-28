package Controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import DAO.KcalDAO;
import DTO.FoodKcal;
import DTO.FoodRecode;
import DTO.Member;

@WebServlet("/")
public class KcalController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	KcalDAO dao;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = new KcalDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doPro(request, response);
	}
	
	protected void doPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getServletPath();
		String site = null;
		
		switch(command) {
		case "/home" :
			site = "index.jsp";
			break;
		case "/list" :
			site = getList(request);
			break;
		case "/alonelist" :
			site = getAloneList(request);
			break;
		case "/delete" :
			site = deleteFood(request);
			break;
		case "/write" :
			site = getFood(request);
			break;
		case "/insert" :
			site = insertFood(request);
			break;
		case "/edit" :
			site = geteditList(request);
			break;
		case "/result" :
			site = "result.jsp";
			break;
		}
		
		if (site.startsWith("redirect:/")) {		// startsWith : redirect 문자열로 시작하는 것을 찾음
			String rview = site.substring("redirect:/".length());
			System.out.println(rview);
			response.sendRedirect(rview);
		} else {	
		getServletContext().getRequestDispatcher("/" + site)
		.forward(request, response);
		}
	}
	
	public String getList(HttpServletRequest request) {
		ArrayList<Member> list = new ArrayList<>();
		
		try {
			list = dao.getList();
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			getServletContext().log("회원 목록 생성 과정에서 문제 발생");
			request.setAttribute("error", "회원 목록이 정상적으로 처리되지 않았습니다!");
		}
		
		return "list.jsp";
	}
	
	public String getFood(HttpServletRequest request) {
		ArrayList<FoodKcal> foodList = new ArrayList<>();
		
		try {
			foodList = dao.getFood();
			request.setAttribute("foodList", foodList);
		} catch (Exception e) {
			e.printStackTrace();
			getServletContext().log("food 생성 과정에서 문제 발생");
			request.setAttribute("error", "food 목록이 정상적으로 처리되지 않았습니다!");
		}
		
		return "write.jsp";
	}
	
	public String getAloneList(HttpServletRequest request) {
		int member_no = Integer.parseInt(request.getParameter("member_no"));
		ArrayList<FoodRecode> list = new ArrayList<>();
		
		try {
			list = dao.getAloneList(member_no);
			request.setAttribute("list", list);
			request.setAttribute("member_no", member_no);
		} catch (Exception e) {
			e.printStackTrace();
			getServletContext().log("회원식단 목록 생성 과정에서 문제 발생");
			request.setAttribute("error", "회원식단 목록이 정상적으로 처리되지 않았습니다!");
		}
		
		return "alonelist.jsp";
	}
	
	public String deleteFood(HttpServletRequest request) {
		int food_no = Integer.parseInt(request.getParameter("food_no"));
		int member_no = Integer.parseInt(request.getParameter("member_no"));
		
		try {
			dao.deleteFood(food_no);
		} catch (Exception e) {
			e.printStackTrace();
			getServletContext().log("회원식단 목록 삭제 과정에서 문제 발생");
			
			try {
			String encodeName = URLEncoder.encode("회원식단이 정상적으로 삭제되지 않았습니다!", "UTF-8");
			return "redirect:/redirect:/alonelist?member_no=" + member_no + "error=" + encodeName;
			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		}
		
		return "redirect:/alonelist?member_no=" + member_no;
//		return "redirect:/list";
	}
	
	public String insertFood(HttpServletRequest request) {
		FoodRecode f = new FoodRecode();
		try {
			BeanUtils.populate(f, request.getParameterMap());
			dao.insertFood(f);
			
		} catch (Exception e) {
			e.printStackTrace();
			getServletContext().log("음식추가 등록과정에서 문제 발생");
			
			try {
				String encodeName = URLEncoder.encode("음식추가가 정상적으로 등록되지 않았습니다!", "UTF-8");
				return "redirect:/redirect:/alonelist?member_no=" + f.getMember_no() + "error=" + encodeName;
				
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		
		return "redirect:/alonelist?member_no=" + f.getMember_no();
//		return "redirect:/home";
	}
	
	public String geteditList(HttpServletRequest request) {
		String food_no = request.getParameter("food_no");
		ArrayList<FoodKcal> foodList = new ArrayList<>();
		FoodRecode list = new FoodRecode();
		
		try {
			list = dao.getEditList(food_no);
			foodList = dao.getFood();
			request.setAttribute("foodList", foodList);
			request.setAttribute("list", list);
			
		} catch (Exception e) {
			e.printStackTrace();
			getServletContext().log("회원식단 수정 목록 생성 과정에서 문제 발생");
			request.setAttribute("error", "회원식단 수정 목록이 정상적으로 처리되지 않았습니다!");
		}
		
		return "edit.jsp";
	}

}
