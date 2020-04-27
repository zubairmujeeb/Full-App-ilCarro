
package com.car.rentservice.common.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j

@Converter(autoApply = true)
public class MapConverter implements AttributeConverter<Map<String, String>, String> {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(Map<String, String> attribute) {
		String convertedAttribute = "";
		try {
			convertedAttribute = OBJECT_MAPPER.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			log.debug("Error occurred while converting map to db column : JsonProcessingException");
		}
		return convertedAttribute;
	}

	@Override
	public Map<String, String> convertToEntityAttribute(String dbData) {
		final TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
		};
		Map<String, String> customerInfo = null;
		try {
			customerInfo = OBJECT_MAPPER.readValue(dbData, typeRef);
		} catch (IOException e) {
			log.debug("Error occurred while converting db column to Map : IOException");
		}

		return customerInfo;
	}
}
