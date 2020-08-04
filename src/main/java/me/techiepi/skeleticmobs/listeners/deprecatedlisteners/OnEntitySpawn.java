package me.techiepi.skeleticmobs.listeners.deprecatedlisteners;

import me.techiepi.skeleticmobs.mobtransformers.transformer.implementations.CreeperTransformer;
import me.techiepi.skeleticmobs.mobtransformers.transformer.implementations.HuskTransformer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class OnEntitySpawn implements Listener {

    @EventHandler
    @Deprecated
    public void onEntitySpawn(EntitySpawnEvent event){

        switch(event.getEntityType()){
            case HUSK:
                HuskTransformer huskTransformer = new HuskTransformer();
                huskTransformer.start(event.getEntity());
                System.out.println("Husk at " + event.getEntity().getLocation());
                break;
            case CREEPER:
                CreeperTransformer creeperTransformer = new CreeperTransformer();
                creeperTransformer.start(event.getEntity());
                System.out.println("Creeper at " + event.getEntity().getLocation());
                break;
        }
    }


}
