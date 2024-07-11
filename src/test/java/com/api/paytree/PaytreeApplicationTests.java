package com.api.paytree;

import com.api.paytree.dto.AESCipher;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaytreeApplicationTests {

    @Test
    void contextLoads() {
        String licenseKey = "Ma29gyAFhvv/+e4/AHpV6pISQIvSKziLIbrNoXPbRS5nfTx2DOs8OJve+NzwyoaQ8p9Uy1AN4S1I0Um5v7oNUg==";

        /*
         * CardPayApi
         */
        String cardNum = "4111111111111111";
        String cardExpire = "3102";
        String buyerAuthNum = "020202";
        String cardPwd = "12";
        String amt = "10000";

        String src = makeEncStr(cardNum, cardExpire, buyerAuthNum, cardPwd, amt);
        System.out.println("src data [" + src + "]");

        AESCipher cipher = new AESCipher(licenseKey);
        String enc = cipher.encrypt(src);
        System.out.println("enc data [" + enc + "]");

        /*
         * CancelPayApi
         */
        String cancelPwd = "123456";
        src = System.currentTimeMillis() + "|" + cancelPwd + "|";
        String encPwd = cipher.encrypt(src);
        System.out.println("enc pwd [" + encPwd + "]");
    }

    private static String makeEncStr(String cardNum, String cardExpire, String buyerAuthNum, String cardPwd, String amt) {
        return System.currentTimeMillis() +
                "|" +
                StringUtils.defaultIfEmpty(cardNum, "") +
                "|" +
                StringUtils.defaultIfEmpty(cardExpire, "") +
                "|" +
                StringUtils.defaultIfEmpty(buyerAuthNum, "") +
                "|" +
                StringUtils.defaultIfEmpty(cardPwd, "") +
                "|" +
                StringUtils.defaultIfEmpty(amt, "0") +
                "|";
    }
}