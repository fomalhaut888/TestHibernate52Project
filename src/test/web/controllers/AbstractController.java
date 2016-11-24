package test.web.controllers;

import org.apache.log4j.Logger;

public abstract class AbstractController {
		
		protected Logger logger;
		
		public AbstractController(){
				System.out.println("" + logger);
				System.out.println("" + this.getClass());
				logger = Logger.getLogger(this.getClass());
		}
}
