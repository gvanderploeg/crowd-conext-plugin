package org.surfnet.crowd;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import org.surfnet.crowd.model.GroupMapping;

@Provider
@Path("/groupmapping")
public class GroupMappingResource {

  @Path("mappings")
  @GET
  public List<GroupMapping>getAllMappings() {
    return Collections.singletonList(new GroupMapping("this", "that"));
  }
}
