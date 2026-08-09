package io.joyoungc.batch.example.item;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class ExampleStep2ItemWriter implements ItemWriter<String> {
    @Override
    public void write(Chunk<? extends String> items) throws Exception {
        items.forEach(System.out::println);
    }
}
