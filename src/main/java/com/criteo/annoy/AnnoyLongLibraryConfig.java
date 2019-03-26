package com.criteo.annoy;

import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;
import org.bytedeco.javacpp.tools.*;

@Properties(
        value = @Platform(
                //includepath = {"/Users/d.parfenchik/Dev/jcpptest/"},
                //preloadpath = {"/path/to/deps/"},
                //linkpath = {"/path/to/lib/"},
                //include = {"annoylong.h"} //src/main/cpp/annoylong.h
                include = {"annoyint.h"}
                //include = {"src/main/cpp/annoyint.h"}
            
                //preload = {"DependentLib"},
                //link = {"NativeLibrary"}
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