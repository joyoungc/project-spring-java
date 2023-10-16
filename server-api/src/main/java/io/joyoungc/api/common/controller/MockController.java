package io.joyoungc.api.common.controller;

import io.joyoungc.api.common.dto.CommonDto;
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
@RequestMapping("/api/mock")
public class MockController {

    @GetMapping("/string")
    public String getString() {
        return Constants.YES;
    }

    @GetMapping("/custom-formatter")
    public CommonDto.RequestConversion customFormatter(CommonDto.RequestConversion requestConversion) {
        return requestConversion;
    }

}
