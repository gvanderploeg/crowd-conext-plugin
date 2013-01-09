package org.surfnet.crowd.model;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ConextConfigTest {

  @Test
  public void invalidInput() {
    // invalid input
    Assert.assertEquals(Collections.emptyList(), ConextConfig.mappingsFromString(null));
    Assert.assertEquals(Collections.emptyList(), ConextConfig.mappingsFromString("foo"));
    Assert.assertEquals(Collections.emptyList(), ConextConfig.mappingsFromString("foo==bar"));
    Assert.assertEquals(Collections.emptyList(), ConextConfig.mappingsFromString("foo="));
  }


  @Test
  public void oneMapping() {
    // input of 1 mapping
    final List<GroupMapping> mapping = ConextConfig.mappingsFromString("foo=bar");
    Assert.assertEquals(1, mapping.size());
    Assert.assertEquals("foo", mapping.get(0).getExternalGroupName());
    Assert.assertEquals("bar", mapping.get(0).getCrowdGroupName());
  }

  @Test
  public void whitespace() {
    final List<GroupMapping> mapping = ConextConfig.mappingsFromString("   foo  =  b a r  ");
    Assert.assertEquals(1, mapping.size());
    Assert.assertEquals("foo", mapping.get(0).getExternalGroupName());
    Assert.assertEquals("b a r", mapping.get(0).getCrowdGroupName());

  }

}
