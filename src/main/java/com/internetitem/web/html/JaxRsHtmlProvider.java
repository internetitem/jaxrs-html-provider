package com.internetitem.web.html;

import com.internetitem.web.html.freemarker.FreemarkerRenderer;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Produces(MediaType.TEXT_HTML)
@Provider
public class JaxRsHtmlProvider implements MessageBodyWriter<Object> {

	private Renderer renderer;

	public JaxRsHtmlProvider() {
		this.renderer = new FreemarkerRenderer();
	}

	public JaxRsHtmlProvider(Renderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		if (TemplateAware.class.isAssignableFrom(type)) {
			return true;
		}
		if (type.getAnnotation(TemplateName.class) != null) {
			return true;
		}
		for (Annotation a : annotations) {
			if (a.annotationType() == TemplateName.class) {
				return true;
			}
		}
		return false;
	}

	@Override
	public long getSize(Object o, Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(Object o, Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
		String templateName = null;
		OUTER:
		if (o instanceof TemplateAware) {
			templateName = ((TemplateAware) o).getTemplateName();
		} else {
			for (Annotation a : annotations) {
				if (a.annotationType() == TemplateName.class) {
					templateName = ((TemplateName) a).name();
					break OUTER;
				}
			}
			TemplateName tnAnnotation = o.getClass().getAnnotation(TemplateName.class);
			if (tnAnnotation != null) {
				templateName = tnAnnotation.name();
			}
		}
		if (templateName == null) {
			throw new NotFoundException("Template not defined");
		}

		renderer.render(templateName, o, entityStream);
	}
}
