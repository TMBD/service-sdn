package sn.esp.dic3;

import org.json.JSONObject;
import sn.esp.dic3.model.Commutateur;
import sn.esp.dic3.model.Machine;
import sn.esp.dic3.utils.Helper;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;


/**
 * Root resource (exposed at "switch" path)
 */

@Path("switch")
public class CommutateurResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return Machine that will be returned as a json response.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response creerCommutateur(Commutateur commutateur) {
        if (commutateur == null)
           return Response.serverError().entity("Veuillez fournir une valeur adequate pour le nombre de port").build();
        commutateur.setSwitchMac(Helper.getRandomMacAddress());
        // Traitement passage de la requête au protocole RMI/XMLRPC
        return Response.ok(commutateur, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("toggle")
    public Response changerEtat(Commutateur commutateur) {

        // Traitement à faire transférer la requête avec le protocole RMI/Xml_RPC à la couche physique
        // commutateur.getSwitchMac() // Recuperation adresse mac;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        return Response.ok(jsonObject.toString(), MediaType.APPLICATION_JSON).build();
    }


}
