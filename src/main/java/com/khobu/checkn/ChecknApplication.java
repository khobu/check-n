package com.khobu.checkn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@SpringBootApplication
public class ChecknApplication extends RepositoryRestConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(ChecknApplication.class, args);
	}

	/*@Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validatingListener.addValidator("beforeCreate", new PetValidator());
    }*/
}
