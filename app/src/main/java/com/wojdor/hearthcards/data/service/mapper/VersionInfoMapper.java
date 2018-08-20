package com.wojdor.hearthcards.data.service.mapper;

import com.wojdor.hearthcards.data.service.model.VersionInfoModel;
import com.wojdor.hearthcards.domain.VersionInfo;

public class VersionInfoMapper {

    private VersionInfoMapper() {
    }

    public static VersionInfo map(VersionInfoModel model) {
        VersionInfo info = new VersionInfo();
        info.setVersion(model.getPatch());
        info.setClassNames(model.getClasses());
        info.setStandardSets(model.getStandard());
        info.setLocales(model.getLocales());
        return info;
    }
}
