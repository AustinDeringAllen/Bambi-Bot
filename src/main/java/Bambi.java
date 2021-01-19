import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.object.reaction.Reaction;
import discord4j.core.object.reaction.ReactionEmoji;
import reactor.core.publisher.Mono;

import java.util.*;

public class Bambi {
    public static final String operator = "!!";

    public static void main(String[] args) {
        GatewayDiscordClient client = DiscordClientBuilder.create(DiscordKey.getKey())
                .build()
                .login()
                .block();

        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    User self = event.getSelf();
                    System.out.println(String.format("Logged in as %s#%s", self.getUsername(), self.getDiscriminator()));
                });

        TestCommands.ListenForCommands(client, operator);
        OtherCommands.ListenForCommands(client, operator);
        MovieCommands.ListenForCommands(client, operator);
        CurrencyCommands.ListenForCommands(client, operator);

        client.onDisconnect().block();
    }

    public static String getAuthor(User user) {
        return user.getUsername();
    }
}
