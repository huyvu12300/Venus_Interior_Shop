package Venus.WebNoiThat.service;


import Venus.WebNoiThat.dto.UserDto;
import Venus.WebNoiThat.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
