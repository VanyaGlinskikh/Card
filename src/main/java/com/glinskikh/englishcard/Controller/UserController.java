package com.glinskikh.englishcard.Controller;

import com.glinskikh.englishcard.Service.UserService;
import com.glinskikh.englishcard.dto.PagedResponse;
import com.glinskikh.englishcard.dto.UserRq;
import com.glinskikh.englishcard.dto.UserRs;
import com.glinskikh.englishcard.model.Card;
import com.glinskikh.englishcard.model.User;
import com.glinskikh.englishcard.util.AppConstants;
import com.glinskikh.englishcard.util.ExceptionResponse;
import com.glinskikh.englishcard.util.EntityException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;


    @GetMapping("/{id}")
    public ResponseEntity<UserRs> findById(@PathVariable("id") Long id) {
        UserRs response = userService.findById(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("name/{username}")
    public ResponseEntity<UserRs> findById(@PathVariable("username") String username) {
        UserRs response = userService.findByUsername(username);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PagedResponse<UserRs>> findAll(@RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                         @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                                         @RequestParam(required = false, defaultValue = "username") String sortBy,
                                                         @RequestParam(defaultValue = "asc") String sortDirection) {
        PagedResponse<UserRs> response = userService.findAll(page, size, sortBy, sortDirection);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserRs> addUser(@RequestBody UserRq userRq){
        UserRs response = userService.addUser(userRq);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<UserRs> updateUser(@RequestBody UserRq userRq,
                                 @PathVariable("id") Long id){
        UserRs  response = userService.updateUser(userRq,id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserRs> deleteUser(@PathVariable("id") Long id){
        UserRs  response = userService.deleteUserById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(EntityException e) {
        ExceptionResponse response = new ExceptionResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
