package co.kr.mychoice.tripmap20.conts


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.kr.mychoice.tripmap20.R
import co.kr.mychoice.tripmap20.getdata.ContsData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.vr.sdk.widgets.pano.VrPanoramaView
import java.net.URL

class ContsImage3Activity : AppCompatActivity() {

    private val vrPanoramaView : VrPanoramaView by lazy{

        findViewById(R.id.vrPanoramaView)
    }

    var datalist: ArrayList<ContsData>? = null

    val option = VrPanoramaView.Options().also {
        it.inputType = VrPanoramaView.Options.TYPE_MONO
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conts_image3)


        vrPanoramaView.setStereoModeButtonEnabled(false)
        vrPanoramaView.setInfoButtonEnabled(false)
        vrPanoramaView.setPureTouchTracking(true)
        vrPanoramaView.setFullscreenButtonEnabled(true)


        showWithUrl()

        //loadFromBitmap()



    }


    private fun showWithUrl(){


        val DEMO_PANORAMA_LINK = "https://tripreview.net/file/1664943464dreamstime_l_192642365.jpg"
        Glide.with(this).asBitmap().load(DEMO_PANORAMA_LINK).into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) { }
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                // "option": Declared at the step 3
                vrPanoramaView.loadImageFromBitmap(resource, option)
            }
        })
    }

    private fun loadFromBitmap(){

        val bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.testimg)
        vrPanoramaView.loadImageFromBitmap(bitmap, option)

    }




    private fun getBitmapFromUrl(url : String) : Bitmap? {

        try {

            val connection = URL(url).openConnection()
            connection.doInput = true
            connection.connect()

            val inputStream = connection.getInputStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)

            inputStream.close()

            return bitmap



        }catch (ex : Exception){

            ex.printStackTrace()

            return  null

        }

    }


    fun getShopDetail(idsids: Int) {

    }



}