package me.tulio.edgeregulator;

import com.google.common.collect.Lists;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import lombok.Getter;
import me.tulio.edgeregulator.command.EdgeRegulatorCommand;
import me.tulio.edgeregulator.cuboid.Cuboid;
import me.tulio.edgeregulator.entry.Data;
import me.tulio.edgeregulator.listener.EventsListener;
import me.tulio.edgeregulator.utilities.FileConfig;
import me.tulio.edgeregulator.utilities.Util;
import me.tulio.edgeregulator.utilities.commands.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Main extends JavaPlugin {

    private final List<Data> data = Lists.newArrayList();

    private FileConfig mainConfig;
    private WorldGuardPlugin worldGuard;

    @Override
    public void onEnable() {
        this.mainConfig = new FileConfig(this, "config.yml");
        this.worldGuard = WorldGuardPlugin.inst();

        getServer().getPluginManager().registerEvents(new EventsListener(), this);

        new CommandManager(this, Lists.newArrayList());
        new EdgeRegulatorCommand();

        init();
    }

    public void init() {
        data.clear();
        Util.consoleLogger("&aGetting WorldGuard regions...");
        Util.consoleLogger("");
        AtomicInteger loaded = new AtomicInteger();
        for (RegionManager regionManager : worldGuard.getRegionContainer().getLoaded()) {
            regionManager.getRegions().forEach((s, protectedRegion) -> {
                data.add(new Data(s, new Cuboid(protectedRegion.getMinimumPoint(), protectedRegion.getMaximumPoint(),
                        regionManager.getName())));
                Util.consoleLogger("&aAdding...");
                loaded.getAndIncrement(); // No me calenté la cabeza del pk no funcionaba el ++ siendo forEach zzzz
            });
        }
        Util.consoleLogger("");
        Util.consoleLogger("&aLoaded " + loaded.get() + " regions.");
    }

    public static Main get() {
        return getPlugin(Main.class);
    }
}
