package io.art.generator.constants

import java.io.File

val stubsOutputDir = {base: File -> File(base, "tmp/kapt3/stubs/main")}
val sourcesOutputDir = {base: File -> File(base, "generated/source/kapt/main")}
