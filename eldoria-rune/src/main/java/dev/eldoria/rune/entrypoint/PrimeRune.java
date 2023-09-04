package dev.eldoria.rune.entrypoint;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;


@Slf4j
@CommandLine.Command
public class PrimeRune implements Runnable {

    @Override
    public void run() {
        log.info("PrimeRune executed");
    }
}