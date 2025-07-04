package runrun.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import runrun.demo.dto.LoginRequestDto;
import runrun.demo.dto.ResponseDto;
import runrun.demo.exception.CustomException;
import runrun.demo.jwt.JwtUtil;
import runrun.demo.model.User;
import runrun.demo.repository.UserRepository;
import runrun.demo.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserService  userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthController(JwtUtil jwtUtil, UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto request) {
        String username = request.getUsername();
        String password = request.getPassword();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("사용자를 찾을 수 없습니다.", HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }

        return jwtUtil.generateToken(username); // 토큰 생성 및 반환
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<String>> signup(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");
        userService.signup(username, password);

        return ResponseEntity.ok(ResponseDto.success("회원가입 성공", null));


    }
}
