package com.wojdor.hearthstonecards.data.service.mapper;

import com.wojdor.hearthstonecards.data.service.model.CardModel;

import java.util.ArrayList;
import java.util.List;

public class ZipResultMapper {

    private static final String HERO_CARD = "hero";

    private ZipResultMapper() {
    }

    public static List<CardModel> map(Object[] objects) {
        List<CardModel> allCards = new ArrayList<>();
        for (Object cardModels : objects) {
            if (isListOfCardModels(cardModels)) {
                ignoreHeroCards(allCards, (List<CardModel>) cardModels);
            }
        }
        return allCards;
    }

    private static void ignoreHeroCards(List<CardModel> allCards, List<CardModel> cardModels) {
        for (CardModel cardModel : cardModels) {
            if (isHeroCard(cardModel)) continue;
            allCards.add(cardModel);
        }
    }

    private static boolean isListOfCardModels(Object cardModels) {
        return cardModels instanceof List
                && !((List) cardModels).isEmpty()
                && ((List) cardModels).get(0) instanceof CardModel;
    }

    private static boolean isHeroCard(CardModel cardModel) {
        return cardModel.getCardId().toLowerCase().contains(HERO_CARD);
    }
}
