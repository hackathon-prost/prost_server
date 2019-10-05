package org.prost.core.domain.error

class CategoryNotFoundException (name: String) : RuntimeException("Category $name not found")