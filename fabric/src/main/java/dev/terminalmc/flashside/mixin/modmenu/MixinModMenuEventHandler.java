package dev.terminalmc.flashside.mixin.modmenu;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.terraformersmc.modmenu.event.ModMenuEventHandler;
import com.terraformersmc.modmenu.gui.widget.UpdateCheckerTexturedButtonWidget;
import dev.terminalmc.flashside.Flashside;
import dev.terminalmc.flashside.config.Config;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ModMenuEventHandler.class)
public class MixinModMenuEventHandler {
    /**
     * Optionally reposition the ModMenu button.
     */
    @WrapOperation(
            method = "afterTitleScreenInit",
            at = @At(
                    value = "NEW",
                    target = "(IIIIIIILnet/minecraft/resources/ResourceLocation;IILnet/minecraft/client/gui/components/Button$OnPress;Lnet/minecraft/network/chat/Component;)Lcom/terraformersmc/modmenu/gui/widget/UpdateCheckerTexturedButtonWidget;"
            )
    )
    private static UpdateCheckerTexturedButtonWidget wrapConstructUpdateCheckerTexturedButtonWidget(
            int x, int y, int width, int height, int u, int v, int hoveredVOffset, 
            ResourceLocation texture, int textureWidth, int textureHeight, 
            Button.OnPress pressAction, Component message, 
            Operation<UpdateCheckerTexturedButtonWidget> original) {
        if (Flashside.mmTitleScreenY != -1) {
            if (Config.options().modmenuIconTop) {
                int temp = Flashside.fbTitleScreenY;
                Flashside.fbTitleScreenY = Flashside.mmTitleScreenY;
                Flashside.mmTitleScreenY = temp;
            }
            y = Flashside.mmTitleScreenY;
        }
        return original.call(x, y, width, height, u, v, hoveredVOffset, texture, 
                textureWidth, textureHeight, pressAction, message);
    }
}
