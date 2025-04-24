package com.hunar.api.constant;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudConfig {

    @Value("${cloud.name}")
    private String name;

    @Value("${cloud.key}")
    private  String key;

    @Value("${cloud.secret}")
    private String secret;

    @Bean
    public Cloudinary cloudinary(){
        Cloudinary cloudinary = new Cloudinary();
        Map map = new HashMap();
        map.put("cloud_name", name);
        map.put("api_key", key);
        map.put("api_secret", secret);
        map.put("secure", true);
        return new Cloudinary(map);
    }
}
