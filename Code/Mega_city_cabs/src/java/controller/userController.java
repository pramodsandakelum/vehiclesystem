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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import model.Driver;
import model.User;
import model.userCredentialDTO;
import org.json.JSONException;
import service.UserBL;
import org.json.JSONObject;

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

    @Path("/sessionCheck")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkSession(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        // Prevent caching
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, private");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("uid") != null) {
            return Response.ok("{\"authenticated\": true}")
                    .header(HttpHeaders.CACHE_CONTROL, "no-store, no-cache, must-revalidate, private")
                    .header("Pragma", "no-cache")
                    .header("Expires", "0")
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"authenticated\": false}")
                    .header(HttpHeaders.CACHE_CONTROL, "no-store, no-cache, must-revalidate, private")
                    .header("Pragma", "no-cache")
                    .header("Expires", "0")
                    .build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Destroy the session
        }
        return Response.ok().cookie(new NewCookie("JSESSIONID", "", "/", null, null, 0, false)).entity("{\"success\": true}").build();
    }

    @Path("/getAllUsers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
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
    public Response getUserByID(@PathParam("id") int id) {
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(User user) {
        try {
            String Result = userbl.createUser(user);

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("message", Result);

            return Response.status(Response.Status.OK).entity(jsonResponse.toString()).build();
        } catch (JSONException e) {

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("message", "Server Error");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse.toString()).build();
        }
    }

    @Path("/updateAccount")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAccount(User user) {
        try {
            System.out.println("update call");
            String Result = userbl.updateUser(user);

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("message", Result);

            return Response.status(Response.Status.OK).entity(jsonResponse.toString()).build();
        } catch (Exception e) {

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("message", "Server Error");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse.toString()).build();
        }
    }

    @Path("deleteAccount/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAccount(@PathParam("id") int id) {
        try {
            String Result = userbl.deleteUser(id);

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("message", Result);

            return Response.status(Response.Status.OK).entity(jsonResponse.toString()).build();
        } catch (Exception e) {

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("message", "Server Error");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse.toString()).build();
        }
    }

    @Path("/getAllDrivers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDrivers() {
        try {
            List<Driver> driverlist = userbl.getallDriver();
            return Response.status(Response.Status.OK).entity(driverlist).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }

    @Path("driverProfile/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response driverProfile(@PathParam("id") int id) {
        try {
            Driver driver = userbl.returnDriverProfile(id);
            if (driver == null) {
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("message", "No Profile Found");
                return Response.status(Response.Status.OK).entity(jsonResponse.toString()).build();
            }
            return Response.status(Response.Status.OK).entity(driver).build();
        } catch (Exception e) {

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("message", "Server Error");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse.toString()).build();
        }
    }

}
