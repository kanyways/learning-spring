/**
 * Project Name:ethereum-parent
 * File Name:IdCardUtils.java
 * Package Name:me.kany.project.learning.spring.utils.idcard
 * Date:2019年07月30日 17:01
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils.idcard;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:IdCardUtils<br/>
 * Function: 身份证验证类<br/>
 * Date:2019年07月30日 17:01<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public class IdCardUtils {

    static Pattern pattern = Pattern.compile("^([A-Z]{1,2})([0-9]{6})([A0-9])$");
    static String strValidChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * validHKIdCard: 验证香港的身份证<br/>
     *
     * @param idCard
     * @return
     * @author Jason.Wang
     * @createTime 2019/7/30 17:07
     */
    public static boolean validHKIdCard(String idCard) {
        //身份证长度小于8位的不处理
        if (StringUtils.isBlank(idCard) || idCard.length() < 8) {
            return false;
        }
        int idCardLen = idCard.length();
        if (8 == idCardLen || 9 == idCardLen) {
            // handling bracket
            if (idCard.charAt(idCard.length() - 3) == '(' && idCard.charAt(idCard.length() - 1) == ')') {
                idCard = idCard.substring(0, idCard.length() - 3) + idCard.charAt(idCard.length() - 2);
            }
            // convert to upper case
            idCard = idCard.toUpperCase();
            // regular expression to check pattern and split
            Matcher matcher = pattern.matcher(idCard);
            if (!matcher.matches()) {
                return false;
            }
            // the character part, numeric part and check digit part
            String charPart = matcher.group(1);
            String numPart = matcher.group(2);
            String checkDigitStr = matcher.group(3);

            char checkDigit = checkDigitStr.charAt(0);

            // calculate the checksum for character part
            int checkSum = 0;
            if (charPart.length() == 2) {
                checkSum += 9 * (10 + strValidChars.indexOf(charPart.charAt(0)));
                checkSum += 8 * (10 + strValidChars.indexOf(charPart.charAt(1)));
            } else {
                checkSum += 9 * 36;
                checkSum += 8 * (10 + strValidChars.indexOf(charPart));
            }

            // calculate the checksum for numeric part
            for (int i = 0, j = 7; i < numPart.length(); i++, j--) {
                checkSum += j * Integer.parseInt(String.valueOf(numPart.charAt(i)));
            }
            // verify the check digit
            int remaining = checkSum % 11;
            int verify = remaining == 0 ? 0 : 11 - remaining;
            try {
                if (verify == 10) {
                    return checkDigit == 'A';
                } else {
                    return verify == Integer.parseInt(checkDigitStr);
                }

            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}
