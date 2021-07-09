package com.app.blockchain.util;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Se realiza el hash SHA-256 para el contenido de los bloques
 *
 *
 */
public class SHA256 {

    /**
     * Se aplica el SHA-256 a partir de una entrada
     * Su retorno es del mismo tipo que la entrada (String)
     *
     * @param input
     * @return String
     */
    public static String getSHA256(String input){

        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            toReturn = String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (Exception e){
            e.printStackTrace();
        }
        return toReturn;
    }
}
