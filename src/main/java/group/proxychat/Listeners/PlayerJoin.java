package group.proxychat.Listeners;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;
import group.proxychat.ProxyChat;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class PlayerJoin {

    private final ProxyChat velocity;
    public PlayerJoin(ProxyChat velocity) {
        this.velocity = velocity;
    }


    @Subscribe
    public void onPlayerJoin(LoginEvent event) {
        for (Player player : velocity.getVelocity().getAllPlayers()) {
            if (player.hasPermission("join.notify")) {
                player.sendMessage(MiniMessage.miniMessage().deserialize(
                        "<color:#b8b8b8>[INFO]</color> <dark_gray>[<gray>" + player.getCurrentServer().get().getServer().getServerInfo().getName() + "<dark_gray>] <gray>[<green>✔<gray>] <white>" + event.getPlayer().getUsername()
                ));
            }
        }
    }
}
