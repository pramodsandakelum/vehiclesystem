/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Vehicle;
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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllVehicle() {
        try {
            List<Vehicle> vehicleList = vehiclebl.getVehicles();
            return Response.status(Response.Status.OK).entity(vehicleList).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }
    
    @Path("/addVehicle")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addVehicle(Vehicle vehicle) {
        try {
            boolean result = vehiclebl.insertVehicle(vehicle);
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }
}
