package com.mettle.feature.db.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum AccessType {

	GLOBAL, CUSTOM, RESTRICTED;

	@JsonCreator
	public static AccessType create(String name) {
		name = name.toUpperCase();
		return valueOf(name);
	}

	@JsonValue
	@Override
	public String toString() {
		return super.toString().toUpperCase();
	}


}
