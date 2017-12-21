package cn.yoogr.clover.widget

import android.content.Context
import android.support.design.widget.BottomSheetDialog
import android.view.View
import cn.yoogr.clover.R
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by yuequan on 2017/11/24.
 */
class MeBottomSheetDialog(context: Context) : BottomSheetDialog(context) {
    var view: View = View.inflate(context, R.layout.layout_bottom_sheet_me, null)
    private var avatar: CircleImageView
    init {
        setContentView(view)
        avatar = view.findViewById(R.id.avatar)
        avatar.apply {
            setOnClickListener {

            }
        }
    }
}