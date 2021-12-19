package com.mettle.feature.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class Utils {

	public static LocalDateTime getNow() {

		return LocalDateTime.now(ZoneOffset.UTC);
	}

}
