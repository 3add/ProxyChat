package group.proxychat.Listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import group.proxychat.ProxyChat;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ServerMove {

    private final ProxyChat velocity;
    public ServerMove(ProxyChat velocity) {
        this.velocity = velocity;
    }

    @Subscribe
    public void onServerMove(ServerConnectedEvent event) {
        for (Player player : velocity.getVelocity().getAllPlayers()) {
            if (player.hasPermission("quit.notify")) {
                player.sendMessage(MiniMessage.miniMessage().deserialize(
                        "<color:#b8b8b8>[INFO]</color> <white>" + event.getPlayer().getUsername() + " <dark_gray>[<gray>" + event.getPreviousServer().get().getServerInfo().getName() + "<dark_gray>] <white>-> <dark_gray>[<gray>" + event.getServer().getServerInfo().getName() + "<dark_gray>]"
                ));
            }
        }
    }
}
