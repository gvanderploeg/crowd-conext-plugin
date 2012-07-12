package org.surfnet.crowd;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormServlet extends HttpServlet {

  TemplateRenderer renderer;

  public FormServlet(TemplateRenderer renderer) {
    this.renderer = renderer;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    res.setContentType("text/html;charset=utf-8");
    renderer.render("form.vm", res.getWriter());
  }
}
