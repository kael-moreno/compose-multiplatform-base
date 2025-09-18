package com.kaelmoreno.compose.composemultiplatformbase.data.encryption

actual fun createEncryptionService(): EncryptionService = IOSEncryptionService()
