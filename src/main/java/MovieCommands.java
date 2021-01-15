import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.reaction.Reaction;
import discord4j.core.object.reaction.ReactionEmoji;

import java.util.ArrayList;
import java.util.Set;

public class MovieCommands {
    public static final ArrayList<String> strings = new ArrayList<>();
    public static void ListenForCommands(GatewayDiscordClient client, String operator) {
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    Message message = event.getMessage();
                    if(message.getContent().contains("!addMovie")) {
                        String[] string = message.getContent().split("!addMovie");
                        strings.add(string[1].trim());
                    }
                });

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    Message message = event.getMessage();
                    if(message.getContent().contains("!removeMovie")) {
                        String[] string = message.getContent().split("!removeMovie");

                        for(String movie : strings) {
                            if(movie.toLowerCase().contains(string[1].trim().toLowerCase())) {
                                strings.remove(movie);
                            }
                        }

                        message.getChannel().block().createMessage("Movie Removed").block();
                    }
                });

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    Message message = event.getMessage();
                    if(message.getContent().equalsIgnoreCase("!listMovies")) {
                        MessageChannel channel = message.getChannel().block();
                        String movieList = "";
                        for(String movie : strings) {
                            movieList += movie + "\n";
                        }
                        channel.createMessage(movieList).block();
                    }
                });

        client.getEventDispatcher().on(ReactionAddEvent.class)
                .subscribe(event -> {
                    Message message = event.getMessage().block();
                    Set<Reaction> reactions = message.getReactions();

                    for(Reaction reaction : reactions) {
                        if(reaction.getEmoji().equals(ReactionEmoji.unicode("✅"))) {
//                            message.addReaction(ReactionEmoji.unicode("0️⃣")).block();
                            message.addReaction(ReactionEmoji.unicode("1️⃣")).block();
                            message.addReaction(ReactionEmoji.unicode("2️⃣")).block();
                            message.addReaction(ReactionEmoji.unicode("3️⃣")).block();
                            message.addReaction(ReactionEmoji.unicode("4️⃣")).block();
                            message.addReaction(ReactionEmoji.unicode("5️⃣")).block();
                            message.addReaction(ReactionEmoji.unicode("6️⃣")).block();
                            message.addReaction(ReactionEmoji.unicode("7️⃣")).block();
                            message.addReaction(ReactionEmoji.unicode("8️⃣")).block();
                            message.addReaction(ReactionEmoji.unicode("9️⃣")).block();
                            message.addReaction(ReactionEmoji.unicode("\uD83D\uDD1F")).block();
                        }
                    }
                });
    }
}
