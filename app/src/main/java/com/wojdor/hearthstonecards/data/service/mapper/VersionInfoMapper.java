package com.wojdor.hearthstonecards.data.service.mapper;

import com.wojdor.hearthstonecards.data.service.model.VersionInfoModel;
import com.wojdor.hearthstonecards.domain.VersionInfo;

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
