package com.mettle.feature.db.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum Status {

	ACTIVE, INACTIVE, DELETED;

	@JsonCreator
	public static Status create(String name) {
		name = name.toUpperCase();
		return valueOf(name);
	}

	@JsonValue
	@Override
	public String toString() {
		return super.toString().toUpperCase();
	}

}
