/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author pramo
 */


import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.User;
import service.UserBL;


@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class userController {
    
    private final UserBL userbl = new UserBL();
    
    @Path("/getAllUsers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(){
        try {
            List<User> userlist = userbl.getallUser();
            return Response.status(Response.Status.OK).entity(userlist).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }
    
    @Path("getUserByID/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByID(@PathParam("id")int id){
        try {
            User singleUser = userbl.getUserbyID(id);
            return Response.status(Response.Status.OK).entity(singleUser).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    
    }
    
    
    @Path("/createAccount")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(User user){
        try {
            String Result = userbl.createUser(user);
            return  Response.status(Response.Status.OK).entity(Result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }
    
    
    @Path("/updateAccount")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAccount(User user){
        try {
            String Result = userbl.updateUser(user);
            return Response.status(Response.Status.OK).entity(Result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }
    
    @Path("deleteAccount/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAccount(@PathParam("id")int id){
        try {
            String Result = userbl.deleteUser(id);
            return Response.status(Response.Status.OK).entity(Result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }
    
    
    
    
    
    
    
}
