package axcavo.sjp;

import java.util.Map;

public class SimpleJsonParser {
    public Map<String, Object> parse(String data) {
        Scanner scanner = new Scanner(data);
        JParser parser = new JParser(scanner.scanTokens());

        Obj astObj = parser.parse();
        return (Map<String, Object>) astObj.accept(new AstMapper());
    }
}
