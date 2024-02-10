package com.titi.app.domain.color.model

data class GraphColor(
    val selectedIndex: Int = 0,
    val direction: GraphDirection = GraphDirection.Right,
    val graphColors: List<GraphColorType> = GraphColorType.entries,
) {
    enum class GraphDirection {
        Left,
        Right,
    }

    enum class GraphColorType {
        D1,
        D2,
        D3,
        D4,
        D5,
        D6,
        D7,
        D8,
        D9,
        D10,
        D11,
        D12,
    }
}
