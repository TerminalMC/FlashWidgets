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

package dev.terminalmc.flashside.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.terminalmc.flashside.Flashside;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class Config {
    private static final Path DIR_PATH = Path.of("config");
    private static final String FILE_NAME = Flashside.MOD_ID + ".json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Options

    public final Options options = new Options();

    public static class Options {
        public static final List<String> secondRowKeysDefault = List.of(
                "gui.stats"
        );
        public List<String> secondRowKeys = secondRowKeysDefault;

        public static final List<String> thirdRowKeysDefault = List.of(
                "menu.reportBugs",
                "menu.server_links",
                "modmenu.title"
        );
        public List<String> thirdRowKeys = thirdRowKeysDefault;

        public static final List<String> fourthRowKeysDefault = List.of(
                "menu.playerReporting",
                "menu.shareToLan"
        );
        public List<String> fourthRowKeys = fourthRowKeysDefault;

        public static final List<String> secondRowStringsDefault = List.of();
        public List<String> secondRowStrings = secondRowStringsDefault;

        public static final List<String> thirdRowStringsDefault = List.of();
        public List<String> thirdRowStrings = thirdRowStringsDefault;

        public static final List<String> fourthRowStringsDefault = List.of();
        public List<String> fourthRowStrings = fourthRowStringsDefault;

        public static final List<String> firstButtonKeysDefault = List.of();
        public List<String> firstButtonKeys = firstButtonKeysDefault;

        public static final List<String> secondButtonKeysDefault = List.of();
        public List<String> secondButtonKeys = secondButtonKeysDefault;

        public static final List<String> thirdButtonKeysDefault = List.of();
        public List<String> thirdButtonKeys = thirdButtonKeysDefault;

        public static final List<String> firstButtonStringsDefault = List.of(
                "Start Recording",
                "Finish Recording"
        );
        public List<String> firstButtonStrings = firstButtonStringsDefault;

        public static final List<String> secondButtonStringsDefault = List.of(
                "Pause Recording",
                "Unpause Recording"
        );
        public List<String> secondButtonStrings = secondButtonStringsDefault;

        public static final List<String> thirdButtonStringsDefault = List.of(
                "Cancel Recording"
        );
        public List<String> thirdButtonStrings = thirdButtonStringsDefault;
    }

    // Instance management

    private static Config instance = null;

    public static Config get() {
        if (instance == null) {
            instance = Config.load();
        }
        return instance;
    }

    public static Config getAndSave() {
        get();
        save();
        return instance;
    }

    public static Config resetAndSave() {
        instance = new Config();
        save();
        return instance;
    }

    // Cleanup

    private void cleanup() {
        // Called before config is saved
    }

    // Load and save

    public static @NotNull Config load() {
        Path file = DIR_PATH.resolve(FILE_NAME);
        Config config = null;
        if (Files.exists(file)) {
            config = load(file, GSON);
        }
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    private static @Nullable Config load(Path file, Gson gson) {
        try (FileReader reader = new FileReader(file.toFile())) {
            return gson.fromJson(reader, Config.class);
        } catch (Exception e) {
            // Catch Exception as errors in deserialization may not fall under
            // IOException or JsonParseException, but should not crash the game.
            Flashside.LOG.error("Unable to load config.", e);
            return null;
        }
    }

    public static void save() {
        if (instance == null) return;
        instance.cleanup();
        try {
            if (!Files.isDirectory(DIR_PATH)) Files.createDirectories(DIR_PATH);
            Path file = DIR_PATH.resolve(FILE_NAME);
            Path tempFile = file.resolveSibling(file.getFileName() + ".tmp");

            try (FileWriter writer = new FileWriter(tempFile.toFile())) {
                writer.write(GSON.toJson(instance));
            } catch (IOException e) {
                throw new IOException(e);
            }
            Files.move(tempFile, file, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
            Flashside.onConfigSaved(instance);
        } catch (IOException e) {
            Flashside.LOG.error("Unable to save config.", e);
        }
    }
}
