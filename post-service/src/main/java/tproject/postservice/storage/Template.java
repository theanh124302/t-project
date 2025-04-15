//package tproject.postservice.storage;
//
//package com.willer.trading.common.utils.aws;
//
//import com.amazonaws.AmazonServiceException;
//import com.amazonaws.HttpMethod;
//import com.amazonaws.SdkClientException;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
//import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.amazonaws.services.s3.transfer.TransferManager;
//import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.io.Closeable;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.util.Date;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.concurrent.Executors;
//
//public class S3FileRepository implements Closeable {
//
//    protected static final Logger LOG = LoggerFactory.getLogger(S3FileRepository.class);
//
//    private final String bucketName;
//    private final AmazonS3 s3Client;
//
//    private final String htmlBucketName;
//    private final String cloudFrontDomainName;
//
//    private final TransferManager tm;
//
//    private final S3Configuration s3Configuration;
//    private static final String PUBLIC = "public";
//
//    public S3FileRepository(S3Configuration s3Configuration,
//                            AwsCredentialsConfiguration awsCredentialsConfiguration) {
//
//        this.bucketName = s3Configuration.getBucket();
//        this.htmlBucketName = s3Configuration.getHtmlBucket();
//        this.cloudFrontDomainName = s3Configuration.getCloudFrontDomain();
//        this.s3Configuration = s3Configuration;
//
//        if (StringUtils.isNotBlank(awsCredentialsConfiguration.getKey()) &&
//                StringUtils.isNotBlank(awsCredentialsConfiguration.getSecret())) {
//            s3Client = AmazonS3Client.builder()
//                    .withRegion(s3Configuration.getRegion())
//                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
//                            awsCredentialsConfiguration.getKey(),
//                            awsCredentialsConfiguration.getSecret())))
//                    .build();
//        } else {
//            s3Client = AmazonS3Client.builder()
//                    .withRegion(s3Configuration.getRegion())
//                    .build();
//        }
//
//        long multipartUploadThreshold = s3Configuration.getMultipartUploadThreshold();
//        int maxUploadThreads = s3Configuration.getMaxUploadThreads();
//        tm = TransferManagerBuilder.standard()
//                .withS3Client(s3Client)
//                .withMultipartUploadThreshold(multipartUploadThreshold)
//                .withExecutorFactory(() -> Executors.newFixedThreadPool(maxUploadThreads))
//                .build();
//    }
//
//    @Override
//    public boolean doesObjectExists(String key) {
//        return s3Client.doesObjectExist(bucketName, key);
//    }
//
//    @Override
//    public void delete(String key) {
//        s3Client.deleteObject(bucketName, key);
//    }
//
//    @Override
//    public URL findURLbyKey(String key) {
//        return s3Client.getUrl(bucketName, key);
//    }
//
//    @Override
//    public PreSignedUrlDTO findPreSignedUrlByKey(String key) {
//        try {
//            if (Objects.isNull(key)) return null;
//
//            Date expiration = new Date();
//            long expTimeMillis = expiration.getTime();
//            expTimeMillis += this.s3Configuration.getPreSignedExpired();
//            expiration.setTime(expTimeMillis);
//
//            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key)
//                    .withMethod(HttpMethod.GET).withExpiration(expiration);
//            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
//            String urlStr = url.toString();
//            if (urlStr.contains("?") && bucketName.contains(PUBLIC)) {
//                urlStr = urlStr.split("\\?")[0];
//            }
//            return new PreSignedUrlDTO(urlStr, expiration.toInstant());
//        } catch (AmazonServiceException e) {
//            if (LOG.isErrorEnabled()) {
//                LOG.error("Amazon S3 couldn't process", e);
//            }
//        } catch (SdkClientException e) {
//            if (LOG.isErrorEnabled()) {
//                LOG.error("couldn't parse the response from Amazon S3", e);
//            }
//        } catch (Exception e) {
//            if (LOG.isErrorEnabled()) {
//                LOG.error("Unknown error", e);
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public void upload(String key, StreamingFileUpload file) {
//        InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, key);
//        s3Client.initiateMultipartUpload(initRequest);
//
//        Flowable.fromPublisher(file)
//                .map(partData -> {
//                    try (InputStream inputStream = partData.getInputStream()) {
//                        PutObjectRequest request = new PutObjectRequest(bucketName,
//                                key,
//                                inputStream,
//                                createObjectMetadata(file)).withCannedAcl(CannedAccessControlList.Private);
//                        return tm.upload(request);
//                    }
//                    catch (Exception e) {
//                        LOG.error("Error closing InputStream: {}", e.getMessage());
//                        throw new RuntimeException(e);
//                    }
//                })
//                .subscribe(upload -> {
//                    do {
//                    } while (!upload.isDone());
//                });
//    }
//
//    @Override
//    public void upload(String key, CompletedFileUpload file) {
//        try (InputStream inputStream = file.getInputStream()) {
//            PutObjectRequest request = new PutObjectRequest(bucketName,
//                    key,
//                    inputStream,
//                    createObjectMetadata(file)).withCannedAcl(CannedAccessControlList.Private);
//            s3Client.putObject(request);
//        } catch (IOException e) {
//            if (LOG.isErrorEnabled()) {
//                LOG.error("Error occurred while uploading file " + e.getMessage());
//            }
//        }
//    }
//
//    public String uploadHtml(String key, File file) {
//        PutObjectRequest request = (new PutObjectRequest(this.htmlBucketName, key, file)).withCannedAcl(CannedAccessControlList.Private);
//        this.s3Client.putObject(request);
//        return this.cloudFrontDomainName + "/" + key;
//    }
//
////    public String uploadHtml(String key, File file) {
////        PutObjectRequest request = (new PutObjectRequest(this.bucketName, key, file)).withCannedAcl(CannedAccessControlList.PublicRead);
////        this.s3Client.putObject(request);
////        return this.s3Client.getUrl(this.bucketName, key).toString();
////    }
//
//
//    @Override
//    public void uploadWithoutTryCatch(String key, CompletedFileUpload file, String nameFile) throws IOException {
//        try (InputStream inputStream = file.getInputStream()) {
//            PutObjectRequest request = new PutObjectRequest(bucketName,
//                    key,
//                    inputStream,
//                    createObjectMetadata(file, nameFile)).withCannedAcl(CannedAccessControlList.Private);
//            s3Client.putObject(request);
//        }
//    }
//
//    public void uploadPublicRead(String key, CompletedFileUpload file) {
//        try (InputStream inputStream = file.getInputStream()) {
//            PutObjectRequest request = new PutObjectRequest(bucketName,
//                    key,
//                    inputStream,
//                    createObjectMetadata(file)).withCannedAcl(CannedAccessControlList.PublicRead);
//            s3Client.putObject(request);
//        } catch (IOException e) {
//            if (LOG.isErrorEnabled()) {
//                LOG.error("Error occurred while uploading file " + e.getMessage());
//            }
//        }
//    }
//
//    @Override
//    public void uploadPublicRead(String key, StreamingFileUpload file) {
//        InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, key);
//        s3Client.initiateMultipartUpload(initRequest);
//
//        Flowable.fromPublisher(file)
//                .map(partData -> {
//                    try (InputStream inputStream = partData.getInputStream()){
//                        PutObjectRequest request = new PutObjectRequest(bucketName,
//                                key,
//                                inputStream,
//                                createObjectMetadata(file)).withCannedAcl(CannedAccessControlList.PublicRead);
//                        return tm.upload(request);
//                    }
//                    catch (Exception e) {
//                        LOG.error("Error closing InputStream: {}", e.getMessage());
//                        throw new RuntimeException(e);
//                    }
//                })
//                .subscribe(upload -> {
//                    do {
//                    } while (!upload.isDone());
//                });
//    }
//
//    @Override
//    public void upload(String key, File file) {
//        PutObjectRequest request = new PutObjectRequest(bucketName, key, file);
//        s3Client.putObject(request);
//    }
//
//    @Override
//    public void close() {
//        tm.shutdownNow();
//    }
//
//    private ObjectMetadata createObjectMetadata(FileUpload file) {
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        file.getContentType().ifPresent(contentType -> objectMetadata.setContentType(contentType.getName()));
//        if (file.getSize() != 0) {
//            objectMetadata.setContentLength(file.getSize());
//        }
//        return objectMetadata;
//    }
//
//    private ObjectMetadata createObjectMetadata(FileUpload file, String specifiedFileName) {
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        file.getContentType().ifPresent(contentType -> objectMetadata.setContentType(contentType.getName()));
//
//        Optional<String> extFileOpt = Optional.ofNullable(file.getFilename())
//                .filter(f -> f.contains("."))
//                .map(f -> f.substring(file.getFilename().lastIndexOf(".") + 1));
//        StringBuilder fullFileName = new StringBuilder(specifiedFileName);
//        extFileOpt.ifPresent(s -> fullFileName.append(".").append(s));
//        objectMetadata.setContentDisposition(fullFileName.toString());
//
//        if (file.getSize() != 0) {
//            objectMetadata.setContentLength(file.getSize());
//        }
//        return objectMetadata;
//    }
//}
//
