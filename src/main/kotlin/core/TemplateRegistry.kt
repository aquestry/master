package dev.anton.core

import dev.anton.model.Template

object TemplateRegistry {
    private val templates = mutableSetOf<Template>()

    fun add(template: Template) {
        templates.add(template)
    }

    fun findTemplateByName(name: String): Template? {
        return templates.firstOrNull { it.name == name }
    }

    fun all(): Set<Template> = templates
}