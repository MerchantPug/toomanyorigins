/*
MIT License

Copyright (c) 2021 apace100

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package com.github.merchantpug.toomanyorigins.data;

import com.github.merchantpug.toomanyorigins.TooManyOrigins;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;

import java.io.Reader;
import java.util.*;
import java.util.stream.Stream;

public class LegacyContentManager extends SinglePreparationResourceReloader<Set<String>> implements IdentifiableResourceReloadListener {
    private static int highestPriority = 0;
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    @Override
    protected Set<String> prepare(ResourceManager manager, Profiler profiler) {
        highestPriority = 0;
        Set<String> enabledSet = new HashSet<>();
        Iterator<Map.Entry<Identifier, Resource>> iterator = manager.findResources("toomanyorigins", (id) -> id.getPath().equals("toomanyorigins/legacy_content.json")).entrySet().iterator();
        Set<String> resourcesHandled = new HashSet<>();
        while (iterator.hasNext()) {
            var next = iterator.next();
            resourcesHandled.clear();
            manager.getAllResources(next.getKey()).forEach(resource -> {
                if(!resourcesHandled.contains(resource.getResourcePackName())) {
                    resourcesHandled.add(resource.getResourcePackName());
                    try {
                        Reader reader = resource.getReader();
                        JsonElement jsonElement = JsonHelper.deserialize(GSON, reader, JsonElement.class);
                        if (jsonElement != null) {
                            JsonObject jsonObject = jsonElement.getAsJsonObject();
                            LegacyContentRegistry.asStream().map(Map.Entry::getKey).forEach((string) -> {
                                if (jsonObject.has(string) && jsonObject.get(string).getAsBoolean()) {
                                    enabledSet.add(string);
                                }
                            });
                            if (jsonObject.has("loading_priority") && jsonObject.get("loading_priority").getAsInt() > highestPriority)
                                highestPriority = jsonObject.get("loading_priority").getAsInt();
                        }
                    } catch (Throwable e) {
                        TooManyOrigins.LOGGER.error("There was a problem reading TooManyOrigins legacy content file from pack '" + resource.getResourcePackName() + "'. (skipping): " + e.getMessage());
                    }
                }
            });

            Stream<String> stringStream = LegacyContentRegistry.asStream().map(Map.Entry::getKey);
            if (stringStream.allMatch(enabledSet::contains)) {
                break;
            }
        }
        return enabledSet;
    }

    @Override
    protected void apply(Set<String> prepared, ResourceManager manager, Profiler profiler) {
        LegacyContentRegistry.disableAll();
        prepared.forEach(LegacyContentRegistry::enable);
    }

    @Override
    public Identifier getFabricId() {
        return TooManyOrigins.identifier("legacy_content");
    }


    protected static void disableAll(Set<String> set) {
        set.clear();
    }
}