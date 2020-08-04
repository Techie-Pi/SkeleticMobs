package me.techiepi.skeleticmobs.listeners.deprecatedlisteners;

import me.techiepi.skeleticmobs.Main;
import me.techiepi.skeleticmobs.mobtransformers.transformer.implementations.CreeperTransformer;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class OnProjectileHit implements Listener {

    @EventHandler
    @Deprecated
    public void ProjectileHit(ProjectileHitEvent event){
        if(event.getEntity().getShooter() instanceof Skeleton){
            CheckCreeperSkeleton((Arrow) event.getEntity());
            event.getEntity().remove();
        }
    }

    @Deprecated
    private void CheckCreeperSkeleton(@NotNull Arrow arrow) {
        Skeleton skeleton = (Skeleton) arrow.getShooter();

        NamespacedKey key = new NamespacedKey(Main.getPluginInstance(), "skeleticmob-type");
        PersistentDataContainer container = Objects.requireNonNull(skeleton).getPersistentDataContainer();

        if(container.has(key, PersistentDataType.STRING)) {
            String value = container.get(key, PersistentDataType.STRING);
            System.out.println(value);

            CreeperTransformer creeperTransformer = new CreeperTransformer();
            creeperTransformer.onAttack(null, arrow);
        }
    }
}
