/*
 * Decompiled with CFR 0.152.
 */
package me.kiras.aimwhere.libraries.slick.util.pathfinding;

import me.kiras.aimwhere.libraries.slick.util.pathfinding.PathFindingContext;

public interface TileBasedMap {
    public int getWidthInTiles();

    public int getHeightInTiles();

    public void pathFinderVisited(int var1, int var2);

    public boolean blocked(PathFindingContext var1, int var2, int var3);

    public float getCost(PathFindingContext var1, int var2, int var3);
}

