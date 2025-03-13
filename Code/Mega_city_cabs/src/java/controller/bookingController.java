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
import model.Bill;
import model.Booking;
import model.Destination;
import model.billCalculateDTO;
import model.bookingDetailDTO;
import org.json.JSONObject;
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
    
    
    @Path("/getallBills")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBills(){
        try {           
            List<Bill> bookingList = bookingbl.getAllBills();
            return Response.status(Response.Status.OK).entity(bookingList).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }
    
    @Path("/getallBookingByDID/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBookingByDID(@PathParam("id")int id){
        try {           
            List<bookingDetailDTO> bookingList = bookingbl.getAllBookingsByDID(id);
            return Response.status(Response.Status.OK).entity(bookingList).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }
    
    @Path("/getBillById/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBillByID(@PathParam("id")int id){
        try {           
            List<Bill> bill = bookingbl.getBillByID(id);
            return Response.status(Response.Status.OK).entity(bill).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }
    
    @Path("/getallBookingByCID/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBookingByCID(@PathParam("id")int id){
        try { 
            System.out.println(id);
            List<bookingDetailDTO> bookingList = bookingbl.getAllBookingsByCID(id);
            System.out.println(bookingList);
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
            JSONObject jsonresult = new JSONObject();
            String result = bookingbl.addBooking(booking);
            jsonresult.put("message", result);
            return Response.status(Response.Status.OK).entity(jsonresult.toString()).build();
        } catch (Exception e) {
            JSONObject jsonresult = new JSONObject();
            return Response.status(Response.Status.OK).entity(jsonresult.put("message", "System Exception").toString()).build();
        }
    }
    
    
    @Path("/updateTripStatus")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBooking(Booking booking){
        System.out.println("update trip");
        try {
            System.out.println(booking.getBid());
            System.out.println(booking.getAction());
            JSONObject jsonresult = new JSONObject();
            String message = bookingbl.updateTripStatus(booking);
            jsonresult.put("message", message);
            return Response.status(Response.Status.OK).entity(jsonresult.toString()).build();
        } catch (Exception e) {
            JSONObject jsonresult = new JSONObject();
            return Response.status(Response.Status.OK).entity(jsonresult.put("message", "System Exception").toString()).build();
        }
    }
    
    @Path("/payBill")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response payBill(Bill bill){
        try {
            JSONObject jsonresult = new JSONObject();
            String result = bookingbl.PayBill(bill);
            jsonresult.put("message", result);
            return Response.status(Response.Status.OK).entity(jsonresult.toString()).build();
        } catch (Exception e) {
            JSONObject jsonresult = new JSONObject();
            return Response.status(Response.Status.OK).entity(jsonresult.put("message", "System Exception").toString()).build();
        }
    }
}
