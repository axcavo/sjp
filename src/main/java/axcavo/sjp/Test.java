package axcavo.sjp;

public class Test {
    public static void main(String[] args) {
        SimpleJsonParser sjp = new SimpleJsonParser();

        // 1. Basic complex object (already in your code)
        String json1 = """
        {
            "name": null,
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
        System.out.println("Test 1:\n" + sjp.parse(json1) + "\n");

        // 2. Empty object
        String json2 = "{}";
        System.out.println("Test 2:\n" + sjp.parse(json2) + "\n");

        // 3. Object with all primitive types
        String json3 = """
        {
            "string": "value",
            "number": 42,
            "booleanTrue": true,
            "booleanFalse": false,
            "nullValue": null
        }
        """;
        System.out.println("Test 3:\n" + sjp.parse(json3) + "\n");

        // 4. Nested empty array and object
        String json4 = """
        {
            "emptyArray": [],
            "emptyObject": {}
        }
        """;
        System.out.println("Test 4:\n" + sjp.parse(json4) + "\n");

        // 5. Deeply nested objects
        String json5 = """
        {
            "a": {
                "b": {
                    "c": {
                        "d": "deep"
                    }
                }
            }
        }
        """;
        System.out.println("Test 5:\n" + sjp.parse(json5) + "\n");

        // 6. Array of primitives
        String json6 = """
        {
            "values": [1, 2, 3, 4, 5]
        }
        """;
        System.out.println("Test 6:\n" + sjp.parse(json6) + "\n");

        // 7. Array of objects
        String json7 = """
        {
            "users": [
                {"id": 1, "name": "Bob"},
                {"id": 2, "name": "Carol"}
            ]
        }
        """;
        System.out.println("Test 7:\n" + sjp.parse(json7) + "\n");

        // 8. Mixed types in array
        String json8 = """
        {
            "mixed": [42, "text", true, null, {"nested": "object"}]
        }
        """;
        System.out.println("Test 8:\n" + sjp.parse(json8) + "\n");

		/*/
        // 9. Key with escaped characters (if supported)
        String json9 = """
        {
            "quote": "She said, \\"Hello!\\"",
            "newline": "Line1\\nLine2"
        }
        """;
        System.out.println("Test 9:\n" + sjp.parse(json9) + "\n");
		*/

        // 10. Boolean and null root object
        String json10 = """
        {
            "bool": false,
            "nullThing": null
        }
        """;
        System.out.println("Test 10:\n" + sjp.parse(json10) + "\n");
    }
}
