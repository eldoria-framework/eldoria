package dev.eldoria.rune.core.model;

import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Data
public class RuneResult {

    private boolean errored;

    private boolean exists;

    private Flux<String> output;

    private RuneResult(Flux<String> output, boolean errored, boolean exists) {
        this.errored = errored;
        this.exists = exists;
        this.output = output != null ? output : Flux.empty();
    }

    public RuneResult(Flux<String> output) {
        this(output, false, true);
    }

    public RuneResult(Mono<String> output, boolean errored) {
        this(output.flux(), errored, true);
    }

    public static RuneResult unknown() {
        return new RuneResult(null, false, false);
    }

}