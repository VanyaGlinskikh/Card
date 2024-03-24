package com.glinskikh.englishcard.service;

import com.glinskikh.englishcard.dto.responses.PagedRs;
import com.glinskikh.englishcard.dto.requests.UserRq;
import com.glinskikh.englishcard.dto.responses.UserRs;

public interface UserService {
    UserRs findById(Long id);

    UserRs findByUsername(String username);

    PagedRs<UserRs> findAll(Integer page, Integer size, String sortBy, String sortDirection);

    UserRs addUser(UserRq userToAdd);

    UserRs updateUser(UserRq userRq, long id);

    UserRs deleteUserById(long id);
}
