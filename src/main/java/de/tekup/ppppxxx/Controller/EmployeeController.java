package de.tekup.ppppxxx.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tekup.ppppxxx.Entitties.Employee;
import de.tekup.ppppxxx.Entitties.Projet;
import de.tekup.ppppxxx.Entitties.Role;
import de.tekup.ppppxxx.Entitties.Tache;
import de.tekup.ppppxxx.Repositories.Employeerepo;
import de.tekup.ppppxxx.Services.EmployeeService;
import lombok.Data;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/emp")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    private Employeerepo employeerepo;
    @PostMapping("/employee/save")
    public ResponseEntity<Employee> saveemployee(@RequestBody Employee employee){
        return ResponseEntity.ok().body(employeeService.saveemployee(employee));
    }
    @PostMapping("/role/save")
    public ResponseEntity<Role> saverole(@RequestBody Role role){
        URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/emp/save").toUriString());
        return ResponseEntity.created(uri).body(employeeService.saverole(role));
    }
    @PostMapping("/addRole")
    public ResponseEntity<Role> AddRoleToEmployee(@RequestBody RoleToUserForm form){
        employeeService.addroletoemployee(form.getUsername(), form.getRolename());
        return ResponseEntity.ok().build();
    }


    @PostMapping()
    public Employee createemployee(@RequestBody Employee employee){

        return employeerepo.save(employee);
    }
    // get employee with id
    @GetMapping("{id}")
    public ResponseEntity<Employee> getemployee_id(@PathVariable long employee_id){
        Employee employee= employeeService.findById(employee_id);

        return ResponseEntity.ok(employee);
    }

    // update employee with id
    @PutMapping("{id}")
    public ResponseEntity<Employee> update_employee(@PathVariable long employee_id,@RequestBody Employee employeeDetails){
        Employee updateemployee= employeeService.findById(employee_id);
        updateemployee.setLastname(employeeDetails.getLastname());
        updateemployee.setFirstname(employeeDetails.getFirstname());
        updateemployee.setEmail(employeeDetails.getEmail());
        employeeService.saveemployee(updateemployee);
        return ResponseEntity.ok(updateemployee);
    }
    // delete employee with id
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete_employee(@PathVariable long employee_id){
        Employee employee= employeeService.findById(employee_id);

        employeeService.deleteemp(employee_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/token/refresh")
    public void RefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorisationheader=request.getHeader(AUTHORIZATION);
        if (authorisationheader != null && authorisationheader.startsWith("Bearer")){
            try {
                String refresh_token =authorisationheader.substring("bearer".length());
                Algorithm algorithm =Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier= JWT.require(algorithm).build();
                DecodedJWT decodedJWT= verifier.verify(refresh_token);
                String firstname= decodedJWT.getSubject();
                Employee employee=employeeService.getemployee(firstname);
                String access_token = JWT.create().withSubject(employee.getEmail()).
                        withExpiresAt(new Date(System.currentTimeMillis()+10 *60 *1000)).
                        withIssuer(request.getRequestURL().toString()).
                        withClaim("roles", employee.getRoles().stream().map(role -> role.getName()).
                                collect(Collectors.toList())).
                        sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);


            } catch (Exception e){
                response.setHeader("error",e.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message",e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }

        }else {
            throw new RuntimeException("refresh token is missing");

        }
    }
    @GetMapping
    public ResponseEntity<List<Employee>> getallemployee(){
        URI uri=URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/emp/save").toUriString());
        return ResponseEntity.created(uri).body(employeeService.getallemployee());
    }
    @Data
    class RoleToUserForm{
        private String username;
        private String rolename;
    }


}
