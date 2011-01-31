/**
 * パターンマッチング
 */

/////////////////////////////////////////
// 値によるmatch
def f000 {
def check(num: Int) {
  // match戻り値をとることができる
  // 戻ってくる型は推論される
  val msg = num match {
    case 0 => "zero"
    case 1 => "one"
    // _はワイルドカード（すべての条件にあてはまる）
    // javaでいうdefault
    case _ => "unknown"
  }
  println(msg)
}
check(0)
check(1)
check(100)
}


/////////////////////////////////////////
// 複数の条件に当てはまるするmatch
def f001 {
def check(num: Int) {
  val msg = num match {
    // | でくくる
    case 0 | 1 => "zero or one"
    // パターンガード（if）とは組み合わせれないっぽい
    // case n if n < 30 | n if n > 50 => "30 under or 50 over"
    // ↑error
    case _ => "unknown"
  }
  println(msg)
}
check(0)
check(1)
check(100)
}


/////////////////////////////////////////
// 型によるmatch
def f002 {
  def check(input: Any) {
    input match {
      case n: Int => println("Int = " + n)
      case s: String => println("String = " + s)
      case _ => println("unknown = " + input)
    }
  }
  class c { override def toString = "class c" }
  check(10)
  check("test")
  check(new c)
}


/////////////////////////////////////////
// ifによるmatch(パターンガードというらしい）
def f010 {
def fizzbuzz( n:Int, cond:Int => Boolean ) = { 
  1 to n        // (A)リストを作る
} filter{ 
  cond          // (B)条件を引数の関数オブジェクトで絞る
} map {         // (C)要素を加工する
  case n if n % 15 == 0 => "FizzBuzz"
  case n if n % 3  == 0 => "Fizz"
  case n if n % 5  == 0 => "Buzz"
  case n                => n.toString 
} foreach { e => 
  println( e )  // (D) 操作を行う
}
fizzbuzz( 30 , (n:Int) => n % 2 == 1 )
}


/////////////////////////////////////////
// シーケンス(Array,Listなど)によるmatch
def f010 {
  def check(input: Any) {
    val msg = input match {
      case Array(a) => "Array1: a = " + a
      case Array(a, b) => "Array2: a = " + a + ", b = " + b
      // ワイルドーカードも指定できる
      // どうでもいいところとかをワイルドカードにするんだと思われ
      case Array(_, b, _) => "Array3: b = " + b
      case List(a) => "List1: a = " + a
      case List(a, b) => "List2: a = " + a + ", b = " + b
      case List(_, b, _) => "List3: b = " + b
      case _ => "unknown: " + input
    }
    println(msg)
  }
  check(Array(5))
  check(Array("a1", "a2"))
  check(Array("a1", "a2", "a3"))
  check(List(10))
  check(List("l1", "l2"))
  check(List("l1", "l2", "l3"))
  check(20) // unknown
}


/////////////////////////////////////////
// Tupleによるmatch
def f020 {
// だいたいArray,Listと一緒
def check(input: Any) {
  val msg = input match {
    case (a, b, c) => "three: %s, %s, %s".format(a, b, c)
    // シーケンスと同様にワイルドカードも使える
    case (_, b) => "two: b=%s".format(b)
    case (a) => "one: %s".format(a)
  }
  println(msg)
}
check((1))
check((1, 2))
check((1, 2, 3))
// 指定されてる以外の長さのTupleは長さ1のTupleとマッチする
check((1, 2, 3, 4))
}


/////////////////////////////////////////
// 正義表現によるmatch
def f030 {
// 正規表現文の()で囲んだ場所がcaseのパラメーターとなる
val http = """http://([\w.]+)(:([\d]+))?(/.*)?""".r
val mail = """([\w]+)@([\w.]+)""".r
def check(input: Any) {
  val msg = input match {
    case http(host, _, port, path) =>
      "http: host(%s), port(%s), path(%s)".format(host,port,path)
    case mail(user, host) =>
      "mail: user(%s), post(%s)".format(user, host)
  }
  println(msg)
}
check("http://abc.com")
check("http://abc.com/xyz")
check("http://abc.com:8080/xyz")
check("taro@abc.com")
}


///////////////////////////////////////
// case classをつかったmatch
def f040 {
case class Person(name: String, age: Int)
def check(p:Person) { 
  val msg = p match {
    case Person("taro", age) if age < 20 => "young taro"
    case Person("taro", _) => "old taro" 
    case _ => "other"
  }
  // case classはtoStringも自動的に拡張してくれる
  println(p + " = " + msg)
}
check(Person("taro", 15))
check(Person("hanako", 30))
check(Person("taro", 30))

}


/////////////////////////////////////////
// マッチする変数を指定する `name`記法
def f050 {
val (s1, s2, s3) = ("a", "i", "u")
val l = List(s1, s2, s3)

l match {
  // ``記法を買わない場合は以下のようになって冗長になる
  //case List(a, b, "u") if b == s2 => println(a + b + "u")

  // 変数を` `で囲んで指定すると省略できて便利
  case List(a, `s2`, "u") => println(a + s2 + "u")

  // ``で指定する変数は val でなければならない
  // var や lazy val だとエラーになる
}}
