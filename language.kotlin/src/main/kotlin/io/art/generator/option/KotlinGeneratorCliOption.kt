/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package io.art.generator.option

import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption

enum class KotlinGeneratorCliOption(
        override val optionName: String,
        override val valueDescription: String,
        override val description: String,
        override val allowMultipleOccurrences: Boolean = false,
) : AbstractCliOption {
    PROJECT_BASE_DIRECTORY_OPTION(
            "baseDirectory", "Base directory",
            ""
    );

    override val required: Boolean = false

    companion object {
        const val KOTLIN_GENERATOR_COMPILER_PLUGIN_ID: String = "io.art.generator"
    }
}
