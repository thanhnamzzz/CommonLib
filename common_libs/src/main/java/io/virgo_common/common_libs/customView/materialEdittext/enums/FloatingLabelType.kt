package io.virgo_common.common_libs.customView.materialEdittext.enums

enum class FloatingLabelType(val value: Int) {
	NONE(0),
	NORMAL(1),
	HIGHLIGHT(2);

	companion object {
		@JvmStatic
		fun fromInt(value: Int) = entries.find { it.value == value } ?: NONE
	}
}