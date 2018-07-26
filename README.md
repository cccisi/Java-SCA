# SCA ——　Side Ｃhannel Ａttack
* a DPA & CPA demo on AES 128

# What is Side Ｃhannel Ａttack?("https://en.wikipedia.org/wiki/Side-channel_attack")

* You can compile this demo in IDEA:
This SCA demo supports:

* Correlation power analysis (CPA)
* Differential power analysis (DPA)("https://en.wikipedia.org/wiki/Power_analysis")

# Some interesting reference
	* [non-profiled LRA](https://eprint.iacr.org/2013/794.pdf)
 	* [Conditional averaging](https://eprint.iacr.org/2013/794.pdf)
  * [Mutual Information Analysis](https://eprint.iacr.org/2007/198.pdf) (MIA)

# 密码工程侧信道大作业

* 包含差分能量攻击和相关能量攻击

# 关键代码
```SCA
julia> # Remove the HW data pass

julia> popDataPasss(trs)

julia> # Correlate with the cipher state at the start of round 5. We define a leak function first.

julia> round5leak(str,state,globstate) = (if str == "r5.start"; globstate[:] = state; end; return state;)
round5leak (generic function with 1 method)

```

```SCA
# first define an attack
attack = AesSboxAttack()
attack.mode = CIPHER
attack.keyLength = KL128
attack.direction = FORWARD
# then an analysis
analysis = CPA()
analysis.leakages = [Bit(i) for i in 0:7]
# combine the two in a DpaAttack. The attack field is now also accessible
# through params.attack, same for params.analysis.
params = DpaAttack(attack,analysis)

```
# 测试结果

```
DPA分析结果：33157e41908000ff75f2103247a88c37
CPA分析结果：33157e41908000ff75f2103247a88c37
```
