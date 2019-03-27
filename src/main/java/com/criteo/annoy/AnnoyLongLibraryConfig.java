package com.criteo.annoy;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;
import org.bytedeco.javacpp.tools.*;

@Properties(
        value = @Platform(
                include = {"annoyint.h"} // "src/main/cpp/annoyint.h"
        ),
        target = "com.criteo.annoy",
        global = "com.criteo.annoy.AnnoyLongLibrary"
)
public class AnnoyLongLibraryConfig implements InfoMapper {
    public void map(InfoMap infoMap) {
        //infoMap.put(new Info("popcount", "ANNOY_NODE_ATTRIBUTE").cppTypes().annotations());
        //infoMap.put(new Info("com.criteo.annoy.AnnoyIndexInterface<int64_t, float>").pointerTypes("AnnoyLong"));
    }
}