package us.tryy3.minatsu.plugins.minatsucorecommands.commands;

import us.tryy3.java.minatsu.Bot;
import us.tryy3.java.minatsu.TCPServer;
import us.tryy3.java.minatsu.command.Command;
import us.tryy3.java.minatsu.utils.MessageBuilder;
import us.tryy3.minatsu.plugins.minatsucorecommands.NumberUtil;
import us.tryy3.minatsu.plugins.minatsucorecommands.Paginator;
import us.tryy3.minatsu.plugins.minatsucorecommands.Paginator.Page;
import us.tryy3.minatsu.plugins.minatsupermissions.PermissionsApi;

/**
 * Created by tryy3 on 2016-01-12.
 */
public class HelpCMD extends Command {
    private Bot bot;
    private PermissionsApi perms;

    public HelpCMD(String name, Bot bot, PermissionsApi api) {
        super(name);

        this.bot = bot;
        this.perms = api;

        this.setUsage("help [cmd] [page]");
        this.setDescription("Show a help page on all commands or specific commands.");
    }

    @Override
    public Boolean onCommand(TCPServer.Connection connection, String user, String channel, Command command, String label, String[] args) {
        if (perms.hasPlayerPermission(user, "core.commands.help")) {
            MessageBuilder builder = new MessageBuilder();

            if (args == null) {
                Paginator paginator = new Paginator(10);

                for (Command cmd : bot.getCommandManager().getCommands()) {
                    paginator.addPage("!" + cmd.getName() + ": " + cmd.getDescription());
                }

                sendPage(channel, connection, paginator, 0);
            } else if ( NumberUtil.isInt(args[0])) {
                Paginator paginator = new Paginator(10);

                for (Command cmd : bot.getCommandManager().getCommands()) {
                    paginator.addPage("!" + cmd.getName() + ": " + cmd.getDescription());
                }

                sendPage(channel, connection, paginator, Integer.parseInt(args[0]));
            } else {
                Paginator paginator = new Paginator(10);

                for (Command cmd : bot.getCommandManager().getCommands()) {
                    if (cmd.getName().equalsIgnoreCase(args[0].toLowerCase())) {
                        paginator.addPage("!" + cmd.getName() + ": " + cmd.getDescription());
                    }
                }

                sendPage(channel, connection, paginator, (args.length > 1 && NumberUtil.isInt(args[1]) ? Integer.parseInt(args[1]) : 0));
            }
        }

        return true;
    }

    public void sendPage(String channel, TCPServer.Connection connection, Paginator paginator, int p) {
        MessageBuilder builder = new MessageBuilder();
        Page page = paginator.getPage(p);

        if (page == null) {
            connection.sendMessage(builder.addMessage(channel, "This isn't a valid page, max page is " + paginator.size()).build());
            return;
        }
        for (String s : page.getText()) {
            builder.addMessage(channel, s);
        }

        connection.sendMessage(builder.build());
    }
}
