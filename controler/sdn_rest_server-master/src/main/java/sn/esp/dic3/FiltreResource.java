package sn.esp.dic3;


import org.json.JSONObject;
import org.json.JSONTokener;
import sn.esp.dic3.model.Commutateur;
import sn.esp.dic3.utils.Helper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("filter")
public class FiltreResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response filtrerParAdresseMacOuPort(@QueryParam("type") String type, InputStream entity) {

        JSONObject jsonObject = Helper.getInputStreamAsJson(entity);
        JSONObject jsonResponse = new JSONObject();
        // jsonObject.getString("switchMac") // Recuperation adresse mac commutateur;
        if (type.equals("mac"))
            jsonObject.getString("adresseMacFiltre"); // Recuperation adresse mac à filtrer dans le cas ou type = mac;
        else if (type.equals("port"))
            jsonObject.getInt("portNumber"); // Recuperation numéro de port à filtrer dans le cas ou type = port;
        else {
            jsonResponse = Helper.createJSONErrorObject("Veuiller renseigner un type adéquat", "Le paramètre type a ete mal defini au niveau de l'URL");
            return Response.serverError().entity(jsonResponse.toString()).build();
        }
        // traitement pour la création du filtre
        jsonResponse.put("success", true);
        return Response.ok(jsonResponse.toString(), MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response supprimerFiltreParAdresseMacOuPort(@QueryParam("type") String type, InputStream entity) {

        JSONObject jsonObject = Helper.getInputStreamAsJson(entity);
        JSONObject jsonResponse = new JSONObject();
        if (type.equals("mac"))
            jsonObject.getString("adresseMacFiltre"); // Recuperation adresse mac à filtrer dans le cas ou type = mac;
        else if (type.equals("port"))
            jsonObject.getInt("portNumber"); // Recuperation numéro de port à filtrer dans le cas ou type = port;
        else {
            jsonResponse = Helper.createJSONErrorObject("Veuiller renseigner un type adéquat", "Le paramètre type a ete mal defini au niveau de l'URL");
            return Response.serverError().entity(jsonResponse.toString()).build();
        }
        // traitement pour la suppression
        jsonResponse.put("success", true);
        return Response.ok(jsonResponse.toString(), MediaType.APPLICATION_JSON).build();
    }
}
