package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.AccountEntity;
import com.warehousing.aquarium.model.request.CreateUserRequest;
import com.warehousing.aquarium.model.request.LoginUserRequest;
import com.warehousing.aquarium.model.response.UserResponse;
import com.warehousing.aquarium.repository.UserRepository;
import com.warehousing.aquarium.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    private JavaMailSender mailSender;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }


    @Override
    public boolean createUser(CreateUserRequest userRequest) {
        ModelMapper mapper = new ModelMapper();
        AccountEntity userEntity = mapper.map(userRequest, AccountEntity.class);

        if (userRepository.getByUsername(userRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username existed");
        }
        Optional<AccountEntity> accountEntity = userRepository.getFirstByEmail(userRequest.getEmail());
        if (accountEntity.isPresent()) {
            throw new RuntimeException("Email existed");
        }
        try {
            userEntity.setRoleId(0);
            userRepository.save(userEntity);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public AccountEntity login(LoginUserRequest loginUserRequest) {
        Optional<AccountEntity> userEntity = userRepository.getByUsernameAndPassword(loginUserRequest.getUsername(), loginUserRequest.getPassword());
        return userEntity.get();
    }

    @Override
    public boolean forgotPassword(String email, String token) {
        Optional<AccountEntity> accountEntity = userRepository.getFirstByEmail(email);
        if (accountEntity.isPresent()) {
            AccountEntity account = accountEntity.get();
            account.setToken(token);
            userRepository.save(account);
            try {
                sendEmail(email, token);
            } catch (MessagingException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean changePassword(String email, String newPassword, String token) {
        Optional<AccountEntity> accountEntity = userRepository.getFirstByEmail(email);
        if (accountEntity.isPresent()) {
            AccountEntity account = accountEntity.get();
            if (account.getToken().equals(token)) {
                account.setToken("");
                account.setPassword(newPassword);
                userRepository.save(account);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<AccountEntity> accountEntities = userRepository.findAll();
        List<UserResponse> list = new ArrayList<>();
        for (AccountEntity account : accountEntities) {
            UserResponse userResponse = new UserResponse();
            userResponse.setUsername(account.getUsername());
            userResponse.setId(account.getUserId());
            userResponse.setName(account.getName());
            list.add(userResponse);
        }
        return list;
    }

    @Override
    public boolean checkToken(String token, Long userId) {
        Optional<AccountEntity> optionalAccount = userRepository.findById(userId);
        return optionalAccount.map(accountEntity -> accountEntity.getToken().equals(token)).orElse(false);
    }

    public void sendEmail(String recipientEmail, String token) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@shopme.com", "Shopme Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Token to reset your password " + token + ":</p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }
}
