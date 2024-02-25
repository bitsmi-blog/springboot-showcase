package com.bitsmi.springbootshowcase.web.testutil.content;

import com.bitsmi.springbootshowcase.application.content.ICreateItemSchemaCommand;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import org.mockito.Mockito;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * TODO Move to Application module
 */
public class CreateItemSchemaCommandMocker
{
    private final ICreateItemSchemaCommand mockedCommand;

    private CreateItemSchemaCommandMocker(ICreateItemSchemaCommand mockedCommand)
    {
        if(!Mockito.mockingDetails(mockedCommand).isMock()) {
            throw new IllegalArgumentException("Command instance must be a mock");
        }

        this.mockedCommand = mockedCommand;
    }

    public static CreateItemSchemaCommandMocker mocker()
    {
        return new CreateItemSchemaCommandMocker(mock(ICreateItemSchemaCommand.class));
    }

    public static CreateItemSchemaCommandMocker fromMockedInstance(ICreateItemSchemaCommand commandInstance)
    {
        return new CreateItemSchemaCommandMocker(commandInstance);
    }

    @BeforeTestExecution
    public void reset()
    {
        this.whenCreateItemSchemaThenReturnData();
    }

    public CreateItemSchemaCommandMocker configureMock(Consumer<ICreateItemSchemaCommand> mockConsumer)
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
