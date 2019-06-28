package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extension.humanizeDiff
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

class ImageMessage(
    id:String,
    from:User?,
    chat:Chat,
    isIncoming:Boolean = false,
    date: Date = Date(),
    var image:String?
    ) :BaseMessage(id,from,chat,isIncoming,date) {
    override fun formatMessage(): String = "id:$id ${Utils.toInitials(from?.firstName, from?.lastName)}"+
    "${if(isIncoming) " получил" else " отправил"} изображение \"$image\" ${date.humanizeDiff(date)}"

}