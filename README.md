# SCA ——　Side Channel Attack[![Build Status](https://travis-ci.org/HelloZeroNet/ZeroNet.svg?branch=master)](https://travis-ci.org/HelloZeroNet/ZeroNet) [![Documentation](https://img.shields.io/badge/docs-faq-brightgreen.svg)](https://zeronet.readthedocs.org/en/latest/faq/) [![Help](https://img.shields.io/badge/keep_this_project_alive-donate-yellow.svg)](https://zeronet.readthedocs.org/en/latest/help_zeronet/donate/)

* a DPA & CPA demo on AES 128

# What is [Side Channel Attack](https://en.wikipedia.org/wiki/Side-channel_attack)?

* You can compile this demo in IDEA:
  This SCA demo supports:

  * Differential power analysis ([DPA](https://en.wikipedia.org/wiki/Power_analysis))
  * Correlation power analysis (CPA)

# Some interesting reference
 * [CPA CHES04](https://link.springer.com/chapter/10.1007%2F978-3-540-28632-5_2)
 * [DPA CRYPTO99](https://www.rambus.com/differential-power-analysis/)
 * [DPA-Simplified](https://www.rambus.com/introduction-to-differential-power-analysis-and-related-attacks/) 
 * [AES能量攻击的建模与分析](http://kns.cnki.net/KCMS/detail/detail.aspx?filename=jsjk200803007&dbname=CJFD&dbcode=CJFQ)
 * [AES加密设备的差分能量攻击原理与仿真](http://kns.cnki.net/KCMS/detail/detail.aspx?filename=hdzj201312048&dbname=CJFD&dbcode=CJFQ)
 
# 这是什么？

* 本项目为密码工程侧信道大作业，对无保护的AES128密码实现进行了基本的侧信道攻击，本Demo中包含了差分能量攻击(DPA)和相关能量攻击(CPA)方法。
  * 差分能量攻击([Differential power analysis](https://en.wikipedia.org/wiki/Power_analysis))
  * 相关能量攻击(Correlation power analysis)

# 关键代码

## 加载.csv数据
```java
    //fl 是FileLoader对象,从.csv加载message，cipher以及能量迹数据
    private ArrayList<ArrayList<Float>> powertraces = fl.getPowertraces();
    private ArrayList<String> message = fl.getMessage();
    //初始化明文，中间值，汉明重量数组
    ArrayList<Integer> hanmmingweight = new ArrayList<>();
    ArrayList<Integer> messagebyte = new ArrayList<>();
    ArrayList<Integer> intermediatevalue = new ArrayList<>();
```
## 逐Byte分析数据
```java
for (int iter16 = 0; iter16 < message.get(0).length()/2; iter16++) {
    //250条能量迹逐8bit选取
     for (int trace250 = 0; trace250 < message.size(); trace250++) {
          messagebyte.add(Integer.valueOf(message.get(trace250).substring(iter16 * 2,iter16 * 2 + 2),16));//HexString转Int
     }
 }

```

## 每Byte对应256种密钥猜测
```java
 // 对每个Byte，猜测256种可能的密钥值
 for(int key = 0; key < 256; key++) {
     for (int imv250 = 0; imv250 < range; imv250++)//计算中间值及中间值的汉明重量
     {
          intermediatevalue.add(sb.cipherBytes(messagebyte.get(imv250),key));
          hanmmingweight.add(hammingWeight(intermediatevalue.get(imv250)));
     }
 }

```

 ## 计算汉明重量
 ```java
    //十进制汉明重量
    public int hammingWeight(int n) {
        int res = 0;
        while(n!=0) {
            res+= (n & 0x01);
            n >>>=1;
        }
        return res;
    }

```

 ## 计算差分
 ```java
     // 计算该 key guess 的 DPA
     float dpaValue = dpaHW5to8Sum[sample10000] / dpaHW5to8Count - dpaHW0to3Sum[sample10000] / dpaHW0to3Count;
     dpaValues[sample10000] = dpaValue;
 ```
 
  ## 计算相关系数
 ```java
 /**
 * @Description: 计算皮尔逊相关系数
 */
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
 ```
 
# 测试结果

```
DPA分析结果：33157e41908000ff75f2103247a88c37
CPA分析结果：33157e41908000ff75f2103247a88c37
```

* ![前8bitDPA测试结果](https://github.com/cccisi/SCA/blob/master/pic/DPA.png)

* ![前8bitCPA测试结果](https://github.com/cccisi/SCA/blob/master/pic/DPA.png)

# 参考文献
 * [CPA CHES04](https://link.springer.com/chapter/10.1007%2F978-3-540-28632-5_2)
 * [DPA CRYPTO99](https://www.rambus.com/differential-power-analysis/)
 * [DPA-精简](https://www.rambus.com/introduction-to-differential-power-analysis-and-related-attacks/) 
 * [AES能量攻击的建模与分析](http://kns.cnki.net/KCMS/detail/detail.aspx?filename=jsjk200803007&dbname=CJFD&dbcode=CJFQ)
 * [AES加密设备的差分能量攻击原理与仿真](http://kns.cnki.net/KCMS/detail/detail.aspx?filename=hdzj201312048&dbname=CJFD&dbcode=CJFQ)
