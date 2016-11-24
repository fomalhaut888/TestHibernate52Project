package test.web.init;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class MyWebApplicationInitializer implements WebApplicationInitializer {
	
	private static Logger logger = Logger.getLogger(MyWebApplicationInitializer.class);

	@Override
	public void onStartup(ServletContext container) throws ServletException {
			logger.info("MyWebApplicationInitializer onStartup!");
			XmlWebApplicationContext appContext = new XmlWebApplicationContext();
			appContext.setConfigLocation("/WEB-INF/spring/main-config.xml");
			
			ServletRegistration.Dynamic mainDispatcher = 
					container.addServlet("main", new DispatcherServlet(appContext));
			mainDispatcher.setLoadOnStartup(1);
			mainDispatcher.addMapping("/s/*");
			FilterRegistration charEncodingfilterReg = 
					container.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
			charEncodingfilterReg.setInitParameter("encoding", "UTF-8");
			charEncodingfilterReg.setInitParameter("forceEncoding", "true");
			charEncodingfilterReg.addMappingForUrlPatterns( null, true, "/*");
	}

}
