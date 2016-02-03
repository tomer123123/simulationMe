package com.example.simulationMe;

import android.util.Log;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


/**
 * Created by Tomer on 11/27/2015.
 */
public class FTP {

    /**
     *
     * @param ip
     * @param userName
     * @param pass
     */
    public FTPClient  connnectingwithFTP(String ip, String userName, String pass) {
        boolean status = false;
        try {
            FTPClient mFtpClient = new FTPClient();
//            mFtpClient.setConnectTimeout(10 * 1000);
            mFtpClient.connect(InetAddress.getByName(ip));
            status = mFtpClient.login(userName, pass);
            Log.e("isFTPConnected", String.valueOf(status));
//            if (FTPReply.isPositiveCompletion(mFtpClient.getReplyCode())) {
//                mFtpClient.setFileType(3);
//                mFtpClient.enterLocalPassiveMode();
//                FTPFile[] mFileArray = mFtpClient.listFiles();
//                Log.e("Size", String.valueOf(mFileArray.length));
//            }
            return mFtpClient;
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {return null;}
    }

    /**
     *
     * @param ftpClient FTPclient object
     * @param downloadFile local file which need to be uploaded.
     */
    public static void uploadFile(FTPClient ftpClient, File downloadFile,String serverfilePath) {
        try {
            FileInputStream srcFileStream = new FileInputStream(downloadFile);
            boolean status = ftpClient.storeFile("remote ftp path",
                    srcFileStream);
            Log.e("Status", String.valueOf(status));
            srcFileStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
