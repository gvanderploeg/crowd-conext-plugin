package org.surfnet.crowd.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.collections.list.UnmodifiableList;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ConextConfig {

  private ConextConfig() {
  }

  private String apiUrl;

  private String callbackUrl;

  private String apiKey;

  private String apiSecret;

  private List<GroupMapping> groupmappings = UnmodifiableList.decorate(new ArrayList<GroupMapping>());

  private static final Logger LOG = LoggerFactory.getLogger(ConextConfig.class);

  public ConextConfig(String apiUrl, String callbackUrl, String apiKey, String apiSecret, List<GroupMapping> groupmappings) {
    this.apiUrl = apiUrl;
    this.callbackUrl = callbackUrl;
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;

    if (groupmappings != null) {
      this.groupmappings = groupmappings;
    }
  }

  /**
   * Create a list of group mappings, based on form input that looks like:<br/>
   * <pre>
   *   group1=group2
   *   group3=group4
   * </pre>
   * @param input the textual input
   * @return the parsed list as List&lt;GroupMapping&gt;
   */
  public static List<GroupMapping> mappingsFromString(String input) {
    List<GroupMapping> result = new ArrayList<GroupMapping>();
    input = StringUtils.strip(input);
    if (input != null) {
      for (String line : input.split("\n")) {
        String[] elements = line.split("=");

        if (elements.length != 2) {
          LOG.info("Cannot parse line, will skip: " + line);
          continue;
        }
        result.add(new GroupMapping(StringUtils.strip(elements[0]), StringUtils.strip(elements[1])));
      }
    }
    return result;
  }

  public String getApiUrl() {
    return apiUrl;
  }

  public String getApiKey() {
    return apiKey;
  }
  public String getApiSecret() {
    return apiSecret;
  }

  public List<GroupMapping> getGroupmappings() {
    return groupmappings;
  }

  public String getGroupMappingAsString() {
    StringBuilder sb = new StringBuilder();
    for (GroupMapping gm : groupmappings) {
      sb.append(gm.getExternalGroupName())
          .append("=")
          .append(gm.getCrowdGroupName())
          .append("\n");
    }
    return sb.toString();
  }

  public String toString() {
    return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
  }

  public String getCallbackUrl() {
    return callbackUrl;
  }
}
