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
// 各行を読み込んだ後、処理を行うラッパー関数
def f010 {
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
def f020 {
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

