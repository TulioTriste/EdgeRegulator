package me.tulio.edgeregulator.command;

import me.tulio.edgeregulator.Main;
import me.tulio.edgeregulator.utilities.CC;
import me.tulio.edgeregulator.utilities.commands.BaseCommand;
import me.tulio.edgeregulator.utilities.commands.Command;
import me.tulio.edgeregulator.utilities.commands.CommandArgs;
import org.bukkit.entity.Player;

public class EdgeRegulatorCommand extends BaseCommand {

    @Command(name = "edgeregulator", permission = "edgeregulator.command")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&cPor favor use: /" + command.getLabel() + " <reload>"));
        }
        else if (args[0].equalsIgnoreCase("reload")) {
            long start = System.currentTimeMillis();
            Main.get().init();
            Main.get().getMainConfig().reload();
            long finish = System.currentTimeMillis();
            player.sendMessage(CC.translate("&aPlugin reiniciado correctamente! &7(" + (finish - start) + "ms)"));
        } else {
            player.sendMessage(CC.translate("&cPor favor use: /" + command.getLabel() + " <reload>"));
        }
    }
}
