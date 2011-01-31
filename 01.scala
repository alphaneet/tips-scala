/**
 * 基本型と演算
 */

///////////////////////////////////////
// 演算子を含む複数行
def f000 {
// Scalaは;で終わらなくてもいいので複数行の演算子を
// 含む文章の場合は注意がいる
val num1 = 1 + 2  // 1 + 2 で文が終わったと思われる
             + 3  // +(正符号）の3を定義と解釈される
println("num1: " + num1)

// 演算子で改行するとまだ文が終わってないと解釈され
// 次の文も評価される
val num2 = 1 + 2 + 
               3;
println("num2: " + num2)

// 複数行にわたる計算式の場合は括弧でくくるのが
// 安全で確実かと思われる
val num3 =  (1 + 2 
            + 3)
println("num3: " + num3)
}


/////////////////////////////////////////
// a = b = c = 10 という代入はできない
def f010 {
// a, b, cはInt型の初期値10として生成される
var a, b, c = 10 
print("a:"+a+" b:"+b+" c:"+c)

// =の演算子はUnitで返されるのでこれはできない
//a = b = c = 10 // <= error
}


/////////////////////////////////////////
// ブロック
def f020 {
//「{}」内がブロック
//ブロック外で宣言された変数にアクセス可能
//ブロックの最終行が結果として扱われる
//結果を変数に格納可能
val x = 10
// ブロックの戻り値を型推論してくれる
val i = {
  val xx = 10
  xx * x
}
println("i = " + i)
}


/////////////////////////////////////////
// Tuple
def f030 {
def func = ("test", 10)
// 複数の変数を指定できる
val (str, num) = func
println("str = %s, num = %d".format(str, num))
}


///////////////////////////////////////
// ,で区切ると複数の変数を定義できる
def f031 {
val x, y = 0
println("x, y = %d, %d".format(x, y))

val str1, str2 = "str"
println("str1, str2 = %s, %s".format(str1, str2))
}


///////////////////////////////////////
// Tupleを使用した複数の変数定義
def f032 {
// ,で区切る方法と違い変数一つ一つに値を設定できる
val (x, y) = (0, 1)
println("x, y = %d, %d".format(x, y))

val (str1, str2) = ("str1", "str2")
println("str1, str2 = %s, %s".format(str1, str2))
}


/////////////////////////////////////////
// AnyVal(Int,Floatなど)とStringの変換
def f040 {
  // toStringでStringに変換
  // toIntでIntに変換できる
  // ほかのAnyValも .toクラス名 で変換できる
  val num1 = 10
  val str1 = "20"
  val num2 = str1.toInt
  val str2 = num1.toString
  println("String => Int:" + num2)
  println("Int => String:" + str2)
}


///////////////////////////////////////
// Option型
def f050 {
// 基本的にScalaではnullの代わりにOptionでくるんだNoneを使う
// ScalaのAPIでnullが帰ってくるものはないらしい

// Some[T]とNoneはcase class。
// Some(型）の形でOptionを生成できる

// Some(型)を使う場合は型推論される
var some = Some(10)
// もちろん型を指定してもおｋ
var some2:Option[Int] = Some(20)
// Noneを入れる場合は型を宣言しないといけない
val none:Option[Int] = None

println("----- create ----")
print("some=" + some + " some2=" + some2 + " none=" + none)
print("\n")
println("\n----- get ----")
// getで中身を取得するけど普通は使わないっぽい
println("some.get = " + some.get)
try {
// Noneに対してgetをするとだったら例外がでる
println("none.get = " + none.get)
} catch {
  case _ => println("none.get = NoSuchElementException")
}

// 通常はmatchを使う
println("\n----- match ----")
def m(o:Option[Int]) {
  print(o + ": ")
  o match {
    case Some(x) => println("Some(x) = " + x)
    case None => println("None")
  }
}
m(some2)
m(none)

// isDefinedで定義されてるかどうかを調べる
println("\n----- isDefined ----")
println("some.isDefined = " + some.isDefined)
println("none.isDefined = " + none.isDefined)

// orElseでNoneだった場合に使うSomeのデフォルト値を設定できる
println("\n----- orElse ----")
println("some.orElse(Some(100)) = " + some.orElse(Some(100)))
println("none.orElse(Some(100)) = " + none.orElse(Some(100)))

// getOrElseでNoneだった場合に使う値のデフォルト値を設定できる
println("\n----- getOrElse ----")
println("some.getOrElse(100) = " + some.getOrElse(100))
println("none.getOrElse(100) = " + none.getOrElse(100))
}


///////////////////////////////////////
// Option型の応用
def f060 {
val some = Some("aiueo")
val none:Option[String] = None

println("----- create ----")
print("some=" + some + " none=" + none)
print("\n")

// foreachで値があったときのみ（None以外）を処理をする
println("\n----- foreach ----")
print("some.foreach{str => print(str)} = "); 
some.foreach{str => print(str)}
print("\n")

// 別の関数の引数にする場合は省略できる
print("none.foreach(print) = "); none.foreach(print)
print("\n")

// mapによる変換
println("\n----- map ----")
println("some.map{ n => n.toUpperCase} = " +
  some.map{ n => n.toUpperCase})
// 要素が一つならを省略して _ をつかえる
println("some.map{_.toUpperCase} = " +
  some.map{_.toUpperCase})
// Noneは実行されない(そのままNoneを返す）
println("none.map(_.toUpperCase) = " + none.map(_.toUpperCase))

// mapとgetOrElseの組み合わせ
println("\n----- map and getOrElse ----")

// "aiueo"という文字列ならtrue
// それ以外ならfalseを返す
def check(o:Option[String]):Boolean = {
  o.map(_ == "aiueo").getOrElse(false)
}
println("check(some) = " + check(some))
println("check(none) = " + check(none))
println("check(\"abcde\") = " + check(Some("abcde")))

// Optionとforの組み合わせ
println("\n----- Option and for ----")
// for式のジェネレーターに指定できる
// 複数のOptionがSomeの場合のみ
// なんらか処理を行わせたい場合に便利

// "hp"と"mp"が指定されてる場合のみ処理を行う
def check2(m:Map[String, Int]) {
  println("check2 = "  + m)
  for(hp <- m.get("hp"); mp <- m.get("mp")) {
    println("hp = %d, mp = %d".format(hp, mp))
  }
}

check2(Map("hp" -> 100, "mp" -> 50))
check2(Map("test" -> 10, "test2" -> 600))
}


///////////////////////////////////////
// Option型の応用2
def f070 {
val aiueo  = Some("aiueo")
val abcde = Some("abcde")
val taro = Some("taro")
val none:Option[String]  = None

println("----- create ----")
print("aiueo=" + aiueo + " abcde=" + abcde + " taro=" + taro + " none=" + none)
print("\n")

// 最後のチェインまでNoneだとforeach実行されない
// 途中でNone以外の要素になれば最後のforeach間でその値になる
// 一つでも値が設定されていたら例外を投げるときとかに使えるらしい
println("\n----- orElse chain ----")
def check(o1:Option[String], o2:Option[String], o3:Option[String]) {
  //o1.orElse(o2).orElse(o3).foreach(println)

  // .や()を省略すると以下ようになる
  o1 orElse o2 orElse o3 foreach(println)
}

print("check(aiueo, abcde, taro) = "); check(aiueo, abcde, taro)
print("check(none, abcde, none) = "); check(none, abcde, none)
print("check(taro, none, aiueo) = "); check(taro, none, aiueo)
print("check(none, none, none) = "); check(none, none, none)
print("\n")

// collectの条件が適用されるときのみmapする
// PartialFunctionというのが引数になるらしい詳しくは分からない
// 条件付mapみたいな感じで受け止めておけばいいかも
// 条件が適用できない場合はNoneになる
println("\n----- collect ----")

// taroは大文字にして"abcde"は"アルファベット"と返してほかはNone
def check2(o1:Option[String]):String = {
  o1.collect {
    case str if str == "taro" => str.toUpperCase
    case "abcde" => "Alphabet"
  } getOrElse("None")
}
println("check2(taro) = " + check2(taro))
println("check2(abcde) = " + check2(abcde))
println("check2(aiueo) = " + check2(aiueo))
println("check2(none) = " + check2(none))

// Optionの値がtrueを返すかチェックする
// Noneに対して呼び出すと常にfalse
println("\n----- exists ----")

// taro以外はfalse
def check3(o1:Option[String]):Boolean = {
  o1.exists(str => str == "taro")
}
println("check3(taro) = " + check3(taro))
println("check3(abcde) = " + check3(abcde))
println("check3(aiueo) = " + check3(aiueo))
println("check3(none) = " + check3(none))

// 引数の値がtrueを返す場合はSome[A]を返す
// falseの場合はNoneが返ってくる
// existsの違いは戻ってくるのがBooleanかOptionかの違いかな？
println("\n----- filter ----")

// check3(exists)のfilter版。taroのみtrue
def check4(o1:Option[String]):Option[String] = {
  o1.filter(str => str == "taro")
}
println("check4(taro) = " + check4(taro))
println("check4(abcde) = " + check4(abcde))
println("check4(aiueo) = " + check4(aiueo))
println("check4(none) = " + check4(none))

// 正直よく分からないんで説明文はコピペ
// flatMap[B](f: (A) ⇒ Option[B]): Option[B]
// "(A) => Option[B]"の結果がSome[B]だったらSome[B]を、NoneだったらNoneを返します。
println("\n----- flatMap ----")
val m = (0 to 10).toList.map(n => n -> (97 + n).toChar).toMap
println("m: " + m)
// valueが'b'とkeyが5以上は取り除いたvalueだけののリストを取得
println("m.flatMap = " + m.flatMap {
  case (_, 'b') => None
  case (k, _) if k >=5 => None
  case (k,v) => Some(v)
})
}


/////////////////////////////////////////
// Enum型
def f080 {
object EnumTest extends Enumeration {
  // Enumの型の名前を定義
  type Number = Value

  val One, Two = Value
}

import EnumTest._

val num = One
println("num = " + num)
println("num.getClass = " + num.getClass)
val num2 = One
println("num == num2: " + (num == num2))
}
