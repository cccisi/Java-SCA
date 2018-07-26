# SCA ——　Side Channel Attack[![Build Status](https://travis-ci.org/HelloZeroNet/ZeroNet.svg?branch=master)](https://travis-ci.org/HelloZeroNet/ZeroNet) [![Documentation](https://img.shields.io/badge/docs-faq-brightgreen.svg)](https://zeronet.readthedocs.org/en/latest/faq/) [![Help](https://img.shields.io/badge/keep_this_project_alive-donate-yellow.svg)](https://zeronet.readthedocs.org/en/latest/help_zeronet/donate/)

* a DPA & CPA demo on AES 128

# What is [Side Channel Attack](https://en.wikipedia.org/wiki/Side-channel_attack)?

* You can compile this demo in IDEA:
  This SCA demo supports:

  * Correlation power analysis (CPA)
  * Differential power analysis ([DPA](https://en.wikipedia.org/wiki/Power_analysis))

# Some interesting reference
 * [CPA CHES04](https://link.springer.com/chapter/10.1007%2F978-3-540-28632-5_2)
 * [DPA CRYPTO99](https://www.rambus.com/differential-power-analysis/)
 * [DPA-Simplified](https://www.rambus.com/introduction-to-differential-power-analysis-and-related-attacks/) 
 * [AES能量攻击的建模与分析](http://kns.cnki.net/KCMS/detail/detail.aspx?filename=jsjk200803007&dbname=CJFD&dbcode=CJFQ)
 * [AES加密设备的差分能量攻击原理与仿真](http://kns.cnki.net/KCMS/detail/detail.aspx?filename=hdzj201312048&dbname=CJFD&dbcode=CJFQ)
 
# 这是什么？

* 本项目为密码工程侧信道大作业，对无保护的AES128密码实现进行了基本的侧信道攻击，本Demo中包含了差分能量攻击(DPA)和相关能量攻击(CPA)方法。

# 关键代码
```SCA
julia> # Remove the HW data pass

```

```SCA
# first define an attack
attack = AesSboxAttack()

```
# 测试结果

```
DPA分析结果：33157e41908000ff75f2103247a88c37
CPA分析结果：33157e41908000ff75f2103247a88c37
```
* [前8bitDPA测试结果]

* [前8bitCPA测试结果]
