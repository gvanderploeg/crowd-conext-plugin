package org.surfnet.crowd;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.surfnet.crowd.model.ConextConfig;

import static org.surfnet.crowd.ConfigurationFormServlet.SETTING_APIKEY;
import static org.surfnet.crowd.ConfigurationFormServlet.SETTING_APISECRET;
import static org.surfnet.crowd.ConfigurationFormServlet.SETTING_APIURL;
import static org.surfnet.crowd.ConfigurationFormServlet.SETTING_CALLBACKURL;
import static org.surfnet.crowd.ConfigurationFormServlet.SETTING_MAPPING;

@Path("/")
public class GroupMappingResource {

  private static final Logger LOG = LoggerFactory.getLogger(GroupMappingResource.class);

  private PluginSettingsFactory settingsFactory;
  private TransactionTemplate transactionTemplate;

  public GroupMappingResource(PluginSettingsFactory settingsFactory, TransactionTemplate transactionTemplate) {
    LOG.info("Constructor. SettingsFactory: {}, transactionTemplate: {}", settingsFactory, transactionTemplate);
    this.settingsFactory = settingsFactory;
    this.transactionTemplate = transactionTemplate;
  }

  @Path("/configuration")
  @GET
  @AnonymousAllowed
  public ConextConfig getConfiguration() {

    return (ConextConfig) transactionTemplate.execute(new TransactionCallback<Object>() {

      PluginSettings settings = settingsFactory.createGlobalSettings();

      @Override
      public Object doInTransaction() {
        ConextConfig conextConfig = new ConextConfig(
          StringUtils.defaultString((String) settings.get(SETTING_APIURL)),
          StringUtils.defaultString((String) settings.get(SETTING_CALLBACKURL)),
          StringUtils.defaultString((String) settings.get(SETTING_APIKEY)),
          StringUtils.defaultString((String) settings.get(SETTING_APISECRET)),
          ConextConfig.mappingsFromString((String) settings.get(SETTING_MAPPING)));
        LOG.info("fetched settings from plugin settings: " + conextConfig);
        return conextConfig;
      }
    });

  }
}
