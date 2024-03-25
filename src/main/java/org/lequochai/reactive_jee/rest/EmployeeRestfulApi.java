package org.lequochai.reactive_jee.rest;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.lequochai.reactive_jee.models.Employee;
import org.lequochai.reactive_jee.models.EmployeeContainer;

@Path ("/employee")
public class EmployeeRestfulApi {
    // Constructors:
    public EmployeeRestfulApi() {

    }

    // Methods:
    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public List<Employee> getAllEmployees() {
        return EmployeeContainer.getInstance()
            .getAll();
    }
}
