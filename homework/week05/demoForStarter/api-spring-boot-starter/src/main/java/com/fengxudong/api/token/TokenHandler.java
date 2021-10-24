package com.fengxudong.api.token;

/**
 * token加解密接口
 * 实现此接口的实现类需要保证单例模式
 */
public interface TokenHandler {

    /**
     * 加密
     * @param beforeEncryptStr
     * @return
     */
    public String encrypt(String beforeEncryptStr);

    /**
     * 解密
     * @param beforeDecryptStr
     * @return
     */
    public String decrypt(String beforeDecryptStr);

    /**
     * 校验token （暂时 只校验token是否可以解密成功，不校验是否一致）
     * @param token
     * @return
     */
    public boolean checkToken(String token);
}
