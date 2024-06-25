package com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.mapper;

import com.bitsmi.springbootshowcase.sampleapps.infrastructure.common.projection.UserSummaryProjection;
import com.bitsmi.springbootshowcase.sampleapps.domain.common.model.UserSummary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "UserSummaryModelMapperImpl"
)
public interface UserSummaryModelMapper
{
    UserSummary mapDomainFromSummaryProjection(UserSummaryProjection userSummaryProjection);
}
