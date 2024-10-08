package com.xuanluan.mc.sdk.config;

import com.xuanluan.mc.sdk.service.i18n.MessageAssert;
import com.xuanluan.mc.sdk.service.i18n.MessageLocale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

public class MessageLocaleConfig implements WebMvcConfigurer {
    @Value("${language.default:vi}")
    private String language;
    @Value("${language.param:lang}")
    private String param;
    @Value("${language.messages:locales/messages,locales/errors}")
    private String[] messages;
    @Value("${language.encode:UTF-8}")
    private String encode;

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames(messages);
        source.setUseCodeAsDefaultMessage(true);
        source.setDefaultEncoding(encode);
        return source;
    }

    @Bean
    public MessageLocale messageLocale() {
        return new MessageLocale(messageSource());
    }

    @Bean
    public MessageAssert messageAssert() {
        return new MessageAssert(messageLocale());
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(new Locale(language));
        return sessionLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName(param);
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry ir) {
        ir.addInterceptor(localeChangeInterceptor());
    }
}
