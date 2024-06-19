package group.proxychat;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import group.proxychat.Listeners.PlayerJoin;
import group.proxychat.Listeners.ProxyQuit;
import group.proxychat.Listeners.ServerMove;
import group.proxychat.commands.BroadcastCommand;
import group.proxychat.commands.StaffChatCommand;
import org.slf4j.Logger;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        Yaml yaml = null;

        File mainDir = new File("./plugins/ProxyChat");
        if (!(mainDir.exists())) {
            mainDir.mkdir();
        }

        File configFile = new File("./plugins/ProxyChat/config.yml");
        if (!(configFile.exists())) {
            try {
                configFile.createNewFile();
                Map<String, Object> elements = new HashMap<>();
                Map<String, Object> staffchatElements = new HashMap<>();
                Map<String, Object> broadcastElements = new HashMap<>();

                staffchatElements.put("format", "<gray>[</gray><color:#ff0004>Staff</color><gray>]</gray> <gray>[%playerserver%]</gray> <color:#ff988f>%playername%:</color> %staffmessage%");
                staffchatElements.put("permission", "staffchat.network");

                broadcastElements.put("format", "<newline><color:#ffde4a><b>NETWORK</b></color> <gray>[<white>%broadcastserver%</white>]</gray> <dark_gray>»</dark_gray> %broadcastmessage%<newline> ");
                broadcastElements.put("permission", "broadcast.network");

                elements.put("staffchat", staffchatElements);
                elements.put("broadcast", broadcastElements);

                PrintWriter writer = new PrintWriter(configFile);

                final DumperOptions options = new DumperOptions();
                options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                options.setPrettyFlow(true);
                yaml = new Yaml(options);

                yaml.dump(elements, writer);

            } catch (IOException e) {
                logger.error("Error creating config.yml File EXCEPTION: " + e);
            }
        }



        logger.info("ProxyChat has successfully enabled. Made by 3add.");

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

        server.getEventManager().register(this, new PlayerJoin(this));
        server.getEventManager().register(this, new ProxyQuit(this));
        server.getEventManager().register(this, new ServerMove(this));

    }
}
