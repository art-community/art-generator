package io.art.generator.constants

const val JAVA = "java"
const val CLASS = "class"
const val JAR = "jar"


const val META_NAME = "meta"
const val LOAD_NAME = "load"
const val DEPENDENCIES_NAME = "dependencies"
const val GENERATOR_NAME = "generator"
const val GET_ELEMENTS_NAME = "getElements"
const val GET_SYMBOLS_NAME = "getSymbols"
const val INVOKE_NAME = "invoke"
const val CONSTRUCTOR_NAME = "constructor"
const val INSTANCE_NAME = "instance"
const val ARGUMENT_NAME = "argument"
const val ARGUMENTS_NAME = "arguments"

const val META_TYPE_NAME = "metaType"
const val META_ARRAY_NAME = "metaArray"
const val META_VARIABLE_NAME = "metaVariable"
const val META_ENUM_NAME = "metaEnum"

val META_METHODS = setOf(
        META_TYPE_NAME,
        META_ARRAY_NAME,
        META_VARIABLE_NAME,
        META_ENUM_NAME
)


const val CAST_NAME = "cast"
const val SET_OF_NAME = "setOf"
