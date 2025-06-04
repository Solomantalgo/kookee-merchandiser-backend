package com.kookee.merchandiser_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "https://stellular-beignet-f4ea84.netlify.app")
public class AuthController {

    @Autowired
    private MerchandiserRepository merchRepo;

    @PostMapping("/login")
    public ResponseEntity<Merchandiser> login(@RequestBody Merchandiser request) {
        Merchandiser merch = merchRepo.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (merch != null) {
            return ResponseEntity.ok(merch);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}