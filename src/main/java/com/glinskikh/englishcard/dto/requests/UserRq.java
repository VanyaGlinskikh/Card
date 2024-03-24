package com.glinskikh.englishcard.dto.requests;

import com.glinskikh.englishcard.model.Card;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UserRq {


    private String username;

    private String firstName;

    private String secondName;

    private String email;

}
