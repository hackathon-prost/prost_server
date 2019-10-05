package org.prost.core.infrastructure.repository

import io.reactivex.Completable
import io.reactivex.Single
import org.prost.core.domain.Categories
import org.prost.core.domain.Category
import org.prost.core.domain.error.CategoryNotFoundException

class InMemoryCategories : Categories {
    override fun findBy(name: String): Single<Category> {
        return Single.create { emitter ->
            val category = categories.find { it.name.toLowerCase() == name.toLowerCase() }
            if (category != null) emitter.onSuccess(category)
            else emitter.onError(CategoryNotFoundException(name))
        }
    }

    private val categories = mutableSetOf<Category>(
        Category("ENVIRONMENT"),
        Category("ANIMALS")
    )

    override fun add(category: Category): Completable {
        return Completable.fromAction {
            categories.add(category)
        }
    }

    override fun findAll(): Single<List<Category>> {
        return Single.just(categories.toList())
    }

    override fun remove(name: String): Completable {
        return Completable.fromAction {
            categories.removeIf { it.name == name }
        }
    }

}