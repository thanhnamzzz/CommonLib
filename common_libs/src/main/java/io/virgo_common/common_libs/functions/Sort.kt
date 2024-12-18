package io.virgo_common.common_libs.functions

object Sort {
    fun <T, R : Comparable<R>> sortByProperty(
        list: MutableList<T>,
        isAscending: Boolean = true,
        propertySelector: (T) -> R,
    ): List<T> {
        return if (isAscending) {
            list.sortedWith(compareBy { propertySelector(it) })
        } else {
            list.sortedWith(compareByDescending { propertySelector(it) })
        }
    }
}