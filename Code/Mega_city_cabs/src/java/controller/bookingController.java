/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Destination;
import model.User;
import model.bookinginfoDTO;
import service.BookingBL;

/**
 *
 * @author pramo
 */
@Path("/booking")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class bookingController {
    
    private final BookingBL bookingbl = new BookingBL();
    
    @Path("/getAllDestination")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDestination(){
        try {
            List<Destination> locations = bookingbl.getAllDestination();
            return Response.status(Response.Status.OK).entity(locations).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }
    
    @Path("calculateFare/{pickupid}/{dropid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDestination(@PathParam("pickupid")int pickup,@PathParam("dropid")int drop){
        try {
            bookinginfoDTO bookingdto = bookingbl.calculateBooking(pickup, drop);
            return Response.status(Response.Status.OK).entity(bookingdto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }
    
}
