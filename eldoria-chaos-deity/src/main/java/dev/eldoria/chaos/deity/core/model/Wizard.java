package dev.eldoria.chaos.deity.core.model;

import dev.eldoria.amulet.core.model.Mage;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.requests.restaction.CacheRestAction;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Wizard extends Mage implements User {

    private User jdaUser;

    @Override
    public String getGlobalName() {
        return jdaUser.getGlobalName();
    }

    @NotNull
    @Override
    public String getName() {
        return jdaUser.getName();
    }

    @NotNull
    @Override
    public String getId() {
        return jdaUser.getId();
    }


    @NotNull
    @Override
    public String getDiscriminator() {
        return jdaUser.getDiscriminator();
    }

    @Override
    public String getAvatarId() {
        return jdaUser.getAvatarId();
    }

    @NotNull
    @Override
    public CacheRestAction<Profile> retrieveProfile() {
        return jdaUser.retrieveProfile();
    }

    @NotNull
    @Override
    public String getAsTag() {
        return jdaUser.getAsTag();
    }

    @Override
    public boolean hasPrivateChannel() {
        return jdaUser.hasPrivateChannel();
    }

    @NotNull
    @Override
    public CacheRestAction<PrivateChannel> openPrivateChannel() {
        return jdaUser.openPrivateChannel();
    }

    @NotNull
    @Override
    public List<Guild> getMutualGuilds() {
        return jdaUser.getMutualGuilds();
    }

    @NotNull
    @Override
    public JDA getJDA() {
        return jdaUser.getJDA();
    }

    @NotNull
    @Override
    public EnumSet<UserFlag> getFlags() {
        return jdaUser.getFlags();
    }

    @Override
    public int getFlagsRaw() {
        return jdaUser.getFlagsRaw();
    }

    @NotNull
    @Override
    public String getDefaultAvatarId() {
        return jdaUser.getDefaultAvatarId();
    }

    @NotNull
    @Override
    public String getAsMention() {
        return jdaUser.getAsMention();
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(id);
    }

    @Override
    public boolean isSystem() {
        return jdaUser.isSystem();
    }

    @Override
    public boolean isBot() {
        return jdaUser.isBot();
    }

    @Override
    public void sendMessage(String message) {
        openPrivateChannel().queue(privateChannel ->
                privateChannel.sendMessage(message).queue());
    }
}