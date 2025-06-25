package axcavo.sjp;

import java.io.IOException;
import java.util.Map;

public class SJP {

	public static void main(String[] args) throws IOException {
		String json = 
		"""
		{
			"name": "Alice",
			"age": 30,
			"isStudent": false,
			"scores": [95, "xd", 75],
			"address": {
				"street": "123 Maple Street",
				"city": "Wonderland"
			},
			"projects": [
				{"title": "AI Research", "completed": true},
				{"title": "Robotics", "completed": false}
			]
		}
		""";

				
		SimpleJsonParser sjp = new SimpleJsonParser();
		System.out.println(sjp.parse(json));
	}
	
}
