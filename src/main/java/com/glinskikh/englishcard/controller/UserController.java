package com.glinskikh.englishcard.controller;

import com.glinskikh.englishcard.service.UserService;
import com.glinskikh.englishcard.dto.responses.PagedRs;
import com.glinskikh.englishcard.dto.requests.UserRq;
import com.glinskikh.englishcard.dto.responses.UserRs;
import com.glinskikh.englishcard.util.AppConstants;
import com.glinskikh.englishcard.util.ExceptionResponse;
import com.glinskikh.englishcard.util.EntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<UserRs> findById(@PathVariable("id") Long id) {
        UserRs response = userService.findById(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("name/{username}")
    public ResponseEntity<UserRs> findByUsername(@PathVariable("username") String username) {
        UserRs response = userService.findByUsername(username);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PagedRs<UserRs>> findAll(@RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                   @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                                   @RequestParam(required = false, defaultValue = "username") String sortBy,
                                                   @RequestParam(defaultValue = "asc") String sortDirection) {
        PagedRs<UserRs> response = userService.findAll(page, size, sortBy, sortDirection);
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
