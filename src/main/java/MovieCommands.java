import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

import java.util.ArrayList;

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
    }
}
