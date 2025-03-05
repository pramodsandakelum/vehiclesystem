/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Vehicle;
import org.json.JSONObject;
import service.VehicleBL;

/**
 *
 * @author pramo
 */
@Path("/vehicle")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class vehicleController {

    private final VehicleBL vehiclebl = new VehicleBL();

    @Path("/getAllVehicle")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getAllVehicle() {
        try {
            List<Vehicle> vehicleList = vehiclebl.getVehicles();
            return Response.status(Response.Status.OK).entity(vehicleList).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }
    
    @Path("/getVehicleByType/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @GET
    public Response getVehicleByType(@PathParam("type") String type) {
        try {
            List<Vehicle> vehicleList = vehiclebl.getVehiclesbyType(type);
            return Response.status(Response.Status.OK).entity(vehicleList).build();
        } catch (Exception e) {
            JSONObject jsonresult = new JSONObject();
            return Response.status(Response.Status.OK).entity(jsonresult.put("message", "System Exception").toString()).build();
        }
    }


    @Path("/addVehicle")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response addVehicle(Vehicle vehicle) {
        try {
            JSONObject jsonresult = new JSONObject();
            String message = "Added Successfully";
            boolean result = vehiclebl.insertVehicle(vehicle);

            if (result == false) {
                message = "Duplicate Entry Or Server Error";
                jsonresult.put("message", message);
            } else {
                jsonresult.put("message", message);
            }

            return Response.status(Response.Status.OK).entity(jsonresult.toString()).build();
        } catch (Exception e) {
            
            JSONObject jsonresult = new JSONObject();
            return Response.status(Response.Status.OK).entity(jsonresult.put("message", "System Exception").toString()).build();
        }
    }

    @Path("/updateVehicle")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response updateVehicle(Vehicle vehicle) {
        try {
            JSONObject jsonresult = new JSONObject();
            String message = "Updated Successfully";
            boolean result = vehiclebl.updateVehicle(vehicle);
            if (result == false) {

                message = "Update Error";
                jsonresult.put("message", message);
            } else {
                jsonresult.put("message", message);
            }

            return Response.status(Response.Status.OK).entity(jsonresult.toString()).build();
        } catch (Exception e) {
            JSONObject jsonresult = new JSONObject();
            return Response.status(Response.Status.OK).entity(jsonresult.put("message", "System Exception").toString()).build();
        }
    }

    @Path("/deleteVehicle/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @GET
    public Response deleteVehicle(@PathParam("id") int id) {
        try {
            JSONObject jsonresult = new JSONObject();
            String message = "Deleted Successfully";
            boolean result = vehiclebl.deleteVehicle(id);
            if (result == false) {

                message = "Delete Error";
                jsonresult.put("message", message);
            } else {
                jsonresult.put("message", message);
            }
            
            return Response.status(Response.Status.OK).entity(jsonresult.toString()).build();
        } catch (Exception e) {
            JSONObject jsonresult = new JSONObject();
            return Response.status(Response.Status.OK).entity(jsonresult.put("message", "System Exception").toString()).build();
        }
    }
}
