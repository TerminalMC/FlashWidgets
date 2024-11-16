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
        Config.Options options = Config.get().options;

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(localized("name"))
                .setSavingRunnable(Config::save);
        ConfigEntryBuilder eb = builder.entryBuilder();
        
        ConfigCategory mc = builder.getOrCreateCategory(localized("option", "mc"));
        
        mc.addEntry(eb.startStrList(
                localized("option", "mc.secondRowKeys"), options.secondRowKeys)
                .setTooltip(localized("option", "mc.secondRowKeys.tooltip"))
                .setDefaultValue(Config.Options.secondRowKeysDefault)
                .setSaveConsumer(val -> options.secondRowKeys = val)
                .setInsertInFront(false)
                .setExpanded(true)
                .build());

        mc.addEntry(eb.startStrList(
                        localized("option", "mc.thirdRowKeys"), options.thirdRowKeys)
                .setTooltip(localized("option", "mc.thirdRowKeys.tooltip"))
                .setDefaultValue(Config.Options.thirdRowKeysDefault)
                .setSaveConsumer(val -> options.thirdRowKeys = val)
                .setInsertInFront(false)
                .setExpanded(true)
                .build());

        mc.addEntry(eb.startStrList(
                        localized("option", "mc.fourthRowKeys"), options.fourthRowKeys)
                .setTooltip(localized("option", "mc.fourthRowKeys.tooltip"))
                .setDefaultValue(Config.Options.fourthRowKeysDefault)
                .setSaveConsumer(val -> options.fourthRowKeys = val)
                .setInsertInFront(false)
                .setExpanded(true)
                .build());

        mc.addEntry(eb.startStrList(
                        localized("option", "mc.secondRowStrings"), options.secondRowStrings)
                .setTooltip(localized("option", "mc.secondRowStrings.tooltip"))
                .setDefaultValue(Config.Options.secondRowStringsDefault)
                .setSaveConsumer(val -> options.secondRowStrings = val)
                .setInsertInFront(false)
                .setExpanded(true)
                .build());

        mc.addEntry(eb.startStrList(
                        localized("option", "mc.thirdRowStrings"), options.thirdRowStrings)
                .setTooltip(localized("option", "mc.thirdRowStrings.tooltip"))
                .setDefaultValue(Config.Options.thirdRowStringsDefault)
                .setSaveConsumer(val -> options.thirdRowStrings = val)
                .setInsertInFront(false)
                .setExpanded(true)
                .build());

        mc.addEntry(eb.startStrList(
                        localized("option", "mc.fourthRowStrings"), options.fourthRowStrings)
                .setTooltip(localized("option", "mc.fourthRowStrings.tooltip"))
                .setDefaultValue(Config.Options.fourthRowStringsDefault)
                .setSaveConsumer(val -> options.fourthRowStrings = val)
                .setInsertInFront(false)
                .setExpanded(true)
                .build());

        ConfigCategory fb = builder.getOrCreateCategory(localized("option", "fb"));

        fb.addEntry(eb.startStrList(
                        localized("option", "fb.firstButtonKeys"), options.firstButtonKeys)
                .setTooltip(localized("option", "fb.firstButtonKeys.tooltip"))
                .setDefaultValue(Config.Options.firstButtonKeysDefault)
                .setSaveConsumer(val -> options.firstButtonKeys = val)
                .setInsertInFront(false)
                .setExpanded(true)
                .build());

        fb.addEntry(eb.startStrList(
                        localized("option", "fb.secondButtonKeys"), options.secondButtonKeys)
                .setTooltip(localized("option", "fb.secondButtonKeys.tooltip"))
                .setDefaultValue(Config.Options.secondButtonKeysDefault)
                .setSaveConsumer(val -> options.secondButtonKeys = val)
                .setInsertInFront(false)
                .setExpanded(true)
                .build());

        fb.addEntry(eb.startStrList(
                        localized("option", "fb.thirdButtonKeys"), options.thirdButtonKeys)
                .setTooltip(localized("option", "fb.thirdButtonKeys.tooltip"))
                .setDefaultValue(Config.Options.thirdButtonKeysDefault)
                .setSaveConsumer(val -> options.thirdButtonKeys = val)
                .setInsertInFront(false)
                .setExpanded(true)
                .build());

        fb.addEntry(eb.startStrList(
                        localized("option", "fb.firstButtonStrings"), options.firstButtonStrings)
                .setTooltip(localized("option", "fb.firstButtonStrings.tooltip"))
                .setDefaultValue(Config.Options.firstButtonStringsDefault)
                .setSaveConsumer(val -> options.firstButtonStrings = val)
                .setInsertInFront(false)
                .setExpanded(true)
                .build());

        fb.addEntry(eb.startStrList(
                        localized("option", "fb.secondButtonStrings"), options.secondButtonStrings)
                .setTooltip(localized("option", "fb.secondButtonStrings.tooltip"))
                .setDefaultValue(Config.Options.secondButtonStringsDefault)
                .setSaveConsumer(val -> options.secondButtonStrings = val)
                .setInsertInFront(false)
                .setExpanded(true)
                .build());

        fb.addEntry(eb.startStrList(
                        localized("option", "fb.thirdButtonStrings"), options.thirdButtonStrings)
                .setTooltip(localized("option", "fb.thirdButtonStrings.tooltip"))
                .setDefaultValue(Config.Options.thirdButtonStringsDefault)
                .setSaveConsumer(val -> options.thirdButtonStrings = val)
                .setInsertInFront(false)
                .setExpanded(true)
                .build());

        return builder.build();
    }
}
