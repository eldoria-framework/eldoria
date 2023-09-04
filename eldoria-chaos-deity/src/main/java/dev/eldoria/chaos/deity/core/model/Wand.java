package dev.eldoria.chaos.deity.core.model;

import dev.eldoria.amulet.core.model.SpellBook;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.attribute.IThreadContainer;
import net.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.concrete.StageChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Wand extends SpellBook implements MessageChannelUnion {

    private MessageChannelUnion messageChannelUnion;

    @NotNull
    @Override
    public String getName() {
        return messageChannelUnion.getName();
    }

    @NotNull
    @Override
    public String getId() {
        return messageChannelUnion.getId();
    }

    @Override
    public void sendMessage(String message) {
        messageChannelUnion.sendMessage(message);
    }

    @Override
    public PrivateChannel asPrivateChannel() {
        return messageChannelUnion.asPrivateChannel();
    }

    @Override
    public TextChannel asTextChannel() {
        return messageChannelUnion.asTextChannel();
    }

    @Override
    public NewsChannel asNewsChannel() {
        return messageChannelUnion.asNewsChannel();
    }

    @Override
    public ThreadChannel asThreadChannel() {
        return messageChannelUnion.asThreadChannel();
    }

    @Override
    public VoiceChannel asVoiceChannel() {
        return messageChannelUnion.asVoiceChannel();
    }

    @Override
    public StageChannel asStageChannel() {
        return messageChannelUnion.asStageChannel();
    }

    @Override
    public IThreadContainer asThreadContainer() {
        return messageChannelUnion.asThreadContainer();
    }

    @Override
    public GuildMessageChannel asGuildMessageChannel() {
        return null;
    }

    @Override
    public AudioChannel asAudioChannel() {
        return messageChannelUnion.asAudioChannel();
    }

    @Override
    public ChannelType getType() {
        return messageChannelUnion.getType();
    }

    @Override
    public JDA getJDA() {
        return messageChannelUnion.getJDA();
    }

    @Override
    public RestAction<Void> delete() {
        return messageChannelUnion.delete();
    }

    @Override
    public long getLatestMessageIdLong() {
        return messageChannelUnion.getLatestMessageIdLong();
    }

    @Override
    public boolean canTalk() {
        return messageChannelUnion.canTalk();
    }

    @Override
    public long getIdLong() {
        return messageChannelUnion.getIdLong();
    }
}
