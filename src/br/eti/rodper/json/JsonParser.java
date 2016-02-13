package br.eti.rodper.json;

import java.util.regex.Pattern;

/**
 * Developed based on http://www.json.org/
 *
 * @author rodper
 *
 */
public class JsonParser {

	private static final Pattern STRING_PATTERN = Pattern.compile("\".*\"");
	private static final Pattern STRING_QUOTES_PATTERN = Pattern.compile("^\"|\"$");
	private static final Pattern STRING_ESCAPE_PATTERN = Pattern.compile(Pattern.quote("\\\""));
	private static final Pattern NUMBER_PATTERN = Pattern.compile("-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?");

	public static JsonObject parseObject(String json) {
		return (JsonObject) parse(json).getValue();
	}

	public static JsonArray parseArray(String json) {
		return (JsonArray) parse(json).getValue();
	}

	public static JsonValue parse(String json) {

		JsonTokenizer tokenizer = new JsonTokenizer(json);
		JsonValue jsonValue = parseValue(tokenizer);
		if (!tokenizer.hasMoreTokens()) {
			return jsonValue;
		}

		throw new RuntimeException(exceptionMessage("'EOF'", tokenizer.nextToken()));
	}

	private static JsonValue parseValue(JsonTokenizer tokenizer) {
		return parseValue(tokenizer, true);
	}

	private static JsonValue parseValue(JsonTokenizer tokenizer, boolean ahead) {

		String token = (ahead) ? tokenizer.nextToken() : tokenizer.currentToken();

		if (isNull(token)) {
			return new JsonValue();
		} else if ("[".equals(token)) {
			return new JsonValue(parseArray(tokenizer));
		} else if ("{".equals(token)) {
			return new JsonValue(parseObject(tokenizer));
		} else if (isString(token)) {
			return new JsonValue(removeQuotes(token));
		} else if (isBoolean(token)) {
			return new JsonValue(Boolean.parseBoolean(token));
		} else if (isNumber(token)) {
			return new JsonValue(Double.parseDouble(token));
		}

		throw new RuntimeException(exceptionMessage("string, number, null, true, false, '{' or '['", token));
	}

	private static JsonArray parseArray(JsonTokenizer tokenizer) {

		JsonArray jsonArray = new JsonArray();
		String token = null;

		while (tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			// closing array
			if ("]".equals(token)) {
				return jsonArray;
			}
			// a comma when there is at least one element
			if (!",".equals(token) && !jsonArray.isEmpty()) {
				break;
			}
			// an element
			jsonArray.add(parseValue(tokenizer, !jsonArray.isEmpty()));
		}

		throw new RuntimeException(exceptionMessage("']', or ','", token));
	}

	private static JsonObject parseObject(JsonTokenizer tokenizer) {

		JsonObject jsonObject = new JsonObject();
		String token = null;

		while (tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			// closing object
			if ("}".equals(token)) {
				return jsonObject;
			}
			// a comma when there is at least one member
			if (!",".equals(token) && !jsonObject.isEmpty()) {
				break;
			}
			// a member
			parsePair(jsonObject, tokenizer, !jsonObject.isEmpty());
		}

		throw new RuntimeException(exceptionMessage("'}', or ','", token));
	}

	private static void parsePair(JsonObject jsonObject, JsonTokenizer tokenizer, boolean ahead) {

		String token = (ahead) ? tokenizer.nextToken() : tokenizer.currentToken();

		// a string key
		if (!isString(token)) {
			throw new RuntimeException(exceptionMessage("string", token));
		}

		String name = removeQuotes(token);

		// a colon
		if (!":".equals(tokenizer.nextToken())) {
			throw new RuntimeException(exceptionMessage("':'", tokenizer.currentToken()));
		}

		jsonObject.put(name, parseValue(tokenizer));
	}

	private static boolean isNull(String text) {
		return "null".equalsIgnoreCase(text);
	}

	private static boolean isString(String text) {
		return (text == null) ? false : STRING_PATTERN.matcher(text).matches();
	}

	private static boolean isBoolean(String text) {
		return "true".equalsIgnoreCase(text) || "false".equalsIgnoreCase(text);
	}

	private static boolean isNumber(String text) {
		return (text == null) ? false : NUMBER_PATTERN.matcher(text).matches();
	}

	private static String removeQuotes(String text) {

		return (text == null) ? null : STRING_ESCAPE_PATTERN.matcher(
				STRING_QUOTES_PATTERN.matcher(text).replaceAll("")).replaceAll("\"");
	}

	private static String exceptionMessage(String expectedTokens, String token) {
		return "Invalid JSON: Expecting " + expectedTokens + ", got '" + token + "'";
	}
}
