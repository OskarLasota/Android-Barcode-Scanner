package com.frezzcoding.barcodescanner

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import me.dm7.barcodescanner.zxing.ZXingScannerView

class MainActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var scannerView : ZXingScannerView
    private lateinit var txtResult : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scannerView = findViewById(R.id.cameraview)
        txtResult = findViewById(R.id.txtView)


        //request permission
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener{
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    scannerView.setResultHandler(this@MainActivity)
                    scannerView.startCamera()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {

                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(applicationContext, "you must accept", Toast.LENGTH_SHORT).show()
                }

            }).check()

    }

    override fun onDestroy() {
        scannerView.stopCamera()
        super.onDestroy()
    }

    override fun handleResult(rawResult: Result?) {
        txtResult.text = rawResult?.text
    }
}


