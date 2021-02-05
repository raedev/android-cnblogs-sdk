package com.cnblogs.api.http;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.annotations.Nullable;

/**
 * HTTPS 证书工厂
 * Created by ChenRui on 2017/4/25 0025 15:31.
 */
public final class HttpsCertificateFactory {

    /**
     * [安全的] SSL Socket Factory
     *
     * @param inputStream 证书文件流
     */
    public static SSLSocketFactory getInstance(InputStream inputStream) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Certificate certificate = certificateFactory.generateCertificate(inputStream);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("trust", certificate);

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [不安全的] 忽略证书错误的SSL Socket Factory
     */
    @Nullable
    public static SSLSocketFactory getInstance() {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[]{new HttpsTrustManager()}, new SecureRandom());
            return context.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HostnameVerifier hostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }


    /**
     * 不校验HTTPS证书
     */
    public static class HttpsTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(
                X509Certificate[] x509Certificates, String s)
                throws java.security.cert.CertificateException {
        }

        @Override
        public void checkServerTrusted(
                X509Certificate[] x509Certificates, String s)
                throws java.security.cert.CertificateException {
        }

        public boolean isClientTrusted(X509Certificate[] chain) {
            return true;
        }

        public boolean isServerTrusted(X509Certificate[] chain) {
            return true;
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }


    }

}
