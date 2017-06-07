package test.servlets;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.entities.Language;
import test.entities.Language_;

/**
 * Servlet implementation class HibernateTestServlet
 */
@WebServlet("/jpaAddLanguage")
public class JpaAddLanguageServlet extends HttpServlet {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3342672069712280247L;
	
	private static EntityManagerFactory emf;
	
	static {
			emf = Persistence.createEntityManagerFactory("HibernateTest");//One of persistence-unit names in classpath://META-INF/persistence.xml
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JpaAddLanguageServlet() {
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
					
					CriteriaBuilder builder = manager.getCriteriaBuilder();
					CriteriaQuery<Language> criteria = builder.createQuery( Language.class );
					Root<Language> root = criteria.from( Language.class );
					criteria = criteria.select(root)
							.where(builder.and(
									builder.equal(root.get(Language_.code), code),
									builder.equal(root.get(Language_.status), 'A')));
					List<Language> list = manager.createQuery(criteria).getResultList();
					Language language = null;
					String html;
					if(list.size() > 0){//update name if code has existed.
							language = list.get(0);
							language.setName(name);
							
							html = "id=" + language.getId() + "; code=" + language.getCode()
									+ "; name=" + language.getName() + "; status=" + language.getStatus()
									+ "! 更新'語言選項' - OK!";
					}else{//new
							language = new Language();
							language.setCode(code);
							language.setName(name);
							language.setStatus('A');
							
							html = "id=" + language.getId() + "; code=" + language.getCode()
									+ "; name=" + language.getName() + "; status=" + language.getStatus()
									+ "! 新增'語言選項' - OK!";
					}
					
					manager.persist(language);
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
