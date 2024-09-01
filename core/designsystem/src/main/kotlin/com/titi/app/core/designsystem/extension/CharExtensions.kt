package com.titi.app.core.designsystem.extension

fun Char.isChineseCharacter(): Boolean {
    val ub = Character.UnicodeBlock.of(this)
    return ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS ||
        ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A ||
        ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B ||
        ub === Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS ||
        ub === Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT
}
