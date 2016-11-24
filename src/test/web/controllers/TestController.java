package test.web.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import test.daos.TestAccess;

@Controller
public class TestController extends AbstractController {
	
		@Autowired
		private TestAccess testAccess;
		
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list(){
				ModelAndView mav = new ModelAndView();
				
				List<Map<String, Object>> users = testAccess.findAllUsers();
				logger.debug("users=" + users);
			
				mav.setViewName("list");
				mav.addObject("users", users);
				return mav;
		}
		
		@RequestMapping(value = "/add", method = RequestMethod.GET)
		public ModelAndView add(){
				ModelAndView mav = new ModelAndView();
				
				List<Map<String, Object>> languages = testAccess.findAllLanguages();
				logger.debug("languages=" + languages);
				
				mav.setViewName("add");
				mav.addObject("languages", languages);
				return mav;
		}
		
		@RequestMapping(value = "/addToDb", method = RequestMethod.POST)
		public void addToDb(
				@RequestParam(value="employeeId")String employeeId,
				@RequestParam(value="name")String name,
				@RequestParam(value="motherLanguage")long motherLanguageId,
				HttpServletRequest request, 
				HttpServletResponse response) 
						throws Exception{
				
				testAccess.addUser(employeeId, name, 'A', motherLanguageId);
				
				response.sendRedirect(request.getContextPath() + "/s/list");
		}
}
