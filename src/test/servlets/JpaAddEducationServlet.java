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

import test.entities.Education;

/**
 * Servlet implementation class HibernateTestServlet
 */
@WebServlet("/jpaAddEducation")
public class JpaAddEducationServlet extends HttpServlet {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5354009274592979036L;
	
	private static EntityManagerFactory emf;
	
	static {
			emf = Persistence.createEntityManagerFactory("HibernateTest");//One of persistence-unit names in classpath://META-INF/persistence.xml
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JpaAddEducationServlet() {
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
					
					List<Education> list = manager
							.createQuery("from Education where code = :code and status = :status", Education.class)
							.setParameter("code", code)
							.setParameter("status", 'A')
							.getResultList();
					Education education = null;
					String html;
					if(list.size() > 0){//update name if code has existed.
							education = list.get(0);
							education.setName(name);
							
							html = "id=" + education.getId() + "; code=" + education.getCode()
									+ "; name=" + education.getName() + "; status=" + education.getStatus()
									+ "! 更新'教育選項' - OK!";
					}else{//new
							education = new Education();
							education.setCode(code);
							education.setName(name);
							education.setStatus('A');
							
							html = "id=" + education.getId() + "; code=" + education.getCode()
									+ "; name=" + education.getName() + "; status=" + education.getStatus()
									+ "! 新增'教育選項' - OK!";
					}
					
					manager.persist(education);
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
