# Week4 HOMEWORK

## 課題
”Google”から”渋谷”を（最短で）たどる経路を探すプログラム

## 使用言語
Java (15.0.2)

## 実行方法


### Javaのインストール

ターミナルで
```
java --version
```
と入力し、以下のような出力が出なければ、この記事[【初心者でもすぐわかる】JDKのインストール方法 Mac編](https://eng-entrance.com/java-install-jdk-mac)を参考にしてインストールしてください。
```
java 15.0.2 2021-01-19
Java(TM) SE Runtime Environment (build 15.0.2+7-27)
Java HotSpot(TM) 64-Bit Server VM (build 15.0.2+7-27, mixed mode, sharing)
```

### ディレクトリ構成
[Wikipedia.java](https://github.com/mayu-snba19/google-step/blob/master/week4/Wikipedia.java)と[wikipedia_data.zip](https://drive.google.com/file/d/1zqtjSb-ZoR4rzVUWZrjNSES5GKJhYmmH/view)をダウンロードして以下のようなディレクトリ構成になるよう配置します。

```
.
├── Wikipedia.java
└── data
    ├── graph_small.png
    ├── links.txt
    ├── links_small.txt
    ├── pages.txt
    └── pages_small.txt
```

ターミナルで以下のコマンドを実行します。

```java
javac Wikipedia.java && java -Xmx5000M Wikipedia
```
-Xmx[サイズ]はヒープの大きさを指定するオプションです。PCに合わせて適切なサイズを設定してください。

## プログラムの説明
最短経路を求めるためにBFSを利用しました。経路を保存する変数dirを用意して任意のノードの最短経路を表示できるようにしています。(とても時間がかかるので)inputFilesメソッドが不用意に呼び出されないようにprivateメソッドにしてsingletonパターンを利用しています。Test.javaはWikipediaSmallに対してのテストコードです。正解がわからないためlinks.txtなどの大きいデータでは試さなくていいと思います。

現在のプログラムでは最短になる経路が複数あった場合も一つしか表示できないので、全て表示できるように改善したいです。
