package org.nearmi.core.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.nearmi.core.resource.ResourceDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Resource loader that scan configured (yaml) file and parse each configured error to an {@link org.nearmi.core.resource.DictionaryEntry}<br/>
 * this
 * @author A.Djebarri
 * @since 1.0
 */
@Slf4j
public class ExceptionResourceLoader {
    @Autowired
    @Qualifier("genericResource")
    private String genericRes;
    @Autowired(required = false)
    @Qualifier("configurableRes")
    private String[] configurableRes;
    @Getter
    private ResourceDictionary dictionary = new ResourceDictionary();
    private final String[] supportedExtension = {"yml", "yaml"};
    @PostConstruct
    public void populate() {
        List<String> mergedRes = new ArrayList<>();
        mergedRes.add(this.genericRes);
        if (configurableRes == null) {
            log.warn("You didn't provide additional bean for exception resource loading - consider defining a yaml ");
        } else {
           mergedRes.addAll(Arrays.asList(this.configurableRes));
        }
        mergedRes.stream().forEach(p -> this.process(p));
    }

    private void process(String path) {
        log.info("processing exception message resources {}", path);
        if (!FilenameUtils.isExtension(path, this.supportedExtension)) {
            throw new IllegalArgumentException(String.format("Unsupported resource %1s. supported file extension are %2s", path, "yml"));
        }
        Constructor constructor = new Constructor(ResourceDictionary.class);
        Yaml yml = new Yaml(constructor);
        try {
            ResourceDictionary dico = yml.load(ExceptionHandlerConfigurer.class.getClassLoader().getResourceAsStream(path));
            log.debug("Loaded {} entries from resource {}", dico.getEntries().size(), path);
            this.dictionary.getEntries().addAll(dico.getEntries());
        } catch (Exception e) {
            log.error("Exception occurred when reading preconfigured resources - processing resource {}", path, e);
            throw new IllegalArgumentException(String.format("Unable to build dictionary from resource %1s - see stack trace for more details", path));
        }
    }
}
