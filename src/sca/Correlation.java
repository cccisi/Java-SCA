package sca;

import java.util.ArrayList;

/**
 * @Auther: cccis
 * @Date: 7/18/2018 00:42
 * @Description: 计算皮尔逊相关系数
 */
public class Correlation {

    public float[] cpaValues;

    public float[] getCorrelation(ArrayList<Integer> hanmmingweight ,ArrayList<ArrayList<Float>> powertraces){
        cpaValues = new float[powertraces.get(0).size()];
        ArrayList<Float> tracesample = new ArrayList<>();
        //hanmmingweight 250;powertraces 250*10000

        // Now calculate the Correlation with hanmmingweight

        for (int sample10000 = 0; sample10000 < powertraces.get(0).size(); sample10000++) {
            tracesample.clear();
            for (int trace250 = 0; trace250 < hanmmingweight.size(); trace250++) {
                tracesample.add(powertraces.get(trace250).get(sample10000));
            }

            // Calculate a DPA value for this key guess
            float cpaValue = (float) getPearsonCorrelationScore(hanmmingweight,tracesample);//强制转换
            cpaValues[sample10000] = cpaValue;
        }

        return cpaValues;
    }


    public static double getPearsonCorrelationScore(ArrayList<Integer> hanmmingweight, ArrayList<Float> traceatime) {
        if (hanmmingweight.size() != traceatime.size())
            throw new RuntimeException("数据不正确！");
        double[] xData = new double[hanmmingweight.size()];
        double[] yData = new double[hanmmingweight.size()];
        for (int i = 0; i < hanmmingweight.size(); i++) {
            xData[i] = hanmmingweight.get(i);
            yData[i] = traceatime.get(i);
        }
        return getPearsonCorrelationScore(xData,yData);
    }

    public static double getPearsonCorrelationScore(double[] xData, double[] yData) {
        if (xData.length != yData.length)
            throw new RuntimeException("数据不正确！");
        double xMeans;
        double yMeans;
        double numerator = 0;// 求解皮尔逊的分子
        double denominator = 0;// 求解皮尔逊系数的分母

        double result = 0;
        // 拿到两个数据的平均值
        xMeans = getMeans(xData);
        yMeans = getMeans(yData);
        // 计算皮尔逊系数的分子
        numerator = generateNumerator(xData, xMeans, yData, yMeans);
        // 计算皮尔逊系数的分母
        denominator = generateDenomiator(xData, xMeans, yData, yMeans);
        // 计算皮尔逊系数
        result = numerator / denominator;
        return result;
    }

    /**
     * 计算分子
     *
     * @param xData
     * @param xMeans
     * @param yData
     * @param yMeans
     * @return
     */
    private static double generateNumerator(double[] xData, double xMeans, double[] yData, double yMeans) {
        double numerator = 0.0;
        for (int i = 0; i < xData.length; i++) {
            numerator += (xData[i] - xMeans) * (yData[i] - yMeans);
        }
        return numerator;
    }

    /**
     * 生成分母
     *
     * @param yMeans
     * @param yData
     * @param xMeans
     * @param xData
     * @return 分母
     */
    private static double generateDenomiator(double[] xData, double xMeans, double[] yData, double yMeans) {
        double xSum = 0.0;
        for (int i = 0; i < xData.length; i++) {
            xSum += (xData[i] - xMeans) * (xData[i] - xMeans);
        }
        double ySum = 0.0;
        for (int i = 0; i < yData.length; i++) {
            ySum += (yData[i] - yMeans) * (yData[i] - yMeans);
        }
        return Math.sqrt(xSum) * Math.sqrt(ySum);
    }

    /**
     * 根据给定的数据集进行平均值计算
     *
     * @param datas 数据集
     * @return 给定数据集的平均值
     */
    private static double getMeans(double[] datas) {
        double sum = 0.0;
        for (int i = 0; i < datas.length; i++) {
            sum += datas[i];
        }
        return sum / datas.length;
    }
}
