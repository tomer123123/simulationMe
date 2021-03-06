package com.example.simulationMe;


import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.SurfaceHolder.Callback;

import java.io.IOException;
import java.util.List;
//
//class Preview extends ViewGroup implements SurfaceHolder.Callback {
//    Camera mCamera;
//    SurfaceView mSurfaceView;
//    SurfaceHolder mHolder;
//
//    Preview(Context context) {
//        super(context);
//
//        mSurfaceView = new SurfaceView(context);
//        addView(mSurfaceView);
//
//        // Install a SurfaceHolder.Callback so we get notified when the
//        // underlying surface is created and destroyed.
//        mHolder = mSurfaceView.getHolder();
//        mHolder.addCallback(this);
//        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//    }
//
//    public void setCamera(Camera camera) {
//        if (mCamera == camera) { return; }
//
//        stopPreviewAndFreeCamera();
//
//        mCamera = camera;
//
//        if (mCamera != null) {
//            List<Camera.Size> localSizes = mCamera.getParameters().getSupportedPreviewSizes();
//            mSupportedPreviewSizes = localSizes;
//            requestLayout();
//
//            try {
//                mCamera.setPreviewDisplay(mHolder);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            // Important: Call startPreview() to start updating the preview
//            // surface. Preview must be started before you can take a picture.
//            mCamera.startPreview();
//        }
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//
//    }
//
//
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//
//    }
//
//    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//        // Now that the size is known, set up the camera parameters and begin
//        // the preview.
//        Camera.Parameters parameters = mCamera.getParameters();
//        parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
//        requestLayout();
//        mCamera.setParameters(parameters);
//
//        // Important: Call startPreview() to start updating the preview surface.
//        // Preview must be started before you can take a picture.
//        mCamera.startPreview();
//    }
//}