package com.tobrainto.authenticatingldap.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/actuator", "/actuator/*","/logout").permitAll()
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 基于ou过滤
        /*auth.ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:8389/dc=springframework,dc=org")
                .and()
                .passwordCompare()
                // 指定密码的加解密方式
                //.passwordEncoder(new BCryptPasswordEncoder())
                // 页面请求的密码字段和 ldap 中的密码不一致时通过这里指定
                .passwordAttribute("userPassword");*/

        // 基于 group 过滤 （GroupOfUniqueNames ｜ groupOfNames）
        auth.ldapAuthentication()
                .userSearchBase("ou=people,dc=springframework,dc=org")
                .userSearchFilter("(&(uid={0})(memberOf=cn=developers,ou=groups,dc=springframework,dc=org))")
                .groupSearchBase("ou=groups,dc=springframework,dc=org")
                .groupSearchFilter("(uniqueMember={0})")
                .contextSource()
                .url("ldap://localhost:389")
                .and()
                .passwordCompare()
                // 指定密码的加解密方式
                //.passwordEncoder(new BCryptPasswordEncoder())
                // 页面请求的密码字段和 ldap 中的密码不一致时通过这里指定
                .passwordAttribute("userPassword");
    }



}