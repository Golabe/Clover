package buzz.pushfit.im.extention

/**
 * Created by yuequan on 2017/10/27.
 */

fun String.isValidPhone():Boolean= this.length == 11
fun String.isValidPassword():Boolean=this.length in 8..12
fun String.isValidCode():Boolean=this.length==6


