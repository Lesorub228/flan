// 
// Decompiled by Procyon v0.6.0
// 

package universalelectricity.core.vector;

public class Vector2 implements Cloneable
{
    public double x;
    public double y;
    
    public Vector2() {
        this(0.0, 0.0);
    }
    
    public Vector2(final double x, final double y) {
        this.x = x;
        this.y = y;
    }
    
    public int intX() {
        return (int)Math.floor(this.x);
    }
    
    public int intY() {
        return (int)Math.floor(this.y);
    }
    
    public Vector2 clone() {
        return new Vector2(this.x, this.y);
    }
    
    public static double distance(final Vector2 point1, final Vector2 point2) {
        final double xDifference = point1.x - point2.x;
        final double yDiference = point1.y - point2.y;
        return Math.sqrt(xDifference * xDifference + yDiference * yDiference);
    }
    
    public static double slope(final Vector2 point1, final Vector2 point2) {
        final double xDifference = point1.x - point2.x;
        final double yDiference = point1.y - point2.y;
        return yDiference / xDifference;
    }
    
    public double distanceTo(final Vector2 target) {
        final double xDifference = this.x - target.x;
        final double yDifference = this.y - target.y;
        return Math.sqrt(xDifference * xDifference + yDifference * yDifference);
    }
    
    public Vector2 add(final Vector2 par1) {
        this.x += par1.x;
        this.y += par1.y;
        return this;
    }
    
    public Vector2 add(final double par1) {
        this.x += par1;
        this.y += par1;
        return this;
    }
    
    public Vector2 invert() {
        this.multiply(-1.0);
        return this;
    }
    
    public Vector2 multiply(final double amount) {
        this.x *= amount;
        this.y *= amount;
        return this;
    }
    
    public Vector2 round() {
        return new Vector2((double)Math.round(this.x), (double)Math.round(this.y));
    }
    
    public Vector2 ceil() {
        return new Vector2(Math.ceil(this.x), Math.ceil(this.y));
    }
    
    public Vector2 floor() {
        return new Vector2(Math.floor(this.x), Math.floor(this.y));
    }
    
    @Override
    public int hashCode() {
        return ("X:" + this.x + "Y:" + this.y).hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof Vector2) {
            final Vector2 vector = (Vector2)o;
            return this.x == vector.x && this.y == vector.y;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "Vector2 [" + this.x + "," + this.y + "]";
    }
}
