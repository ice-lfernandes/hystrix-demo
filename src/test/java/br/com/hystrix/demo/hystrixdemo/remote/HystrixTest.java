package br.com.hystrix.demo.hystrixdemo.remote;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import br.com.hystrix.demo.hystrixdemo.command.RemoteServiceCommand;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.jupiter.api.Test;

public class HystrixTest {

    @Test
    void givenSvcTimeoutOf100AndDefaultSettings_whenRemoteSvcExecuted_thenReturnSuccess()
            throws InterruptedException {

        assertThat(invokeRemoteService(500), equalTo("Success"));
    }

    @Test
    void givenSvcTimeoutOf100AndDefaultSettings_whenRemoteSvcExecuted_thenReturnFallback_andCircuitBreakOpen()
            throws InterruptedException {

        assertThat(invokeRemoteService(10000), equalTo("Fallback"));
        assertThat(invokeRemoteService(10000), equalTo("Fallback"));
        assertThat(invokeRemoteService(10000), equalTo("Fallback"));
    }

    private String invokeRemoteService(int timeout)
            throws InterruptedException {

        String response = null;

        try {
            response = new RemoteServiceCommand(new RemoteServiceSimulator(timeout)).execute();
        } catch (HystrixRuntimeException ex) {
            System.out.println("ex = " + ex);
        }

        return response;
    }

}
