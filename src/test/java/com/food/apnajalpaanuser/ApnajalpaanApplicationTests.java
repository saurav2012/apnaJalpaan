package com.food.apnajalpaanuser;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

class ApnajalpaanApplicationTests {
    @Test
    void monoImpl(){
        Mono<?> res = Mono.just("hello world")
                .then(Mono.error(new Throwable("error"))).log();
        res.subscribe(System.out::println);
    }
}
