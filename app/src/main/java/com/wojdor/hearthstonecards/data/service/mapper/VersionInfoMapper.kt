package com.wojdor.hearthstonecards.data.service.mapper

import com.wojdor.hearthstonecards.data.service.model.VersionInfoModel
import com.wojdor.hearthstonecards.domain.VersionInfo

object VersionInfoMapper {

    fun map(model: VersionInfoModel) = VersionInfo(
            model.patch,
            model.classes,
            model.standard,
            model.locales
    )
}
