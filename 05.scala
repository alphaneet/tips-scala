/**
 * クラス
 */

///////////////////////////////////////
// classをtraitに継承させた場合
def f000 { 
// コンストラクタがありとなしとデフォルト引数のを３つ定義
class C1(val name:String)
class C2 { val name = "default" }
class C3(val name:String = "default")

// トレイトから引数が必要なクラスは継承できない
//trait T1 extends C("name") // <= error

// 引数を指定しないデフォルト引数コンストラクタや
// コンストラクタなしのクラスは生成できる
trait T2 extends C2
// 引数を指定したデフォルト引数コンストラクタは使えない
//trait T3 extends C3("trait") // <= error
// こっちはおｋ
trait T3 extends C3

// extends と with で変数名などがかぶっている場合は
// extendsのほうが優先される
object O1 extends T2
println("O1.name = " + O1.name)
// デフォルト引数コンストラクタのクラスを継承した場合は
// デフォルト引数のフィールドにアクセスしようとすると
// エラーがでる
object O2 extends T3
//println("O2.name = " + O2.name) // <= error
object O3 extends C3("object") with T3
println("O3.name = " + O3.name)

// 結論
// コンストラクタがないclassならばtraitでも継承できるものの
// 基本的にtraitからclassを継承するべきではない
// has-a（移譲だっけ？）にするべき
}


///////////////////////////////////////
// objectでのコンストラクタ
def f010 {
// 結論：参照した最初の１回だけ呼ばれる
class C1
object O1 {
  var c1 = new C1
  // １回だけ呼ばれてる
  println("constructor O1")
}
// objectのコンストラクタは
// 参照した始めに１回だけ呼ばれる
print("O1.hashCode = " + O1.hashCode)
print(" O1.c1.hashCode = " + O1.c1.hashCode)
print("\n")

// 参照を渡してもコンストラクタは呼ばれない
val o1 = O1
println("val o1 = O1")
print("O1.hashCode = " + O1.hashCode)
print(" O1.c1.hashCode = " + O1.c1.hashCode)
print("\n")

val o2 = O1
println("val o2 = O1")
print("O1.hashCode = " + O1.hashCode)
print(" O1.c1.hashCode = " + O1.c1.hashCode)
print("\n")

// 直接フィールドにnewしたら新たに生成される（当然だよねｗ）
O1.c1 = new C1
println("O1.c1 = new C1")
print("O1.hashCode = " + O1.hashCode)
print(" O1.c1.hashCode = " + O1.c1.hashCode)
print(" o2.c1.hashCode = " + o2.c1.hashCode)
print("\n")
}


/////////////////////////////////////////
// object を static import したときのアクセス
def f020 { 
object O {
  private val str1 = "private"
  private def func1 = println("visible is " + str1)

  protected val str2 = "protected"
  protected def func2 = println("visible is " + str2)

  val str3 = "public"
  def func3 = println("visible is " + str3)
}

object O2 {
  import O._
  // static import した場合はpublicのみアクセス可能
  //func1 <= error
  //func2 <= error
  func3
}
  
O2
}


///////////////////////////////////////
// case class
def f030 {
/*
case class クラス名でクラスを生成することができる
通常のクラスとの違いは
・コンストラクタ引数にvar,valつけなくてもフィールドが生成される
・そのフィールドは読み込み可能書き込み不可である
・new をつけなくても生成できる
・==が参照比較でなくコンストラクタ引数同士の==になる
・match文に組み込める(詳細は07.scala:case calssをつかったmatch)
*/

// いちおうvarもコンストラクタに指定できるけどたぶんよくないと思う
// 通常はvarもvalも指定しないで書く
case class Person(name: String, var age: Int) {
  var num = 0
}

val p1 = Person("p", 10)
val p2 = Person("p", 10)
// 参照比較でなく同値比較である
println(p1 == p2)

// var,valを省略したコンストラクタ引数は
// 読み込みは可能だが書き込みはできない
println("p1.name = " + p1.name)
//p1.name = "Moke" <= error

// 注）何度も書くがほんとはコンストラクタ引数を
// varにするのはよくないけどテストのためにvarにしてある
p1.age = 20
// コンストラクタ引数同士が==でなくなるとfalseになる
println(p1 == p2)

val p3 = Person("p", 10)
val p4 = Person("p", 10)
// コンストラクタ引数以外の値が変更しても==の結果は変わらない
p3.num = 30; p4.num = 40
println(p3 == p4)
}


///////////////////////////////////////
// applyとupdate
def f040 {
// シンクタックシュガーという機能で
// クラスのapplyメソッドとupdateメソッドを定義した場合
// 省略して呼び出せる
object O {
  def apply(num: Int) = println("apply: " + num)
  def update(key: Int, value: String) = 
    println("update: %d = %s".format(key, value))
}

// applyメソッドの呼び出しの省略形
O(30)

// updateメソッドの呼び出しの省略形
O(40) = "moke"

// コレクション(Array,List,Mapなど）はこれを応用して
// 分かりやすい記述で操作できるようになってる
}


///////////////////////////////////////
// classOf と reflect
def f050 {
class C1
// javaでいうクラス名.classはclassOfを使用する
val c = classOf[C1]

println("\n----- classOf -----")
println("val c = classOf[C1] = " + c)

// ほかにもforNameなどで作れる
// スクリプト上だと名前空間の関係で例外がでるので
// 適当に標準クラスでやってみる
// コンパイル上だと自作のクラスでもうごく
println("----- forName -----")
println(Class.forName("java.lang.String"))

println("\n----- newInstance -----")
println("classOf[C1].newInstance = " + classOf[C1].newInstance)

class Person(val name:String, val age:Int) {
  def self = println("name:%s age:%d".format(name,age))
  def say(str:String, str2:String) =
    println(str + " " + str2)
}
val clazz = classOf[Person]
println("\n----- getConstructors -----")
val args = List("Taro", 20).map(_.asInstanceOf[AnyRef]).toArray
val ob = clazz.getConstructors()(0)
            .newInstance(args: _*).asInstanceOf[Person]
ob.self

println("\n----- getMethods -----")
clazz.getMethods foreach(println)

println("\n----- getMethod -----")
print("call self: ")
clazz.getMethod("self").invoke(ob)
print("call say: ")
val types = 
  (for { i <- 0 until 2 } yield { classOf[String] }).toArray
clazz.getMethod("say", types:_*).invoke(ob, "yahoo", "google")

println("\n----- getField -----")
// getFieldは上手くいかない
// getMethodsで見れば分かるが
// val or var両方ともメソッドとして登録されている
// なのでフィールドはgetFieldでなくgetMethodで取得する

// フィールドをとりたい場合はgetMethodで取得する
println(clazz.getMethod("name").invoke(ob))
// clazz.getField("name") <= error
}


///////////////////////////////////////
// クラスの初期化とclassOf
def f051 {
class C { 
  var name = "C"
}

val c = new C
println(c.getClass)
println((c.getClass == classOf[C]) + " " + c.isInstanceOf[C])

// クロージャ内で初期化した場合クラス名も変わってしまう
// わかりずらいが$anon$1が語尾について別のクラスになっている
val c2 = new C { name = "c2" }
println(c2.getClass)

// よってclassOfによるイコールはfalseになる
// isInstanceOfはtrueになる

println((c2.getClass == classOf[C]) + " " + c2.isInstanceOf[C])

}


///////////////////////////////////////
// this.type
def f060 {
// クラスメソッドでthis.typeをつかうと
// 自分の型を意味する
class C {
  val num = 10
  def func:this.type = this

  // classOfには指定できない
  // classOf[this.type] // <= error

  // asInstanceOfには指定できる
  def cast(any:Any):this.type = {
    any.asInstanceOf[this.type]
  }
  def say = println(toString + " dayo-n")
  override def toString = "class C"
}

class C2 extends C {
  override def toString = "class C2"
}

val c = new C
val c2 = new C2

println(c.func)
println(c2.func)

println(c.func.num)
c2.func.say

//val any:Any = new C
val any = new C:Any
c.cast(any).say

// anyはCクラスのインスタンスなので
// 当然c2.castではエラーがでる
c2.cast(any)
}


///////////////////////////////////////
// className.this
def f070 {
// クロージャの中や内部クラス内で
// 一つ上の階層のthisを取得するときに使う
// 一時変数(selfなど）を作るのがめんどくさい時は便利
class C {
  val str = "hogehoge"
  class C2 {
    println(C.this + " " + C.this.str)
  }
  override def toString = "class C dayo-n"
}
val c = new C
new c.C2
}


///////////////////////////////////////
// thisの代わりの変数
def f071 {
// className.this の変わりの変数を
// 省略して作成する記述もある

// このselfはthisの代入
class C { self =>
  val str = "hogehoge"
  class C2 {
    println(self + " " + self.str)
  }
  override def toString = "class C dayo-n"
}
val c = new C
new c.C2
}






//
//object s { 
//  private val vl = 10
//  private var vr = 20
//  def say = println("vl:" + vl + " vr:" + vr)
//}
//

//// default args
//class c(_name:String = "aiueo") {
//  var name:String = _name
//}
//

//// constructor
//class c2(_name:String = "default") {
//  println("_name :" + _name)
//  val name = _name.toUpperCase
//  println("name :" + name)
//}

// setter getter
//class c3(private var aname:String = "val") {
//  def name = { println("getter"); aname }
//  def name_= (_name:String) {
//    println("setter")
//    aname = _name
//  }
//}

//// AnyVal
//def anyVal {
//  // error because v is value
//  //def ref(v:Int) { v = v * 10; println("v:" + v) }
//  var v1 = 10
//  var v2 = v1
//  println("v1:" + v1 + " v2:" + v2)
//  //ref(vv)
//  v2 *= 10
//  println("v1:" + v1 + " v2:" + v2)
//}

//// 文字列の比較テスト
//def checkString {
//  var s1 = "aiueo"
//  var s2 = s1
//  println("s1:" + s1 + " s2:" + s2 + " s1==s2:" + (s1 == s2))
//  s2 = "hogemoke"
//  println("s1:" + s1 + " s2:" + s2 + " s1==s2:" + (s1 == s2))
//  s2 = "aiueo"
//  println("s1:" + s1 + " s2:" + s2 + " s1==s2:" + (s1 == s2))
//}

//// 補助コンストラクタ
//class hojo(val name:String) {
//  println(name)
//  def this(num: Int) = this(num.toString)
//  def this(n1: String, n2:String) {
//    this(n1 + " " + n2)
//  }
//}


/**
 * 継承とトレイト
 */

/////////////////////////////////////////
//// 親クラスのコンストラクタ
//class oya(val name:String) {
//  var str:String = _ // _ でその型のデフォルト値を割り振れる
//  println("oya name:" + name)
//}
//// {} を省略できるっぽい
//class ko1() extends oya("ko1") 
//// nameの名前も一緒で大丈夫そう
//// もちデフォ引きもおｋ
//class ko2(name:String="ko2") extends oya(name)

/////////////////////////////////////////
//// {}でのフィールドの初期化
//class hoge {
//  println("hoge constructor")
//  var v1:Int = _
//  var v2:String = _
//}
//println("-----------------------------")
//// new クラス名{ ここの部分で初期化できる }
//var h = new hoge {
//  println("h constructor")
//  v1 = 20
//  v2 = "moke"
//  println(v1 + " " + v2)
//}
    
/////////////////////////////////////////
//// オーバーライド
//// overrideを必ずつけなければいけない
//class oya {
//  def say() { print("oya ") }
//}
//class ko1 extends oya {
//  override def say() { super.say(); print(" ko1\n") }
//}
//class ko2 extends oya {
//  // def say() { } <= error
//}
//(new ko1).say

/////////////////////////////////////////
//// 抽象変数
//// サブクラスで必ず定義されなければならない変数
//abstract class oya { 
//  var name:String
//  println("-oya constructor-")
//  say
//  def say = println("oya :" + name)
//}
//// abstractを続けると次のサブクラスまで後れる
//abstract class ko1 extends oya
//class mago(var name:String) extends ko1
//
//// setter getter を使っても指定できる
//class ko2 extends oya {
//  println("-ko2 constructor-")
//
//  // これだと自分自身を関数として読んでることになる
//  //override def name:String = { name }
//
//  // 適当に実態のあるインスタンスを容易しないといけない
//  // 同名はだめっぽい
//  var name2 = "ko2"
//  override def name:String = { name2 }
//  override def name_=(_name:String) {
//    name2 = _name.toUpperCase
//  }
//}
//println("-----------------------------")
//new mago("mago")
//val ko = new ko2
//ko.say
//ko.name = "kokokoke-"
//ko.say

/////////////////////////////////////////
//// トレイト
//// コンストラクタが指定できないクラスとして作成
//// withによって複数継承させることができる
//trait t1 {
//  val v1 = "trait1"
//}
//trait t2 {
//  val v2 = "trait2"
//}
//class oya(val name:String) 
//// with name with name という感じで複数つなげる
//class c1 extends oya("c1") with t1 with t2 {
//  println(v1 + " " + v2 + " " + name)
//}
//// traitだけの継承の場合はextends
//class c2 extends t1 with t2
//println("-----------------------------")
//new c1
//// new 時にもトレイトをくっつけることができる
//var hoge = new oya("hoge") with t1
//println("hoge.v1:" + hoge.v1)
