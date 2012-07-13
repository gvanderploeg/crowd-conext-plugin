package org.surfnet.crowd;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.surfnet.crowd.model.ConextConfig;
import org.surfnet.crowd.model.GroupMapping;

import static org.junit.Assert.assertEquals;

public class ConextConfigTest {

  @Test
  public void invalidInput() {
    // invalid input
    assertEquals(Collections.emptyList(), ConextConfig.mappingsFromString(null));
    assertEquals(Collections.emptyList(), ConextConfig.mappingsFromString("foo"));
    assertEquals(Collections.emptyList(), ConextConfig.mappingsFromString("foo==bar"));
    assertEquals(Collections.emptyList(), ConextConfig.mappingsFromString("foo="));
  }


  @Test
  public void oneMapping() {
    // input of 1 mapping
    final List<GroupMapping> mapping = ConextConfig.mappingsFromString("foo=bar");
    assertEquals(1, mapping.size());
    assertEquals("foo", mapping.get(0).getExternalGroupName());
    assertEquals("bar", mapping.get(0).getCrowdGroupName());
  }

  @Test
  public void whitespace() {
    final List<GroupMapping> mapping = ConextConfig.mappingsFromString("   foo  =  b a r  ");
    assertEquals(1, mapping.size());
    assertEquals("foo", mapping.get(0).getExternalGroupName());
    assertEquals("b a r", mapping.get(0).getCrowdGroupName());

  }

}
