package dev.eldoria.amulet.core.usecase.enrich;

import dev.eldoria.amulet.core.model.CodexMagus;
import dev.eldoria.amulet.core.model.Mage;
import dev.eldoria.amulet.core.model.SpellBook;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class EnrichCodex {

    @Getter
    private static EnrichCodex instance;

    private final Map<Long, CodexMagus> codexMagusList = new ConcurrentHashMap<>();

    void init() {
        instance = this;
    }

    void destroy() {
        instance = null;
    }

    public Mono<Void> setCodexMagus(CodexMagus codexMagus) {
        return Mono.fromRunnable(() -> {
            if (codexMagus == null) {
                codexMagusList.remove(Thread.currentThread().getId());
            } else {
                codexMagusList.put(Thread.currentThread().getId(), codexMagus);
            }
        });
    }

    public Mono<CodexMagus> getCodexMagus() {
        return Mono.defer(() -> Mono.justOrEmpty(codexMagusList.get(Thread.currentThread().getId())));
    }

    public Mono<Mage> getMage() {
        return getCodexMagus().map(CodexMagus::getMage);
    }

    public Mono<SpellBook> getSpellBook() {
        return getCodexMagus().map(CodexMagus::getSpellbook);
    }

    public <T, S extends CodexMagus> Mono<Void> runWithCodexMagus(S codexMagus, Function<S, T> function) {
        val currentCodexMagus = getCodexMagus();
        return setCodexMagus(codexMagus)
                .then(Mono.fromCallable(() -> function.apply(codexMagus)))
                .publishOn(Schedulers.boundedElastic())
                .doFinally(signalType -> currentCodexMagus.subscribe(this::setCodexMagus))
                .then();

    }

    public <T, S extends CodexMagus> Mono<Void> runWithCodexMagus(S codexMagus, Supplier<T> supplier) {
        return runWithCodexMagus(codexMagus, (Function<S, T>) s -> supplier.get());
    }

    public <S extends CodexMagus> Mono<Void> runWithCodexMagus(S codexMagus, Consumer<S> consumer) {
        return runWithCodexMagus(codexMagus, s -> {
            consumer.accept(s);
            return null;
        });
    }

    public <S extends CodexMagus> Mono<Void> runWithCodexMagus(S codexMagus, Runnable runnable) {
        return runWithCodexMagus(codexMagus, s -> {
            runnable.run();
            return null;
        });
    }

    @SneakyThrows
    private <V> V call(Callable<V> callable) {
        return callable.call();
    }
}
