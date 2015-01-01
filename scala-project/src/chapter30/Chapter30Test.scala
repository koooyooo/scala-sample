
package chapter30


import org.hamcrest.core.Is._
import org.hamcrest.core.IsCollectionContaining._
import org.junit._
import org.junit.Assert._


/**
 * 
 */
class Chapter30Test {
  
  /**
   * 【材料 1】
   * hashCodeを実装していない Point
   */
  class NonHashPoint(val x: Int, val y: Int) {
    override def equals(other: Any): Boolean = {
      other match {
        case other: NonHashPoint =>
          this.x == other.x && this.y == other.y
        case _ => false
      }
    }
  }
  
  /**
   * 【材料 2】
   * hashCodeを実装した Point
   */
  class HashPoint(override val x: Int, override val y: Int) extends NonHashPoint(x, y) {
    override def hashCode():Int = {
      (this.x *41) + (this.y * 41)
    }
  }

  
  /**
   * hashCodeを実装していない場合は、
   * a) 単純な比較は上手くゆくが
   * b) Hashを利用するクラス無いでは上手くゆかない
   */
  @Test def testNoHash() = {
    import scala.collection.mutable.HashSet
    
    // 単純な比較では trueが返る
    val p1, p2 = new NonHashPoint(1, 2)
    assertThat(p1, is(p2))
    
    // HashSetに入れると、含まれているのに falseが返る
    val set = new HashSet[NonHashPoint]()
    set.add(p1)
    assertThat(false, is(set.contains(p2)))
  }
  
  /**
   * hashCodeを実装している場合は、
   * a) 単純な比較は上手くゆき
   * b) Hashを利用するクラス無いでも上手くゆく
   */
  @Test def testHash() = {
    import scala.collection.mutable.HashSet
    
    // hashCodeを実装していれば、問題がない
    val q1, q2 = new HashPoint(1, 2)
    val set = new HashSet[HashPoint]()
    set.add(q1)
    assertThat(true, is(set.contains(q2)))
  }
  
  /**
   * 【材料 3】
   * 
   * フィールドが varのPointクラス
   */
  class VarPoint(var x: Int, var y: Int) {
    override def hashCode = (x * 41) + (y * 41)
    override def equals(other: Any): Boolean = {
      other match {
        case other: VarPoint => 
          x == other.x && y == other.y
        case _ => false
      }
    }
  }
  
  /**
   * イミュータブルなクラスをハッシュに入れた場合、
   * ハッシュに格納後の変化に追従できない。
   */
  @Test def testConstructorWithVar() = {
    import scala.collection.mutable.HashSet
    
    val p = new VarPoint(1, 2)
    val set = HashSet[VarPoint]()
    set += p
    // HashSetに格納された際の hashのままなら問題ないが
    assertTrue(set.contains(p))
    // 値が変化してしまうと containsが効かなくなる
    p.x += 1
    assertFalse(set.contains(p))
  }
  
}