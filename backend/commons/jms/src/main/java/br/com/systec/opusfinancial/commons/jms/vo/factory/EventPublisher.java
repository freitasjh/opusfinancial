package br.com.systec.opusfinancial.commons.jms.vo.factory;

public interface EventPublisher {

    void publish(String destination, Object event);
}
