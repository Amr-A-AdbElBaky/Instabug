package com.nagwa.instabugchallenge.modules.words.data.source.local.model.mapper

import com.nagwa.instabugchallenge.modules.words.data.source.local.model.WordDto
import com.nagwa.instabugchallenge.modules.words.domain.entity.WordEntity

fun WordEntity.toDto() =WordDto(
    name = name,
    count = count
)

fun WordDto.toEntity() = WordEntity(
    name = name,
    count = count
)