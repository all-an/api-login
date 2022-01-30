package com.api.login.controllers;

import com.api.login.controllers.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class TesteController {

    @PostMapping("/login")
    public ResponseEntity<String> validariaLogin(@RequestBody User user){
        return ResponseEntity.ok("Logou !");
    }

    @GetMapping("/login")
    public ResponseEntity<String> retornaLogin(){
        return ResponseEntity.ok("Logado !");
    }
}
