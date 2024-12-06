package com.example.backend.Utility;

import lombok.AllArgsConstructor;
import lombok.Data;

// Data transfer object for User
@Data
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String password;
}
