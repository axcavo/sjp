package axcavo.sjp;

import java.util.List;
import java.util.Map;

import static axcavo.sjp.TokenType.*;

public class Parser {
    enum jsonExpr {
        OBJECT,
        ARRAY,
    }

    private TokenType previousTokenType;

    /*
    public Object parse(String data) {
        Scanner scanner = new Scanner(data);
        List<Token> tokens =  scanner.scanTokens();

        tokens.forEach(token -> {

        });

        return null;
    }
    */

    private Map<String, Object> parseObject(Token token) {
        switch (previousTokenType) {
            case OBJECT_START:
                if (token.type != )
        }
        return null;
    }

}
