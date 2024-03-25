package org.lequochai.reactive_jee.rest;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.lequochai.reactive_jee.models.Employee;
import org.lequochai.reactive_jee.models.EmployeeContainer;
import org.lequochai.reactive_jee.response.Response;

@Path ("/employee")
public class EmployeeRestfulApi {
    // Constructors:
    public EmployeeRestfulApi() {

    }

    // Methods:
    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public Response<List<Employee>> getAllEmployees() {
        Response<List<Employee>> response = new Response<>();
        response.setSuccess(true);
        response.setResult(
            EmployeeContainer
                .getInstance()
                .getAll()
        );
        return response;
    }

    @DELETE
    @Path ("/generateEmployees")
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Void> cancelGenerateEmployees() {
        Response<Void> response = new Response<Void>();
        
        try {
            EmployeeContainer
                .getInstance()
                .cancelGenerateEmployees();
            
            response.setSuccess(true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return response;
    }
}
