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

import test.entities.Language;
import test.entities.User;

/**
 * Servlet implementation class HibernateTestServlet
 */
@WebServlet("/jpaAddTest")
public class JpaAddTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static EntityManagerFactory emf;
	
	static {
			emf = Persistence.createEntityManagerFactory( "HibernateTest" );
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JpaAddTestServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			EntityManager manager = null;
			EntityTransaction tx = null;
			try{
					manager = emf.createEntityManager();
					tx = manager.getTransaction();
					tx.begin();
					double r = Math.random();
					String motherLangCode;
					if(r > 0.5d){
							motherLangCode = "zh";
					}else{
							motherLangCode = "en";
					}
					List<Language> list = manager
							.createQuery("from Language where code = :code and status = :status", Language.class)
							.setParameter("code", motherLangCode)
							.setParameter("status", 'A')
							.getResultList();
					Language motherLanguage = null;
					if(list.size() > 0){
							motherLanguage = list.get(0);
					}
					if(motherLanguage == null){
							motherLanguage = new Language(motherLangCode, 
									(motherLangCode.equals("zh")?"中文": "英文"),
									'A');
							manager.persist(motherLanguage);
					}
					User user = new User("leoA00000", "陳先生", 'A', motherLanguage);
					manager.persist(user);
					tx.commit();
					
					response.setContentType("text/html; charset=UTF-8");
					response.getWriter().append("id=" + user.getId() + "; employeeId=" + user.getEmployeeId()
							+ "; name=" + user.getName() + "; status=" + user.getStatus()
							+ "; mother language=" + user.getMotherLanguage().getName() + "! 新增OK!");
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
