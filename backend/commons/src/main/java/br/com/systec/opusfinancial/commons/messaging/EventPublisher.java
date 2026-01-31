package br.com.systec.opusfinancial.commons.messaging;

public interface EventPublisher {

    void publish(String destination, Object event);
}
