package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.config.WebSecurityConfig;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;

@Slf4j
@RequestMapping("/Users")
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Управление аккаунтом")
public class UserController {

    private final WebSecurityConfig webSecurityConfig;

    @Operation(summary = "Обновления пароля", tags = {"Пользователи"})
    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword (@AuthenticationPrincipal UserDetails currentUser
            ,@RequestBody NewPassword password){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)){
            if (password.getCurrentPassword().equals(password.getNewPassword())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("старый и новый пароли должны отличаться");
            }
            if (!webSecurityConfig.passwordEncoder().matches(password.getCurrentPassword(),currentUser.getPassword())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("старый пароль введён не верно");
            }
            //добавить метод смены пароля
            return ResponseEntity.ok("пароль успешно изменён");
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("залогинься");
    }

    @Operation(summary = "Получение информации об авторизованном пользователе", tags = {"Пользователи"})
    @GetMapping("/me")
    public ResponseEntity<?> getUser (){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)){
            //добавить метод выдачи пользователя
            return ResponseEntity.ok("пользователь выведен");
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("залогинься");
    }

    @Operation(summary = "Обновление информации об авторизованном пользователе", tags = {"Пользователи"})
    @PatchMapping("/me")
    public ResponseEntity<?> updateUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)){
            //добавить метод обновления данных
            return ResponseEntity.ok("данные успешно обновлены");
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("залогинься");
    }

    @Operation(summary = "Обновление аватара авторизованного пользователя", tags = {"Пользователи"})
    @PostMapping("/me/image")
    public ResponseEntity<?> updateUserImage (@RequestParam("multipart/form-data") MultipartFile newAvatar){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)){
            //добавить метод обновления фото
            return ResponseEntity.ok("фото успешно обновлено");
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("залогинься");
    }
}
