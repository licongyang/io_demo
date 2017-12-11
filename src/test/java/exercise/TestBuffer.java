package exercise;

import java.nio.IntBuffer;

import org.junit.Test;

public class TestBuffer {
	
	@Test
//	rewind() 主要针对于读模式. 在读模式时, 读取到 limit 后, 可以调用 rewind() 方法, 将读 position 置为0.
	public void testRebind(){
		 IntBuffer intBuffer = IntBuffer.allocate(2);
	        intBuffer.put(1);
	        intBuffer.put(2);
	        System.err.println("position: " + intBuffer.position());

	        intBuffer.rewind();
	        System.err.println("position: " + intBuffer.position());
	        System.err.println("limit: " + intBuffer.limit());
	        intBuffer.put(1);
//	        intBuffer.put(2);
	        System.err.println("position: " + intBuffer.position());

	        
	        intBuffer.flip();
	        System.err.println("position: " + intBuffer.position());
	        System.err.println("limit: " + intBuffer.limit());
	        intBuffer.get();
//	        intBuffer.get();
	        System.err.println("position: " + intBuffer.position());

	        intBuffer.rewind();
	        System.err.println("position: " + intBuffer.position());
	        System.out.println( intBuffer.get());

	}
	@Test
//	通过调用 Buffer.mark()将当前的 position 的值保存起来, 随后可以通过调用 Buffer.reset()方法将 position 的值回复回来
	public void testMakeReset(){
		 IntBuffer intBuffer = IntBuffer.allocate(2);
	        intBuffer.put(1);
	        intBuffer.put(2);
	        intBuffer.flip();
	        System.err.println(intBuffer.get());
	        System.err.println("position: " + intBuffer.position());
	        intBuffer.mark();
	        System.err.println(intBuffer.get());

	        System.err.println("position: " + intBuffer.position());
	        intBuffer.reset();
	        System.err.println("position: " + intBuffer.position());
	        System.err.println(intBuffer.get());
	    }
	
	@Test
//	 clear 将 positin 设置为0, 将 limit 设置为 capacity
//	如果Buffer中有一些未读的数据，调用clear()方法，数据将“被遗忘”，意味着不再有任何标记会告诉你哪些数据被读过，哪些还没有
	public void testClear(){
		IntBuffer intBuffer = IntBuffer.allocate(2);
		intBuffer.flip();
		System.err.println("position: " + intBuffer.position());
		System.err.println("limit: " + intBuffer.limit());
		System.err.println("capacity: " + intBuffer.capacity());

		// 这里不能读, 因为 limit == position == 0, 没有数据.
		//System.err.println(intBuffer.get());

		intBuffer.clear();
		System.err.println("position: " + intBuffer.position());
		System.err.println("limit: " + intBuffer.limit());
		System.err.println("capacity: " + intBuffer.capacity());

		// 这里可以读取数据了, 因为 clear 后, limit == capacity == 2, position == 0,
		// 即使我们没有写入任何的数据到 buffer 中.
		System.err.println(intBuffer.get()); // 读取到0
		System.err.println(intBuffer.get()); // 读取到0
	}
	@Test
//	将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。limit属性依然像clear()方法一样，设置成capacity
	public void testCompact(){
		IntBuffer intBuffer = IntBuffer.allocate(4);
		intBuffer.put(1);
		intBuffer.put(2);
		intBuffer.put(3);
		System.err.println("position: " + intBuffer.position());
		System.err.println("limit: " + intBuffer.limit());
		intBuffer.flip();
		intBuffer.get();
		System.err.println("position: " + intBuffer.position());
		System.err.println("limit: " + intBuffer.limit());
		intBuffer.compact();//还有两个未读，放到buffer前面
		System.err.println("position: " + intBuffer.position());
		System.err.println("limit: " + intBuffer.limit());
		intBuffer.put(3);
		System.err.println("position: " + intBuffer.position());
		System.err.println("limit: " + intBuffer.limit());
		
	}
	


}
