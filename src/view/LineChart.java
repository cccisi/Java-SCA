package view;

import java.awt.*;

import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
/**
 * @Auther: cccis
 * @Date: 7/19/2018 12:08
 * @Description:
 */
public class LineChart {
    public static void createLineChart(float[][] Result, int key, int key2, boolean isDPA) {
        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
        mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
        mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
        mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
        ChartFactory.setChartTheme(mChartTheme);
        CategoryDataset mDataset = GetDataset(Result,key,key2);
        JFreeChart mChart = ChartFactory.createLineChart(
                isDPA? "DPA前8bits侧信道结果折线图":"CPA前8bits侧信道结果折线图",//图名字
                "采样编号",//横坐标
                isDPA?"Power":"Correlation",//纵坐标
                mDataset,//数据集
                PlotOrientation.VERTICAL,
                true, // 显示图例
                true, // 采用标准生成器
                false);// 是否生成超链接

        CategoryPlot mPlot = (CategoryPlot) mChart.getPlot();
        mPlot.setBackgroundPaint(Color.LIGHT_GRAY);
        mPlot.setRangeGridlinePaint(Color.BLUE);//背景底部横虚线
        mPlot.setOutlinePaint(Color.RED);//边界线

        ChartFrame mChartFrame = new ChartFrame("密码工程侧信道攻击折线图", mChart);
        mChartFrame.pack();
        mChartFrame.setVisible(true);

    }

    public static CategoryDataset GetDataset(float[][] Result, int key,int key2) {
        DefaultCategoryDataset mDataset = new DefaultCategoryDataset();
        for (int i = key-1; i < key+2; i++) {
            for (int j = 0; j < Result[i].length; j++) {
                mDataset.addValue(Result[i][j],String.valueOf(i), String.valueOf(j));
            }
        }
        return mDataset;
    }
}