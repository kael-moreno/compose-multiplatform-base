package com.kaelmoreno.compose.composemultiplatformbase.data.encryption

/**
 * Common interface for encryption operations across platforms
 */
interface EncryptionService {

    /**
     * Encrypts a string value
     * @param data The plain text to encrypt
     * @return The encrypted data as a Base64 string, or null if encryption fails
     */
    fun encrypt(data: String): String?

    /**
     * Decrypts a string value
     * @param encryptedData The encrypted data as a Base64 string
     * @return The decrypted plain text, or null if decryption fails
     */
    fun decrypt(encryptedData: String): String?

    /**
     * Checks if the encryption service is properly initialized
     */
    fun isInitialized(): Boolean
}
