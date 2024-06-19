package group.proxychat.Listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;
import group.proxychat.ProxyChat;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ProxyQuit {


    private final ProxyChat velocity;
    public ProxyQuit(ProxyChat velocity) {
        this.velocity = velocity;
    }

    @Subscribe
    public void onQuit(DisconnectEvent event) {
        for (Player player : velocity.getVelocity().getAllPlayers()) {
            if (player.hasPermission("quit.notify")) {
                player.sendMessage(MiniMessage.miniMessage().deserialize(
                        "<color:#b8b8b8>[INFO]</color> <dark_gray>[<gray>NETWORK<dark_gray>] <gray>[<red>❌<gray>] <white>" + event.getPlayer().getUsername()
                ));
            }
        }
    }
}
