package axcavo.sjp;

enum TokenType {
	// Structural tokens
	OBJECT_START,
	OBJECT_END,
	ARRAY_START,
	ARRAY_END,
	KEY_SEPARATOR,
	VALUE_SEPARATOR,
	EOF,
	
	// Value tokens
	STRING_VALUE,
	NUMBER_VALUE,
	BOOLEAN_VALUE,
	NULL_VALUE,
}
