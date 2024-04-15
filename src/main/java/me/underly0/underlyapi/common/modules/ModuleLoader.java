package me.underly0.underlyapi.common.modules;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import me.underly0.underlyapi.api.module.PluginModule;

@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModuleLoader {
    final PluginModule module;
    final ModuleConfig config;

    boolean enabled;

    public void loadModule() {
        if (!enabled) {
            module.onLoad();
            enabled = true;
        }
    }

    public void unloadModule() {
        if (enabled) {
            module.onUnload();
            enabled = false;
        }
    }
}
