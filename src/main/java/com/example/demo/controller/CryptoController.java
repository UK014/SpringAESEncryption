package com.example.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    @PostMapping("/encrypt")
    public String encrypt(@RequestBody String request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(request);
        String plaintext = jsonNode.path("request").asText();

        String salt = KeyGenerators.string().generateKey();
        BytesEncryptor encryptor = Encryptors.stronger("password", salt);
        byte[] encryptedBytes = encryptor.encrypt(plaintext.getBytes());
        String encryptedText = bytesToHex(encryptedBytes);
        return encryptedText;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(String.format("%02X", aByte));
        }
        return result.toString();
    }

    }

