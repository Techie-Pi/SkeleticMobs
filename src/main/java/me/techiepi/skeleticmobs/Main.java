package me.techiepi.skeleticmobs;

import me.techiepi.skeleticmobs.mobtransformers.transformer.implementations.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
/*
        (Deprecated listeners)
        OnEntitySpawn onEntitySpawn = new OnEntitySpawn();
        OnPlayerReceiveDamage onPlayerReceiveDamage = new OnPlayerReceiveDamage();
        OnProjectileHit onProjectileHit = new OnProjectileHit();

        getServer().getPluginManager().registerEvents(onEntitySpawn, this);
        getServer().getPluginManager().registerEvents(onPlayerReceiveDamage, this);
        getServer().getPluginManager().registerEvents(onProjectileHit, this);

 */
        CreeperTransformer creeperTransformer = new CreeperTransformer();
        HuskTransformer huskTransformer = new HuskTransformer();
        VindicatorTransformer vindicatorTransformer = new VindicatorTransformer();
        ZombieTransformer zombieTransformer = new ZombieTransformer();
        PillagerTransformer pillagerTransformer = new PillagerTransformer();
        WitchTransformer witchTransformer = new WitchTransformer();

        getServer().getPluginManager().registerEvents(creeperTransformer, this);
        getServer().getPluginManager().registerEvents(huskTransformer, this);
        getServer().getPluginManager().registerEvents(vindicatorTransformer, this);
        getServer().getPluginManager().registerEvents(zombieTransformer, this);
        getServer().getPluginManager().registerEvents(pillagerTransformer, this);
        getServer().getPluginManager().registerEvents(witchTransformer, this);
    }

    @Override
    public void onDisable(){
        instance = null;
    }

    public static Main getPluginInstance(){
        return instance;
    }


}
