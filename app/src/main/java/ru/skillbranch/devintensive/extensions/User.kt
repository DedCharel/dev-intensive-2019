package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.data.User
import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.utils.Utils

fun User.toUserView():UserView{
    var nickName = Utils.transliteration("$firstName $lastName")
    var initials = Utils.toInitials(firstName, lastName)
    var status = if(lastVisit == null) "Ещё ни разу не был" else if(isOnline) "online" else "Последний раз был ${lastVisit.humanizeDiff()}"
    return UserView(
        id,
        fullName = "$firstName $lastName",
        nickName = nickName,
        avatar = avatar,
        status = status,
        initials = initials
    )
}


