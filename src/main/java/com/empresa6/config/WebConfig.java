package com.empresa6.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.http.MediaType;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("js", MediaType.valueOf("application/javascript"));
    }

    // Configuración para el uso de JSP
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/"); // Ubicación de tus archivos JSP
        resolver.setSuffix(".jsp"); // Extensión de tus vistas JSP
        registry.viewResolver(resolver);
    }

    // Configuración para servir recursos estáticos como CSS, JS, etc.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	 registry.addResourceHandler("/js/**")
         .addResourceLocations("classpath:/static/js/")
         .setCachePeriod(3600) // Tiempo de caché, en segundos
         .resourceChain(true);
    }
}

