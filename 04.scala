/**
 * 関数
 */

///////////////////////////////////////
// クロージャ
def f000 {
// 戻り値は型推論で勝手に決まるっぽい
// c1() の形で実行
var c1 = () => println("クロージャ")
// {}もできる
var c2 = () => {
  println("kuro-ja")
}
// 既存の関数も 名前 _ をつければ変数にいれれる
def kizon {
  println("kizon")
}
// これだとkizonの戻り値を格納するという意味になる
var k = kizon
//k() // よべない
// 変数に関数を入れる場合は _ をつける
var k2 = kizon _
k2() // よべる
}


///////////////////////////////////////
// 関数の引数にクロージャ
def f010 {
  def say(func: () => Unit) = func() 
  // () => の形で引数に渡す{ }でブロックを作ると複数行かける
  say(() => println("moke"))

  def test() { println("test") }

  // 既存の関数も引数に指定できる
  say(test)

  def test2 { println("test2") }
  // ()を省略している関数の場合は _ をつける
  say(test2 _)
}


///////////////////////////////////////
// デフォルト引数
def f011 { 
  // 通常の関数はデフォルト引数を定義できる
  def f1(str:String = "call f1()") = println(str)
  f1()

  // Stringの部分は省略できない（型推論してくれない）
  //def f2(str = "moke") = println(str) // <= error

  //クロージャの場合はデフォルト引数を設定できない
  //var f3 = (str:String = "moke") => println(str) <= error

  // 関数内部のクラスのコンストラクタの場合
  // デフォルト引数は定義はできるが
  // 生成ができない（結局できないと同じようなもの）
  class c1(val name:String = "")
  //val ob1 = new c1 // <= error

  // 引数を指定すればエラーは出ない
  val ob2 = new c1("new c1")
  println(ob2.name)

  // classやobjectの内部クラスや通常のクラス定義では
  // デフォルト引数のコンストラクタを定義できる
  object Obj { 
    class c3(val name:String="new c3")
    val ob3 = new c3
    println(ob3.name)
  }
  Obj
}


///////////////////////////////////////
// 名前渡しパラメーター
def f020 {
def nano() = {
  println("Geting nano")
  System.nanoTime
}
// =>の左側（関数の引数パラメーター）の部分を
// 省略すると関数そのものを渡せる
def delayed(t: => Long) = {
  println("In delayed method")
  // このtの時点で関数が実行される
  // 名前私の場合は()はいらない（あるとエラー）
  println("Param: " + t)
}

// 名前渡しするときは関数名 _ はいらない。
// そのまま関数を呼び出す感じで引数に指定する
delayed(nano())
}


///////////////////////////////////////
// returnの型推論
def f030 {
// 関数名:型の形で戻り値の型を指定できる
def f0:String = { "f0" }

// :Stringは省略可能（型推論してくる）
def f1 = { "f1" }

// 要素が一つだと {} も省略できる
def f2 = "f2"

// =を省略するとUnitが戻り値という意味になる
def f3 { "f3" }
// ↑これは戻り値がUnit

println("f0 = " + f0 + " | f0.getClass = " + f0.getClass)
println("f1 = " + f1 + " | f0.getClass = " + f1.getClass)
println("f2 = " + f2 + " | f0.getClass = " + f2.getClass)
println("f3 = " + f3 + 
        " | f3.getClass = " + f3.asInstanceOf[AnyRef].getClass)
}


///////////////////////////////////////
// 可変長引数
def f040 {
// 引数の型の後の*をつける
def f(x:Int*) {
  println("f(1,2,3,4) = " + x)
  println("x.getClass = " + x.getClass)
}
f(1, 2, 3, 4)

// 内部的には 
// scala(scalaは省略可能).collection.mutable.WrappedArray
// が作られてるらしい
// よく分からないけど可変長であること以外は
// 普通のArrayと同じ使い方で大丈夫そう
def f2(x:Int*) {
  // 要素の指定もできる
  println("x(0) = " + x(0))

  // 高階関数も使える
  println("x.foreach(println)")
  x.foreach(println)
}

f2(1, 2, 3, 4, 5, 6)

// シーケンス(ListやArray)を可変長引数に渡したい場合は
// 変数名:_* を受けて引数に指定する
def f3(str:String*) {
  println("str.foreach(println)")
  str.foreach(println)
}

val args = List("Taro", "Jiro", "Hanako")
f3(args:_*)

// もちろんいきなりやってもおｋ
//f3(List("Taro", "Jiro", "Hanako"):_*)
}


///////////////////////////////////////
// プレースホルダー構文
def f050 {
def line = {
  print("please input: " )
  val str = readLine
  println
  if(str == "") "default" else str
}
val l1, l2 = line

def input(converter: (String, String) => String) {
  println("print :" + converter(l1, l2))
}

// 通常は以下にようになるが冗長気味
input((f1:String, f2:String) => 
  f1.toUpperCase + " " + f2.toLowerCase)

// 型の指定を省略することもできる
input((f1, f2) => f1.toUpperCase + " " + f2.toUpperCase)

// 引数が一度しか使われず、
// 第一引数から順番に使用される場合は引数自体を省略可能
// _が引数の省略形で第一引数から順番に呼ばれる
input(_.toLowerCase + " " + _.toUpperCase)
}


///////////////////////////////////////
// カリー化(関数の部分適用）
def f060 {
def func(x:Int)(y:Int) = x + y

// 新しい関数を作る
// 内部的には def func(y:Int) = { val x = 10; x + y }
// と一緒な感じになる
var f1 = func(10)(_)
println(f1(30))

// ()は普通省略するらしい

func(30)_

// 最後でない場合は型宣言しないとエラーになる

// func(_)(40) <= error

val f2 = func(_:Int)(50)
println(f2(60))
}


///////////////////////////////////////
// カリー化(新しい制御構造化）
def f061 {
def loop(ct:Int)(func:Int => Unit) {
  for(i <- 0 until ct) func(i)
}

loop(2)((i:Int) => println((i+97).toChar))

// 引数が一度しか呼ばれない場合は省略できるのは
// 各種Collectionと一緒
loop(3)(println)

// 引数が一つの場合中括弧に変更することが可能
// より制御構文ぽくなる。
// i => という形で引数を受け取れる
// まったく他のCollectionと一緒
loop(4) { i =>
  println(i + " count")
}

// 部分適用を利用して5回ループ固定の関数を作成
val loop5 = loop(5)_

loop5 { i =>
  println(i + " count: loop5")
}

}
