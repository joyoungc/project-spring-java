package io.joyoungc.batch.example.item;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ExampleItemWriter implements ItemWriter<String> {

    @Override
    public void write(List<? extends String> items) {
        items.forEach(System.out::println);
    }
}
