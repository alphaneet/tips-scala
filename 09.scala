/**
 * 型パラメーター
 */


/////////////////////////////////////////
// classに型パラメーター
def f000 {
class Test[T](_val:T) {
  // 初期値がない場合は _ でよい（どうやらnullになるらしい）

  var var1: T = _
  val val1 = _val
  def say = println("var1 = " + var1 + " : val1 = " + val1)
}

println("----- create Test[Int] -----")
val num = new Test[Int](10)
num.say
num.var1 = 30
num.say

println("----- create Test[String] -----")
// 型推論で省略もできる
val str = new Test("hoge")
str.var1 = "test"
str.say
}


/////////////////////////////////////////
// 関数に型パラメーター
def f010 {
def func[T](value:T) {
  val clazz = value.asInstanceOf[AnyRef].getClass
  println(value + " : " + clazz.getName)
}

func[Int](30)

// 型推論で省略もできる
func("str")

class Hoge { override def toString = "class Hoge" } 
func(new Hoge)
}

/////////////////////////////////////////
// 型の境界
def f020 {
}


/////////////////////////////////////////
// Type型

def f030 {
// 関数も型にできる
type Action = () => Unit
}


///////////////////////////////////////////
// 抽象Type型

def f040 {
abstract class Food
abstract class Animal {
  // 境界も指定できる
  type T <: Food
}
class Grass extends Food
class Cow extends Animal {
  type T = Grass
}

val cow = new Cow

val food = new cow.T:Food
// なんとnewもできる
// Grassクラスが生成される
println(food.getClass)

food.asInstanceOf[cow.T]
}


///////////////////////////////////////////
// 抽象Type型のつづき(#の使い方とか）

def f041 {
class Base {
  var name = "name"
  // 継承先で再定義しない場合
  // 境界の上限がデフォルトの型になるっぽい

  type This <: Base
  def cast(any:Any) = any.asInstanceOf[This]
  override def toString = name
  def say = println("say:" + name)
}
class C1 extends Base {
  // Thisを再定義しないクラスを定義
}
class C2 extends Base {
  type This = C2

  // C2固有のメソッドを定義
  def sayC2 = println("sayC2:" + name)
}
val c1 = new C1
c1.name = "class c1 dayo-n"
val any1 = c1:Any

val cast1 = c1.cast(any1)
println(cast1)

// 再定義してない場合はc1.Thisの形は使えない
// classOf[c1.This] <= error

// でもBaseの関数は呼べる(Base#Thisにはキャストできている）
c1.cast(any1).say

val c2 = new C2 { name = "c2 c2 c2" }

// c2はクロージャ内で初期化してしまっているので(別クラスになっているので）
// classOfとgetClassで比較した場合はfalseになる
println(classOf[c2.This] == c2.getClass)

// isInstanceOfなら問題ない
println(c2.isInstanceOf[c2.This])

// class C2でThis = C2にしているので問題なくtrueになる
println(classOf[c2.This] == classOf[C2])

val any2 = c2:Any

// この場合は型はc2.Thisになる（中身はC2)
val cast2 = c2.cast(any2)

// C2固有のメソッドも呼べる
cast2.say

// C2という名前でないと問題が出る場合は
// クラス名#型名を指定すればいい
val cast3:C2#This = c2.cast(any2)

cast3.say

}


///////////////////////////////////////
// 含まれてるメソッドやフィールドを
// 指定する型パラメーター
def f050 {
// ;で区切ると複数指定できる
def get[T <: {def say(s:String):Unit; val str:String}](t: T) {
  t.say(t.str.toUpperCase)
}
class C(val str:String) {
  def say(s:String) { println(s) }
}
get(new C("aiueo"))
}


///////////////////////////////////////
// mixin先を指定できるtrait
def f060 {
class C {
  var name = "class C"
  def say = println(name)
}

trait T {
// this:type の形でmixinする場合、
// 継承先が含まれてないといけないクラスを指定できる
// ↓はTをmixinする場合は
// かならずCを継承していないといけないという意味
this:C => 

// class C 固有のメソッドを呼ぶことが可能
def func = say
}

class C2 extends C with T 
(new C2 { name = "C2 C2" }).func

// 指定した型が含まれてない場合はコンパイルエラーが出る
// class Other extends T <= error
}


///////////////////////////////////////
// null.asInstanceOf[T]
def f070 {
class MyArray[T] {
  val buffer = new collection.mutable.ArrayBuffer[T]
  def apply(key:T):T = {
    val idx = buffer.findIndexOf(_==key) 
    // 型パラメーターを返すメソッドで
    // nullを返したい場合は
    // null.asInstanceOf[T]にすればよいらしい
    if(idx == -1) null.asInstanceOf[T]
    else buffer(idx)
  }
  def +=(value:T):T = { buffer += value; value }
  def -=(key:T) = { buffer -= key }
}
class C(name:String) {
  override def toString = "class " + name
}
val arr = new MyArray[C]
val c = arr += new C("aiueo")
val other = new C("other")
println(arr(c))
println(arr(other))
}
