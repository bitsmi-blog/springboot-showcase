package com.bitsmi.springbootshowcase.infrastructure.common.mapper;

import com.bitsmi.springbootshowcase.infrastructure.common.projection.IUserSummaryProjection;
import com.bitsmi.springbootshowcase.domain.common.model.UserSummary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "UserSummaryModelMapperImpl"
)
public interface IUserSummaryModelMapper
{
    UserSummary fromProjection(IUserSummaryProjection userSummaryProjection);
}
