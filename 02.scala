/**
 * 文字列
 */
///////////////////////////////////////
// 生文字リテラル
def f000 {
// """value"""のように"""でくくると生文字リテラルが生成できる
// 中身は\などもそのまま使えるので正規表現などに向く

// 複数行にわたって記述できる
val str1 = """\n%d%d\n\n
mokeke
"""
println("===生文字リテラルテスト===")
println(str1 + "\n")

// stripMarginをつかえば | の左側の部分を削除できる
// インデックスを合わせる生文字リテラルの作成に便利そう
val str2 = """mokeke
             |hogegege
             |testeststte""" // .stripMargin
println("===no stripMargin===")
println(str2 + "\n")
println("===stripMargin===")
println(str2.stripMargin + "\n")
}


///////////////////////////////////////
// 正規表現オブジェクトの生成

///////////////////////////////////////
// formatを用いた文字列の整形

