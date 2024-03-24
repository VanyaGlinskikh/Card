package com.glinskikh.englishcard.controller;

import com.glinskikh.englishcard.service.CardService;
import com.glinskikh.englishcard.dto.requests.CardRq;
import com.glinskikh.englishcard.dto.responses.CardRs;
import com.glinskikh.englishcard.dto.responses.PagedRs;
import com.glinskikh.englishcard.util.AppConstants;
import com.glinskikh.englishcard.util.EntityException;
import com.glinskikh.englishcard.util.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping()
    public ResponseEntity<PagedRs<CardRs>> findAll(@RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                   @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        PagedRs<CardRs> response = cardService.findAll(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardRs> findById(@PathVariable(name = "id") Long id) {
        CardRs response =  cardService.findById(id);
        return new ResponseEntity< >(response, HttpStatus.CREATED);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<PagedRs<CardRs>>  findAllByUserId(@PathVariable Long userId,
                                                            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        PagedRs<CardRs> response = cardService.findByUserId(userId, page, size);
        return new ResponseEntity< >(response, HttpStatus.OK);
    }

    @GetMapping("{userId}/{cardId}")
    public ResponseEntity<CardRs> findAllCardByUserId(@PathVariable Long userId,
                                                      @PathVariable Long cardId) {
        CardRs response = cardService.findByUserIdAndId(userId,cardId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<CardRs> addCard(@RequestBody CardRq cardRq,
                                          @PathVariable(name = "userId") Long userId){
        CardRs response= cardService.addCard(cardRq,userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("{userId}/{cardId}")
    public ResponseEntity<CardRs> updateCard(@PathVariable(name = "userId") Long userId,
                                             @PathVariable(name = "cardId") Long cardId,
                                             @RequestBody CardRq cardRq) {
        CardRs response= cardService.updateCard(userId, cardId, cardRq);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CardRs> deleteCard(@PathVariable(name = "id") Long id) {
        CardRs response = cardService.deleteCard(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(EntityException e) {
        ExceptionResponse response = new ExceptionResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
