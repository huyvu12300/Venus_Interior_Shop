package Venus.WebNoiThat.service.impl;

import Venus.WebNoiThat.dto.UserDto;
import Venus.WebNoiThat.model.Role;
import Venus.WebNoiThat.model.User;
import Venus.WebNoiThat.repository.RoleRepository;
import Venus.WebNoiThat.repository.UserRepository;
import Venus.WebNoiThat.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        long userCount = userRepository.count();
        boolean isFirstUser = (userCount == 0);
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        if (isFirstUser) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }
            user.setRoles(Arrays.asList(adminRole));
        } else {
            // Nếu không phải là tài khoản đầu tiên, gán quyền user bình thường
            Role role = roleRepository.findByName("ROLE_USER");
            if (role == null) {
                role = checkRoleExist();
            }
            user.setRoles(Arrays.asList(role));
        }
//        Role role = roleRepository.findByName("ROLE_USER");
//        if(role == null){
//            role = checkRoleExist();
//        }
//        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    private UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }
}
