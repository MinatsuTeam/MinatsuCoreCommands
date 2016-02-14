package us.tryy3.minatsu.plugins.minatsucorecommands.commands;

import us.tryy3.java.minatsu.Bot;
import us.tryy3.java.minatsu.TCPServer;
import us.tryy3.java.minatsu.command.Command;
import us.tryy3.java.minatsu.utils.MessageBuilder;
import us.tryy3.minatsu.plugins.minatsupermissions.PermissionsApi;

import java.util.Collections;
import java.util.UUID;

/**
 * Created by tryy3 on 2016-01-12.
 */
public class ListenersCMD extends Command {
    private Bot bot;
    private PermissionsApi perms;

    public ListenersCMD(String name, Bot bot, PermissionsApi api) {
        super(name);

        this.bot = bot;
        this.perms = api;

        this.setUsage("listener");
        this.setAliases(Collections.singletonList("listeners"));
        this.setDescription("Show a list of listeners.");
    }

    @Override
    public Boolean onCommand(TCPServer.Connection connection, String user, String channel, Command command, String label, String[] args) {
        if (perms.hasPlayerPermission(user, "core.commands.listeners")) {
            MessageBuilder builder = new MessageBuilder();
            if (args == null || args.length < 1) {
                builder.addMessage(channel, "Listener connections");
                int i = 1;
                for (UUID id : bot.getTcpServer().getConnections().keySet()) {
                    builder.addMessage(channel, String.format("%s: %s", i++, id));
                }
                connection.sendMessage(builder.build());
            } else {
                TCPServer.Connection con = bot.getTcpServer().getConnection(UUID.fromString(args[0]));
                if (con == null) {
                    connection.sendMessage(channel, String.format("There is no listener with the UUID %s!", args[0]));
                } else {
                    builder.addMessage(channel, String.format("Information about the listener %s!", args[0]));
                    builder.addMessage(channel, String.format("Name: %s", con.getListenerName()));
                    builder.addMessage(channel, String.format("Type: %s", con.getType()));
                    builder.addMessage(channel, String.format("VersionListener: %s", con.getVersionListener()));
                    builder.addMessage(channel, String.format("VersionStandard: %s", con.getVersionStandard()));

                    connection.sendMessage(builder.build());
                }
            }
        }

        return true;
    }
}
