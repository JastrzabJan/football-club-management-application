package pjatk.edu.pl.footballclubmanagementapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pjatk.edu.pl.footballclubmanagementapplication.backend.data.entity.User;
import pjatk.edu.pl.footballclubmanagementapplication.backend.repository.UserRepository;
import pjatk.edu.pl.footballclubmanagementapplication.backend.service.UserService;
import pjatk.edu.pl.footballclubmanagementapplication.security.SecurityConfiguration;
import pjatk.edu.pl.footballclubmanagementapplication.ui.MainView;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication(scanBasePackageClasses = {SecurityConfiguration.class, MainView.class, FootballClubManagementApplication.class,
        UserService.class}, exclude = ErrorMvcAutoConfiguration.class)
@EntityScan(basePackageClasses = {User.class})
@EnableJpaRepositories(basePackageClasses = {UserRepository.class})
public class FootballClubManagementApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FootballClubManagementApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FootballClubManagementApplication.class);
    }

}
