package com.github.dig.gui.inventory;

import com.github.dig.gui.exception.SlotOutOfBoundsException;
import lombok.NonNull;
import org.bukkit.Bukkit;

public abstract class ChestInventoryGui extends InventoryGui {

    protected static final int WIDTH = 9;

    protected final int height;

    protected final int minX = 0;
    protected final int minY = 0;

    protected final int maxX = WIDTH - 1;
    protected final int maxY;

    public ChestInventoryGui(int height, @NonNull String title) {
        super(Bukkit.createInventory(null, height * WIDTH, title));
        this.height = height;
        this.maxY = height - 1;
    }

    @Override
    protected int slotOf(int x, int y) {
        if (x < minX || x > maxX) throw new SlotOutOfBoundsException("X coordinate is out of range");
        if (y < minY || y > maxY) throw new SlotOutOfBoundsException("Y coordinate is out of range");
        return (y * WIDTH) + x;
    }
}
