import deque.ArrayDeque61B;

import deque.Deque61B;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }

     @Test
    public void addFirstLastTest() {
        ArrayDeque61B<Integer> test = new ArrayDeque61B<>();
        test.addFirst(1);
        test.addLast(2);
        test.addFirst(3);
        test.addFirst(4);
     }

     @Test
    public void toListTest() {
         ArrayDeque61B<Integer> test = new ArrayDeque61B<>();
         assertThat(test.toList()).isEmpty();
         test.addFirst(1);
         test.addLast(2);
         test.addFirst(3);
         test.addFirst(4);
         test.addLast(5);
         assertThat(test.toList()).containsExactly(4, 3, 1, 2, 5).inOrder();
     }

     @Test
    public void isEmptyTest() {
         ArrayDeque61B<Integer> test = new ArrayDeque61B<>();
         assertThat(test.isEmpty()).isTrue();
         test.addFirst(1);
         assertThat(test.isEmpty()).isFalse();
     }

     @Test
    public void SizeTest() {
         ArrayDeque61B<Integer> test = new ArrayDeque61B<>();
         assertThat(test.size()).isEqualTo(0);
         test.addFirst(1);
         assertThat(test.size()).isEqualTo(1);
     }

     @Test
     public void removeLastFirstTest() {
         ArrayDeque61B<Integer> test = new ArrayDeque61B<>();
         assertThat(test.removeLast()).isEqualTo(null);
         test.addFirst(1);
         test.addLast(2);
         test.addFirst(3);
         test.addFirst(4);
         test.addLast(5);
         test.addFirst(1);
         test.addFirst(1);
         test.addFirst(1);
         test.addFirst(1);
         assertThat(test.removeFirst()).isEqualTo(1);
         assertThat(test.removeLast()).isEqualTo(5);
         test.removeLast();
         test.removeLast();
         test.removeLast();
         test.removeLast();
         test.removeLast();
         assertThat(test.toList()).containsExactly(1, 1, 1, 4, 3, 1, 2).inOrder();
     }

     @Test
    public void getTest(){
         ArrayDeque61B<Integer> test = new ArrayDeque61B<>();
         assertThat(test.get(2)).isEqualTo(null);
         test.addFirst(1);
         test.addLast(2);
         test.addFirst(3);
         test.addFirst(4);
         test.addLast(5);
         assertThat(test.get(2)).isEqualTo(1);
         assertThat(test.get(-1)).isEqualTo(5);
         assertThat(test.get(11)).isEqualTo(null);
         assertThat(test.get(-11)).isEqualTo(null);
     }

     @Test
    public void IteratorTest() {
         ArrayDeque61B<Integer> test = new ArrayDeque61B<>();
         test.addFirst(1);
         test.addLast(2);
         test.addFirst(3);
         test.addFirst(4);
         test.addLast(5);
         assertThat(test).containsExactly(4, 3, 1, 2, 5);
     }

     @Test
    public void EqualsTest() {
         ArrayDeque61B<Integer> test1 = new ArrayDeque61B<>();
         ArrayDeque61B<Integer> test2 = new ArrayDeque61B<>();
         test1.addFirst(1);
         test2.addLast(1);
         assertThat(test1.equals(test2)).isTrue();
     }

     @Test
    public void toStringTest() {
         Deque61B<Integer> test = new ArrayDeque61B<>();
         test.addFirst(1);
         test.addLast(1);
         System.out.println(test);
     }
}
