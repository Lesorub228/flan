// 
// Decompiled by Procyon v0.6.0
// 

package universalelectricity.prefab.vector;

import universalelectricity.core.vector.Vector2;

public class Region2
{
    public Vector2 min;
    public Vector2 max;
    
    public Region2() {
        this(new Vector2(), new Vector2());
    }
    
    public Region2(final Vector2 min, final Vector2 max) {
        this.min = min;
        this.max = max;
    }
    
    public boolean isIn(final Vector2 point) {
        return point.x > this.min.x && point.x < this.max.x && point.y > this.min.y && point.y < this.max.y;
    }
    
    public boolean isIn(final Region2 region) {
        return region.max.x > this.min.x && region.min.x < this.max.x && (region.max.y > this.min.y && region.min.y < this.max.y);
    }
}
