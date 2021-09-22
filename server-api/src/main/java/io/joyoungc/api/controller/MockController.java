package io.joyoungc.api.controller;

import io.joyoungc.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * Created by Aiden Jeong on 2021.09.22
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class MockController {

    @GetMapping("/mock/string")
    public String getString() {
        return Constants.YES;
    }

}
