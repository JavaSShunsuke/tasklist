package jp.gihyo.projava.tasklist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = HomeController.class)
public class TasListApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasListApplication.class, args);
	}

}
