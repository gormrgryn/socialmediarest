package com.gor.socialmediarest;

import com.gor.socialmediarest.testutils.PostFactory;
import com.gor.socialmediarest.testutils.UserFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public PostFactory postFactory() {
        return new PostFactory();
    }
    
    @Bean
    public UserFactory userFactory() {
        return new UserFactory();
    }
}
