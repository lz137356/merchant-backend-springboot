package com.lxtx.pay.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class T {
    private static final String key = "hispeedb";
    
    public static void main(String[] args) throws Exception {
//    	String s = "Ajo2Fzqhr8E2cONbIOr0Xw==";
//    	System.out.println(d(s));
    	
    	String s1 = "Slide to find ";
    	System.out.println(Base64.encode(s1.getBytes("utf-8")));
    }
    
//    public String encode(String data) throws Exception {
//        Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// 得到加密对象Cipher
//        enCipher.init(Cipher.ENCRYPT_MODE, key, iv);// 设置工作模式为加密模式，给出密钥和向量
//        byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
//        //BASE64Encoder base64Encoder = new BASE64Encoder();
//        return Base64.encode(pasByte);
//        //return base64Encoder.encode(pasByte);
//    }


    public static final String d(String code) {
        try {
            byte[] a = Base64.decode(code);
            DESKeySpec keySpec = new DESKeySpec(key.getBytes("utf-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(T.key.getBytes("utf-8")));
            byte[] result = cipher.doFinal(a);
            return new String(result);
        } catch (Exception e) {
        }
        return null;
    }

    public static class Base64 {
        private static final char [] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/" .toCharArray();

        public static String encode( byte [] data) {
            int start = 0 ;
            int len = data.length;
            StringBuffer buf = new StringBuffer(data.length * 3 / 2 );

            int end = len - 3 ;
            int i = start;
            int n = 0 ;

            while (i <= end) {
                int d = (((( int ) data[i]) & 0x0ff ) << 16 ) | (((( int ) data[i + 1 ]) & 0x0ff ) << 8 ) | ((( int ) data[i + 2 ]) & 0x0ff );

                buf.append(legalChars[(d >> 18 ) & 63 ]);
                buf.append(legalChars[(d >> 12 ) & 63 ]);
                buf.append(legalChars[(d >> 6 ) & 63 ]);
                buf.append(legalChars[d & 63 ]);

                i += 3 ;

                if (n++ >= 14 ) {
                    n = 0 ;
                    buf.append( " " );
                }
            }

            if (i == start + len - 2 ) {
                int d = (((( int ) data[i]) & 0x0ff ) << 16 ) | (((( int ) data[i + 1 ]) & 255 ) << 8 );

                buf.append(legalChars[(d >> 18 ) & 63 ]);
                buf.append(legalChars[(d >> 12 ) & 63 ]);
                buf.append(legalChars[(d >> 6 ) & 63 ]);
                buf.append( "=" );
            } else if (i == start + len - 1 ) {
                int d = ((( int ) data[i]) & 0x0ff ) << 16 ;

                buf.append(legalChars[(d >> 18 ) & 63 ]);
                buf.append(legalChars[(d >> 12 ) & 63 ]);
                buf.append( "==" );
            }

            return buf.toString();
        }

        private static int decode( char c) {
            if (c >= 'A' && c <= 'Z' )
                return (( int ) c) - 65 ;
            else if (c >= 'a' && c <= 'z' )
                return (( int ) c) - 97 + 26 ;
            else if (c >= '0' && c <= '9' )
                return (( int ) c) - 48 + 26 + 26 ;
            else
                switch (c) {
                    case '+' :
                        return 62 ;
                    case '/' :
                        return 63 ;
                    case '=' :
                        return 0 ;
                    default :
                        throw new RuntimeException( "unexpected code: " + c);
                }
        }

        /**
         * Decodes the given Base64 encoded String to a new byte array. The byte array holding the decoded data is returned.
         */

        public static byte [] decode(String s) {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                decode(s, bos);
            } catch (IOException e) {
                throw new RuntimeException();
            }
            byte [] decodedBytes = bos.toByteArray();
            try {
                bos.close();
                bos = null ;
            } catch (IOException ex) {
                System.err.println( "Error while decoding BASE64: " + ex.toString());
            }
            return decodedBytes;
        }

        private static void decode(String s, OutputStream os) throws IOException {
            int i = 0 ;

            int len = s.length();

            while ( true ) {
                while (i < len && s.charAt(i) <= ' ' )
                    i++;

                if (i == len)
                    break ;

                int tri = (decode(s.charAt(i)) << 18 ) + (decode(s.charAt(i + 1 )) << 12 ) + (decode(s.charAt(i + 2 )) << 6 ) + (decode(s.charAt(i + 3 )));

                os.write((tri >> 16 ) & 255 );
                if (s.charAt(i + 2 ) == '=' )
                    break ;
                os.write((tri >> 8 ) & 255 );
                if (s.charAt(i + 3 ) == '=' )
                    break ;
                os.write(tri & 255 );

                i += 4 ;
            }
        }
    }
}
