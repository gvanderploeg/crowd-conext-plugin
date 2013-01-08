package org.surfnet.crowd;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class RestAuthenticationFilter implements Filter {

  private static final String USER = "inherlutq8228ojoivhjmknbh";
  private static final String PASS = "noemeruifhpoi8899unhfvi";

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

    String username = authenticate((HttpServletRequest) req);
    if (username == null) {
      ((HttpServletResponse) res).sendError(HttpServletResponse.SC_UNAUTHORIZED);
    } else {
      req.setAttribute("org.surfnet.crowd.user", username);
      chain.doFilter(req, res);
    }
  }

  private String authenticate(HttpServletRequest req) {
    UserPassCredentials credentials = new UserPassCredentials(req.getHeader("Authorization"));

    if (credentials.isValid() &&
      credentials.getUsername().equals(USER) &&
      credentials.getPassword().equals(PASS))
      return credentials.getUsername();

    return null;
  }

  @Override
  public void init(FilterConfig config) throws ServletException {
  }

  @Override
  public void destroy() {
  }


  /**
   * Blatantly copied from Apis project.
   */
  public static class UserPassCredentials {

    private static final char SEMI_COLON = ':';
    private static final int BASIC_AUTH_PREFIX_LENGTH = "Basic ".length();

    private String username;
    private String password;

    /**
     * Parse the username and password from the authorization header. If
     * the username and password cannot be found they are set to null.
     * @param authorizationHeader the authorization header
     */
    public UserPassCredentials(final String authorizationHeader) {
      if (authorizationHeader == null || authorizationHeader.length() < BASIC_AUTH_PREFIX_LENGTH) {
        noValidAuthHeader();
        return;
      }

      String authPart = authorizationHeader.substring(BASIC_AUTH_PREFIX_LENGTH);
      String userpass = new String(Base64.decodeBase64(authPart.getBytes()));
      if (userpass.indexOf(SEMI_COLON) < 1) {
        noValidAuthHeader();
        return;
      }
      username = userpass.substring(0, userpass.indexOf(SEMI_COLON));
      password = userpass.substring(userpass.indexOf(SEMI_COLON) + 1);
    }

    public UserPassCredentials(String username, String password) {
      super();
      this.username = username;
      this.password = password;
    }

    private void noValidAuthHeader() {
      username = null;
      password = null;
      return;
    }

    public boolean isValid() {
      return !StringUtils.isBlank(username) && !StringUtils.isBlank(password);
    }

    /**
     * Get the username.
     * @return the username or null if the username was not found
     */
    public String getUsername() {
      return username;
    }

    /**
     * Get the password.
     * @return the password or null if the password was not found
     */
    public String getPassword() {
      return password;
    }


    @Override
    public String toString() {
      return "UserPassCredentials [username=" + username + "]";
    }

  }
}