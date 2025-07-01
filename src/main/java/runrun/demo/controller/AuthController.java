package runrun.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runrun.demo.jwt.JwtUtil;
import runrun.demo.model.User;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;


    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        if ("성진".equals(user.getUsername()) && "1234".equals(user.getPassword())) {
            return jwtUtil.generateToken(user.getUsername());
        } else {
            return "로그인 실패 : 사용자 정보 불일치";
        }

    }
}
