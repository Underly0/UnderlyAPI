package me.underly0.underlyapi.commons.files.types;

import lombok.Getter;
import me.underly0.underlyapi.api.file.FileObject;

@Getter
public enum FileType implements FileObject {
    CONFIG("config.yml"),
    LOOT("loot.yml"),
    DATA("data.yml"),
    LANG("lang.yml");

    private final String fileName;
    FileType(String fileName) {
        this.fileName = fileName;
    }
    public static FileObject getFileByName(String name) {
        return () -> name;
    }
}
