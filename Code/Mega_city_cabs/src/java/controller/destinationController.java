package controller;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Destination;
import org.json.JSONObject;
import service.DestinationBL;

@Path("/destination")
public class destinationController {
    
    private final DestinationBL destinationbl = new DestinationBL();
    
    @Path("/getAllDestination")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDestination() {
        try {
            List<Destination> locations = destinationbl.getAllDestination();
            return Response.status(Response.Status.OK).entity(locations).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @Path("/addDestination")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDestination(Destination destination) {
        try {
            boolean success = destinationbl.addDestination(destination);
            JSONObject jsonResponse = new JSONObject();
            
            if (success) {               
                jsonResponse.put("message", "Destination added successfully");
                return Response.status(Response.Status.CREATED).entity(jsonResponse.toString()).build();
            } else {                
                jsonResponse.put("message", "Failed to add destination");
                return Response.status(Response.Status.BAD_REQUEST).entity(jsonResponse.toString()).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @Path("/updateDestination")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDestination(Destination destination) {
        try {
            boolean success = destinationbl.updateDestination(destination);
            JSONObject jsonResponse = new JSONObject();
            
            if (success) {
                jsonResponse.put("message", "Destination updated successfully");
                return Response.status(Response.Status.OK).entity(jsonResponse.toString()).build();
            } else {
                jsonResponse.put("message", "Failed to update destination");
                return Response.status(Response.Status.BAD_REQUEST).entity(jsonResponse.toString()).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @Path("/deleteDestination/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDestination(@PathParam("id") int id) {
        try {
            boolean success = destinationbl.deleteDestination(id);
            JSONObject jsonResponse = new JSONObject();
            
            if (success) {
                jsonResponse.put("message", "Destination deleted successfully");
                return Response.status(Response.Status.OK).entity(jsonResponse.toString()).build();
            } else {
                jsonResponse.put("message", "Destination not found");
                return Response.status(Response.Status.NOT_FOUND).entity(jsonResponse.toString()).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
