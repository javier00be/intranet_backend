package com.example.intranet_school.domain.ports.out;

public interface EventPublisherPort {
    void publish(Object event);
}
