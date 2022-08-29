package io.joyoungc.batch.example.item;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ExampleStep2ItemWriter implements ItemWriter<String> {
    @Override
    public void write(List<? extends String> items) throws Exception {
        items.forEach(System.out::println);
    }
}
