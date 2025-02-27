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
import model.Booking;
import model.Destination;
import model.billCalculateDTO;
import model.bookingDetailDTO;
import service.BookingBL;
import service.DestinationBL;

/**
 *
 * @author pramo
 */
@Path("/booking")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class bookingController {
    
    private final BookingBL bookingbl = new BookingBL();
    private final DestinationBL destinationbl = new DestinationBL();
    
    @Path("/getAllDestination")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDestination(){
        try {
            List<Destination> locations = destinationbl.getAllDestination();
            return Response.status(Response.Status.OK).entity(locations).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }
    
    @Path("calculateFare/{pickupid}/{dropid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculateFare(@PathParam("pickupid")int pickup,@PathParam("dropid")int drop){
        try {
            billCalculateDTO faredto = bookingbl.calculateBooking(pickup, drop);
            return Response.status(Response.Status.OK).entity(faredto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }
    
    @Path("/getallBooking")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooking(){
        try {           
            List<bookingDetailDTO> bookingList = bookingbl.getAllBookings();
            return Response.status(Response.Status.OK).entity(bookingList).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }
    
    @Path("/addBooking")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBooking(Booking booking){
        try {
            String result = bookingbl.addBooking(booking);
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }
    
    
}
