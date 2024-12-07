package com.example.backend.Controller;

import com.example.backend.Model.Items;
import com.example.backend.Model.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.example.backend.Service.JwtService;
import com.example.backend.Service.UserService;
import com.example.backend.Utility.AuthRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    
    @GetMapping("/")
    public String isWorking() {
        return "Everything is working! :)";
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        Users user = service.findByUsername(username);
        if(user == null){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/available/{username}")
    public ResponseEntity<?> checkUsernameAvailability(@PathVariable("username") String username){
        boolean user = service.existsByUsername(username);
        if(user){
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestPart Users user, @RequestPart(value = "profile_pic", required = false) MultipartFile profile_pic) {
        try {
            service.registerUser(user, profile_pic);
            return new ResponseEntity<>(true, HttpStatus.CREATED);

        } catch (Exception e) {
            System.err.println("Error during registration: " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest){
        System.out.println(authRequest.getUsername() + " " + authRequest.getPassword());
        
        try{
            authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }


        String jwtToken = jwtService.generateToken(authRequest.getUsername());
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }
    
    
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }
}

 