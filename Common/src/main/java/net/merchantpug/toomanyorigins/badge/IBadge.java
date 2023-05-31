package net.merchantpug.toomanyorigins.badge;

import io.github.apace100.origins.badge.Badge;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;

import java.util.List;

public interface IBadge<P>  extends Badge {

    List<ClientTooltipComponent> generateTooltipComponents(P power, int widthLimit, float time, Font textRenderer);

}