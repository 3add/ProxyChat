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
            String message = String.join(" ", args).trim();

            if (!(message.isBlank())) {
                for (RegisteredServer server : velocity.getVelocity().getAllServers()) {

                    for (Player player : server.getPlayersConnected()) {
                        if (player.hasPermission("staffchat.network")) {
                            player.sendMessage(MiniMessage.miniMessage().deserialize(
                                    "<gray>[</gray><color:#ff0004>Staff</color><gray>]</gray> <gray>[" + sender.getCurrentServer().get().getServer().getServerInfo().getName() + "]</gray> <color:#ff988f>" + source.get(Identity.NAME).orElse("NONE") + ":</color> " + message
                            ));
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

        return inovation.source().hasPermission("staffchat.network");
    }

}
