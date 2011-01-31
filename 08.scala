/**
 * XML
 */

///////////////////////////////////////
// XMLリテラルの生成
def f000 {
// そのままxmlを書き込むとxmlリテラルが生成される
val xml = <xml><name>hogege</name></xml>
println(xml)

// 変数の埋め込みは{}を使う

val name = "taro"

// 改行も反映される

val xml2 = 
<xml2>
<name2>{name}</name2>

<!-- コメントも記入可能(WindowsのDos画面だと文字化けする) -->
{xml}

</xml2>
println(xml2)

// 特殊文字は自動的にエスケープされるが
// エスケープしてほしくない場合（スクリプトを埋め込むときなど）
// PCData()を使う

val info = """ 
var x = "";
if(true && false) alert('Woof');
"""

// 通常の場合<,%などはエクケープされる
println("\n----- not use PCData() -----")
println(<script>{info}</script>)

// PCDataを使うとそのまま出力される
println("\n----- use PCData() -----")
println(<script>{scala.xml.PCData(info)}</script>)
}


///////////////////////////////////////
// 入出力

def f010 {
val xml = 
<persons>
  <person>
    <name>Taro</name>
    <age>30</age>
  </person>

  <person>
    <name>Hanako</name>
    <age>10</age>
  </person>
</persons>

// scala.xml.XML.saveで保存
scala.xml.XML.save("output.xml", xml, "utf-8")

// scala.xml.XML.loadFileで読み込み
val xml2 = scala.xml.XML.loadFile("output.xml")

// toStringで文字列に変換
println(xml2.toString)
}


///////////////////////////////////////
// 要素と属性の検索 \ \\ メソッド
def f020 {
val persons =  
List(
  ("taro", 12, "japan"),
  ("john", 30, "usa")
) map { e =>
<person country={e._3}>
<name>{e._1}</name>
<age>{e._2}</age>
</person>
}

val doc = 
<persons>
{persons}
</persons>

println("----- create xml -----")
println(doc)

print("\n----- ")
print("""doc \ "person" \ "name" """)
println("-----")
// /メソッドはどんどんつなげていける

println(doc \ "person" \ "name")

print("\n----- ")
print("""(doc \ "person" \ "name").map(_.text) """)
println("-----")
// .textで中身を取得

println((doc \ "person" \ "name").map(_.text))

print("\n----- ")
print("""doc \\ "age" """)
println("-----")
// \\メソッドで全ての要素を検索
println(doc \\ "age")

// 要素のシーケンスに対しては@シーケンス名でアクセス可能
print("\n----- ")
print("""(doc \ "person").map(_ \ "@country") """)
println("-----")
println((doc \ "person").map(_ \ "@country"))
}
