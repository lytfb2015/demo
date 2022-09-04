package com.yogcn.iot.aggregation.controller;

import com.yogcn.iot.aggregation.response.HelloResponse;
import com.yogcn.iot.aggregation.service.TestService;
import com.yogcn.iot.common.core.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/aggregation")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("test")
    public Result<List<HelloResponse>> test() {
        List<HelloResponse> list = testService.hello();
        return Result.ok(list);
    }
}
