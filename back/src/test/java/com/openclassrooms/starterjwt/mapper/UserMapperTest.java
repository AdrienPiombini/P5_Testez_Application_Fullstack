package com.openclassrooms.starterjwt.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;

@ExtendWith(SpringExtension.class)
public class UserMapperTest {

    @InjectMocks
    UserMapperImpl userMapperImpl;

    LocalDateTime date = LocalDateTime.now();

    private UserDto UserDto = new UserDto(1L, "lastName@firstName.com", "LastName", "FirstName", false, "Secret134!",
            date, date);
    private User User = new User(1L, "lastName@firstName.com", "LastName", "FirstName", "Secret134!", false, date,
            date);
    private List<User> UsersList = new ArrayList<>(2);
    private List<UserDto> UsersDtoList = new ArrayList<>(2);
    private User UserNull;
    private UserDto UserDTONull;
    private List<User> UsersListNull;
    private List<UserDto> UsersDTOListNull;

    @BeforeEach
    void setup() {
        UsersList.add(User);
        UsersDtoList.add(UserDto);

    }

    @Test
    void should_return_User() {
        User result = userMapperImpl.toEntity(UserDto);
        assertThat(result).isEqualTo(User);
        assertThat(userMapperImpl.toEntity(UserDTONull)).isNull();
    }

    @Test
    void should_return_User_dto() {
        UserDto result = userMapperImpl.toDto(User);
        assertThat(result).isEqualTo(UserDto);
        assertThat(userMapperImpl.toDto(UserNull)).isNull();

    }

    @Test
    void should_return_User_list() {
        List<User> result = userMapperImpl.toEntity(UsersDtoList);
        assertThat(result).isEqualTo(UsersList);
        assertThat(userMapperImpl.toEntity(UsersDTOListNull)).isNull();
    }

    @Test
    void should_return_UserDto_list() {
        List<UserDto> result = userMapperImpl.toDto(UsersList);
        assertThat(result).isEqualTo(UsersDtoList);
        assertThat(userMapperImpl.toDto(UsersListNull)).isNull();

    }

}
