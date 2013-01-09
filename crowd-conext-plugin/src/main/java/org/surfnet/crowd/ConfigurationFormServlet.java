package org.surfnet.crowd;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.templaterenderer.TemplateRenderer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.surfnet.crowd.model.ConextConfig;

/**
 * Form for configuring Conext properties.
 *
 * @author Geert van der Ploeg
 */
public class ConfigurationFormServlet extends HttpServlet {

  Logger LOG = LoggerFactory.getLogger(ConfigurationFormServlet.class);
  TemplateRenderer renderer;
  PluginSettingsFactory settingsFactory;
  private TransactionTemplate transactionTemplate;

  public ConfigurationFormServlet(TemplateRenderer renderer, PluginSettingsFactory settingsFactory, TransactionTemplate transactionTemplate) {
    this.renderer = renderer;
    this.settingsFactory = settingsFactory;
    this.transactionTemplate = transactionTemplate;

    LOG.info("Constructor of servlet. SettingsFactory: {}, transactionTemplate: {}", settingsFactory, transactionTemplate);

  }

  public final static String SETTING_BASE = "org.surfnet.crowd.conext";
  public final static String SETTING_APIURL = SETTING_BASE + ".apiUrl";
  public final static String SETTING_CALLBACKURL = SETTING_BASE + ".callbackUrl";
  public final static String SETTING_APIKEY = SETTING_BASE + ".apiKey";
  public final static String SETTING_APISECRET = SETTING_BASE + ".apiSecret";
  public final static String SETTING_MAPPING = SETTING_BASE + ".mapping";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    res.setContentType("text/html;charset=utf-8");

    Map<String, Object> model = new HashMap<String, Object>();

    ConextConfig c = (ConextConfig) transactionTemplate.execute(new TransactionCallback<Object>() {
      @Override
      public Object doInTransaction() {
      PluginSettings settings = settingsFactory.createGlobalSettings();

        return new ConextConfig(
            StringUtils.defaultString((String) settings.get(SETTING_APIURL)),
            StringUtils.defaultString((String) settings.get(SETTING_CALLBACKURL)),
            StringUtils.defaultString((String) settings.get(SETTING_APIKEY)),
            StringUtils.defaultString((String) settings.get(SETTING_APISECRET)),
          ConextConfig.mappingsFromString((String) settings.get(SETTING_MAPPING)));
      }
    });
    LOG.info("fetched settings from plugin settings: " + c);
    model.put("config", c);
    renderer.render("form.vm", model, res.getWriter());
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    String apiKey = req.getParameter("apiKey");
    String callbackUrl = req.getParameter("callbackUrl");
    String apiSecret = req.getParameter("apiSecret");
    String apiUrl = req.getParameter("apiUrl");
    String groupMapping = req.getParameter("groupMapping");

    final ConextConfig c = new ConextConfig(apiUrl, callbackUrl, apiKey, apiSecret, ConextConfig.mappingsFromString(groupMapping));
    LOG.info("About to store settings: " + c);


    transactionTemplate.execute(new TransactionCallback<Object>() {
      @Override
      public Object doInTransaction() {
        PluginSettings settings = settingsFactory.createGlobalSettings();

        settings.put(SETTING_APIKEY, c.getApiKey());
        settings.put(SETTING_APISECRET, c.getApiSecret());
        settings.put(SETTING_APIURL, c.getApiUrl());
        settings.put(SETTING_CALLBACKURL, c.getCallbackUrl());
        settings.put(SETTING_MAPPING, c.getGroupMappingAsString());
        return null;
      }
    });

    res.sendRedirect("configure");
  }
}
