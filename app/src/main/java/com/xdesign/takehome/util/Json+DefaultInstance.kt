package com.xdesign.takehome.util

import kotlinx.serialization.json.Json

val jsonDefaultInstance = Json { ignoreUnknownKeys = true; isLenient = true; encodeDefaults = false }
