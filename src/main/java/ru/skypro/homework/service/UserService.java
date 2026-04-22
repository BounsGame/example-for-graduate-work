package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entities.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.util.ImageUtil;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsManager {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    @Override
    public void createUser(UserDetails user) {
        User newUser = User.builder()
                .email(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(newUser);
    }

    @Override
    public void updateUser(UserDetails user) {
        User existing = findByEmail(user.getUsername());
        existing.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(existing);
    }

    @Override
    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = findByEmail(auth.getName());

            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                throw new IllegalArgumentException("старый пароль не совпадает");
            }

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User toEntity(UserDTO DTO) {
        return User.builder().id(DTO.getId()).email(DTO.getEmail()).image(DTO.getImage()).phone(DTO.getPhone())
                .role(DTO.getRole()).firstname(DTO.getFirstname()).lastname(DTO.getLastname()).build();
    }

    public UserDTO toDTO(User entity) {
        return UserDTO.builder().id(entity.getId()).email(entity.getEmail()).image(entity.getImage())
                .role(entity.getRole()).phone(entity.getPhone()).firstname(entity.getFirstname())
                .lastname(entity.getLastname()).build();
    }

    public User findByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new UsernameNotFoundException("пользователь не найден");
        }
    }

    public void changePassword(NewPassword password) {
        changePassword(password.getCurrentPassword(), password.getNewPassword());
    }

    public UserDTO getUser(UserDetails userDetails) {
        return toDTO(findByEmail(userDetails.getUsername()));
    }

    public void updateUser(UserDetails userDetails, UpdateUser updateUser) {
        User user = findByEmail(userDetails.getUsername());
        user.setFirstname(updateUser.getFirstname());
        user.setLastname(updateUser.getLastname());
        user.setPhone(updateUser.getPhone());
        userRepository.save(user);
    }

    public void updateUserImage(UserDetails userDetails, MultipartFile image) {
        User user = findByEmail(userDetails.getUsername());
        ImageUtil.deleteImage(user.getImage());
        user.setImage(ImageUtil.saveImage(image));
        userRepository.save(user);
    }
}
