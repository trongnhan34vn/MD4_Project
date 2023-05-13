package rikkei.academy.guitarplusclonejava.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import rikkei.academy.guitarplusclonejava.service.CatalogService.CatalogServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.IImageService.ImageServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.ProductService.ProductServiceIMPL;

import java.io.IOException;

@Configuration
@EnableWebMvc
@ComponentScan("rikkei.academy.guitarplusclonejava.controller")
@PropertySource("classpath:upload-file.properties")
public class AppConfig implements WebMvcConfigurer, ApplicationContextAware {
    @Value("${file-upload}")
    private String fileUpload;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //Cấu hình Thymleaf
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
        emailTemplateResolver.setPrefix("/WEB-INF/views");
        emailTemplateResolver.setSuffix(".html");
        emailTemplateResolver.setTemplateMode("HTML5");
        emailTemplateResolver.setCharacterEncoding("UTF_8");
        emailTemplateResolver.setOrder(1);
        return emailTemplateResolver;
    }

    //Cấu hình upload file
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + fileUpload)
                .setCachePeriod(999999999);

        registry.addResourceHandler("classpath:/css/**")
                .addResourceLocations("/assets/css/")
                .setCachePeriod(999999999);

        registry.addResourceHandler("classpath:vendor/**")
                .addResourceLocations("/assets/vendor/")
                .setCachePeriod(999999999);

//        registry.addResourceHandler("/js/**")
//                .addResourceLocations("/assets/lib/")
//                .addResourceLocations("/assets/js/")
//                .addResourceLocations("/assets/mail/")
//                .setCachePeriod(999999999);

    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getResolver() throws IOException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(52428800);
        return resolver;
    }

//    @Bean
//    public CatalogServiceIMPL catalogServiceIMPL() {
//        return new CatalogServiceIMPL();
//    }
//
//    @Bean
//    public ProductServiceIMPL productServiceIMPL() {
//        return new ProductServiceIMPL();
//    }
//
//    @Bean
//    public ImageServiceIMPL imageServiceIMPL() {
//        return new ImageServiceIMPL();
//    }
}
