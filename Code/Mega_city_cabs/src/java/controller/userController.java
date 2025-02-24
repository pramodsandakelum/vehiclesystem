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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import model.User;
import model.userCredentialDTO;
import service.UserBL;


@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class userController {
    
    private final UserBL userbl = new UserBL();
    
    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(userCredentialDTO userCred, @Context HttpServletRequest request) {
    try {
        userCredentialDTO user = userbl.userLogin(userCred.getUsername(), userCred.getPassword());
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\": \"Invalid Login\"}").build();
        } else {
            HttpSession session = request.getSession(true);
            session.setAttribute("uid", user.getUid());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            return Response.ok().cookie(new NewCookie("JSESSIONID", session.getId(), "/", null, null, 3600, false)).entity("{\"success\": true}").build();
        }
    } catch (Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
    }
    }

    @GET
    @Path("/sessionCheck")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkSession(@Context HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("uid") != null) {
        return Response.ok("{\"authenticated\": true}").build();
    } else {
        return Response.status(Response.Status.UNAUTHORIZED)
                       .entity("{\"authenticated\": false}")
                       .build();
    }
    }
    
    @Path("logout")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@Context HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
        session.invalidate();
    }
    return Response.ok("{\"success\": true, \"message\": \"Logged out successfully\"}").build();
    }
    
    
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
