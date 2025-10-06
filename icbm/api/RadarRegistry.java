// 
// Decompiled by Procyon v0.6.0
// 

package icbm.api;

import java.util.HashSet;
import java.util.Iterator;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.vector.Region2;
import java.util.ArrayList;
import java.util.List;
import universalelectricity.core.vector.Vector2;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import java.util.Set;

public class RadarRegistry
{
    private static Set<TileEntity> detectableTileEntities;
    private static Set<Entity> detectableEntities;
    
    public static void register(final TileEntity tileEntity) {
        if (!RadarRegistry.detectableTileEntities.contains(tileEntity)) {
            RadarRegistry.detectableTileEntities.add(tileEntity);
        }
    }
    
    public static void unregister(final TileEntity tileEntity) {
        if (RadarRegistry.detectableTileEntities.contains(tileEntity)) {
            RadarRegistry.detectableTileEntities.remove(tileEntity);
        }
    }
    
    public static void register(final Entity entity) {
        if (!RadarRegistry.detectableEntities.contains(entity)) {
            RadarRegistry.detectableEntities.add(entity);
        }
    }
    
    public static void unregister(final Entity entity) {
        if (RadarRegistry.detectableEntities.contains(entity)) {
            RadarRegistry.detectableEntities.remove(entity);
        }
    }
    
    public static List<TileEntity> getTileEntitiesInArea(final Vector2 minVector, final Vector2 maxVector) {
        cleanUpArray();
        final List<TileEntity> returnArray = new ArrayList<TileEntity>();
        for (final TileEntity tileEntity : RadarRegistry.detectableTileEntities) {
            if (new Region2(minVector, maxVector).isIn(new Vector3(tileEntity).toVector2())) {
                returnArray.add(tileEntity);
            }
        }
        return returnArray;
    }
    
    public static List<Entity> getEntitiesWithinRadius(final Vector2 vector, final int radius) {
        cleanUpArray();
        final List<Entity> returnArray = new ArrayList<Entity>();
        for (final Entity entity : RadarRegistry.detectableEntities) {
            if (Vector2.distance(vector, new Vector3(entity).toVector2()) <= radius) {
                returnArray.add(entity);
            }
        }
        return returnArray;
    }
    
    public static Set<TileEntity> getTileEntities() {
        cleanUpArray();
        return RadarRegistry.detectableTileEntities;
    }
    
    public static Set<Entity> getEntities() {
        cleanUpArray();
        return RadarRegistry.detectableEntities;
    }
    
    public static void cleanUpArray() {
        try {
            final Iterator<TileEntity> it = RadarRegistry.detectableTileEntities.iterator();
            while (it.hasNext()) {
                final TileEntity tileEntity = it.next();
                if (tileEntity == null) {
                    it.remove();
                }
                else if (tileEntity.func_70320_p()) {
                    it.remove();
                }
                else {
                    if (tileEntity.field_70331_k.func_72796_p(tileEntity.field_70329_l, tileEntity.field_70330_m, tileEntity.field_70327_n) == tileEntity) {
                        continue;
                    }
                    it.remove();
                }
            }
            final Iterator<Entity> it2 = RadarRegistry.detectableEntities.iterator();
            while (it2.hasNext()) {
                final Entity entity = it2.next();
                if (entity == null) {
                    it2.remove();
                }
                else {
                    if (!entity.field_70128_L) {
                        continue;
                    }
                    it2.remove();
                }
            }
        }
        catch (final Exception e) {
            System.out.println("Failed to clean up radar list properly.");
            e.printStackTrace();
        }
    }
    
    static {
        RadarRegistry.detectableTileEntities = new HashSet<TileEntity>();
        RadarRegistry.detectableEntities = new HashSet<Entity>();
    }
}
