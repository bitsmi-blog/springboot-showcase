package com.bitsmi.springbootshowcase.web.test.content;

import com.bitsmi.springbootshowcase.application.testsupport.common.IApplicationCommonTestScenario;
import com.bitsmi.springbootshowcase.application.testsupport.content.IApplicationContentTestScenario;
import com.bitsmi.springbootshowcase.application.testsupport.content.RetrieveItemSchemaApplicationQueryMocker;

public class ContentStandardTestScenario implements
        IApplicationCommonTestScenario,
        IApplicationContentTestScenario
{
    @Override
    public void configureRetrieveItemSchemaApplicationQueryMocker(RetrieveItemSchemaApplicationQueryMocker mocker)
    {
        // This is just an example on how to create Test scenarios. It doesn't make sense because it does the same as the base implementation
        mocker.whenRetrieveAllItemSchemasThenReturnData()
                .whenRetrieveAllItemSchemasGivenPaginationThenReturnData();
    }
}
