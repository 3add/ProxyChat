package group.proxychat.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import group.proxychat.ProxyChat;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class BroadcastCommand implements SimpleCommand {
    ProxyChat velocity;

    public BroadcastCommand(ProxyChat velocity) {
        this.velocity = velocity;
    }

    @Override
    public void execute(final Invocation invocation) {
        if (invocation.source().hasPermission("broadcast.network")) {
            String toBroadcast = String.join(" ", invocation.arguments()).trim();
            Player sender = (Player) invocation.source();
            if (toBroadcast.isBlank()) {
                invocation.source().sendMessage(MiniMessage.miniMessage().deserialize(
                        "<red>Usage: /networkbroadcast <Message you want to broadcast>"
                ));
            } else {
                File configFile = new File("./plugins/ProxyChat/config.yml");

                InputStream stream = null;
                try {
                    stream = new FileInputStream(configFile);
                } catch (IOException e){}

                Yaml yaml = new Yaml();

                Map<String, Object> data = yaml.load(stream);


                Map<String, Object> staffMap = (Map<String, Object>) data.get("broadcast");
                String format = staffMap.get("format").toString();

                String message = String.join(" ", invocation.arguments()).trim();

                format = format.replaceAll("%broadcastserver%", sender.getCurrentServer().get().getServer().getServerInfo().getName());
                format = format.replaceAll("%broadcastmessage%", message);

                for (Player proxiedPlayer : velocity.getVelocity().getAllPlayers()) {
                    proxiedPlayer.sendMessage(MiniMessage.miniMessage().deserialize(format));
                }
            }
        } else {
            invocation.source().sendMessage(MiniMessage.miniMessage().deserialize(
                    "<red>You don't have the permisison to use this command."
            ));
        }
    }
}
