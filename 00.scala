/**
 * 基礎的な文法
 */

///////////////////////////////////////
// コメント文
// /* */ はネストすることができる

/* /* /* もうまんたい */ */ */

// /* */を対応させないとエラーになる
//  /* /* */  <= これはエラー


/////////////////////////////////////////
// package
// スクリプト中ではpackageを宣言できないっぽいので
// コメント文にしてある

// foo.bar.Hogeクラスが作られる
//package foo.bar
//class Hoge { }

// とげ括弧で適用範囲を決めれる
//package foo {
//  package bar {
//    class Hoge { }
//  }
//}

// foo.bar.Hogeクラスと
// foo.bar.sub.Subクラスが作られる。
//package foo.bar
//class Hoge { }
//
//package sub {
//  class Sub { }
//}

// foo.bar.Hogeクラスが作られる。
//package foo
//package bar
//class Hoge { }


/////////////////////////////////////////
// import
def f000 {
// 一つのクラスだけをインポートする。
import java.util.Date

// ワイルドカードは「_」で表す。
import java.util._

// 同一パッケージ内の複数のクラスをまとめて一行で指定できる。
import java.util.{List, Map}

// クラスを別の名前でインポートする。
import java.util.{Map => JavaMap}

// 別名を「_」にすると、そのクラスはインポートされなくなる。
// 別途「_」だけ指定すると、それ以外のクラスは全てインポートされる。
// 下記の例では、Date以外のjava.utilのクラスが全てインポートされる。
import java.util.{Date => _, _}

// 前に記述されたimport文のスコープに影響をうける
import java.awt._;
// 本当はjava.awt.color
import color._; 
// 2回目以降も影響は続く(java.awt.dnd._)
import dnd._;
// 相対importからのimportは次に影響を及ぼさないっぽい
// 絶対importを記述した位置からのimportになる
// それが絶対importを入力するか
//import peer._; <= error
import dnd.peer._;
import java.awt.dnd.peer._;
// 別の絶対インポートを定義しても
// 今までの絶対import情報は残る
// 相対import時にに複数の絶対import下に
// 同じ名前のパッケージがあった場合はエラーになる
// javax.swing.event._ と java.awt.event._ が競合している
// import javax.swing._;
// import event._; <= error

import javax.sql._; 
import event._;
}


///////////////////////////////////////
// static import
def f010 {
// Scalaではstaticメソッドやフィールドにアクセスする場合
// たとえ継承してたとしてもクラス名.メソッド名 or フィールド名の
// 形にしないといけない。
// これがめんどくさい場合はjavaでいうstatic import文を使うといい
// Scalaではstaticを省略できる
// processingの定数がstatic finalの形で
// 定義されているインターフェースをimportする場合
import processing.core.PConstants._
// processingの定数がそのまま使えて便利
println("ARGB:" + ARGB + " P2D:" + P2D + " PI:" + PI)

object Hoge { val name = "Hoge" }

// objectもstatic importできる
def importHoge {
  // importはどこでも定義でき
  // スコープの範囲内だけで有効
  import Hoge._
  // import したので Hoge.name を省略
  println(name)
}
importHoge
}


///////////////////////////////////////////
// isInstanceOfとasInstanceOf
// Javaの instanceof と 型キャスト(class)
def f020 {
val num = 10
// isInstanceOfでインスタンスの型を調べれる
// []はジャネリクス
println("num.isInstanceOf[Int]:" + num.isInstanceOf[Int])

// asInstanceOfで型キャストができる
println("num.asInstanceOf[Byte]:" + num.asInstanceOf[Byte])

class oya
class tanin
trait hoge { val moke = 10 }
class ko extends oya with hoge

val k = new ko
 
// 全然関係ないところのキャストは例外がでる
//k.asInstanceOf[tanin]  <= error
val o1 = k.asInstanceOf[oya] // アップキャストはおｋ
println("val o1 = k.asInstanceOf[oya]:" + o1.getClass.getName)

// サブクラスを含んでいるスーパークラスからのダウンキャストはおｋ
val k2 = o1.asInstanceOf[ko]
println("o1.asInstanceOf[ko]:" + k2.getClass.getName)

// サブクラスを含んでいないスーパークラスからの
// ダウンキャストは例外がでる
val o2 = new oya
//o2.asInstanceOf[ko]  <= error 
}


///////////////////////////////////////////
// asInstanceOfの省略(:)
def f021 {
// アップキャストの場合は
// 変数名 : 型名 の形で型変換ができる
class C
class C2 extends C

val c = new C2:C
println(c)

val c2 = new C2
println(c2:C)

// ダウンキャストの場合はasInstanceOfを使わないとエラーになる
//println(c:C2) <= error

// プリミティブ型(AnyVal)のAnyRefへの変換の場合も
// asInstanceOfを使用しないとエラーになる
// Any や AnyValの変換ならできる
// println(10:AnyRef) <= error
println(10:Any)
println(30:AnyVal)
}


///////////////////////////////////////////
// getClass
def f030 {
// AnyRefのサブクラス(通常のクラス)はgetClassで取得できる
val str = "aiueo"
println("String.getClass: " + str.getClass)

// AnyValのサブクラス(プリミティブ型)はAnyRefにキャスト
// （通常はJavaのプリミティブラッパークラス(Integerとか))
// しなければいけない
val num = 10

// AnyRefにキャストしてください的なエラーが出る
//num.getClass  <= error

// javaのプリミティブラッパークラスに変換される
println("Int.asInstanceOf[AnyRef].getClass: " +
          num.asInstanceOf[AnyRef].getClass)
}


/////////////////////////////////////////
// eq と ne
def f040 {
// Stringなどの一部のAnyRefのサブクラスは
// == の時に参照の比較でなく値の比較をする
// そういったクラスに参照の比較をさせたい場合は
// eq か ne の演算子を使う
class oya(val name:String)
trait const {
  val o = new oya("const_oya")
  val str1 = "const1"
  val str2 = new String("const2")

  val num1 = BigInt(20)
  //val num2 = new BigInt(20) <= errorになる
}
class A extends oya("A") with const
class B extends oya("B") with const

var a = new A
var b = new B

val s1 = new String("test")
val s2 = new String("test")

val s3 = "test2"
val s4 = "test2"

// newを使用して新たなStringのインスタンスを生成した場合は
// == は true になるが eq は false になる
println("new String(\"value\")")
println("==: " + (s1 == s2))
println("eq: " + (s1 eq s2) + "\n")

// "String"で文字列を生成した場合は == も eq も true になる
println("\"value\"")
println("==: " + (s3 == s4))
println("eq: " + (s3 eq s4) + "\n")

// trait val で宣言して with させた場合でも
// クラスを生成たびにインスタンスをつくるっぽい
println("trait { new class }")
println("==: " + (a.o == b.o))
println("eq: " + (a.o eq b.o) + "\n")

// newせずに生成したインスタンス(AnyVal,String,BigInt ...etc)は
// 参照も同じになるのでtraitの中で
// static final風味に使っても大丈夫かもしれない（分からない）
// 実は毎回インスタンス作ってるかもしれない
println("trait { \"value\" }")
println("==: " + (a.str1 == b.str1))
println("eq: " + (a.str1 eq b.str1) + "\n")

println("trait { new String(\"value\") }")
println("==: " + (a.str2 == b.str2))
println("eq: " + (a.str2 eq b.str2) + "\n")

println("trait { BigInt(value) }")
println("==: " + (a.num1 == b.num1))
println("eq: " + (a.num1 eq b.num1) + "\n")

// Array の場合は値が一緒でnewを使用せずに生成した場合でも
// == eq ともにfalseになる
val a1 = Array(1, 2, 3)
val a2 = Array(1, 2, 3)
println("Array(value list)")
println("==: " + (a1 == a2))
println("eq: " + (a1 eq a2) + "\n")

// Listの場合は値が一緒ならば==はtrueになり
// eq はfalseになる
val l1 = List(1, 2, 3)
val l2 = List(1, 2, 3)
println("List(value list)")
println("==: " + (l1 == l2))
println("eq: " + (l1 eq l2) + "\n")
}

