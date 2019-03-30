package com.criteo.annoy;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnnoyLibTests {

    private static float delta = 0.00001f;

    @Test
    public void createIndicesOfEachTypeSaveAndLoad() throws IOException {
        //System.out.println(Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).toString());
        int dimension = 100;
        int nbItems = 123;
        int nbTrees = 32;
        float seedValue = 0.5f;

        long[] indices = new long[]{
            AnnoyLib.createAngular(dimension),
            AnnoyLib.createEuclidean(dimension),
            AnnoyLib.createDotProduct(dimension),
            //AnnoyLib.createHamming(dimension),
            AnnoyLib.createDotProduct(dimension),
        };

        File dir = Files.createTempDirectory("AnnoyLib").toFile();
        String indexPathStr = new File(dir, "index.annoy").toString();
        System.out.println(indexPathStr);

        for (long index : indices) {
            assertEquals(0, AnnoyLib.getNItems(index));

            for (int i = 0; i < nbItems; i++) {
                AnnoyLib.addItem(index, i, getVector(dimension, seedValue / (i + 1)));
            }

            assertEquals(nbItems, AnnoyLib.getNItems(index));
            AnnoyLib.build(index, nbTrees);
            assertEquals(nbItems, AnnoyLib.getNItems(index));

            byte[] indexPath = Utils.toCharBytes(indexPathStr);

            assertTrue(AnnoyLib.save(index, indexPath));

            AnnoyLib.unload(index);

            assertTrue(AnnoyLib.load(index, indexPath));

            float[] item = new float[dimension];

            for (int i = 0; i < nbItems; i++) {
                float expectedValue = seedValue / (i + 1);
                AnnoyLib.getItem(index, i, item);
                for (int j = 0; j < dimension; j++) {
                    assertEquals(expectedValue, item[j], delta);
                }
            }
        }
    }

    private float[] getVector(int dimension, float value) {
        float[] vector = new float[dimension];
        Arrays.fill(vector, value);
        return vector;
    }
}
