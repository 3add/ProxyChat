package group.proxychat.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import group.proxychat.ProxyChat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

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
                for (RegisteredServer server : velocity.getVelocity().getAllServers()) {
                    for (Player serverPlayer : server.getPlayersConnected()) {
                        serverPlayer.sendMessage(MiniMessage.miniMessage().deserialize(
                                "<newline><color:#ffde4a><b>NETWORK</b></color> <gray>[<white>" + sender.getCurrentServer().get().getServerInfo().getName() + "</white>]</gray> <dark_gray>»</dark_gray> " + toBroadcast + "<newline> "
                        ));
                    }
                }
            }
        } else {
            invocation.source().sendMessage(MiniMessage.miniMessage().deserialize(
                    "<red>You don't have the permisison to use this command."
            ));
        }
    }
}
