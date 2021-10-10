package io.github.kimmking.gateway.token;

/**
 *  token校验执行器
 */
public class TokenHandlerExecutor{

    private static TokenHandler tokenHandler;

    public TokenHandlerExecutor(TokenHandler tokenHandler){
        TokenHandlerExecutor.tokenHandler = tokenHandler;
    }

    public static String encrypt(String beforeEncryptStr) {
        return tokenHandler.encrypt(beforeEncryptStr);
    }


    public static String decrypt(String beforeDecryptStr) {
        return tokenHandler.decrypt(beforeDecryptStr);
    }


    public static boolean checkToken(String token) {
        return tokenHandler.checkToken(token);
    }
}
