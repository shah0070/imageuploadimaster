package com.devtides.androidarchitectures.util;

import android.content.Context;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class Apputil {
    public static AmazonS3 s3 = null;
    public static TransferUtility utility = null;
    public static CognitoCachingCredentialsProvider credentialsProvider = null;

    public static synchronized CognitoCachingCredentialsProvider getCredentialsProvider(Context context) {

        if (credentialsProvider == null) {
            credentialsProvider = new CognitoCachingCredentialsProvider(
                    context,
                    Constants.IDENTITY_POOL_ID, // Identity Pool ID
                    Regions.AP_SOUTH_1
            );
        }

        return credentialsProvider;
    }
    public static synchronized TransferUtility getTransferService(Context context, AmazonS3 s3) {
        if (utility == null) {
            utility = TransferUtility.builder()
                    .context(context)
                    .s3Client(s3)
                    .build();
        }
        return utility;
    }

    public static synchronized AmazonS3 getS3(CognitoCachingCredentialsProvider credentialsProvider, ClientConfiguration clientConfiguration) {
        if (s3 == null) {
            s3 = new AmazonS3Client(credentialsProvider, clientConfiguration);
        }
        return s3;
    }
}
