package dduwcom.mobile.finalreport

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class IntroductionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.introduce_me)

        val profileimage: ImageView = findViewById(R.id.profileimage)
        val tvName: TextView = findViewById(R.id.tvName)
        val tvStuid: TextView = findViewById(R.id.tvStuid)
        val tvClass: TextView = findViewById(R.id.tvClass)
        val tvintroduce : TextView = findViewById(R.id.tvintroduce)

        val profile = R.drawable.me
        val name = "이지우"
        val stuId = "20210805"
        val s_class = "01분반"
        val introduction = "안녕하세요! 저는 $name 입니다. 한 학기 동안 안드로이드 스튜디오에 대해 잘 강의해 주셔서 감사합니다."

        profileimage.setImageResource(profile)
        tvName.text = name
        tvStuid.text = stuId
        tvClass.text = s_class
        tvintroduce.text = introduction
    }
}