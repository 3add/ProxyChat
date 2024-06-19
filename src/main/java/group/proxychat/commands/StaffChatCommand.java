package group.proxychat.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import group.proxychat.ProxyChat;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.yaml.snakeyaml.Yaml;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class StaffChatCommand implements SimpleCommand {

    private final ProxyChat velocity;
    public StaffChatCommand(ProxyChat velocity) {
        this.velocity = velocity;
    }

    @Override
    public void execute(final Invocation invocation) {
        String[] args = invocation.arguments();
        CommandSource source = invocation.source();
        Player sender = (Player) source;

        if (args.length < 0) {
            source.sendMessage(MiniMessage.miniMessage().deserialize(
                    "<red>/sc <message>"
            ));
            return;
        } else {
            File configFile = new File("./plugins/ProxyChat/config.yml");

            InputStream stream = null;
            try {
                stream = new FileInputStream(configFile);
            } catch (IOException e){}

            Yaml yaml = new Yaml();

            Map<String, Object> data = yaml.load(stream);


            Map<String, Object> staffMap = (Map<String, Object>) data.get("staffchat");
            String format = staffMap.get("format").toString();

            String message = String.join(" ", args).trim();

            format = format.replaceAll("%playerserver%", sender.getCurrentServer().get().getServer().getServerInfo().getName());
            format = format.replaceAll("%playername%", source.get(Identity.NAME).orElse("NONE"));
            format = format.replaceAll("%staffmessage%", message);


            if (!(message.isBlank())) {
                for (RegisteredServer server : velocity.getVelocity().getAllServers()) {

                    for (Player player : server.getPlayersConnected()) {
                        if (player.hasPermission("staffchat.network")) {
                            player.sendMessage(MiniMessage.miniMessage().deserialize(format));
                        }
                    }
                }
            } else {
                source.sendMessage(MiniMessage.miniMessage().deserialize(
                        "<red>Usage: /staffchat <message>"
                ));
            }

        }
    }


    @Override
    public boolean hasPermission(final Invocation inovation) {
        File configFile = new File("./plugins/ProxyChat/config.yml");

        InputStream stream = null;
        try {
            stream = new FileInputStream(configFile);
        } catch (IOException e){}

        Yaml yaml = new Yaml();

        Map<String, Object> data = yaml.load(stream);


        Map<String, Object> staffMap = (Map<String, Object>) data.get("staffchat");


        return inovation.source().hasPermission(staffMap.get("permission").toString());
    }

}
