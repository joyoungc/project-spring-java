package io.joyoungc.batch.example.item;

import io.joyoungc.batch.simplejob.item.SimpleJobParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ExampleStep1ItemWriter implements ItemWriter<String> {

    private final SimpleJobParameter jobParameter;

    @Override
    public void write(List<? extends String> items) {
        log.info("## jobParameter = {}", jobParameter);
        items.forEach(System.out::println);
    }
}
