package com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper;

import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.projection.IUserSummaryProjection;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserSummary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "UserSummaryModelMapperImpl"
)
public interface IUserSummaryModelMapper
{
    UserSummary fromProjection(IUserSummaryProjection userSummaryProjection);
}
