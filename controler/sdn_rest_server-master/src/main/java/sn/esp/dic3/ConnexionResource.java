package sn.esp.dic3;

import org.json.JSONObject;
import org.json.JSONTokener;
import sn.esp.dic3.model.Commutateur;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * Root resource (exposed at "connection" path)
 */

@Path("connection")
public class ConnexionResource {

    @Path("cross")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response connecterCommutateurEtMachine(InputStream entity) {


        JSONTokener tokener = new JSONTokener(entity);
        JSONObject jsonObject = new JSONObject(tokener);
        // Traitement à faire transférer la requête avec le protocole RMI/Xml_RPC à la couche physique
        // jsonObject.getString("switchMac") // Recuperation adresse mac commutateur;
        // jsonObject.getString("laptopMac") // Recuperation adresse mac machine;
        // jsonObject.getString("portNumber") // Recuperation numéro de port;

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", true);
        return Response.ok(jsonResponse.toString(), MediaType.APPLICATION_JSON).build();
    }

    @Path("switch")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response connecterDeuxCommutateurs(InputStream entity) {


        JSONTokener tokener = new JSONTokener(entity);
        JSONObject jsonObject = new JSONObject(tokener);
        // Traitement à faire transférer la requête avec le protocole RMI/Xml_RPC à la couche physique
        // jsonObject.getString("switchMac1") // Recuperation adresse mac commutateur1;
        // jsonObject.getString("switchMac2") // Recuperation adresse mac commutateur2;
        // jsonObject.getString("switchPort1") // Recuperation numéro de port commutateur1;
        // jsonObject.getString("switchPort2") // Recuperation numéro de port commutateur2;

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", true);
        return Response.ok(jsonResponse.toString(), MediaType.APPLICATION_JSON).build();
    }
}
