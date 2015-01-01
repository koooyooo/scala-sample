package chapter07

import org.hamcrest.core.Is._
import org.junit._
import org.junit.Assert._

class Chapter07Test {
  
  /**
   * if式
   * 式としての利用 (式の右辺や、引数の内側で利用)
   */
  @Test
  def testIf() = {
    // if式は値を返す。このため、3項演算子的な使い方が可能
    val value = if (1 + 1 == 2) "right" else "wrong"
    assertThat(value, is("right"))
    
    // 引数の中でも記述可能
    val str = new String(if (1 + 1 == 2) "right" else "wring")
    assertThat(str, is("right"))
  }
  
  /**
   * for式
   * ジェネレータ式 (Java の foreach文と等価)
   */
  @Test
  def forByGenerator() = {
    val sb = new StringBuilder()
    for (element <- List("Hello", " ", "World")) {
      sb.append(element)
    }
    assertThat(sb.toString, is("Hello World"))
  }
  
  /**
   * for式
   * Range式 (数値を数え上げる時に利用)
   */
  @Test
  def forByRange() = {
    // to を利用した場合
    val sb1 = new StringBuilder()
    for (i <- 1 to 10) sb1.append(i.toString)
    assertThat(sb1.toString(), is("12345678910"))
    
    // until を利用した場合
    val sb2 = new StringBuilder()
    for (i<- 1 until 10) sb2.append(i.toString)
    assertThat(sb2.toString, is("123456789"))
    
    // デクリメント (toを利用)
    val sb3 = new StringBuilder()
    for (i <- 10 to 1 by -1) sb3.append(i.toString)
    assertThat(sb3.toString, is("10987654321"))
  }
  
  /**
   * for式
   * フィルタリングを活用 (要素の数え上げに条件を追加)
   */
  @Test
  def forWithFiltering() = {
    // ifを利用
    val sb1 = new StringBuilder()
    for (i <- 1 to 10 if (i % 2 == 0)) sb1.append(i.toString())
    assertThat(sb1.toString, is("246810"))
    
    // ifを複数利用
    val sb2 = new StringBuilder()
    for (i<- 1 to 10 if (i % 2 == 0) if (5 < i)) sb2.append(i.toString())
    assertThat(sb2.toString, is("6810"))
  }
  
  /**
   * for式
   * ループのネスト (2重のループを 1つのループで表現)
   */
  @Test
  def forWithNest() = {
    val sb = new StringBuilder()
    for (i <- 1 to 3; j <- 'a' to 'c') {
      sb.append(i).append(j)
    }
    assertThat(sb.toString, is("1a1b1c2a2b2c3a3b3c"))
  }
  
  /**
   * for式
   * 変数への中間結果の束縛 (式の内部で値を変数に束縛可能)
   */
  @Test
  def forWithAssignment() = {
    val sb = new StringBuilder()
    for (i <- 1 to 3; decorated = "(" + i.toString + ")") {
      sb.append(decorated)
    }
    assertThat(sb.toString, is("(1)(2)(3)"))
  }
  
  /**
   * for式
   * yieldによる Seqへの割り当て (実体は scala.collection.immutable.Vector型)
   */
  @Test
  def forWithYield() = {
    val evenNumbers = for (i <- 1 to 10 if (i % 2 == 0)) yield i
    assertThat(evenNumbers, is(Seq(2, 4, 6, 8, 10)))
  }
  
  /**
   * try式
   * catch節による補足と、finally節の通過
   */
  @Test
  def tryNormal() = {
    val sb = new StringBuilder()
    try {
      val result = if (1 + 1 == 1) "wrong" else throw new IllegalStateException()
      sb.append("never reach")
    } catch {
      case ex: NullPointerException => sb.append("NullPointerException")
      case ex: IllegalStateException => sb.append("IllegalStateException ")
      case ex: Exception => sb.append("Exception ")
    } finally {
      sb.append("finally")
    }
    assertThat(sb.toString, is("IllegalStateException finally"))
  }
  
  /**
   * try式
   * 評価結果の代入 (tryも式として利用できる)
   */
  @Test
  def tryAsExpression() = {
    val value = try { "Hello" } catch {case ex: Exception => "World"}
    assertThat(value, is("Hello"))
  }
  
  /**
   * try式
   * 評価結果の代入(finally)
   * ⇒ returnの有無で結果が変わる
   * ⇒ 何れにせよ、finallyで結果を返すコードは非推奨
   */
  @Test
  def tryAsExpressionFinally() = {
    assertThat(withValue, is(1))
    assertThat(withReturn, is(2))
  }
  
  def withValue(): Int = {
    try { 1 } finally { 2 }
  }
  
  def withReturn():Int = {
    try { return 1 } finally { return 2 }
  }
  
  /**
   * match式
   * 副作用のある Java的な利用法
   */
  @Test
  def matchWithImperativeCoding() = {
    val str = "Hello"
    var result: String = null
    str match {
      case "First" => result = "First"
      case "Hello" => result = "Hello"
      case "World" => result = "World"
    }
    assertThat(result, is("Hello"))
  }
  
  /**
   * match式
   * 副作用の無い Scala的な利用法
   */
  @Test
  def matchWithoutImperativeCoding() = {
    val str = "Hello"
    var result = str match {
      case "First" => "First"
      case "Hello" => "Hello"
      case "World" => "World"
    }
    assertThat(result, is("Hello"))
  }
  
  /**
   * while文
   * continue文 break文を使わない記述法 (foundItフラグを活用)
   */
  @Test
  def whileWithoutBreakAndContinue() = {
    var list = List("hello", "-world", "goodbye!", "-dream!")
    var i = 0
    var foundIt = false
    while (i < list.length && !foundIt) {
      if (!list(i).startsWith("-")) {
        if (list(i).endsWith("!")) { 
          foundIt = true
        }
      }
      if (!foundIt) i = i + 1
    }
    assertThat(i, is(2))
  }
  
  /**
   * 再起式
   * continue文 break文を使わない記述法
   */
  @Test
  def recursiveWithoutBreakAndContinue() = {
    var list = List("hello", "-world", "goodbye!", "-dream!")
    def searchFrom(i: Int): Int = {
      if (i > list.length) -1
      else if (list(i).startsWith("-")) searchFrom(i + 1)
      else if (!list(i).endsWith("!")) searchFrom(i + 1)
      else i
    }
    assertThat(searchFrom(0), is(2))
  }

}