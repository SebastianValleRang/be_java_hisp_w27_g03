package co.mercadolibre.SocialMeli.controller;

import co.mercadolibre.SocialMeli.dto.request.PostRequestDTO;
import co.mercadolibre.SocialMeli.service.impl.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Validated
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostRequestDTO post){
        return new ResponseEntity<>(postService.createPost(post), HttpStatus.OK);
    }

    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<?> getPostByUserLastTwoWeeks(@PathVariable @Positive(message = "El id debe ser positivo") int userId, @RequestParam(required = false) @NotBlank(message = "El orden no puede ser nulo.") String order){
        return new ResponseEntity<>(postService.getPostsByFollowedUsersLastTwoWeeks(userId,order), HttpStatus.OK);
    }

}
