package sca;

import java.util.ArrayList;

/**
 * @Auther: cccis
 * @Date: 7/18/2018 00:41
 * @Description: 按照汉明重量计算差分，即汉明重量大于等于4和小于4的差分
 */
public class Differential {

    public int dpaKeyByte;
    public float[] dpaValues;

    public float[] getDifferential(ArrayList<Integer> hanmmingweight ,ArrayList<ArrayList<Float>> powertraces){
        dpaValues = new float[powertraces.get(0).size()];
        //hanmmingweight 250;powertraces 250*10000

        // Now calculate the sum and count of all selection bits with a zero or a one
        float[] dpaHW0Sum = new float[powertraces.get(0).size()];
        float[] dpaHW8Sum = new float[powertraces.get(0).size()];

        for (int sample10000 = 0; sample10000 < powertraces.get(0).size(); sample10000++) {
            int dpaHW0Count = 0;
            int dpaHW8Count = 0;
            for (int trace250 = 2; trace250 < hanmmingweight.size(); trace250++) {
                if(hanmmingweight.get(trace250) < 4){
                    dpaHW0Count += 1;
                    dpaHW0Sum[sample10000] += powertraces.get(trace250).get(sample10000);
                }else if(hanmmingweight.get(trace250) >= 4){
                    dpaHW8Count += 1;
                    dpaHW8Sum[sample10000] += powertraces.get(trace250).get(sample10000);
                }
            }

            // Calculate a DPA value for this key guess
            float dpaValue = dpaHW8Sum[sample10000] / dpaHW8Count - dpaHW0Sum[sample10000] / dpaHW0Count;
            dpaValues[sample10000] = dpaValue;
        }

        return dpaValues;
    }

    public void setDpaKeyByte(float[] dpaKeyByte) {

    }

    public int getDpaKeyByte() {
        return dpaKeyByte;
    }
}
