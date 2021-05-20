package sn.esp.dic3;

import org.json.JSONObject;
import sn.esp.dic3.model.Machine;
import sn.esp.dic3.utils.Helper;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "laptop" path)
 */
@Path("laptop")
public class MachineResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return Machine that will be returned as a json response.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response creerMachine() {
        Machine machine = new Machine(Helper.getRandomMacAddress());
        //String json = new JSONObject(machine).toString();

        // Traitement passage de la requête au protocole RMI/XMLRPC
        return Response.ok(machine, MediaType.APPLICATION_JSON).build();
    }
}
