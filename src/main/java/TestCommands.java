import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Mono;

public class TestCommands {
    public static void ListenForCommands(GatewayDiscordClient client, String operator) {
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase(operator + "ping"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Pong!"))
                .subscribe();

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                   Message message = event.getMessage();
                   if(message.getContent().contains(operator + "test")) {
                       String[] strings = message.getContent().split(" ");
                       MessageChannel channel = message.getChannel().block();
                       Snowflake userId = message.getAuthor().map(User::getId).get();
                       String string = message.getContent();
//                       System.out.println(strings[1]);
                       System.out.println(userId);

                       for(int i=0; i<strings.length; i++) {
                           System.out.println(strings[i]);
                       }
//                       channel.createMessage(string).block();
                   }
                });

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    Message message = event.getMessage();
                    if(message.getContent().equalsIgnoreCase("$me")) {
                        MessageChannel channel = message.getChannel().block();
                        channel.createMessage(message.getAuthor().map(User::getAvatarUrl).get()).block();
                    }
                });

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    Message message = event.getMessage();
                    if(message.getContent().equalsIgnoreCase("!name")) {
                        MessageChannel channel = message.getChannel().block();
                        channel.createMessage(message.getAuthor().map(User::getUsername).get()).block();
                    }
                });

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    Message message = event.getMessage();
                    if(message.getContent().equalsIgnoreCase("!name")) {
                        MessageChannel channel = message.getChannel().block();
                        channel.createMessage(String.valueOf(message.getAuthor().map(User::getId).get())).block();
                    }
                });

        client.getEventDispatcher().on(MessageUpdateEvent.class)
                .subscribe(event -> {
                    Mono<Message> message =  event.getMessage();
                    Message message1 = message.block();
                    MessageChannel channel = message1.getChannel().block();
                    channel.createMessage("DYLAN").block();
                });
    }
}
