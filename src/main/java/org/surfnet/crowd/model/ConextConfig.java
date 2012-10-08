package org.surfnet.crowd.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement
public class ConextConfig {

  @XmlElement
  private String apiUrl;
  @XmlElement
  private String apiKey;
  @XmlElement
  private String apiSecret;

  @XmlElement
  private List<GroupMapping> groupmappings = new ArrayList<GroupMapping>();

  private static final Logger LOG = LoggerFactory.getLogger(ConextConfig.class);

  public ConextConfig(String apiUrl, String apiKey, String apiSecret, List<GroupMapping> groupmappings) {
    this.apiUrl = apiUrl;
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

  public void setApiUrl(String apiUrl) {
    this.apiUrl = apiUrl;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getApiSecret() {
    return apiSecret;
  }

  public void setApiSecret(String apiSecret) {
    this.apiSecret = apiSecret;
  }

  public List<GroupMapping> getGroupmappings() {
    return groupmappings;
  }

  public void setGroupmappings(List<GroupMapping> groupmappings) {
    this.groupmappings = groupmappings;
  }

  public void addGroupmappings(GroupMapping groupmappings) {
    this.groupmappings.add(groupmappings);
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
}
