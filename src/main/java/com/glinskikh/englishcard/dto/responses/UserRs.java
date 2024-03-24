package com.glinskikh.englishcard.dto.responses;

import com.glinskikh.englishcard.model.Card;
import com.glinskikh.englishcard.model.User;
import lombok.Data;

import javax.validation.constraints.Email;
import java.util.List;

@Data
public class UserRs {

    private String username;

    private String firstName;

    private String secondName;

    private String email;

}
