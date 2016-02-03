package br.eti.rodper.json;

import java.util.StringTokenizer;

public class JsonTokenizer {

	private StringTokenizer tokenizer;
	private String currentToken;

	public JsonTokenizer(String json) {
		tokenizer = new StringTokenizer(json, "\"{}[],:\b\n\t\f ", true);
	}

	public String nextToken() {

		while(hasMoreTokens()) {
			String token = tokenizer.nextToken().trim();
			if(!token.isEmpty()) {
				// a string value
				if(token.startsWith("\"")) {
					do {
						token += tokenizer.nextToken();
					}
					while((!token.endsWith("\"") || token.endsWith("\\\"")) && hasMoreTokens());
				}
				currentToken = token;
				return token;
			}
		}
		currentToken = null;
		return null;
	}

	public String currentToken() {
		return currentToken;
	}

	public boolean hasMoreTokens() {
		return tokenizer.hasMoreTokens();
	}
}
