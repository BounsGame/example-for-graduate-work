package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CreateOrUpdateComment;

@Slf4j
@RequestMapping("/ads/{id}/comments")
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Tag(name = "Комментарии", description = "Управление комментариями")
public class CommentController {

    @Operation(summary ="Получение комментариев объявления", tags = {"Комментарии"})
    @GetMapping("")
    public ResponseEntity<?> getComments(@RequestParam("id") int id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)){
            if (true /* добавить проверку на существование поста*/){
                //добавить метод получения комментария
                return ResponseEntity.ok("комментарии выведены");
            }else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("такого объявления не найдено");
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("залогинься");
    }

    @Operation(summary ="Добавление комментария к объявлению", tags = {"Комментарии"})
    @PostMapping("")
    public ResponseEntity<?> addComment(@RequestParam("id") int id, @RequestBody CreateOrUpdateComment newComment){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)){
            if (true /* добавить проверку на существование поста*/){
                //добавить метод добавления комментария
                return ResponseEntity.ok("комментарий добавлен");
            }else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("такого объявления не найдено");
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("залогинься");
    }

    @Operation(summary ="Удаление комментария", tags = {"Комментарии"})
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@RequestParam("adId") int adId, @RequestParam("commentId") int commentId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)){
            if (true /* добавить проверку на существование поста и комментария*/){
                if (true /* добавить проверку того что комментарий принадлежит юзеру*/){
                    //добавить метод удаления поста
                    return ResponseEntity.ok("комментарий удалён");
                }else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("пользователь не является владельцем объявления");
            }else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("такого объявления не найдено");
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("залогинься");
    }

    @Operation(summary ="Обновление комментария", tags = {"Комментарии"})
    @PatchMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@RequestParam("adId") int adId,@RequestParam("commentId") int commentId,
                                        @RequestBody CreateOrUpdateComment comment){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)){
            if (true /* добавить проверку на существование поста и комментария*/){
                if (true /* добавить проверку того что комментарий принадлежит юзеру*/){
                    //добавить метод обновления комментария
                    return ResponseEntity.ok("комментарий обновлен");
                }else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("пользователь не является владельцем объявления");
            }else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("такого объявления не найдено");
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("залогинься");
    }
}
