package SpringDoc.uz.pdp.docs.controller;

import SpringDoc.uz.pdp.docs.dto.TokenRequestDto;
import SpringDoc.uz.pdp.docs.securityconfig.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    public static final String TOKEN = "/token";

    public AuthController(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(AuthController.TOKEN)
    public String token(@RequestBody TokenRequestDto tokenRequest){
        String name = tokenRequest.name();
        String password = tokenRequest.password();

        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(name, password);

        authenticationManager.authenticate(authentication);

        return jwtTokenUtil.generateToken(name);
    }
}
