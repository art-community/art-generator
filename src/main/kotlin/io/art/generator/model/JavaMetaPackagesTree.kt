/*
 * ART
 *
 * Copyright 2019-2021 ART
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.art.generator.model

import io.art.core.combiner.SectionCombiner.combine
import io.art.core.constants.StringConstants.DOT

data class JavaMetaNode(
        val packageFullName: String,
        val classes: Set<JavaMetaClass>,
        val children: Map<String, JavaMetaNode>,
        val packageShortName: String = packageFullName.substringAfterLast(DOT)
)

private class JavaMetaPackagesTree(classes: Sequence<JavaMetaClass>) {
    private val rootPackages = mutableMapOf<String, Node>()

    init {
        classes.forEach { metaClass ->
            val packageName = metaClass.type.classPackageName!!.substringBefore(DOT)
            rootPackages[packageName]?.add(metaClass)?.let { return@forEach }
            rootPackages[packageName] = Node(packageName).apply { add(metaClass) }
        }
    }

    private inner class Node(val packageFullName: String) {
        private val classes = mutableSetOf<JavaMetaClass>()
        private val children = mutableMapOf<String, Node>()

        fun add(childClass: JavaMetaClass) {
            val childPackage = childClass.type.classPackageName!!
            if (childPackage == packageFullName) {
                classes.add(childClass)
                return
            }
            if (childPackage.isEmpty()) return
            val nextPackageShortName = childPackage.removePrefix(packageFullName + DOT).substringBefore(DOT)
            val nextPackageFullName = combine(packageFullName, nextPackageShortName)
            children[nextPackageShortName]?.add(childClass)?.let { return }
            children[nextPackageShortName] = Node(nextPackageFullName).apply { add(childClass) }
        }

        fun collect(): JavaMetaNode = JavaMetaNode(
                packageFullName,
                classes,
                children.mapValues { node -> node.value.collect() })
    }

    fun collect() = rootPackages.mapValues { node -> node.value.collect() }
}

fun Sequence<JavaMetaClass>.asTree(): Map<String, JavaMetaNode> = JavaMetaPackagesTree(this).collect()
