package io.joyoungc.batch.example.item;

import org.springframework.batch.item.ItemReader;

public class ExampleItemReader implements ItemReader<String> {
    private final String[] messages = { "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten" };
    private int count = 0;

    @Override
    public String read() {
        if (count < messages.length) {
            return messages[count++];
        } else {
            count = 0;
        }
        return null;
    }
}
