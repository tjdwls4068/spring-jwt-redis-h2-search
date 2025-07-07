package runrun.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import runrun.demo.dto.LoginRequestDto;
import runrun.demo.dto.ResponseDto;
import runrun.demo.exception.CustomException;
import runrun.demo.jwt.JwtUtil;
import runrun.demo.model.User;
import runrun.demo.repository.UserRepository;
import runrun.demo.service.RedisService;
import runrun.demo.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;


    public AuthController(JwtUtil jwtUtil, UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder, RedisService redisService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
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

        String token = jwtUtil.generateToken(username);// 토큰 생성 및 반환

        // Redis 토큰에 저장
        long expiration = jwtUtil.getExpiration(token);
        redisService.saveToken(token, expiration);

        return token;
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<String>> signup(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");
        userService.signup(username, password);

        return ResponseEntity.ok(ResponseDto.success("회원가입 성공", null));


    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Void>> logout(@RequestHeader("Authorization") String tokenHeader) {
                String token = tokenHeader.substring(7);
        if (!jwtUtil.validationToken(token)) {
            throw new CustomException("유효하지 않은 토큰입니다. ", HttpStatus.BAD_REQUEST);

        }
        long expiration = jwtUtil.getExpiration(token);

        // 블랙리스트 등록
        redisService.blacklistIsToken(token, expiration);

        return ResponseEntity.ok(ResponseDto.success("로그아웃 완료", null));

    }   
}
