
package io.joyoungc.batch.example.item;


import org.springframework.batch.item.ItemReader;

public class ExampleStep2ItemReader implements ItemReader<String> {

    private String[] messages = { "일", "이", "삼", "사", "오", "육", "칠", "팔", "구", "십" };
    private int count = 0;

    @Override
    public String read() throws Exception {
        if (count < messages.length) {
            return messages[count++];
        } else {
            count = 0;
        }
        return null;
    }
}
