package co.kr.mychoice.tripmap20.conts;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class Security {

    private static final String TAG = "IABUtil/Security";

    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    /**
     * Verifies that the data was signed with the given signature, and returns
     * the verified purchase. The data is in JSON format and signed
     * with a private key.
     * and product ID of the purchase.
     *
     *
     * @param base64PublicKey the base64-encoded public key to use for verifying.
     * @param signedData      the signed JSON string (signed, not encrypted)
     * @param signature       the signature for the data, signed with the private key
     */
    public static boolean verifyPurchase(String base64PublicKey,
                                         String signedData, String signature) throws IOException
    {
        if (TextUtils.isEmpty(signedData) || TextUtils.isEmpty(base64PublicKey) ||
                TextUtils.isEmpty(signature))
        {


            Log.e(TAG, "Purchase verification failed: missing data.");
            return false;
        }

        PublicKey key = generatePublicKey(base64PublicKey);
        return verify(key, signedData, signature);
    }

    /**
     * Generates a PublicKey instance from a string containing the
     * Base64-encoded public key.
     *
     * @param encodedPublicKey Base64-encoded public key
     * @throws IllegalArgumentException if encodedPublicKey is invalid
     */
    public static PublicKey generatePublicKey(String encodedPublicKey) throws IOException
    {
        try
        {
            byte[] decodedKey = Base64.decode(encodedPublicKey, Base64.DEFAULT);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
            return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
        catch (InvalidKeySpecException e)
        {
            String msg = "Invalid key specification";
            throw new IOException(msg);
        }

    }

    /**
     * Verifies that the signature from the server matches the computed
     * signature on the data.  Returns true if the data is correctly signed.
     *
     * @param publicKey  public key associated with the developer account
     * @param signedData signed data from server
     * @param signature  server signature
     * @return true if the data and signature match
     */
    public static boolean verify(PublicKey publicKey, String signedData, String signature)
    {
        byte[] signatureBytes;
        Signature sig;
        try
        {
            signatureBytes = Base64.decode(signature,Base64.DEFAULT);

        }
        catch (IllegalArgumentException e)
        {
            //Base64 decoding failed
            return false;
        }

        try{

            Signature signatureAlgorithm = Signature.getInstance(SIGNATURE_ALGORITHM);
            signatureAlgorithm.initVerify(publicKey);
            signatureAlgorithm.update(signedData.getBytes());
            if(signatureAlgorithm.verify(signatureBytes)){
                return false;
            }

            return true;


        } catch (NoSuchAlgorithmException e) {
            throw  new RuntimeException(e);
        }
        catch (InvalidKeyException e)
        {
            Log.e(TAG, "Invalid key specification.");
        }
        catch (SignatureException e)
        {
            Log.e(TAG, "Signature exception.");
        }
        catch (IllegalArgumentException e) {
            Log.e(TAG, "Base64 decoding failed.");
        }
        return false;
    }

}
