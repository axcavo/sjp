package axcavo.sjp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static axcavo.sjp.TokenType.*;

public class Scanner {
	private static final Map<String, TokenType> keywords;

	static {
		keywords = new HashMap<>();
		keywords.put("true", BOOLEAN_VALUE);
		keywords.put("false", BOOLEAN_VALUE);
		keywords.put("null", NULL_VALUE);
	}

	private final String source;
	private final List<Token> tokens = new ArrayList<>();

	private int start = 0;
	private int current = 0;
	private int line = 1;

	Scanner(String source) {
		this.source = source;
	}

	List<Token> scanTokens() {
		while(!isAtEnd()) {
			start = current;
			scanToken();
		}

		tokens.add(new Token(EOF, "", null, line));
		return tokens;
	}

	private boolean isAtEnd() {
		return current >= source.length();
	}

	private void scanToken() {
		char c = advance();
		switch(c) {
			case ' ':
			case '\r':
			case '\t': break;
			case '\n': line++; break;

			case '{': addToken(OBJECT_START); break;
			case '}': addToken(OBJECT_END); break;
			case '[': addToken(ARRAY_START); break;
			case ']': addToken(ARRAY_END); break;
			case ':': addToken(KEY_SEPARATOR); break;
			case ',': addToken(VALUE_SEPARATOR); break;
			
			case '"': string(); break;
			default:
			if (isAlpha(c)) {
				keyword();
			} else if(isDigit(c)) {
				number();
			} else {
				throw new ScannerException(String.format("Invalid token %c found at line %d.", c, current, line));
			}
			break;
		}
	}
	
	private char advance() {
		return source.charAt(current++);
	}
	
	private void addToken(TokenType type) {
		addToken(type, null);
	}
	
	private void addToken(TokenType type, Object literal) {
		String text = source.substring(start, current);
		tokens.add(new Token(type, text, literal, line));
	}
	
	private char peek() {
		if (isAtEnd()) return '\0';
		return source.charAt(current);
	}
	
	private char peekNext() {
		if (current + 1 >= source.length()) return '\0';
		return source.charAt(current + 1);
	}
	
	private void string() {
		while(peek() != '"' && !isAtEnd()) {
			if (peek() == '\n') line++;
			advance();
		}

		if (isAtEnd()) {
			throw new ScannerException(String.format("Found unterminated string started at line %d.", line));
		}

		advance();

		String value = source.substring(start + 1, current -1);
		addToken(STRING_VALUE, value);
	}

	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}

	private void number() {
		while(isDigit(peek())) advance();

		if (peek() == '.' && isDigit(peekNext())) {
			advance();
			while (isDigit(peek())) advance();
		}

		addToken(NUMBER_VALUE, Double.parseDouble(source.substring(start, current)));
	}

	private boolean isAlpha(char c) {
		return (c >= 'a' && c <= 'z') ||
			   (c >= 'A' && c <= 'Z');
	}

	private void keyword() {
		while(isAlpha(peek())) advance();

		String text = source.substring(start, current);
		TokenType type = keywords.get(text);

		Object literal = switch(text) {
			case "true" -> true;
			case "false" -> false;
			case "null" -> null;
			default -> throw new ScannerException(String.format("Invalid value found at line %d.", line));
		};

		addToken(type, literal);
	}
}