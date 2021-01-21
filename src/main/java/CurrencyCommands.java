import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class CurrencyCommands {
    public static final HashMap<Long, Long> currency = new HashMap<>();
    public static final HashMap<Snowflake, Long> testCurrency = new HashMap<>();

    public static void ListenForCommands(GatewayDiscordClient client, String operator) {
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    Message message = event.getMessage();
                    if (message.getContent().equalsIgnoreCase(operator + "register")) {
                        String username = message.getAuthor().map(User::getUsername).get();
                        Snowflake userId = message.getAuthor().map(User::getId).get();
                        if (!currency.containsKey(username)) {
                            currency.put(userId.asLong(), 1000L);
                            for (Map.Entry<Long, Long> entry : currency.entrySet()) {
                                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                            }
                        }
                    }
                });

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    Message message = event.getMessage();
                    MessageChannel channel = message.getChannel().block();
                    if (message.getContent().equalsIgnoreCase(operator + "currency")) {
                        String username = message.getAuthor().map(User::getUsername).get();
                        Long userId = message.getAuthor().map(User::getId).get().asLong();
                        if (currency.containsKey(userId)) {
                            channel.createMessage(username + " has " + currency.get(userId) + " money").block();
                        } else {
                            channel.createMessage(username + " doesn't exist within our database").block();
                        }
                    }
                });

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                   Message message = event.getMessage();
                   MessageChannel channel = message.getChannel().block();
                   if(message.getContent().contains(operator + "pay")) {
                       String[] userInput = message.getContent().split(" ");
                       Long userId = message.getAuthor().map(User::getId).get().asLong();
                       Long recipientId = Long.parseLong(userInput[1].substring(3,userInput[1].length()-1));
                       Long amount = Long.parseLong(userInput[2]);

                       if(currency.containsKey(userId)) {
                           if(currency.containsKey(recipientId)) {

                           } else {
                               System.out.println("Recipient doesn't exist in database");
                           }
                       } else {
                           System.out.println("User doesn't exist in database");
                       }
                   }
                });
    }
}