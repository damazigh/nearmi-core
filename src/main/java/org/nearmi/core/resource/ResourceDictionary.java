package org.nearmi.core.resource;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Global resource class wrapper, contains a list of {@link DictionaryEntry} loaded at startup
 * @author A.Djebarri
 * @since 1.0
 * @see DictionaryEntry
 */
@Data
public class ResourceDictionary {
    @Getter(AccessLevel.NONE)
    private List<DictionaryEntry> entries;

    public DictionaryEntry findByKey(String key) {
        return this.getEntries().stream().filter(e -> e.getKey().equals(key)).findFirst().orElse(null);
    }

    public List<DictionaryEntry> getEntries() {
        if (entries == null) {
            entries = new ArrayList<>();
        }
        return entries;
    }
}
