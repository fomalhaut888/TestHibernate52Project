package test;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.entities.User;
import test.entities.User_;

/**
 * Servlet implementation class HibernateTest
 */
@WebServlet("/hibernateTest")
public class HibernateTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static EntityManagerFactory emf;
	
	static {
			emf = Persistence.createEntityManagerFactory( "HibernateTest" );
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HibernateTest() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			EntityManager manager = null;
			try{
					manager = emf.createEntityManager();
					CriteriaBuilder builder = manager.getCriteriaBuilder();
					CriteriaQuery<User> criteria = builder.createQuery( User.class );
					Root<User> root = criteria.from( User.class );
					criteria.select( root );
					criteria.where( builder.equal( root.get( User_.name ), "Simon Chen" ) );
					List<User> users = manager.createQuery(criteria).getResultList();
					StringBuffer html = new StringBuffer();
					for(User user: users){
							html.append("<p>");
							html.append("id=");
							html.append(user.getId());
							html.append("; employeeId=");
							html.append(user.getEmployeeId());
							html.append("; name=");
							html.append(user.getName());
							html.append("; status=");
							html.append(user.getStatus());
							html.append("; motherLanguage=");
							if(user.getMotherLanguage() != null){
									html.append(user.getMotherLanguage().getName());
							}else{
									html.append("Unknown");
							}
							html.append("</p>");
					}
					response.setContentType("text/html; charset=UTF-8");
					response.getWriter().append(html.toString());
			}catch(Exception e){
					e.printStackTrace();
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
