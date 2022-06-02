import com.warehousing.aquarium.controller.UserController;
import com.warehousing.aquarium.model.response.UserResponse;
import com.warehousing.aquarium.repository.UserRepository;
import com.warehousing.aquarium.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(UserController.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;



    @Test
    void whenGetAll_shouldReturnList() {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername("test");
        List<UserResponse> list = new ArrayList<>();
        list.add(userResponse);

        Mockito.when(userService.getAllUser()).thenReturn(list);

        Assert.assertEquals(list.size(), 1);
    }


}
