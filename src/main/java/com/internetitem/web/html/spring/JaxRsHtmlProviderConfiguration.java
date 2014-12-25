package com.internetitem.web.html.spring;

import com.internetitem.web.html.freemarker.FreemarkerRenderer;
import com.internetitem.web.html.JaxRsHtmlProvider;
import com.internetitem.web.html.Renderer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JaxRsHtmlProviderConfiguration {

    @Bean
    public JaxRsHtmlProvider jaxRsHtmlProvider(Renderer renderer) {
        return new JaxRsHtmlProvider(renderer);
    }

    @ConditionalOnMissingBean
    @Bean
    public Renderer freemarkerHtmlGenerator(freemarker.template.Configuration freemarkerConfig) {
        return new FreemarkerRenderer(freemarkerConfig);
    }

}
