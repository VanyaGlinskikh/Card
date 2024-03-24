package com.glinskikh.englishcard.dto.responses;

import com.glinskikh.englishcard.model.Card;
import com.glinskikh.englishcard.model.User;
import lombok.Data;

import java.util.List;

@Data
public class CardRs {

    private String word;

    private String translationWord;

}
