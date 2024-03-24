package com.glinskikh.englishcard.service;

import com.glinskikh.englishcard.dto.requests.CardRq;
import com.glinskikh.englishcard.dto.responses.CardRs;
import com.glinskikh.englishcard.dto.responses.PagedRs;
import com.glinskikh.englishcard.model.Card;
import com.glinskikh.englishcard.model.User;
import com.glinskikh.englishcard.repo.CardRepository;
import com.glinskikh.englishcard.repo.UserRepository;
import com.glinskikh.englishcard.util.EntityException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService{

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public CardRs findById(Long id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isPresent()){
            return modelMapper.map(optionalCard.get(), CardRs.class);
        }else {
            throw new EntityException("Card not found with Id " + id);
        }
    }

    @Override
    public PagedRs<CardRs> findByUserId(Long userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Card> cards = cardRepository.findByUserId(userId, pageable);
        List<CardRs> content = cards.getNumberOfElements() == 0 ? Collections.emptyList() :
                cards.getContent().stream()
                        .map(card -> modelMapper.map(card, CardRs.class))
                        .collect(Collectors.toList());
        if (content.isEmpty())
            throw new EntityException("User or cards not found");
        else
            return new PagedRs<>(content, cards.getNumber(), cards.getSize(), cards.getTotalElements(),
                    cards.getTotalPages(), cards.isLast());

        }


    @Override
    public PagedRs<CardRs> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Card> cards = cardRepository.findAll(pageable);
        List<CardRs> content = cards.getNumberOfElements() == 0 ? Collections.emptyList() :
                cards.getContent().stream()
                        .map(card -> modelMapper.map(card, CardRs.class))
                        .collect(Collectors.toList());
        if (content.isEmpty())
            throw new EntityException("Cards not found");
        else
            return new PagedRs<>(content, cards.getNumber(), cards.getSize(), cards.getTotalElements(),
                    cards.getTotalPages(), cards.isLast());
    }

    @Override
    public CardRs addCard(CardRq cardAdd, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        Card card = modelMapper.map(cardAdd, Card.class);
        if(user.isPresent()) {
            user.ifPresent(card::setUser);
            return modelMapper.map(cardRepository.save(card), CardRs.class);
        }else
            throw new EntityException("User not found with Id " + userId);
    }

    @Override
    public CardRs updateCard(Long userId, Long id, CardRq cardRq) {
        Optional<Card> optionalCard = cardRepository.findByUserIdAndId(userId,id);
        if(optionalCard.isPresent())
        {
            Card card = optionalCard.get();
            modelMapper.map(cardRq,card);
            return  modelMapper.map(cardRepository.save(card),CardRs.class);
        }else
            throw new EntityException("The user with id " + userId + "did not have a card with id " + id);
    }

    @Override
    public CardRs deleteCard(Long id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isPresent()) {
            cardRepository.deleteById(id);
            return modelMapper.map(optionalCard.get(),CardRs.class);

        }else
            throw new EntityException("Card not found with Id " + id);

    }

    @Override
    public CardRs findByUserIdAndId(Long userId, Long cardId) {
        Optional<Card> optionalCard = cardRepository.findByUserIdAndId(userId,cardId);
        if (optionalCard.isPresent()) {
            return modelMapper.map(optionalCard, CardRs.class);
        }else
            throw new EntityException("The user with id " + userId + "did not have a card with id " + cardId);
    }


}
