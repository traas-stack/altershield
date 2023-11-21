/*
 * MIT License
 *
 * Copyright (c) [2023]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.alipay.altershield.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lex
 * @date 2018-9-13 14:32:11
 */
public class JsonHelper {
	/**
	 * Gets get json property.
	 *
	 * @param object the object
	 * @param path   the path
	 * @return the get json property
	 */
	public static String getJsonProperty(Object object, String path) {
		if (object == null || StringUtils.isBlank(path)) {
			return null;
		}

		String[] paths = StringUtils.split(path, '.');
		try {
			Object ret = traverse(object, paths, 0, paths.length);
			return ret == null ? null : ret.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Gets get json.
	 *
	 * @param object the object
	 * @param path   the path
	 * @return the get json
	 */
	public static JSON getJson(Object object, String path) {
		if (object == null || StringUtils.isBlank(path)) {
			return null;
		}

		String[] paths = StringUtils.split(path, '.');
		return toJSON(traverse(object, paths, 0, paths.length));
	}

	/**
	 * Gets get json.
	 *
	 * @param object the object
	 * @param path   the path
	 * @return the get json
	 */
	public static JSON getJsonNoThrow(Object object, String path) {
		try {
			return getJson(object, path);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * If the path exist in the JSON object, then replace it with the new value.
	 *
	 * @param object
	 *            the JSON object
	 * @param path
	 *            the path to the value to be replaced
	 * @param value
	 *            the new value
	 * @return true if replaced, false otherwise
	 */
	public static boolean replaceJson(JSON object, String path, Object value) {
		if (object == null || StringUtils.isBlank(path)) {
			return false;
		}

		String[] paths = StringUtils.split(path, '.');
		int length = paths.length - 1;
		try {
			Map<String, Object> obj = toMap(traverse(object, paths, 0, length), path);
			String key = paths[length];
			if (obj.containsKey(key)) {
				obj.put(key, value);
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * Update json boolean.
	 *
	 * @param object the object
	 * @param path   the path
	 * @param value  the value
	 * @return the boolean
	 */
	public static boolean updateJson(JSON object, String path, Object value) {
		if (object == null || StringUtils.isBlank(path)) {
			return false;
		}

		String[] paths = StringUtils.split(path, '.');
		int length = paths.length - 1;
		try {
			Map<String, Object> obj = toMap(traverse(object, paths, 0, length), path);
			obj.put(paths[length], value);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static Object traverse(Object source, String[] paths, int start, int end) {
		while (start < end) {
			String p = paths[start++];
			String relativePath = buildRelativePath(paths, start);

			if (source == null) {
				throw new IllegalArgumentException("Invalid path: " + relativePath + " object is null.");
			}

			if (p.endsWith("]")) {
				int idx = p.indexOf("[");
				if (idx == -1) {
					throw new IllegalArgumentException("Invalid path: " + relativePath + " invalid array notation.");
				} else if (idx != 0) {
					String q = p.substring(0, idx);
					source = stepInto(source, q, relativePath);
					p = p.substring(idx);
				}

				return traverseArray(source, paths, start, end, p, relativePath);
			} else {
				source = stepInto(source, p, relativePath);
			}
		}

		return source;
	}

	private static Object traverseArray(Object object, String[] paths, int start, int end, String p,
			String relativePath) {
		String index = p.substring(p.indexOf("[") + 1, p.length() - 1);
		object = toJSON(object);

		// Array item 0 in path but array not found, just ignore.
		if ("0".equals(index) && object instanceof JSONObject) {
			return traverse(object, paths, start, end);
		}

		if (!(object instanceof JSONArray)) {
			throw new IllegalArgumentException(
					"Invalid path: " + relativePath + " array notation found but object is not array: "
							+ object.getClass().getName());
		}
		JSONArray arr = (JSONArray) object;

		if (index.equals("*")) {
			// Search the whole array.
			for (int j = 0; j < arr.size(); ++j) {
				try {
					Object tmpRes = traverse(arr.get(j), paths, start, end);
					if (tmpRes != null) {
						return tmpRes;
					}
				} catch (Throwable t) {
					// Ignore.
				}
			}
			throw new IllegalArgumentException(
					"Invalid path: " + relativePath + " all array items not satisfy the whole path.");
		} else {
			// Search only one element.
			int idx = Integer.parseInt(index);
			if (arr.size() > idx) {
				return traverse(arr.get(idx), paths, start, end);
			} else {
				throw new IllegalArgumentException("Invalid path: " + relativePath + " index out of array bound.");
			}
		}
	}

	private static Object stepInto(Object object, String p, String relativePath) {
		return toMap(object, relativePath).get(p);
	}

	private static JSON toJSON(Object o) {
		if (o instanceof String) {
			o = JSON.parse((String) o);
		}
		if (o instanceof JSON) {
			return (JSON) o;
		} else {
			return (JSON) JSON.toJSON(o);
		}
	}

	private static Map<String, Object> toMap(Object o, String relativePath) {
		if (o instanceof String) {
			o = JSON.parse((String) o);
		}
		while (o instanceof JSONArray) {
			JSONArray arr = (JSONArray) o;
			if (arr.size() == 1) {
				o = arr.get(0);
			} else {
				throw new IllegalStateException(
						"JSONArray found size [" + arr.size() + "] but not defined in path: " + relativePath);
			}
		}
		if (o instanceof JSONObject) {
			return (JSONObject) o;
		} else if (o instanceof Map) {
			return (Map) o;
		} else {
			throw new IllegalStateException(
					"Invalid object type [" + o.getClass().getName() + "] found in path: " + relativePath);
		}
	}

	private static String buildRelativePath(String[] paths, int end) {
		return Arrays.stream(paths, 0, end).collect(Collectors.joining("."));
	}

}
