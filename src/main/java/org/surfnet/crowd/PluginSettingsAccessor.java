package org.surfnet.crowd;

import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

public class PluginSettingsAccessor  {

  private static PluginSettingsFactory pluginSettingsFactory;

  public PluginSettingsAccessor(PluginSettingsFactory pluginSettingsFactory) {
    this.pluginSettingsFactory = pluginSettingsFactory;
  }

  public static PluginSettingsFactory getPluginSettingsFactory() {
    return pluginSettingsFactory;
  }

}
