package chapter08

import org.junit._
import org.junit.Assert._
import org.hamcrest.core.Is._

class Chapter08Test {
  
  /**
   * メソッドのネスト
   * 関数の中に関数を定義する事が出来る。
   * privateメソッドのスコープがクラス内であるのに対し、こちらはメソッド内。
   */
  @Test
  def testNestedMethod() = {
    // メソッド内部にメソッド定義が可能
    def plus(x: Int, y: Int): Int = {
      x + y
    }
    assertThat(plus(1, 2), is(3))
    
    // 内部メソッドは外部メソッドの変数にアクセスが可能
    val PI = 3.14
    def circumference(diameter: Double): Double = {
      diameter * PI
    }
    assertThat(circumference(2), is(6.28))
  }
  
  /**
   * 値としての関数
   */
  @Test
  def testFunctionValue() = {
    // 値としての関数 (右辺が関数リテラル)
    var increment = (x: Int) => x + 1
    assertThat(increment(3), is(4))
    
    // 値としての関数は再代入もできる
    increment = (x: Int) => x + 2
    assertThat(increment(3), is(5))
    
    // 複数の分を詰め込みたいときは {}で括る
    increment = (x: Int) => {
      var temp = x
      temp += 1
      temp += 2
      temp += 3
      temp
    }
    assertThat(increment(3), is(9))
  }
  
  /**
   * 関数リテラルを利用した関数の利用
   */
  @Test
  def testFunctionValueSample() = {
    // Collectionの filter
    val evenNumList = List(1, 2, 3, 4, 5).filter(x => x % 2 == 0)
    assertThat(evenNumList, is(List(2, 4)))
    
    // Collectionの foreach 
    // (foreachは右被演算、要素演算共に 手続き[Unitを返す]の為、副作用が必要)
    var sum = 0
    List(1, 2, 3, 4, 5).foreach { x => sum += x }
    assertThat(sum, is(15))
  }
  
  /**
   * 関数リテラルの省略
   */
  @Test
  def testAbbrevitationForFunctionLiteral() = {
    // 基本形 ( => の左辺に注目)
    var evenNumList = List(1, 2, 3, 4, 5).filter((x: Int) => x % 2 == 0)
    assertThat(evenNumList, is(List(2, 4)))
    
    // 型の推論 ( => の左辺に注目)
    evenNumList = List(1, 2, 3, 4, 5).filter((x) => x % 2 == 0)
    assertThat(evenNumList, is(List(2, 4)))
    
    // 括弧()の省略 ( => の左辺に注目)
    evenNumList = List(1, 2, 3, 4, 5).filter(x => x % 2 == 0)
    assertThat(evenNumList, is(List(2, 4))) 
  }
  
  /**
   * 関数リテラルの省略 (プレースホルダー"_"の利用)
   */
  @Test
  def testPlaceholder() = {
    // プレースホルダー(_)の利用  (一度しか変数を参照しない場合のみ利用可)
    var evenNumList = List(1, 2, 3, 4, 5).filter(_ % 2 == 0)
    assertThat(evenNumList, is(List(2, 4))) 
    
    // プレースホルダー(_)の利用  (型の明示化)
    evenNumList = List(1, 2, 3, 4, 5).filter((_: Int) % 2 == 0)
    assertThat(evenNumList, is(List(2, 4)))
    
    // 複数のプレースホルダー(_)の利用 (引数は順に適用される)
    val plus3 = (_:Int) + (_:Int) + (_:Int)
    assertThat(plus3(1, 2, 3), is(6))
    
    // プレースホルダー(_)の利用  (引数括弧の省略)(_左のスペースは必要)
    val sb = new StringBuilder()
    List(1, 2, 3, 4, 5).foreach(sb.append _)
    assertThat(sb.toString, is("12345"))
  }
  
  
  
  
}