package com.internetitem.web.html;

import java.io.IOException;
import java.io.OutputStream;

public interface Renderer {

	String render(String templateName, Object obj) throws IOException;

	void render(String templateName, Object obj, OutputStream out) throws IOException;
}
