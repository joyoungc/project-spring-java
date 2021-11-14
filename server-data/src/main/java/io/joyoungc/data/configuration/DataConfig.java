package io.joyoungc.data.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/***
 * Created by Aiden Jeong on 2021.09.22
 */
@Configuration
@EnableJpaAuditing(modifyOnCreate = false)
public class DataConfig {
}
