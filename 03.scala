/**
 * 制御構文
 */

///////////////////////////////////////
// to と until 
def f000 {
// n to m はn~mまで
// n until m はn~m-1まで
for(i <- 0 to 10) print(i + " ")
print("\n")
for(i <- 0 until 10) print(i + " ")
print("\n")

// toにbyを指定すると増える数を指定できる
// 0~30まで3ずつ増加するfor文
for(i <- 0 to 30 by 3) print(i + " ")
print("\n")
}


///////////////////////////////////////
// for { }
def f010 {
// 正直いまいち分からないが{ }でもforができるらしい
// ネストもできる
for { i <- 1 to 10
      j <- 1 to 10}
// ; で区切れば一行に書くことも可能
//for { i <- 1 to 10; j <- 1 to 10}
  {
    println("i = " + i + ", j = " + j)
  }
}


/////////////////////////////////////////
// for文におけるTuple
def f020 {
val l = List((1, 20), (2, 20), (3, 30))
println("----- List -----")
for((x, y) <- l) println(x+":"+y)

println("----- Map -----")
val m = Map("key1" -> "val1", "key2" -> "val2", "key3" -> "val3")
for((x, y) <- m) println(x+":"+y)
}


/////////////////////////////////////////
// for文における正規表現
def f040 {
val mail = """([\w]+)@([\w.]+)""".r
val contacts = List("taro@abc.com", "hanako@abc.co.jp", 
   "jiro@abc.co.jp", "http://abc.com/contact")
println("----- リスト一覧 -----")
for(c <- contacts) println(c)

println("----- メールアドレスだけを抽出 -----")
for(mail(user, host) <- contacts) println(user + ":" + host)

println("----- co.jpのみを抽出 -----")
// 抽出した要素をListに格納
val l = 
  for(mail(user, host) <- contacts; if host.endsWith(".co.jp"))
    yield user + ":" + host
for(s <- l) println(s)
}


///////////////////////////////////////
// for内でのパターンガード
def f050 {
  // 偶数以外出力しない
  for(i <- 1 to 10 if i%2 == 0) println(i)
}
