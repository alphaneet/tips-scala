/**
 * tips
 * 項目を設けるほどではないもの
 * 量が増えたら独自に項目を作るかも
 */

///////////////////////////////////////
// 単項演算子を定義
def f000 {
// 関数定義時に「unary_」を付加することで単項演算子となる
class Point(x: Int, y: Int) {
  // x と y を反転したオブジェクトを返す
  def unary_~ = new Point(y, x) 
  override def toString = "x = " + x + ", y = " + y
}
val point = new Point(10, 20)
println(" point:" + point)
println("~point:" + ~point)
}


///////////////////////////////////////
// implicitを利用した暗黙の引数
def f010 {
// implicit修飾子をつけて暗黙の引数に使いますよと宣言する
implicit val str = "hoge"

// implicitを()の最初に付けて宣言する
def say(s1:String)(implicit s2:String) {
  println(s1 + " " + s2)
}

// 暗黙に str="hoge" が s2 に適応される
say("moke")

// 手動で指定する引数を変える事もできる
say("fire")("hugaga")

// implicitを使う場合は以下のような条件がある。
// エラー例も一緒に上げていく

// ①引数の () を分けなければならない
// def say(s1:String, implicit s2:String) {} // <= error

// ②implicit を使う () は最後でなければならない
// def say(implicit s1:String)(s2:String) {} // <= error

// ③implicit は () の最初に宣言し、その () 内の引数はすべて implicit になる
// def say(implicit s1:String, s2:String){}
// ↑の場合 s1, s2 両方とも implicit の引数扱いである
}


///////////////////////////////////////
// 各行を読み込んだ後、処理を行うラッパー関数
def f100 {
def getLinesFromFile(filename: String, f:(String) => Unit) = {
  val source = scala.io.Source.fromFile(filename)
  try {
    source.getLines.foreach(f)
  } finally {
    source.close
  }
}
// *でくくる文字列ほんとに意味はない
def myPrintln(str: String) {
  println("*%s*".format(str))
}
getLinesFromFile("test.txt", myPrintln)
}



///////////////////////////////////////
// qsort
def f300 {
def qsort(n: List[Int]): List[Int] = {
  n match {
    case Nil => Nil
    case List(_) => n
    case List(x, y) => if(x <= y) List(x,y) else List(y, x)
    case x :: r => 
qsort(r.filter(_ <= x)) ::: List(x) ::: qsort(r.filter(_ > x))
  }
}

println(qsort(List()))
println(qsort(List(100)))
println(qsort(List(5, 2)))
println(qsort(List(3, 5, 4, 6, 1, 334, 50, 23)))
}

