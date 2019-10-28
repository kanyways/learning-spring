## 使用Ethereum Wallet连接节点

在“安装以太坊环境”中说到“Ethereum Wallet”这个钱包，默认这个钱包是可以直接连接Ethereum的主网络、测试网络的。很多人在打开对应的程序后发现不能连接到自己建立的节点上，也没有办法直接选择自己的节点。
现在我们来说怎么连接到自己的节点并且修改，数据存放目录。

## 以太坊的节点

```bash
/**
 * 区块链主要地址
 */
public final static String ETHEREUM_MAIN_NODE_ENDPOINT = "https://mainnet.infura.io";

/**
 * Rinkeby的测试地址
 */
public final static String ETHEREUM_TEST_RINKEBY_NODE_ENDPOINT = "https://rinkeby.infura.io";

/**
 * Kovan的测试地址
 */
public final static String ETHEREUM_TEST_KOVAN_NODE_ENDPOINT = "https://kovan.infura.io";
/**
 * Ropsten的测试地址
 */
public final static String ETHEREUM_TEST_ROPSTEN_NODE_ENDPOINT = "https://ropsten.infura.io";
```

## 修改存放目录

本来在正常的Ethereum Wallet版本中呢，是可以直接使用`--node-datadir`来修改数据存放的目录的，案例如下：

```bash
修改快捷方式的路径为：
D:\Tools\Ethereum-Wallet\Ethereum Wallet.exe" --node-datadir="D:\Tools\Ethereum-Wallet\data"
```

> 现在咱们来说但是，根据我自己的验证，我使用的这个版本是没有办法显示这个功能的，所以咱们换一种方式来实现

首先咱们打开`命令行提示工具`

```bash
mklink /j "C:\Users\这个地方是用户名\AppData\Roaming\Ethereum Wallet" D:\Tools\Ethereum-Wallet\data
# 如果不想使用这样的方式可以使用过下面的命令，但是我们要确定是不是这个目录：
echo %AppData%
# 看看输出的是不是Roaming的目录，如果是则执行下面的语句
mklink /j "%AppData%\Ethereum Wallet" D:\Tools\Ethereum-Wallet\data
```

这样做的原理是建立一个文件夹的链接，相对来说比较方便。因为默认打开的“Ethereum Wallet.exe”是存放在“%AppData%\Roaming\Ethereum Wallet”下的。
但是不足之处就在于，如果换一个用户进来又需要再次这样执行。

## 连接私有节点

上面说完咱们设置不同的存放目录，现在配置怎么连接远程的私有节点，这个才是关键。

依然是修改桌面上的快捷方式：
```bash
# 这个IP是建立节点的Ip，至于端口为什么是9999是因为在创建的时候指定了。
"D:\Tools\Ethereum-Wallet\Ethereum Wallet.exe" --node-datadir="D:\Tools\Ethereum-Wallet\data" -rpc http://xxx.xxx.xxx.xxx:9999
```

## 重点

配置完成之后打开当前的“Ethereum Wallet”，创建帐号，并开始挖矿。怎么去使用这个钱包创建账户我就不说了，前面的“创建以太坊节点”中已经说了常用的命令了。
记住一定要设置一个挖矿的奖励账户，并且开始挖矿。

```bash
# 远程连接私有节点
geth attach http://xxx.xxx.xxx.xxx:9999
# 获取当前的账户
eth.accounts
# 设置挖矿的奖励帐号
miner.setEtherbase(eth.accounts[0])
# 开始挖矿
miner.start()
```

至此，一个私有节点就算真的可以使用了，至于怎么去创建合约等，自己去体验就好

## 结语

到这里有基础的人应该都知道怎么开始使用钱包了，不会的也没有关系，不用担心网上有很多资料，但是我不说了，因为我很懒。

## 参考资料

- [以太坊钱包Ethereum Wallet C盘数据转移](https://blog.csdn.net/wo541075754/article/details/77650693)
- [Ethereum wallet连接指定节点geth](https://blog.csdn.net/mazhaozi/article/details/81075168)
- [如何将以太坊Ethereum Wallet钱包geth从C盘成功自定义到其他盘](https://blog.csdn.net/gsl222/article/details/79420029)