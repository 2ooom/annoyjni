package com.criteo.annoy;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnoyIndex {
    private long pointer;
    private long[] indexToId;

    public static AnnoyIndex create(String metric, int dimensions) {
        long pointer;
        switch (metric) {
            case Metrics.Angular: pointer = AnnoyLib.createAngular(dimensions); break;
            case Metrics.Euclidean: pointer = AnnoyLib.createEuclidean(dimensions); break;
            case Metrics.Manhattan: pointer = AnnoyLib.createManhattan(dimensions); break;
            case Metrics.Hamming: pointer = AnnoyLib.createHamming(dimensions); break;
            case Metrics.DotProduct: pointer = AnnoyLib.createDotProduct(dimensions); break;
            default: throw new NotImplementedException();
        }

        return new AnnoyIndex(pointer);
    }

    private AnnoyIndex(long pointer) {
        this.pointer = pointer;
    }

    public int load(String path, long[] indexToId) {
        byte[] pathBytes = Utils.toCharBytes(path);
        boolean loadSuccess = AnnoyLib.load(pointer, pathBytes);
        if (!loadSuccess) {
            throw new RuntimeException("Couldn't load path: " + path);
        }
        this.indexToId = indexToId;
        return getNItems();
    }

    public int getNItems() {
        return AnnoyLib.getNItems(pointer);
    }

    public List<KnnResult> getClosestVectors(float[] vector, int nbItems, int searchDepth) {
        int[] items = new int[nbItems];
        Arrays.fill(items, -1);
        float[] distances = new float[nbItems];
        Arrays.fill(distances, -1F);
        List<KnnResult> results = new ArrayList<KnnResult>(nbItems);
        AnnoyLib.getNnsByVector(pointer, vector, nbItems, searchDepth, items, distances);
        for (int i = 0; i < nbItems; i++) {
            int index = items[i];
            if (index == -1) continue;
            long item = indexToId[index];
            KnnResult resultItem = new KnnResult(item, distances[i]);
            results.add(resultItem);
        }
        return results;
    }

    public boolean save(String path) {
        return AnnoyLib.save(pointer, Utils.toCharBytes(path));
    }

    public void build(float[][] items, long[]indexToId, int numTrees) {
        for(int i = 0; i < items.length; i++) {
            AnnoyLib.addItem(pointer, i, items[i]);
        }
        this.indexToId = indexToId;
        AnnoyLib.build(pointer, numTrees);
    }
}