package tech.lucidrohit.todo_app.Configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    
    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
