package com.criteo.annoy;

public class HelloAnnoy {
    public static void main(String[] args) {
        // Pointer objects allocated in Java get deallocated once they become unreachable,
        // but C++ destructors can still be called in a timely fashion with Pointer.deallocate()
        long index = AnnoyLongLibrary.createAngular(100);
        float[] arr = new float[10];
        for(float i = 0.0f; i < 10; i++) {
            arr[(int)i] = i;
        }
        AnnoyLongLibrary.addItem(index, 32, arr);
        AnnoyLongLibrary.build(index, 32);
        float[] res = new float[10];
        AnnoyLongLibrary.getItem(index, 32, res);
        for(int i = 0; i < 10; i++) {
            System.out.print(res[i] + " ");
        }
        System.out.println("\nWooho");
    }
}
