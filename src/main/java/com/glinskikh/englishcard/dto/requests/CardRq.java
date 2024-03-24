package com.glinskikh.englishcard.dto.requests;

import com.glinskikh.englishcard.model.User;
import lombok.Data;

@Data
public class CardRq {

    private String word;

    private String translationWord;

    private User user;
}
