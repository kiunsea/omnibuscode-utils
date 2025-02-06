package com.omnibuscode.utils;


import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;

class JSONUtilTest {

	@Test
	void test() throws Exception {
		JSONObject tJson = new JSONObject();
		tJson.put("A", "1");
		tJson.put("B", "2");
		
		JsonNode jn = JSONUtil.jsonObjectToJsonNode(tJson);
		System.out.println(jn.get("B"));
	}

}
