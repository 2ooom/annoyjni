package com.criteo.annoy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class HelloAnnoy {
    public static void main(String[] args) {
        int dimensions = 101;
        int ItemsToHave = 867;
        int numTrees = 32;
        int nbQueries = 100;
        String metric = Metrics.Euclidean;

        String indexPath = "my.annoy";
        AnnoyIndex index = AnnoyIndex.create(metric, dimensions);

        System.out.println("Nb Items in index: " + index.getNItems());

        float[][] items = new float[ItemsToHave][];
        long[] ids = new long[ItemsToHave];
        for(int i = 0; i < ItemsToHave; i++) {
            float[] vector = new float[dimensions];
            Arrays.fill(vector, 0.5f);
            items[i] = vector;
            ids[i] = i;
        }

        index.build(items, ids, numTrees);

        System.out.println("Nb Items in index: " + index.getNItems());
        System.out.println("Saved:             " + index.save(indexPath));
        index.unload();
        System.out.println("Reading from:      " + indexPath);
        System.out.println("Loaded:            " + index.load(indexPath, ids));
        System.out.println("Nb Items in index: " + index.getNItems());

        String partnerId = "5932";
        String basePath = "/Users/d.parfenchik/Dev/annoyjni/indices/" + partnerId;
        List<Long> idsList = new ArrayList<Long>();
        try (Stream<String> stream = Files.lines(Paths.get(basePath + ".ids"))) {
            stream.forEach(line -> idsList.add(new Long(line)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ids = idsList.stream().mapToLong(Long::longValue).toArray();
        index = AnnoyIndex.create(metric, dimensions);
        index.load(basePath + ".annoy", ids);


        Random r = new Random();
        for (int i = 0; i < nbQueries; i++) {
            float[] arr = new float[dimensions];
            for(int j = 0; j < dimensions; j++) {
                arr[j] = r.nextFloat();
            }
            List<KnnResult> res = index.getClosestVectors(arr, 20, -1);
            KnnResult first = res.get(0);
            System.out.println(first.getItem() + " -> " + first.getDistance());
        }
        System.out.println("\nWooho");
    }
}
