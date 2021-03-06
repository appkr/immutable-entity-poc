package dev.appkr.immutableentity.config;

import dev.appkr.immutableentity.stream.ProducerChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(value = {ProducerChannel.class})
public class MessagingConfiguration {
}