import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;

import java.util.HashMap;
import java.util.Random;

public class CurrencyCommands {
    public static final HashMap<String, Long> currency = new HashMap<>();

    public static void ListenForCommands(GatewayDiscordClient client, String operator) {
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    Message message = event.getMessage();
                    if(message.getContent().equalsIgnoreCase("!register")) {
                        String username = message.getAuthor().map(User::getUsername).get();
                        if(!currency.containsKey(username))
                            currency.put(username, 1000L);
                    }
                });

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    Message message = event.getMessage();
                    MessageChannel channel = message.getChannel().block();
                    if(message.getContent().equalsIgnoreCase("!currency")) {
                        String username = message.getAuthor().map(User::getUsername).get();
                        if(currency.containsKey(username)) {
                            channel.createMessage(username + " has " + currency.get(username) + " money");
                        } else {
                            channel.createMessage(username + " doesn't exist within our database");
                        }
                    }
                });

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    Message message = event.getMessage();
                    if(message.getContent().contains("!pay")) {
                        String[] userInput = message.toString().split(" ");
                        String username = message.getAuthor().map(User::getUsername).get();
                    }
                });
    }
}
