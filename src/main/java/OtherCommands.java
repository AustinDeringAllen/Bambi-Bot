import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;

import java.util.Random;

public class OtherCommands {
    public static int bullets = 6;

    public static void ListenForCommands(GatewayDiscordClient client, String operator) {

        // Russian Roulette
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    Message message = event.getMessage();
                    if(message.getContent().equalsIgnoreCase("!rr")) {
                        Random random = new Random();
                        int randNum = random.nextInt(bullets)+1;
                        bullets--;
                        MessageChannel channel = message.getChannel().block();
                        String username = message.getAuthor().map(User::getUsername).get();
                        if(randNum == 1) {
                            channel.createMessage("Bang! " + username + " is dead.").block();
                            channel.createMessage("Reloading ~ 6 bullets remain").block();
                            bullets = 6;
                        } else {
                            channel.createMessage("click. " + username + " lives to see another day.").block();
                            channel.createMessage(bullets + " remain.").block();
                        }
                        if (bullets == 1) {
                            channel.createMessage("One bullet left. Reloading~").block();
                            bullets = 6;
                        }
                    }
                });

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    Message message = event.getMessage();
                    MessageChannel channel = message.getChannel().block();
                    if(message.getContent().contains(operator + "roll " + "d")) {
                        String[] string = message.getContent().split("d");
                        int number = Integer.parseInt(string[1]);
                        Random random = new Random();
                        int randNum = random.nextInt(number) + 1;
                        System.out.println(randNum);
                    }
                });

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    Message message = event.getMessage();
                    MessageChannel channel = message.getChannel().block();
                    if(message.getContent().equalsIgnoreCase(operator + "draw")) {
                        String card;
                        String suit;
                        Random random = new Random();
                        int cardNum = random.nextInt(13) + 1;
                        int suitNum = random.nextInt(4) + 1;
                    }
                });
    }
}
