package sn.esp.dic3;


import org.json.JSONObject;
import sn.esp.dic3.utils.Helper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("send")
public class MessageResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response envoyerUnMessage(@QueryParam("type") String type, InputStream entity) {
        return null;
    }
}
