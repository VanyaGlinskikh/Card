package com.glinskikh.englishcard.Service;

import com.glinskikh.englishcard.dto.PagedResponse;
import com.glinskikh.englishcard.dto.UserRq;
import com.glinskikh.englishcard.dto.UserRs;
import com.glinskikh.englishcard.model.User;
import com.glinskikh.englishcard.repo.UserRepository;
import com.glinskikh.englishcard.util.EntityException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PagedResponse<UserRs> findAll(Integer page, Integer size, String sortBy, String sortDirection){
        Sort sort = Sort.by(sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> users = userRepository.findAll(pageable);
        List<UserRs> content = users.getNumberOfElements() == 0 ? Collections.emptyList() :
                users.getContent().stream()
                        .map(user -> modelMapper.map(user, UserRs.class))
                        .collect(Collectors.toList());
        if (content.isEmpty())
            throw new EntityException("Users not found");
        else
            return new PagedResponse<>(content, users.getNumber(), users.getSize(), users.getTotalElements(),
                    users.getTotalPages(), users.isLast());
    }

    @Override
    public UserRs findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            return modelMapper.map(optionalUser.get(),UserRs.class);
        }else
            throw new EntityException("User not found with id " + id);
    }


    @Override
    public UserRs findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()){
            return modelMapper.map(optionalUser, UserRs.class);
        }else
            throw new EntityException("User not found with username " + username);
    }

    @Override
    public UserRs addUser(UserRq userRq){
        Optional<User> optionalUser = userRepository.findByUsername(userRq.getUsername());
        if (optionalUser.isPresent())
            throw new EntityException("User with username " + userRq.getUsername() + " already exists.");
        else
            return modelMapper.map(userRepository.save(modelMapper.map(userRq, User.class)), UserRs.class);
    }

    @Override
    public UserRs updateUser(UserRq userRq, long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent())
        {
            User user = optionalUser.get();
            modelMapper.map(userRq,user);
            return modelMapper.map(userRepository.save(user), UserRs.class);
        }
        else {
            throw new EntityException("User not found with ID: " + id);
        }


    }
    @Override
    public UserRs deleteUserById(long id){
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()){
            userRepository.deleteById(id);
            return modelMapper.map(userOptional, UserRs.class);
        }else{
            throw new EntityException("User not found with ID: "+id);
        }
    }

}
