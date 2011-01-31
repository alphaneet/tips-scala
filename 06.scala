/**
 * コレクション
 */

/////////////////////////////////////////
// コレクションの生成
def f000 {
// ListとArrayの違い
// Listは中身を変更できない。Arrayはできる。

// Listの生成
// ::演算子（後述）でも生成できる
val l = List(1, 2, 3)
println("----- List -----")
for(s <- l) println(s)

// Arrayの生成
val a = Array("one", "two", "three")
println("----- Array -----")
for(s <- a) println(s)

// Int型の要素が3個の配列を生成
val a2 = Array[Int](3) 

// Mapの生成 
println("----- Map -----")
val m = Map(1 -> "one", 2 -> "two", 3 -> "three")
for((k, v) <- m) println("key: %d, value: %s".format(k,v))

// こういう風にも作れる
print("\n")
val m2 = (0 to 10).toList.map(n => n -> (97 + n).toChar).toMap
m2.foreach(n => println("key: %2d, value: %c".format(n._1, n._2)))
}


/////////////////////////////////////////
// 多次元配列
def f010 {
  // 5x5のInt配列の生成文
  // これだとワーニングがでる（コンパイルは通る）
  // var arr = new Array[Array[Int]](5,5) // <= warning

  // 1個1個作るとワーニングは出ない
  var arr = new Array[Array[Int]](5)
  for(i<-0 until 5; j<-0 until 5) {
    arr(i) = new Array[Int](5)
    arr(i)(j) = (i*5) + j
    println("arr(%d)(%d) = %d ".format(i, j, arr(i)(j) ))
  }
}


/////////////////////////////////////////
// Mapコレクションの基本的な操作
def f011 {
// Mapは2個のTupleの配列と考えてほぼ問題ないっぽい
var m = Map(0 -> "a", 1 -> "b", 2 -> "c")

// += で要素の追加
// valだと追加できない
m += 3 -> "d"
println(m)

// -= で要素の削除
m -= 0
println(m)

// sizeメソッドで要素の数
println("m.size = " + m.size)

// getメソッドを使えばOptionで取得できる
println("m.get(2) = " + m.get(2))
println("m.get(0) = " + m.get(0))

println("m.get(2).get = " + m.get(2).get)
// applyメソッドでも値を取得できるが
// 例外処理とかをやりやすくするため
// getでオプションをとってmatchか
// getOrElseを使うのが一般的な模様
println("m.apply(1) = " + m.apply(1))
println("m(2) = " + m(2))

// getで帰ってくるのはOption型なので
// getOrElseが使える
println("m.getOrElse(3) = " + m.getOrElse(3, "not found"))
println("m.getOrElse(6) = " + m.getOrElse(6, "not found"))
}


/////////////////////////////////////////
// Mapのキーに対する操作
def f012 {
// toMapやtoListしたあとは ; をつけたほうがいいかも
val map = (0 to 5) map { e => e -> (e + 97).toChar.toString } toMap;
println("----- create map -----")
// ↑;がない場合まだ文が終わってないと判断してしまう
// そのためprintlnがtoMapの続きとして評価してしまう
// その場合printlnはUnitしか返さないのでエラーになる
// ;をつければ大丈夫なのでつけるの無難かも
// それか改行して一行開ければ文は終了したと判断する

println(map)

println("\n----- Map.keys -----")
// keysでキーの集合を求めることができる
println(map.keys)

println("\n----- Map.values -----")
// valuesで値だけを取得できる
println(map.values)

println("\n----- Map.filterKeys -----")
// filterKeysでkeyだけにfilterを適用することができる
println("key < 3:  " + map.filterKeys(_<3).keys)

println("\n----- Map.filter -----")
// filterの中の式はcase文でもいい
println("value < 30: " + map.filter { case (k,v) => v == "a"  })

println("\n----- Map.contains -----")
// キーが含まれてるかどうかを調べるときはcontainsメソッドを使う
println("3 key: " + map.contains(3))

}


/////////////////////////////////////////
// SortedMap
def f013 {
// 通常のMapは順番はぐちゃぐちゃになってるが
// SortedMapを使えば整列したマップを扱うことができる

// おそらくsortメソッド的なのを拡張すれば
// 自分の作成したクラスでも SortedMap のキーに使えると思うが
// やり方がわからないのでIntを使う。

println("----- create SortedMap -----")
var map = collection.SortedMap[Int, Int]()
(0 to 5) foreach { e =>
  map += e * 10 -> e 
}
println(map)

println("----- SortedMap.+= -----")
// 適当な数を入れても順番通りになる
map += 3 -> 0
map += 15 -> 0
map += 13 -> 0
println(map)
}


/////////////////////////////////////////
// ::メソッド
def f020 {
val list = List(1, 2)
// = の左側だとリストを分割に使える
// xに一番最初の要素を渡して
// yにそれ以外を渡す
// ListのみでArrayはできない
// Listの長さと変数の数が一緒でも最後の変数は必ずList型になる
val x :: y = list
println("x = " + x)
println("y = " + y)

// = の右側だとリストの生成に使える
// リストの終端はNilにする
val list2 = "moke" :: "hoge" :: "fire" :: Nil
for(s <- list2) println(s)
}

/////////////////////////////////////////
// :::メソッド
def f030 {
// リスト同士を連結させる場合は:::メソッドを使う
val l = List(1, 2) ::: List(3, 4, 5)
for(s <- l) println(s)
}


/////////////////////////////////////////
// toList, toArray, toMap
def f040 {
// Array,List,Mapの相互変換
val a = Array(1, 2, 3)
val l = a.toList
println("----- Array -> List -----")
for(s <- l) print(s + " ")
print("\n")

val l2 = List(1, 2, 3)
val a2 = l2.toArray
println("----- List -> Array -----")
for(s <- a2) print(s + " ")
print("\n")

val m = Map(1 -> "a", 2 -> "b", 3 -> "c")
val l3 = m.toList
println("----- Map -> List -----")
for(s <- l3) print(s + " ")
print("\n")

// 2個のTupleの配列
val a3 = Array((1, 2), (3, 4), (5, 6))
println("----- Array -> Map -----")
val m2 = a3.toMap
for((x, y) <- m2) print("x=%d:y=%d ".format(x,y))
print("\n")
}


/////////////////////////////////////////
// to byでのコレクション生成
def f050 {
val a = (1 to 18 by 3).toList
for(num <- a) println(num)
}


/////////////////////////////////////////
// for~yieldでのコレクション作成
def f060 {
val l = List("abc", "def", "ghi")
// 単語の前後に*をつけた文字列に変換して格納する
val l2 = for(s <- l) yield {
  "*" + s +  "*"
}
for(s <- l2) println(s)

// 偶数だけの配列に変換
val a = (0 to 30).toArray
val a2 = for{i <- a if i%2==0} yield { i }
for(s <- a2) print(s + " ")
print("\n")
}


///////////////////////////////////////
// foreach
def f070 {
// for(num <- l) でもいいが、こちらはいろいろと省略できる
val l = List(1, 2, 3)
l.foreach(e => print(e + " "))
print("\n")

// 要素が一度しか使用されない場合は変数名を省略できる
def myPrint(str:Any) = print(str + " ")
l.foreach(myPrint(_))
print("\n")

// さらにforeachメソッドのパラメーターと同じ型の関数なら
// 直接していできる
l.foreach(myPrint)
print("\n")
}


///////////////////////////////////////
// mapメソッド
def f080 {
// コレクションに対して変換処理をおこなって
// 新たなコレクションを返す
val n1 = List(1, 2, 3, 4, 5)
val n2 = n1.map(elem => elem + 10)
n2.foreach(println)
}


///////////////////////////////////////
// filterメソッド
def f090 {
// 条件文を指定してfalseのものを除去

// 0~10のリストを生成して偶数だけを抽出
val l = (0 to 10).toList.filter(_ % 2 == 0)
l.foreach(println)
}


///////////////////////////////////////
// reduceLeft foldLeft
def f091 {
// リストを連結処理したい時に使うメソッド
// reduceLeftとfoldLeftの違いは
// foldLeftは始動要素を指定できる。
// あとfoldLeftは省略記述ができる

val l = (0 to 10).toList.map { e => (e+97).toChar.toString }
println("----- create list -----")
println(l)

println("\n----- reduceLeft -----")
// 左の要素から一つずつ重ねていく
println(l.reduceLeft(_+_))

println("\n----- foldLeft -----")
// 初期文字列を指定できる
println(l.foldLeft("foldLeft:")(_+_))

// /: メソッド（？）を使えば簡略化できる
println(("foldLeft2:" /: l)(_+_))

// ちなみにreduceRight,foldRightというのもある
// 違いは左（リストの先頭）から開始するか
// 右（リストの最後）から開始するかの違い
}


///////////////////////////////////////
// reverseメソッド
def f100 {
// 逆順にしたものをかえす
(0 to 10).toList.reverse.foreach(println)
}


///////////////////////////////////////
// lengthメソッド
def f110 {
// 要素の数を返す
println((0 to 10).toList.length)
}


///////////////////////////////////////
// isEmptyメソッド
def f120 {
def isEmptyString(l: List[Any]) =
  if(l.isEmpty) { "empty" } else { "not empty" }

// 要素が空かどうか調べる
println("List() is " + isEmptyString(List()))
println("List(1, 2, 3) is " + isEmptyString(List(1, 2, 3)))
}


///////////////////////////////////////
// existsメソッド
def f130 {
// 要素があるか判定
val l = (0 to 5).toList
println("l.exists(_ == 3) = " + l.exists(_ == 3))
println("l.exists(_ == 7) = " + l.exists(_ == 7))
}


///////////////////////////////////////
// findメソッド
def f140 {
// 要素を検索
val l = List((1, "a"), (2, "b"), (3, "c"))

// Tupleの2番目の要素をチェックしている
// Option型で返ってくる
println("l.find(_._2 == \"b\" = " + l.find(_._2 == "b"))
println("l.find(_._2 == \"d\" = " + l.find(_._2 == "d"))

// Mapでも一緒っぽい
val m = l.toMap
println("m.find(_._2 == \"c\" = " + m.find(_._2 == "c"))
}


///////////////////////////////////////
// mkStringメソッド
def f150 {
// 各要素を文字列に連結してStringに変換
val m:String = (0 to 10).toArray.mkString
println(m)

// ３つString引数を持つことができ、それぞれ
// 文字列の前、間、後の文字列になる
val l = (0 to 10).toList
println(l.mkString("(", " ", ")"))
// 引数が１つなら間の文字列の指定になる
println(l.mkString(","))
}


///////////////////////////////////////
// head と tail メソッド
def f160 {
val l = (0 to 10).toList
println("l = " + l)

// headは一番最初の要素を取得
println("head = " + l.head)

// tailは2番目以降の要素を取得
println("tail = " + l.tail)
}


///////////////////////////////////////
// splitAt, take, drop メソッド
def f170 {
// 指定したインデックスを境界にListを2つに分割
val l = "abcdefghij".toList
val (l2, l3) = l.splitAt(5)
println("l  = " + l)
println("l2 = " + l2)
println("l3 = " + l3)

// takeは先頭から指定したインデックスまでの要素を取得
println("l.take(3) = " + l.take(3))
// dropは指定したインデックスから最後までの要素を取得
println("l.drop(3) = " + l.drop(3))
}


///////////////////////////////////////
// ListBuffer,ArrayBuffer
def f180 {
// ・違い
// 機能は一緒だが内部データをArrayで保持しているか
// Listで保持しているかの違いがある

// ListBuffer - 先頭への挿入最後尾への追加の性能がいい
// ArrayBuffer - 最後尾への追加、インデックスの参照
//               更新の性能がいい
// 挿入や削除がない場合は基本的にはArrayBufferを使う。
import scala.collection.mutable.{ListBuffer, ArrayBuffer}

// 作成
val buf = new ArrayBuffer[String]

// 要素の追加
buf += "taro"
println("buf += \"taro\": " + buf)

// ++=メソッドでリストをまとめて追加
buf ++= List("Spring", "Summer", "Fall", "Winter")
println("buf ++= List:  " + buf)

// 長さの取得
println("buf.length: " + buf.length)

// 要素の取得
println("buf(3): " + buf(3))

// 要素の削除
buf -= "Fall"
println("buf -= \"Fall\": " + buf)

// removeメソッドでインデックスを指定して削除
buf.remove(2)
println("buf.remove(2): " + buf)

// 要素の更新
buf(1) = "jiro"
println("buf(1) = \"jiro\": " + buf)

// ListとArrayの変換
println("buf.toList: " + buf.toList)
println("buf.toArray: " + buf.toArray)
}

///////////////////////////////////////
// TupleからListの変換
def f190 {
  // productIteratorを使えば
  // Any型のリストに変換できる
  val l = (1, 2, 3, 4).productIterator.toList
  println(l)
}

