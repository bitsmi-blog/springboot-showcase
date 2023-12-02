package com.bitsmi.springbootshowcase.core.common.mapper;

import com.bitsmi.springbootshowcase.core.common.entity.IUserSummaryProjection;
import com.bitsmi.springbootshowcase.core.common.model.UserSummary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "UserSummaryModelMapperImpl"
)
public interface IUserSummaryModelMapper
{
    UserSummary fromProjection(IUserSummaryProjection userSummaryProjection);
}
