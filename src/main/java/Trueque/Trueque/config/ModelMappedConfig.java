package Trueque.Trueque.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;


@Configuration
public class ModelMappedConfig {
    @Bean
    public ModelMapper modelMapper (){
        return new ModelMapper();
    }

}
