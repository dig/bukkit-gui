package com.github.dig.gui.inventory;

import com.github.dig.gui.Gui;
import com.github.dig.gui.GuiRegistry;
import com.github.dig.gui.state.ComponentClickState;
import com.github.dig.gui.state.GuiDragState;
import com.github.dig.gui.state.GuiItemChangeState;
import com.github.dig.gui.util.TriConsumer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public abstract class InventoryGui implements Gui {

    protected final Inventory inventory;

    protected final Set<Integer> controllableSlots = new HashSet<>();

    // Player, Item, Slot
    private final Map<Integer, Consumer<ComponentClickState>> callbacks = new HashMap<>();

    private final Set<Player> viewers = new HashSet<>();

    public InventoryGui(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void handleOpen(Player player) {
        render();
    }

    @Override
    public void showTo(Player player) {
        if (!viewers.contains(player)) {
            player.openInventory(inventory);
            viewers.add(player);
        }
    }

    @Override
    public void removeViewer(Player player) {
        viewers.remove(player);
    }

    @Override
    public boolean isViewer(Player player) {
        return viewers.contains(player);
    }

    @Override
    public int getViewerCount() {
        return viewers.size();
    }

    @Override
    public Listener createListener(GuiRegistry guiRegistry) {
        return new InventoryGuiListener(callbacks, this, guiRegistry);
    }

    public boolean isControllable(int slot) {
        return controllableSlots.contains(slot);
    }

    public boolean match(Inventory inventory) {
        return this.inventory == inventory;
    }

    public void clear() {
        inventory.clear();
        callbacks.clear();
    }

    public void setItem(int slot, @Nullable ItemStack item) {
        callbacks.remove(slot);
        inventory.setItem(slot, item != null ? item : new ItemStack(Material.AIR));
    }

    public void setItem(int x, int y, @Nullable ItemStack item) {
        setItem(slotOf(x, y), item);
    }

    public void setItem(int slot, @Nullable ItemStack item, Consumer<ComponentClickState> onSlotClick) {
        setItem(slot, item);
        callbacks.put(slot, onSlotClick);
    }

    public void setItem(int x, int y, @Nullable ItemStack item, Consumer<ComponentClickState> onSlotClick) {
        setItem(slotOf(x, y), item, onSlotClick);
    }

    public void render() {}

    public void handleItemChange(GuiItemChangeState state) {}

    public void handleComponentClick(ComponentClickState state) {}

    public void handleDrag(GuiDragState state) {}

    protected abstract int slotOf(int x, int y);
}
