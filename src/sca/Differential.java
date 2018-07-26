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
        float[] dpaHW0to3Sum = new float[powertraces.get(0).size()];
        float[] dpaHW5to8Sum = new float[powertraces.get(0).size()];

        for (int sample10000 = 0; sample10000 < powertraces.get(0).size(); sample10000++) {
            int dpaHW0to3Count = 0;
            int dpaHW5to8Count = 0;
            for (int trace250 = 2; trace250 < hanmmingweight.size(); trace250++) {
                if(hanmmingweight.get(trace250) < 4){
                    dpaHW0to3Count += 1;
                    dpaHW0to3Sum[sample10000] += powertraces.get(trace250).get(sample10000);
                }else if(hanmmingweight.get(trace250) > 4){
                    dpaHW5to8Count += 1;
                    dpaHW5to8Sum[sample10000] += powertraces.get(trace250).get(sample10000);
                }
            }

            // 计算该 key guess 的 DPA
            float dpaValue = dpaHW5to8Sum[sample10000] / dpaHW5to8Count - dpaHW0to3Sum[sample10000] / dpaHW0to3Count;
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
