package com.glinskikh.englishcard.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Cards")
@Data
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "word")
    private String word;
    @Column(name = "translation_word")
    private String translationWord;

    @ManyToOne()
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private User user;

}
