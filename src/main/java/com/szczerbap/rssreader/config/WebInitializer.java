package com.szczerbap.rssreader.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/*
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

        @Override
        protected Class<?>[] getRootConfigClasses() {
            //return new Class[] {
                    return null;//RootConfig.class,SecurityConfig.class
            //};
        }

        @Override
        protected Class<?>[] getServletConfigClasses() {
            return new Class[] { WebConfig.class };
        }

        @Override
        protected String[] getServletMappings() {
            return new String[] { "/" };
        }


}
*/

public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletCxt) {

        AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
        dispatcherServlet.register(WebConfig.class);

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootConfig.class);
        rootContext.register(SecurityConfig.class);
        servletCxt.addListener(new ContextLoaderListener(rootContext));


        ServletRegistration.Dynamic dispatcher = servletCxt.addServlet("dispatcher", new DispatcherServlet(dispatcherServlet));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

}
