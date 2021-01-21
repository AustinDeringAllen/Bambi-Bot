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
                   }
                });
    }
}
//
//        client.getEventDispatcher().on(MessageCreateEvent.class)
//                .subscribe(event -> {
//                    Message message = event.getMessage();
//                    MessageChannel channel = message.getChannel().block();
//                    if(message.getContent().contains("!pay")) {
//                        String[] userInput = message.toString().split(" ");
//                        String username = message.getAuthor().map(User::getUsername).get();
//                        String recipient = "";
//                        long amount = Long.parseLong(userInput[userInput.length-1]);
//                        for(int i=1; i<userInput.length-1; i++) {
//                            recipient += userInput[i];
//                        }
//
//                        if(currency.containsKey(username)) {
//                            if(currency.containsKey(recipient)) {
//                                if(currency.get(username) - amount < 0) {
//                                    currency.put(username, currency.get(username) - amount);
//                                    currency.put(recipient, currency.get(recipient + amount));
//                                    channel.createMessage("Transaction Complete");
//                                } else {
//                                    channel.createMessage("User trying to send money does not have enough");
//                                }
//                            } else {
//                                channel.createMessage("User to receive send money isn't registered");
//                            }
//                        } else {
//                            channel.createMessage("User trying to send money isn't registered");
//                        }
//                    }
//                });
//    }
//
//    public static int checkForErrors(String username, String recipient, long amount) {
//        if(!currency.containsKey(username)) {
//            return 1;
//        }
//
//        if(!currency.containsKey(recipient))
//            return 2;
//
//        if(currency.get(username) - amount < 0)
//            return 3;
//
//        return 0;
//    };
//
//    public static boolean returnErrorMessage(int errorNumber, MessageChannel channel) {
//        switch (errorNumber) {
//            case 1:
//                channel.createMessage("User trying to send money isn't registered");
//                return false;
//
//            case 2:
//                channel.createMessage("User to receive send money isn't registered");
//                return false;
//
//            case 3:
//                channel.createMessage("User trying to send money doesn't have enough");
//                return false;
//
//            default:
//                return true;
//        }
//    }
//}