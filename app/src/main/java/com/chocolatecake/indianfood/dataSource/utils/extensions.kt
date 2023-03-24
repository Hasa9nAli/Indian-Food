package com.chocolatecake.indianfood.dataSource.utils

fun String.splitByDots(): List<String> = split(".")

fun String.splitBySemicolon(): List<String> = split(";")

fun String.removeEmptyLines(): String = replace("\n", "")