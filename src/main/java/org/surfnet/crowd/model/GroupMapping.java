package org.surfnet.crowd.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class GroupMapping implements Serializable {

  private GroupMapping() {
  }

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


  public String getCrowdGroupName() {
    return crowdGroupName;
  }

  public String toString() {
    return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
  }
}
