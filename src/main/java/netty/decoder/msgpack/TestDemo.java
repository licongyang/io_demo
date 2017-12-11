package netty.decoder.msgpack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

public class TestDemo {

	public static void main(String[] args) throws IOException {
		//Create serialize objects
		List<String> lists  = new ArrayList<>();
		lists.add("msgpack");
		lists.add("viver");
		lists.add("duran");
		MessagePack msgpack = new MessagePack();
		//Serialize
		byte[] bytes = msgpack.write(lists);
		//deserialize directly using a template
		List<String> dst = msgpack.read(bytes,Templates.tList(Templates.TString));
		System.out.println(dst.get(0));
		System.out.println(dst.get(2));
	}	

}
