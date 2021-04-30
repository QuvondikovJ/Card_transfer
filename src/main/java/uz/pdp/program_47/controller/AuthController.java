package uz.pdp.program_47.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.program_47.security.JwtProvider;
import uz.pdp.program_47.payload.UserDto;
import uz.pdp.program_47.service.MyAuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
@Autowired
    MyAuthService myAuthService;
@Autowired
    JwtProvider jwtProvider;
@Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping
    public ResponseEntity<?> add(@RequestBody UserDto userDto){
        UserDetails userDetails = myAuthService.loadUserByUsername(userDto.getUsername());
        boolean checkPassword = passwordEncoder.matches(userDto.getPassword(), userDetails.getPassword());
        if (checkPassword){
            String token = jwtProvider.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Login yoki parol noto'g'ri!");
    }




}
