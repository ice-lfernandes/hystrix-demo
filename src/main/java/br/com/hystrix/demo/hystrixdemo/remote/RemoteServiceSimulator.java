package br.com.hystrix.demo.hystrixdemo.remote;

public class RemoteServiceSimulator {

    private long wait;

    RemoteServiceSimulator(long wait) throws InterruptedException {
        this.wait = wait;
    }

    public String execute() throws InterruptedException {
        System.out.println("chamando método");
        Thread.sleep(wait);
        return "Success";
    }

}
