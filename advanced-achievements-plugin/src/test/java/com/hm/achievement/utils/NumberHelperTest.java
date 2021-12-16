package com.hm.achievement.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * @author Yurinan
 * @since 2021/12/15 18:10
 */

class NumberHelperTest {

	@Test
	void shouldComputeNextMultipleOf9() {
		assertEquals(18, NumberHelper.nextMultipleOf9(17));
		assertEquals(18, NumberHelper.nextMultipleOf9(18));
		assertEquals(27, NumberHelper.nextMultipleOf9(19));
	}

}
