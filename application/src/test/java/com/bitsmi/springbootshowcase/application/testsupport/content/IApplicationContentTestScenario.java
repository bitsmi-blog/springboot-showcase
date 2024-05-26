package com.bitsmi.springbootshowcase.application.testsupport.content;

public interface IApplicationContentTestScenario
{
    static IApplicationContentTestScenario getDefaultInstance()
    {
        return DefaultApplicationContentTestScenario.INSTANCE;
    }

    default void configureRetrieveItemSchemaApplicationQueryMocker(RetrieveItemSchemaApplicationQueryMocker mocker)
    {
        mocker.whenRetrieveAllItemSchemasThenReturnData()
                .whenRetrieveAllItemSchemasGivenPaginationThenReturnData();
    }

    final class DefaultApplicationContentTestScenario implements IApplicationContentTestScenario
    {
        private static final DefaultApplicationContentTestScenario INSTANCE = new DefaultApplicationContentTestScenario();

        private DefaultApplicationContentTestScenario() { }
    }
}
