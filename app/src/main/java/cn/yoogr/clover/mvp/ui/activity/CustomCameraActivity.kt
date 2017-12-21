package cn.yoogr.clover.mvp.ui.activity

import android.hardware.Camera
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import cn.yoogr.clover.R

class CustomCameraActivity : AppCompatActivity(), SurfaceHolder.Callback {
      var mCamera: Camera?=null
      var mPreview: SurfaceView?=null
      var mHolder: SurfaceHolder?=null

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        mCamera?.stopPreview()
        setStartPreview(mCamera, mHolder)
    }
    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        releaseCamera()
    }
    override fun surfaceCreated(holder: SurfaceHolder?) {
        setStartPreview(mCamera, mHolder)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_camera)
        mPreview = findViewById<View>(R.id.preview) as SurfaceView
        mHolder = mPreview?.holder
        mHolder?.addCallback(this)

    }


    override fun onResume() {
        super.onResume()
        if (mCamera == null) {
            mCamera = getCamera()
            if (mHolder != null) {
                setStartPreview(mCamera, mHolder)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        releaseCamera()
    }

    /*
    获取Camera 对象
     */
    private fun getCamera(): Camera ?{
        var camera: Camera? =null
        try {
           camera= Camera.open()

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return camera
    }

    /**
     * 开始预览相机内容
     */
    private fun setStartPreview(camera: Camera?, holder: SurfaceHolder?) {
        try {
            camera?.setPreviewDisplay(holder)
            //设置屏幕旋转90度
            camera?.setDisplayOrientation(90)
            camera?.startPreview()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 释放相机资源
     */
    private fun releaseCamera() {
        if (mCamera != null) {
            mCamera?.setPreviewCallback(null)
            mCamera?.stopPreview()
            mCamera?.release()
            mCamera = null
        }
    }
}
