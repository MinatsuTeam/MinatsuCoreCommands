package us.tryy3.minatsu.plugins.minatsucorecommands.commands;

import us.tryy3.java.minatsu.Bot;
import us.tryy3.java.minatsu.TCPServer;
import us.tryy3.java.minatsu.command.Command;
import us.tryy3.java.minatsu.utils.MessageBuilder;
import us.tryy3.minatsu.plugins.minatsucorecommands.Utils;
import us.tryy3.minatsu.plugins.minatsupermissions.PermissionsApi;

/**
 * Created by tryy3 on 2016-01-12.
 */
public class InfoCMD extends Command {
    private Bot bot;
    private PermissionsApi perms;

    public InfoCMD(String name, Bot bot, PermissionsApi api) {
        super(name);

        this.bot = bot;
        this.perms = api;

        this.setUsage("info");
        this.setDescription("Show info about the bot, uptime, memory usage etc.");
    }

    @Override
    public Boolean onCommand(TCPServer.Connection connection, String user, String channel, Command command, String label, String[] args) {
        if (perms.hasPlayerPermission(user, "core.commands.info")) {
            String uptime = Utils.uptime(bot.getStartupTime());
            long maxM = Runtime.getRuntime().maxMemory() / 1024 / 1024;
            long totM = Runtime.getRuntime().totalMemory() / 1024 / 1024;
            long freeM = Runtime.getRuntime().freeMemory() / 1024 / 1024;
            String cpu = ""; // TODO: Implement CPU usage in the future.
            int plugins = bot.getPlugins().size();
            int listeners = bot.getTcpServer().getConnections().size();

            connection.sendMessage(new MessageBuilder()
                    .addMessage(channel, String.format("Uptime: %s", uptime))
                    .addMessage(channel, String.format("Maximum memory: %s MB", maxM))
                    .addMessage(channel, String.format("Allocated memory: %s MB", totM))
                    .addMessage(channel, String.format("Free memory: %s MB", freeM))
                    .addMessage(channel, String.format("Plugin count: %s", plugins))
                    .addMessage(channel, String.format("Listener count: %s", listeners))
                    .build());
        }

        return true;
    }
}
