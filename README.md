<!DOCTYPE html>
<html>
<body>
    <a href="https://eldoria.dev">
        <img style=" border-radius: 50%" class="rounded" align="right" src="https://i.imgur.com/rbKkKFl.png" height="220" width="220">
    </a>
    <h1>Welcome to the Eldoria Wiki! 🪄✨</h1>
    <h2>Project Overview 🌟</h2>
    <p><b>Eldoria is not just your ordinary framework; it's a magical journey into the realms of guilds,amulets and runes! 🚀

At its core, Eldoria is a versatile framework designed to cast spells (or, as we like to call them, "runes") to enhance
the capabilities of various platforms like Discord, Telegram, Google Chat, and more. It's like wielding a wizard's wand
for your coding needs! 🧙‍♂️

But that's not all! Eldoria can also assist you with chat-bots and project management, offering seamless integration
with tools like Notion and Jira. It's your trusty companion in the quest for productivity and efficiency! 🤖📈
</body>
</html>
Certainly, here's the improved content in Markdown format:

# Modules of Magic 📚🔮

## Amulet Module (The Heart of Eldoria) 📿

The "Amulet" module serves as the heart of Eldoria, the mystical amulet that binds everything together. It orchestrates
the enchantments and connects your runes with the world, ensuring a seamless and harmonious experience. It's the true
gem of our magical framework! 💎🪄

## Rune Module (Powered by Picocli) 🪄

The "Rune" module, powered by the Picocli library, is where the magic happens! This module empowers you to create and
wield your own custom runes, granting you command-line prowess that feels like spellcasting. It's your ticket to
command-based wizardry! 🪄💻

## Chaos Deity Module (Powered by JDA) ⚡🌪️

The "Chaos Deity" module, powered by JDA (Java Discord API), taps into the wild forces of Discord. It's where you'll
find the power to communicate with the denizens of Discord with unparalleled finesse. Unleash your chaotic charisma! 🪐⚡

## Coding Magic 🪄💻

Eldoria was conjured using the mystic arts of reactive programming and the principles of clean architecture. It's a Java
project with the power of Spring and Maven, ensuring reliability, scalability, and maintainability.

## Getting Started 📝

To embark on your enchanting journey with Eldoria, follow these steps:

1. Make sure the jitpack.io repository is in your `pom.xml`:

```xml

<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

2. Include the Eldoria dependency in your `pom.xml`:

```xml

<dependency>
    <groupId>com.github.eldoria-framework</groupId>
    <artifactId>eldoria</artifactId>
    <version>-{version}</version>
</dependency>
```

## Casting Runes With Picocli 🪄💻

To cast a rune, create a class similar to this example. This code snippet demonstrates how to use Eldoria's Picocli
integration:

```java
import lombok.RequiredArgsConstructor;

@HarmonyRune
@RequiredArgsConstructor
@CommandLine.Command(name = "golem", aliases = {"create", "evoke"})
public class GolemCreateRune implements Callable<Mono<String>> {

    @Autowired
    private final ProcessCreateGolemUseCase processCreateGolemUseCase;

    @CommandLine.Parameters(index = "0", defaultValue = "Golem")
    private String golemName;

    @Override
    public Mono<String> call() {
        return processCreateGolemUseCase.execute(golemName)
                .onErrorResume(InvalidGolemNameException.class, exception ->
                        Mono.fromRunnable(() -> log.error("[Eldoria][GolemCreateRune] the golem name is not valid! {} ",
                                golemName, exception)))
                .onError(throwable -> Mono.just(throwable.getMessage()));
    }
}
```

For more examples, visit the [Picocli website](http://picocli.info/) or explore
the [picocli-spring-boot-starter](https://github.com/kakawait/picocli-spring-boot-starter) repository.

## Join the Eldoria Guild! 🌌🪄

Feel the call of magic? Join the Eldoria Guild, where you can wield the power of runes in your projects. Dive into
our [GitHub repository](https://github.com/eldoria-framework) to explore, contribute, and start your own enchanting
journey with Eldoria! 🪄🌐

May your code be ever efficient, and your runes always enchanting! ✨🌟