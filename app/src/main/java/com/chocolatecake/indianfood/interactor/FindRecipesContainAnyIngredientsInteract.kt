import com.chocolatecake.indianfood.interactor.IndianFoodDataSource
import com.chocolatecake.indianfood.model.Recipe

class FindRecipesContainAnyIngredientsInteractactor(
    private val dataSource: IndianFoodDataSource
) {
    operator fun invoke(ingredientNames: List<String>): List<Recipe> =
        dataSource.getAllRecipesData()
            .ifEmpty { throw IllegalStateException() }
            .filter { recipe ->
                recipe.ingredients.containsAnyIgnoreCase(ingredientNames)
            }

    private fun List<String>.containsAnyIgnoreCase(other: List<String>) =
        any { ingredientName ->
            other.map { it.lowercase() }.contains(ingredientName.lowercase())
        }
}
