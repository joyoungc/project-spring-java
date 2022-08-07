/*
 * Copyright (c) 2022 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package io.joyoungc.batch.simplejob.item;

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
