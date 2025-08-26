package com.bookmyshow.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {
	private static final ObjectMapper MAPPER = new ObjectMapper()
			.enable(SerializationFeature.INDENT_OUTPUT);
	
	public static <T> T read(String filePath, Class<T> clazz) {
		try {
			return MAPPER.readValue(new File(filePath), clazz);
		} catch (IOException e) {
			throw new RuntimeException("Failed to read JSON: " + filePath, e);
		}
	}
	
	public static <T> T read(String filePath, TypeReference<T> typeRef) {
		try {
			return MAPPER.readValue(new File(filePath), typeRef);
		} catch (IOException e) {
			throw new RuntimeException("Failed to read JSON: " + filePath, e);
		}
	}
	
	public static void write(String filePath, Object data) {
		try {
			File target = new File(filePath);
			File parent = target.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			MAPPER.writeValue(target, data);
		} catch (IOException e) {
			throw new RuntimeException("Failed to write JSON: " + filePath, e);
		}
	}
	
	public static <T> void appendToArray(String filePath, T element, TypeReference<List<T>> listType) {
		try {
			List<T> list;
			File file = new File(filePath);
			if (file.exists()) {
				list = MAPPER.readValue(file, listType);
			} else {
				list = new java.util.ArrayList<>();
			}
			list.add(element);
			MAPPER.writeValue(file, list);
		} catch (IOException e) {
			throw new RuntimeException("Failed to append to JSON array: " + filePath, e);
		}
	}
	
	public static void ensureFile(String filePath) {
		try {
			Path path = Path.of(filePath);
			if (!Files.exists(path)) {
				Files.createDirectories(path.getParent());
				Files.writeString(path, "{}", StandardOpenOption.CREATE);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to ensure JSON file: " + filePath, e);
		}
	}
} 