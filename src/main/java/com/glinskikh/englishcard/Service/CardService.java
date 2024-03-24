package com.glinskikh.englishcard.Service;

import com.glinskikh.englishcard.dto.CardRq;
import com.glinskikh.englishcard.dto.CardRs;
import com.glinskikh.englishcard.dto.PagedResponse;
import com.glinskikh.englishcard.model.Card;

import java.util.List;
import java.util.Optional;


public interface CardService {


    PagedResponse<CardRs> findByUserId(Long userId, Integer page, Integer size);

    PagedResponse<CardRs> findAll(Integer page, Integer size);

    CardRs addCard(CardRq cardRq, Long IdUser);

    CardRs findById(Long id);

    CardRs updateCard(Long userId, Long id, CardRq cardRq);

    CardRs deleteCard(Long id);

    CardRs findByUserIdAndId(Long userId, Long cardId);
}
