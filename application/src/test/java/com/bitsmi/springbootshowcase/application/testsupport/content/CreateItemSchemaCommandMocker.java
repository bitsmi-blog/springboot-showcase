package com.bitsmi.springbootshowcase.application.testsupport.content;

import com.bitsmi.springbootshowcase.application.content.ICreateItemSchemaApplicationCommand;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import org.mockito.Mockito;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateItemSchemaCommandMocker
{
    private final ICreateItemSchemaApplicationCommand mockedCommand;

    private CreateItemSchemaCommandMocker(ICreateItemSchemaApplicationCommand mockedCommand)
    {
        if(!Mockito.mockingDetails(mockedCommand).isMock()) {
            throw new IllegalArgumentException("Command instance must be a mock");
        }

        this.mockedCommand = mockedCommand;
    }

    public static CreateItemSchemaCommandMocker mocker()
    {
        return new CreateItemSchemaCommandMocker(mock(ICreateItemSchemaApplicationCommand.class));
    }

    public static CreateItemSchemaCommandMocker fromMockedInstance(ICreateItemSchemaApplicationCommand commandInstance)
    {
        return new CreateItemSchemaCommandMocker(commandInstance);
    }

    @BeforeTestExecution
    public void reset()
    {
        this.whenCreateItemSchemaThenReturnData();
    }

    public CreateItemSchemaCommandMocker configureMock(Consumer<ICreateItemSchemaApplicationCommand> mockConsumer)
    {
        mockConsumer.accept(mockedCommand);
        return this;
    }

    public CreateItemSchemaCommandMocker whenCreateItemSchemaThenReturnData()
    {
        when(mockedCommand.createItemSchema(any()))
                .thenAnswer(answer -> {
                    ItemSchema itemSchema = answer.getArgument(0);
                    LocalDateTime now = LocalDateTime.now();
                    return itemSchema.toBuilder()
                            .id(9999L)
                            .creationDate(now)
                            .lastUpdated(now)
                            .build();
                });
        return this;
    }
}
