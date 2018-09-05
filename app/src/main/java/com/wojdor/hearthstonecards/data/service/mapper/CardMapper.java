package com.wojdor.hearthstonecards.data.service.mapper;

import com.wojdor.hearthstonecards.data.service.model.CardModel;
import com.wojdor.hearthstonecards.domain.Card;

import java.util.ArrayList;
import java.util.List;

public class CardMapper {

    private CardMapper() {
    }

    public static List<Card> map(List<CardModel> models) {
        List<Card> cards = new ArrayList<>();
        for (CardModel model : models) {
            cards.add(map(model));
        }
        return cards;
    }

    public static Card map(CardModel model) {
        Card card = new Card();
        card.setCardId(model.getCardId());
        card.setDbfId(model.getDbfId());
        card.setName(model.getName());
        card.setSet(model.getCardSet());
        card.setRarity(model.getRarity());
        card.setCost(model.getCost());
        card.setText(model.getText());
        card.setFlavorText(model.getFlavor());
        card.setArtist(model.getArtist());
        card.setClassName(model.getPlayerClass());
        return card;
    }
}
