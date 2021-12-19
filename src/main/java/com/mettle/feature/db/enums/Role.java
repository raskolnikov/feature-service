package com.mettle.feature.db.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum Role {

	ADMIN, USER, ANY;

	@JsonCreator
	public static Role create(String name) {
		name = name.toUpperCase();
		return valueOf(name);
	}

	@JsonValue
	@Override
	public String toString() {
		return super.toString().toUpperCase();
	}

}
