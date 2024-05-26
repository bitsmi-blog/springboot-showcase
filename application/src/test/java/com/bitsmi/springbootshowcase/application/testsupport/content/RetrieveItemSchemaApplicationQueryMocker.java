package com.bitsmi.springbootshowcase.application.testsupport.content;

import com.bitsmi.springbootshowcase.application.content.IRetrieveItemSchemaApplicationQuery;
import com.bitsmi.springbootshowcase.domain.common.dto.PaginatedData;
import com.bitsmi.springbootshowcase.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.domain.testsupport.content.model.ItemSchemaTestDataBuilder;
import org.apache.commons.lang3.ObjectUtils;
import org.mockito.Mockito;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RetrieveItemSchemaApplicationQueryMocker
{
    private final IRetrieveItemSchemaApplicationQuery mockedQuery;
    private final IApplicationContentTestScenario testScenario;

    private RetrieveItemSchemaApplicationQueryMocker(IRetrieveItemSchemaApplicationQuery mockedQuery, IApplicationContentTestScenario testScenario)
    {
        if(!Mockito.mockingDetails(mockedQuery).isMock()) {
            throw new IllegalArgumentException("Query instance must be a mock");
        }

        this.mockedQuery = mockedQuery;
        this.testScenario = testScenario;
    }

    public static RetrieveItemSchemaApplicationQueryMocker mocker()
    {
        return mocker(null);
    }

    public static RetrieveItemSchemaApplicationQueryMocker mocker(IApplicationContentTestScenario mockingScenario)
    {
        return new RetrieveItemSchemaApplicationQueryMocker(
                mock(IRetrieveItemSchemaApplicationQuery.class),
                ObjectUtils.defaultIfNull(mockingScenario, IApplicationContentTestScenario.getDefaultInstance())
        );
    }

    public static RetrieveItemSchemaApplicationQueryMocker fromMockedInstance(IRetrieveItemSchemaApplicationQuery queryInstance)
    {
        return fromMockedInstance(queryInstance, null);
    }

    public static RetrieveItemSchemaApplicationQueryMocker fromMockedInstance(IRetrieveItemSchemaApplicationQuery queryInstance, IApplicationContentTestScenario mockingScenario)
    {
        return new RetrieveItemSchemaApplicationQueryMocker(
                queryInstance,
                ObjectUtils.defaultIfNull(mockingScenario, IApplicationContentTestScenario.getDefaultInstance())
        );
    }

    @BeforeTestExecution
    public void reset()
    {
        testScenario.configureRetrieveItemSchemaApplicationQueryMocker(this);
    }

    public RetrieveItemSchemaApplicationQueryMocker configureMock(Consumer<IRetrieveItemSchemaApplicationQuery> mockConsumer)
    {
        mockConsumer.accept(mockedQuery);
        return this;
    }

    public RetrieveItemSchemaApplicationQueryMocker whenRetrieveAllItemSchemasThenReturnEmpty()
    {
        when(mockedQuery.retrieveAllItemSchemas())
                .thenReturn(Collections.emptyList());
        return this;
    }

    public RetrieveItemSchemaApplicationQueryMocker whenRetrieveAllItemSchemasThenReturnData()
    {
        List<ItemSchema> results = List.of(
                ItemSchemaTestDataBuilder.schema1(),
                ItemSchemaTestDataBuilder.schema2());
        when(mockedQuery.retrieveAllItemSchemas())
                .thenReturn(results);

        return this;
    }

    public RetrieveItemSchemaApplicationQueryMocker whenRetrieveAllItemSchemasGivenPaginationThenReturnEmpty()
    {
        when(mockedQuery.retrieveAllItemSchemas(any()))
                .thenAnswer(answer -> {
                    Pagination pagination = answer.getArgument(0);
                    return PaginatedData.<ItemSchema>builder()
                            .content(Collections.emptyList())
                            .pagination(pagination)
                            .pageCount(0)
                            .totalCount(0)
                            .build();
                });
        return this;
    }

    public RetrieveItemSchemaApplicationQueryMocker whenRetrieveAllItemSchemasGivenPaginationThenReturnData()
    {
        List<ItemSchema> results = List.of(
                ItemSchemaTestDataBuilder.schema1(),
                ItemSchemaTestDataBuilder.schema2());

        when(mockedQuery.retrieveAllItemSchemas(any()))
                .thenAnswer(answer -> {
                    Pagination pagination = answer.getArgument(0);
                    return PaginatedData.<ItemSchema>builder()
                            .content(results)
                            .pagination(pagination)
                            .pageCount(results.size())
                            .totalCount((long) pagination.pageNumber() * pagination.pageSize() + results.size())
                            .totalPages(pagination.pageNumber() + 1)
                            .build();
                });
        return this;
    }
}
