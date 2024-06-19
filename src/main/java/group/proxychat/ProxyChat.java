package group.proxychat;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import group.proxychat.commands.BroadcastCommand;
import group.proxychat.commands.StaffChatCommand;
import org.slf4j.Logger;

@Plugin(
        id = "proxychat",
        name = "ProxyChat",
        version = BuildConstants.VERSION,
        description = "The proxy chat sytem made by 3add",
        authors = {"3add"}
)

public class ProxyChat {

    private final ProxyServer server;
    private final Logger logger;

    public ProxyServer getVelocity() {
        return server;
    }


    @Inject
    public ProxyChat(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;

        logger.info("ProxyChat has successfully enabled.");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        CommandManager cmdManager = server.getCommandManager();

        CommandMeta commandMeta = cmdManager.metaBuilder("staffchat")
                .aliases("sc")
                .plugin(this)
                .build();

        SimpleCommand command = new StaffChatCommand(this);
        cmdManager.register(commandMeta, command);

        commandMeta = cmdManager.metaBuilder("networkbroadcast")
                .aliases("netbc")
                .plugin(this)
                .build();

        command = new BroadcastCommand(this);
        cmdManager.register(commandMeta, command);
    }
}
