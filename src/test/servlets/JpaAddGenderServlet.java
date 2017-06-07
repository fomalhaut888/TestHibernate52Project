package test.servlets;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.entities.Gender;

/**
 * Servlet implementation class HibernateTestServlet
 */
@WebServlet("/jpaAddGender")
public class JpaAddGenderServlet extends HttpServlet {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9146075530300929643L;
	
	private static EntityManagerFactory emf;
	
	static {
			emf = Persistence.createEntityManagerFactory("HibernateTest");//One of persistence-unit names in classpath://META-INF/persistence.xml
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JpaAddGenderServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			EntityManager manager = null;
			EntityTransaction tx = null;
			try{
					String code = request.getParameter("code");
					String name = request.getParameter("name");
				
					manager = emf.createEntityManager();
					tx = manager.getTransaction();
					tx.begin();
					
					List<Gender> list = manager
							.createQuery("from Gender where code = :code and status = :status", Gender.class)
							.setParameter("code", code)
							.setParameter("status", 'A')
							.getResultList();
					Gender gender = null;
					String html;
					if(list.size() > 0){//update name if code has existed.
							gender = list.get(0);
							gender.setName(name);
							
							html = "id=" + gender.getId() + "; code=" + gender.getCode()
									+ "; name=" + gender.getName() + "; status=" + gender.getStatus()
									+ "! 更新'性別選項' - OK!";
					}else{//new
							gender = new Gender();
							gender.setCode(code);
							gender.setName(name);
							gender.setStatus('A');
							
							html = "id=" + gender.getId() + "; code=" + gender.getCode()
									+ "; name=" + gender.getName() + "; status=" + gender.getStatus()
									+ "! 新增'性別選項' - OK!";
					}
					
					manager.persist(gender);
					tx.commit();
					
					response.setContentType("text/html; charset=UTF-8");
					response.getWriter().append(html);
			}catch(Exception e){
					e.printStackTrace();
					if(tx != null){
							tx.rollback();
					}
					throw e;
			}finally{
					if(manager != null){
							manager.close();
					}
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doGet(request, response);
	}

}
