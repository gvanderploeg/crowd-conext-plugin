package org.surfnet.crowd;

import java.io.IOException;
import java.net.URI;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atlassian.sal.api.user.UserManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Makes sure that users are authorized, otherwise redirect to login url.
 *
 * @author Geert van der Ploeg
 */
public class AuthorizationFilter implements Filter {

  private final static Logger LOG = LoggerFactory.getLogger(AuthorizationFilter.class);

  private UserManager userManager;

  public AuthorizationFilter(UserManager userManager) {
    this.userManager = userManager;
  }

  private String redirectUri = null;
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    redirectUri = filterConfig.getInitParameter("redirectUri");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    String username = userManager.getRemoteUsername((HttpServletRequest) request);
    if (username == null || !userManager.isSystemAdmin(username)) {
      LOG.info("Will redirect user ({}) to login url, not logged in or not system admin.", username);
      redirectToLogin((HttpServletRequest) request, (HttpServletResponse) response);
    }
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
  }

  private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // LoginUriProvider is not implemented in Crowd yet. Will redirect to root.
    response.sendRedirect(redirectUri);
  }

  private URI getUri(HttpServletRequest request) {
    StringBuffer builder = request.getRequestURL();
    if (request.getQueryString() != null) {
      builder.append("?");
      builder.append(request.getQueryString());
    }
    return URI.create(builder.toString());
  }

}
