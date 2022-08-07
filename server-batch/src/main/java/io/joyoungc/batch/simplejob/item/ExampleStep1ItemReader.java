/*
 * Copyright (c) 2022 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.joyoungc.batch.simplejob.item;


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
