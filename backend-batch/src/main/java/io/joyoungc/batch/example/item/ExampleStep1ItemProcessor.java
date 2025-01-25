package io.joyoungc.batch.example.item;

import io.joyoungc.batch.simplejob.item.SimpleJobParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

@RequiredArgsConstructor
public class ExampleStep1ItemProcessor implements ItemProcessor<String, String> {

    private final SimpleJobParameter jobParameter;

    @Override
    public String process(String item) throws Exception {
        return jobParameter.getName() + " : " + item.toUpperCase();
    }
}
