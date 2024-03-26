package org.lequochai.reactive_jee.rest;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
    public Response<List<Employee>> getAllEmployees(@QueryParam ("keyword") String keyword) {
        Response<List<Employee>> response = new Response<>();
        response.setSuccess(true);
        
        if (keyword == null) {
            response.setResult(
                EmployeeContainer.getInstance()
                    .getAll()
            );
        }
        else {
            response.setResult(
                EmployeeContainer.getInstance()
                    .get(keyword)  
            );
        }

        return response;
    }

    @GET
    @Path ("/{id}")
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Employee> getEmployee(@PathParam("id") String idStr) {
        Response<Employee> response = new Response<>();

        int id;
        try {
            id = Integer.parseInt(idStr);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(
                "ID must be an integer!"
            );
            response.setCode("ID_INVALID");
            return response;
        }

        response.setSuccess(true);
        response.setResult(
            EmployeeContainer
                .getInstance()
                .get(id)  
        );

        return response;
    }

    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Void> insertEmployee(Employee employee) {
        Response<Void> response = new Response<>();

        if (
            EmployeeContainer
                .getInstance()
                .get(
                    employee.getId()
                ) != null
        ) {
            response.setSuccess(false);
            response.setMessage("Employee with given id already exist!");
            response.setCode("EMPLOYEE_ALREADY_EXIST");
            return response;
        }

        EmployeeContainer
            .getInstance()
            .insert(employee);
        
        response.setSuccess(true);

        return response;
    }

    @PUT
    @Path ("/{id}")
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Void> updateEmployee(@PathParam ("id") String idStr, Employee employee) {
        Response<Void> response = new Response<>();

        int id;
        try {
            id = Integer.parseInt(idStr);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("ID must be an integer!");
            response.setCode("ID_INVALID");
            return response;
        }

        Employee target = EmployeeContainer
            .getInstance()
            .get(id);

        if (target == null) {
            response.setSuccess(false);
            response.setMessage("Employee with with given id doesn't exist!");
            response.setCode("EMPLOYEE_NOT_EXIST");
            return response;
        }

        target.setName(employee.getName());

        response.setSuccess(true);
        return response;
    }

    @DELETE
    @Path ("/{id}")
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Void> deleteEmployee(@PathParam ("id") String idStr) {
        Response<Void> response = new Response<>();

        int id;
        try {
            id = Integer.parseInt(idStr);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("ID Must be an integer!");
            response.setCode("ID_INVALID");
            return response;
        }

        if (
            EmployeeContainer
                .getInstance()
                .get(id) == null
        ) {
            response.setSuccess(false);
            response.setMessage("Employee with given ID doesn't exist!");
            response.setCode("EMPLOYEE_NOT_EXIST");
            return response;
        }

        EmployeeContainer.getInstance()
            .delete(id);

        response.setSuccess(true);

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
        }
        catch (Exception e) {
            e.printStackTrace();

            response.setSuccess(false);
            response.setMessage(
                "Failed while canceling generate employees!"
            );
            response.setCode("FAILED_CANCELING_GENERATE_EMPLOYEES");

            return response;
        }
        
        response.setSuccess(true);

        return response;
    }
}
