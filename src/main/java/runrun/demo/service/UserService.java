package runrun.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import runrun.demo.exception.UnauthorizedException;
import runrun.demo.model.User;
import runrun.demo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new UnauthorizedException("이미 존재하는 사용자입니다.");

        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, encodedPassword);
        userRepository.save(user);

    }


}
