package chapter30

import org.hamcrest.core.Is._
import org.junit._
import org.junit.Assert._

class Chapter07Test {
  
  /**
   * if式
   * 式としての利用
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
   * ジェネレータ式
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
   * Range式
   */
  @Test
  def forByRange() = {
    // to を利用した場合
    val sb1 = new StringBuilder()
    for (i <- 1 to 10) sb1.append(i.toString())
    assertThat(sb1.toString(), is("12345678910"))
    
    // until を利用した場合
    val sb2 = new StringBuilder()
    for (i<- 1 until 10) sb2.append(i.toString())
    assertThat(sb2.toString, is("123456789"))
  }
  
  /**
   * for式
   * フィルタリングを活用
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
   * ループのネスト
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
   * 変数への中間結果の束縛
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
  def exception() = {
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
  

}