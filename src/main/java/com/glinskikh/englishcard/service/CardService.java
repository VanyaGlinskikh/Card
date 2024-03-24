package com.glinskikh.englishcard.service;

import com.glinskikh.englishcard.dto.requests.CardRq;
import com.glinskikh.englishcard.dto.responses.CardRs;
import com.glinskikh.englishcard.dto.responses.PagedRs;


public interface CardService {


    PagedRs<CardRs> findByUserId(Long userId, Integer page, Integer size);

    PagedRs<CardRs> findAll(Integer page, Integer size);

    CardRs addCard(CardRq cardRq, Long IdUser);

    CardRs findById(Long id);

    CardRs updateCard(Long userId, Long id, CardRq cardRq);

    CardRs deleteCard(Long id);

    CardRs findByUserIdAndId(Long userId, Long cardId);
}
