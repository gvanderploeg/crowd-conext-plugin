package org.surfnet.crowd.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class GroupMapping implements Serializable {

  private static final long serialVersionUID = 0L;

  private String externalGroupName;
  private String crowdGroupName;

  public GroupMapping(String externalGroupName, String crowdGroupName) {
    this.externalGroupName = externalGroupName;
    this.crowdGroupName = crowdGroupName;
  }

  public String getExternalGroupName() {
    return externalGroupName;
  }

  public void setExternalGroupName(String externalGroupName) {
    this.externalGroupName = externalGroupName;
  }

  public String getCrowdGroupName() {
    return crowdGroupName;
  }

  public void setCrowdGroupName(String crowdGroupName) {
    this.crowdGroupName = crowdGroupName;
  }

  public String toString() {
    return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
  }
}
