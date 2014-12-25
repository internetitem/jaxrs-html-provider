package com.internetitem.web.html.freemarker;

import com.internetitem.web.html.Renderer;
import freemarker.log.Logger;
import freemarker.template.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FreemarkerRenderer implements Renderer {

    private Configuration freemarkerConfig;

    public FreemarkerRenderer() {
        this.freemarkerConfig = defaultFreemarkerConfig();
    }

    public FreemarkerRenderer(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    public String render(String templateName, Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        render(templateName, obj, baos);
        return baos.toString("UTF-8");
    }

    public void render(String templateName, Object obj, OutputStream out) throws IOException {
        Template template = freemarkerConfig.getTemplate(templateName);
        try {
            template.process(obj, new OutputStreamWriter(out, "UTF-8"));
        } catch (TemplateException e) {
            throw new IOException("Error processing templateName [" + templateName + "]: " + e.getMessage(), e);
        }
    }

    public static Configuration defaultFreemarkerConfig() {
        Version freemarkerVersion = new Version(2, 3, 21);
        try {
            Logger.selectLoggerLibrary(Logger.LIBRARY_SLF4J);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error setting up logging library: " + e.getMessage(), e);
        }
        freemarker.template.Configuration fmConfig = new freemarker.template.Configuration(freemarkerVersion);
        fmConfig.setClassForTemplateLoading(FreemarkerRenderer.class, "/templates");
        fmConfig.setObjectWrapper(new DefaultObjectWrapperBuilder(freemarkerVersion).build());
        fmConfig.setDefaultEncoding("UTF-8");
        fmConfig.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        return fmConfig;
    }
}
