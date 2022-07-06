package br.com.hystrix.demo.hystrixdemo.command;

import br.com.hystrix.demo.hystrixdemo.remote.RemoteServiceSimulator;
import com.netflix.hystrix.*;

public class RemoteServiceCommand extends HystrixCommand<String> {

    private final RemoteServiceSimulator remoteService;

    public RemoteServiceCommand(RemoteServiceSimulator remoteService) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("getString"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(1000)
                        .withCircuitBreakerSleepWindowInMilliseconds(4000)
                        .withCircuitBreakerRequestVolumeThreshold(1))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withMaxQueueSize(15)
                        .withQueueSizeRejectionThreshold(10))
        );
        this.remoteService = remoteService;
    }

    @Override
    protected String run() throws Exception {
        return remoteService.execute();
    }

    @Override
    protected String getFallback() {
        System.out.println("fallback");
        System.out.println("circuit-break-is-open=" + this.isCircuitBreakerOpen());
        System.out.println(this.getCommandGroup());
        System.out.println(this.getCommandKey());
        return "Fallback";
    }
}
