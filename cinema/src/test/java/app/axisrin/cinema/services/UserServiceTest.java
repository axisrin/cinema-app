package app.axisrin.cinema.services;

import app.axisrin.cinema.entities.User;
import app.axisrin.cinema.repos.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepo userRepository;


    @Test
    void findByName() {
        User newUser1 = new User();
        newUser1.setUsername("Vasya");

        User newUser2 = new User();
        newUser2.setUsername("Kolya");

        List<User> listUsers = new ArrayList<User>();

        listUsers.add(newUser1);
        listUsers.add(newUser2);

        Mockito.when(userRepository.findByUsername("Vasya")).thenReturn(newUser1);
        Mockito.when(userRepository.findByUsername("Kolya")).thenReturn(newUser2);

        User checkUser1 =  userService.findByName("Vasya");
        User checkUser2 =  userService.findByName("Kolya");

        Assertions.assertEquals(newUser1.getUsername(), checkUser1.getUsername());
        Assertions.assertEquals(newUser2.getUsername(), checkUser2.getUsername());

    }
}