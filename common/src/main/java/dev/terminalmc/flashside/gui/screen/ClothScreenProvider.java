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

package dev.terminalmc.flashside.gui.screen;

import dev.terminalmc.flashside.config.Config;
import me.shedaniel.clothconfig2.api.*;
import net.minecraft.client.gui.screens.Screen;

import static dev.terminalmc.flashside.util.Localization.localized;

public class ClothScreenProvider {
    /**
     * Builds and returns a Cloth Config options screen.
     * @param parent the current screen.
     * @return a new options {@link Screen}.
     * @throws NoClassDefFoundError if the Cloth Config API mod is not
     * available.
     */
    static Screen getConfigScreen(Screen parent) {
        Config.Options options = Config.options();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(localized("name"))
                .setSavingRunnable(Config::save);
        ConfigEntryBuilder eb = builder.entryBuilder();
        
        ConfigCategory mc = builder.getOrCreateCategory(localized("option", "mc"));

        mc.addEntry(eb.startIntSlider(
                localized("option", "mc.startRow"), options.startRow, 0, 4)
                .setTooltip(localized("option", "mc.startRow.tooltip"))
                .setDefaultValue(Config.Options.startRowDefault)
                .setSaveConsumer(val -> options.startRow = val)
                .setTextGetter(val -> localized("option", "mc.startRow.value", val)) // op
                .build());

        mc.addEntry(eb.startBooleanToggle(
                localized("option", "mc.modmenuIconTop"), options.modmenuIconTop)
                .setTooltip(localized("option", "mc.modmenuIconTop.tooltip"))
                .setDefaultValue(Config.Options.modmenuIconTopDefault)
                .setSaveConsumer(val -> options.modmenuIconTop = val)
                .build());

        return builder.build();
    }
}
