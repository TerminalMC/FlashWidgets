/*
 * Copyright 2024 TerminalMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.terminalmc.flashside.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.util.Pair;
import com.moulberry.flashback.screen.BottomTextWidget;
import dev.terminalmc.flashside.config.Config;
import dev.terminalmc.flashside.gui.widget.FlashsideButton;
import dev.terminalmc.flashside.mixin.accessor.ButtonAccessor;
import dev.terminalmc.flashside.mixin.accessor.GridLayoutAccessor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Iterator;
import java.util.function.Consumer;

@Mixin(PauseScreen.class)
public class MixinPauseScreen extends Screen {
    protected MixinPauseScreen(Component title) {
        super(title);
    }
    
    @WrapOperation(
            method = "createPauseMenu",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/layouts/GridLayout;visitWidgets(Ljava/util/function/Consumer;)V"
            )
    )
    private void wrapVisitWidgets(GridLayout grid, Consumer<AbstractWidget> consumer, 
                                      Operation<Void> original) {
        if (grid == null) return;

        Button mcb1 = null;
        Button mcb2 = null;
        Button mcb3 = null;

        Iterator<LayoutElement> elementIter = ((GridLayoutAccessor)grid).getChildren().iterator();
        Iterator<GridLayout.CellInhabitant> inhabitantIter = ((GridLayoutAccessor)grid).getCellInhabitants().iterator();
        while (elementIter.hasNext() && inhabitantIter.hasNext()) {
            LayoutElement element = elementIter.next();
            inhabitantIter.next();
            
            boolean remove = false;
            if (element instanceof Button button) {
                Config.Options options = Config.get().options;
                if (button.getMessage().getContents() instanceof TranslatableContents contents) {
                    if (options.secondRowKeys.contains(contents.getKey())) {
                        mcb1 = button;
                    } else if (options.thirdRowKeys.contains(contents.getKey())) {
                        mcb2 = button;
                    } else if (options.fourthRowKeys.contains(contents.getKey())) {
                        mcb3 = button;
                    } else if (mcb1 != null && options.firstButtonKeys.contains(contents.getKey())) {
                        this.addRenderableWidget(getFlashsideButton(button, mcb1));
                        remove = true;
                    } else if (mcb2 != null && options.secondButtonKeys.contains(contents.getKey())) {
                        this.addRenderableWidget(getFlashsideButton(button, mcb2));
                        remove = true;
                    } else if (mcb3 != null && options.thirdButtonKeys.contains(contents.getKey())) {
                        this.addRenderableWidget(getFlashsideButton(button, mcb3));
                        remove = true;
                    }
                } else if (button.getMessage().getContents() instanceof PlainTextContents.LiteralContents contents) {
                    if (options.secondRowStrings.contains(contents.text())) {
                        mcb1 = button;
                    } else if (options.thirdRowStrings.contains(contents.text())) {
                        mcb2 = button;
                    } else if (options.fourthRowStrings.contains(contents.text())) {
                        mcb3 = button;
                    } else if (mcb1 != null && options.firstButtonStrings.contains(contents.text())) {
                        this.addRenderableWidget(getFlashsideButton(button, mcb1));
                        remove = true;
                    } else if (mcb2 != null && options.secondButtonStrings.contains(contents.text())) {
                        this.addRenderableWidget(getFlashsideButton(button, mcb2));
                        remove = true;
                    } else if (mcb3 != null && options.thirdButtonStrings.contains(contents.text())) {
                        this.addRenderableWidget(getFlashsideButton(button, mcb3));
                        remove = true;
                    }
                }
            } else if (element instanceof BottomTextWidget) {
                remove = true;
            }
            
            if (remove) {
                elementIter.remove();
                inhabitantIter.remove();
            }
        }
        
        original.call(grid, consumer);
    }

    @Unique
    private static @NotNull FlashsideButton getFlashsideButton(Button original, Button ref) {
        FlashsideButton fsb = new FlashsideButton(
                ref.getX() + ref.getWidth() + 5,
                ref.getY(),
                ref.getHeight(),
                ref.getHeight(),
                Component.empty(),
                ((ButtonAccessor) original).getOnPress(),
                getButtonInfo(original.getMessage().getString())
        );
        fsb.setTooltip(Tooltip.create(original.getMessage()));
        return fsb;
    }
    
    @Unique
    private static Pair<ResourceLocation,String> getButtonInfo(String text) {
        return switch(text) {
            case "Start Recording" -> new Pair<>(FlashsideButton.OVERLAY_START, text);
            case "Finish Recording" -> new Pair<>(FlashsideButton.OVERLAY_FINISH, text);
            case "Pause Recording" -> new Pair<>(FlashsideButton.OVERLAY_PAUSE, text);
            case "Unpause Recording" -> new Pair<>(FlashsideButton.OVERLAY_UNPAUSE, text);
            case "Cancel Recording" -> new Pair<>(FlashsideButton.OVERLAY_CANCEL, text);
            default -> new Pair<>(FlashsideButton.OVERLAY_UNKNOWN, "Unknown Function");
        };
    }
}
