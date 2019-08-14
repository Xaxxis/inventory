package id.xaxxis.inventory.security;


import id.xaxxis.inventory.service.security.CustomAccessDeniedHandler;
import id.xaxxis.inventory.service.security.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.security.SecureRandom;

@EnableWebSecurity
public class MultipleSecurityConfig {

    @Autowired
    private UserSecurityService userSecurityService;

    private static final String SALT = "ThisSALTisVErySecreetForM4nd4putrnu5ant412a";

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
    }

    private static final String[] PUBLIC_MATCHERS = {
            "/webjars/**",
            "/assets/**",
            "/",
            "/error/**/*",
            "/login",
            "/v3/getaccount/**/*",
    };

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .anyRequest()
                    .hasRole("API")
                    .and()
                    .httpBasic();
        }
    }

    @Configuration
    public static class LoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private CustomAccessDeniedHandler accessDeniedHandler;

        @Autowired
        private DataSource dataSource;

        @Bean
        public PersistentTokenRepository persistentTokenRepository(){
            JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
            tokenRepositoryImpl.setDataSource(dataSource);
            return tokenRepositoryImpl;
        }

        @Bean
        public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
            return new UrlAuthenticationSuccessHandler();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers(PUBLIC_MATCHERS).permitAll()
                    .antMatchers("/app/user/change-password/**").access("hasAnyRole('ADMIN','USER')")
                    .antMatchers("/app/user/profile").access("hasAnyRole('ADMIN','USER')")
                    .antMatchers("/app/inventory/list").access("permitAll()")
                    .antMatchers("/app/inv/warehouse").access("permitAll()")
                    .antMatchers("/app/inv/**").access("hasAnyRole('ADMIN','GUDANG')")
                    .antMatchers("/app/purchasing/request/**").access("hasAnyRole('ADMIN','GUDANG','SO','PURCHASING','KASIR')")
                    .antMatchers("/app/purchasing/**").access("hasAnyRole('ADMIN','GUDANG','PURCHASING')")
                    .antMatchers("/app/so/**").access("hasAnyRole('ADMIN','SO')")
                    .antMatchers("/app/**").access("hasRole('ADMIN')")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
                    .usernameParameter("username").passwordParameter("password")
                    .and()
                    .rememberMe().rememberMeParameter("remember-me").tokenRepository(persistentTokenRepository())
                    .and()
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout").permitAll()
                    .and()
                    .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
//            http
//                    .sessionManagement()
//                    .maximumSessions(1)
//                    .expiredUrl("/login?expired")
//                    .maxSessionsPreventsLogin(true)
//                    .sessionRegistry(sessionRegistry());
        }

    }

    @Bean
    static SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher(){
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }
}
