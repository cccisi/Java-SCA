package sca;

import dataloader.FileLoader;

import java.util.ArrayList;

import static view.LineChart.createLineChart;

/**
 * @Auther: cccis
 * @Date: 7/17/2018 13:32
 * @Description:
 */
public class PA {
//    /**
//     * Create a new DataLoader object
//     */
//    public PA(){
//
//    }
//
//    public PA(ArrayList<ArrayList<Float>> powertraces, ArrayList<String> message)
//    {
//        this.powertraces = powertraces;
//        this.message = message;
//    }

    Differential dpa = new Differential();
    Correlation cpa = new Correlation();
    Sbox sb = new Sbox();
    FileLoader fl = new FileLoader();
    private ArrayList<ArrayList<Float>> powertraces = fl.getPowertraces();
    private ArrayList<String> message = fl.getMessage();

    public void getPaValue(){
        fl.loadData();
        ArrayList<Integer> hanmmingweight = new ArrayList<>();
        ArrayList<Integer> messagebyte = new ArrayList<>();
        ArrayList<Integer> intermediatevalue = new ArrayList<>();
        String dpaKey = "";
        String cpaKey = "";
        int dpaTime = 0;
        int cpaTime = 0;

        //每8bit一轮
        for (int iter16 = 0; iter16 < message.get(0).length()/2; iter16++) {
            int dpaKeyByte = 0;
            int cpaKeyByte = 0;
            float[][] dpaResult = new float[256][powertraces.get(0).size()];//256*10000
            float[][] cpaResult = new float[256][powertraces.get(0).size()];
            messagebyte.clear();
            for (int trace250 = 0; trace250 < message.size(); trace250++) {
                messagebyte.add(Integer.valueOf(message.get(trace250).substring(iter16 * 2,iter16 * 2 + 2),16));//HexString转Int
            }

            // 对每个Byte，猜测256种可能的密钥值
            for(int key = 0; key < 256; key++) {
                intermediatevalue.clear();
                hanmmingweight.clear();//清空一个key对应的HW向量
                int range = messagebyte.size();
                for (int imv250 = 0; imv250 < range; imv250++)//计算中间值及中间值的汉明重量
                {
                    intermediatevalue.add(sb.cipherBytes(messagebyte.get(imv250),key));
                    hanmmingweight.add(hammingWeight(intermediatevalue.get(imv250)));
                }

                dpaResult[key] = dpa.getDifferential(hanmmingweight,powertraces);//hanmmingweight 250;powertraces 250*10000
                cpaResult[key] = cpa.getCorrelation(hanmmingweight,powertraces);

            }  // end 每byte有 256 key guesses
            dpaKeyByte = getKeyByte(dpaResult)[0];
            cpaKeyByte = getKeyByte(cpaResult)[0];
//            dpaTime = getKeyByte(dpaResult)[1];
//            cpaTime = getKeyByte(dpaResult)[1];
            dpaKey = dpaKey + (dpaKeyByte>15? Integer.toHexString(dpaKeyByte):"0" + Integer.toHexString(dpaKeyByte));
            cpaKey = cpaKey + (cpaKeyByte>15? Integer.toHexString(cpaKeyByte):"0" + Integer.toHexString(cpaKeyByte));

            if(iter16 < 2){
//                createLineChart(dpaResult,dpaKeyByte,true);
//                createLineChart(cpaResult,cpaKeyByte,false);
                createLineChart(dpaResult,dpaKeyByte,cpaKeyByte,true);
                createLineChart(cpaResult,dpaKeyByte,cpaKeyByte,false);

            }
//            dpaResult[256][10000],cpaResult可输出绘图
        }//end 16个byte
        System.out.println("DPA分析结果：" + dpaKey + "\nCPA分析结果：" + cpaKey);
    }

    public int hammingWeight(int n) {
        int res = 0;
        while(n!=0) {
            res+= (n & 0x01);
            n >>>=1;
        }
        return res;
    }

    public int[] getKeyByte(float[][] paValues){
        int[] result = new int[2];
        float max = 0;
        for (int i = 0; i < paValues.length; i++) {
            for (int j = 0; j < paValues[i].length - 1; j++) {
                if (max < Math.abs(paValues[i][j])) {
                    max = paValues[i][j];//算出最大值
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;
    }
}
