package com.wojdor.hearthcards.data.service.mapper;

import com.wojdor.hearthcards.data.service.model.InfoModel;
import com.wojdor.hearthcards.domain.Info;

public class InfoMapper {

    private InfoMapper() {
    }

    public static Info map(InfoModel model) {
        Info info = new Info();
        info.setVersion(model.getPatch());
        info.setClassNames(model.getClasses());
        info.setStandardSets(model.getStandard());
        info.setLocales(model.getLocales());
        return info;
    }
}
