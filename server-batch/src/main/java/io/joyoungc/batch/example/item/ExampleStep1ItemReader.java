package io.joyoungc.batch.example.item;


import io.joyoungc.batch.simplejob.item.SimpleJobParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;

@Slf4j
@RequiredArgsConstructor
public class ExampleStep1ItemReader implements ItemReader<String> {

    private final SimpleJobParameter jobParameter;

    private final String[] messages = { "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten" };
    private int count = 0;

    @Override
    public String read() {
        if (count < messages.length) {
            return messages[count++];
        } else {
            log.info("## jobParameter = {}", jobParameter);
            count = 0;
        }
        return null;
    }
}
