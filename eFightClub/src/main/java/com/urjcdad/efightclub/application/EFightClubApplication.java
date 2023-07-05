package com.urjcdad.efightclub.application;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

@SpringBootApplication
@EnableHazelcastHttpSession
@EnableCaching
public class EFightClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(EFightClubApplication.class, args);
	}
	
    @Bean
    public Config config() {
        Config config = new Config();
        JoinConfig joinConfig = config.getNetworkConfig().getJoin();
        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(true).setMembers(Arrays.asList("efightclub-web-1", "efightclub-web-2"));
        return config;
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("notificaciones");
    }
}