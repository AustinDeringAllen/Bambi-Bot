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
    public final String operator = "$";
    public static final ArrayList<String> strings = new ArrayList<>();
    public static int bullets = 6;
    public static final HashMap<String, Long> currency = new HashMap<>();

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

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("$ping"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Pong!"))
                .subscribe();

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("$Ching"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Chong!"))
                .subscribe();

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!rrr"))
                .flatMap(Message::getChannel)
                .subscribe();

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
                   if(message.getContent().equalsIgnoreCase("!register")) {
                       String username = message.getAuthor().map(User::getUsername).get();
                       currency.put(username, 1000L);
                   }
                });

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                   Message message = event.getMessage();
                   if(message.getContent().equalsIgnoreCase("!currency")) {

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

//        client.getEventDispatcher().on(MessageCreateEvent.class)
//                .subscribe(event -> {
//                    Message message = event.getMessage();
//                    if(message.getContent().equalsIgnoreCase("!me")) {
//                        MessageChannel channel = message.getChannel().block();
//                        channel.createMessage(message.getAuthor().map(User::getUsername).get()).block();
//
//                });


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
                        for(String movie : strings) {
                            channel.createMessage(movie).block();
                        }
                    }
                });

//        client.getEventDispatcher().on(MessageCreateEvent.class)
//                .subscribe(event -> {
//                    Message message = event.getMessage();
//                    if(message.getContent().equalsIgnoreCase("!join")) {
//                        Member member = event.getMember().orElse(null);
//                        if(member != null) {
//                            VoiceState voiceState = member.getVoiceState().block();
//                            if(voiceState != null) {
//                                VoiceChannel channel = voiceState.getChannel().block();
//                                if(channel != null) {
//                                    channel.join(spec -> spec.setProvider(provider)).block();
//                                }
//                            }
//                        }
//                    }
//                });

        client.getEventDispatcher().on(MessageUpdateEvent.class)
                .subscribe(event -> {
                    Mono<Message> message =  event.getMessage();
                    Message message1 = message.block();
                    MessageChannel channel = message1.getChannel().block();
                    channel.createMessage("DYLAN").block();
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

        client.onDisconnect().block();
    }

    public static String getAuthor(User user) {
        return user.getUsername();
    }
}
