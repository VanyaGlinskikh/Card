package com.glinskikh.englishcard.Service;

import com.glinskikh.englishcard.dto.PagedResponse;
import com.glinskikh.englishcard.dto.UserRq;
import com.glinskikh.englishcard.dto.UserRs;

public interface UserService {
    UserRs findById(Long id);

    UserRs findByUsername(String username);

    PagedResponse<UserRs> findAll(Integer page, Integer size, String sortBy, String sortDirection);

    UserRs addUser(UserRq userToAdd);

    UserRs updateUser(UserRq userRq, long id);

    UserRs deleteUserById(long id);
}
