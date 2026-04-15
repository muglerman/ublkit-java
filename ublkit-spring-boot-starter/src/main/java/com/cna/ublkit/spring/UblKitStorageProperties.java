package com.cna.ublkit.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propiedades para configurar el almacenamiento de documentos UBL.
 */
@ConfigurationProperties(prefix = "ublkit.storage")
public class UblKitStorageProperties {

    private Type type = Type.LOCAL;
    private final Local local = new Local();
    private final S3 s3 = new S3();

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Local getLocal() {
        return local;
    }

    public S3 getS3() {
        return s3;
    }

    public enum Type {
        LOCAL,
        S3
    }

    public static class Local {
        private String baseDirectory = "./build/ublkit-storage";

        public String getBaseDirectory() {
            return baseDirectory;
        }

        public void setBaseDirectory(String baseDirectory) {
            this.baseDirectory = baseDirectory;
        }
    }

    public static class S3 {
        private String endpoint;
        private String region = "us-east-1";
        private String accessKey;
        private String secretKey;
        private String bucket;
        private boolean pathStyleAccess = true;
        private boolean createBucketOnStartup = false;

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public String getBucket() {
            return bucket;
        }

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }

        public boolean isPathStyleAccess() {
            return pathStyleAccess;
        }

        public void setPathStyleAccess(boolean pathStyleAccess) {
            this.pathStyleAccess = pathStyleAccess;
        }

        public boolean isCreateBucketOnStartup() {
            return createBucketOnStartup;
        }

        public void setCreateBucketOnStartup(boolean createBucketOnStartup) {
            this.createBucketOnStartup = createBucketOnStartup;
        }
    }
}