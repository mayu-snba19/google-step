## WEEK6 HOMEWORK

## プログラム構成
- [malloc.c](https://github.com/mayu-snba19/google-step/tree/master/week6/real_malloc/malloc.c) : first-fit
- [best_fit.c](https://github.com/mayu-snba19/google-step/tree/master/week6/real_malloc/best_fit.c) : best-fit
- [worst_fit.c](https://github.com/mayu-snba19/google-step/tree/master/week6/real_malloc/worst_fit.c) : worst-fit

## 実行方法

[Makefile](https://github.com/mayu-snba19/google-step/tree/master/week6/real_malloc/Makefile)の2行目を、上のファイルのうちのいずれかに変更する。
```
SRCS=main.c [実行したいプログラムファイル名] simple_malloc.c
```
その後、ターミナルで
```
make run
```
と入力する。
<br>


## メモリ使用率
|        | first fit | best fit | worst fit |
| -------| :-------: | :-------:| :-------: |
| Challenge 1 |70%|69%|70%|
| Challenge 2 |40%|39%|39%|
| Challenge 3 |8%|50%|3% |
| Challenge 4 |15%|71%|5% |
| Challenge 5 |15% |74%|6%|

## 実行時間
|        | first fit | best fit | worst fit |
| -------| :-------: | :-------:| :-------: |
| Challenge 1 |9ms|1550ms|1510ms|
| Challenge 2 |6ms|630ms|571ms|
| Challenge 3 |119ms|869ms|76261ms|
| Challenge 4 |18601ms|9703ms|739650ms|
| Challenge 5 |14740ms|6392ms|5626655ms|

## コメント
メモリ使用効率・実行時間共にbest fitが一番いいという結果になった。
