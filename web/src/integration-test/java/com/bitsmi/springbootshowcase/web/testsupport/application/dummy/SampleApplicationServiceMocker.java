package com.bitsmi.springbootshowcase.web.testsupport.application.dummy;

import com.bitsmi.springbootshowcase.application.dummy.ISampleApplicationService;
import org.mockito.Mockito;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SampleApplicationServiceMocker
{
    public static final String DEFAULT_EXPECTED_SAMPLE = "TEST SAMPLE";

    private final ISampleApplicationService mockedService;

    private SampleApplicationServiceMocker(ISampleApplicationService serviceInstance)
    {
        if(!Mockito.mockingDetails(serviceInstance).isMock()) {
            throw new IllegalArgumentException("Service instance must be a mock");
        }

        this.mockedService = serviceInstance;
    }

    public static SampleApplicationServiceMocker mocker()
    {
        return new SampleApplicationServiceMocker(mock(ISampleApplicationService.class));
    }

    public static SampleApplicationServiceMocker fromMockedInstance(ISampleApplicationService serviceInstance)
    {
        return new SampleApplicationServiceMocker(serviceInstance);
    }

    @BeforeTestExecution
    public void reset()
    {
        this.whenGetSampleThenReturnDefault();
    }

    public SampleApplicationServiceMocker configureMock(Consumer<ISampleApplicationService> mockConsumer)
    {
        mockConsumer.accept(mockedService);
        return this;
    }

    public SampleApplicationServiceMocker whenGetSampleThenReturnDefault()
    {
        return whenGetSampleThenReturnString(DEFAULT_EXPECTED_SAMPLE);
    }

    public SampleApplicationServiceMocker whenGetSampleThenReturnString(String value)
    {
        when(mockedService.getSample())
                .thenReturn(value);
        return this;
    }
}
