package pl.mazak.cigscanner.ui.products

import pl.mazak.cigscanner.data.db.product.Product

data class ProductUiState(
    val id: Int = 0,
    val name: String = "",
    val code: String = "",
    val price: String = "",
) {
    fun toProduct(): Product {
        return Product(
            id = this.id,
            name = this.name,
            code = this.code,
            plnPrice = this.price.toDouble()
        )

    }
}

fun Product.toUiState(): ProductUiState {
    return ProductUiState(
        id = this.id,
        name = this.name,
        code = this.code,
        price = this.plnPrice.toString()
    )
}