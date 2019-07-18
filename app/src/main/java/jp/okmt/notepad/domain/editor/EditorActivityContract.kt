package jp.okmt.notepad.domain.editor

interface EditorActivityContract {
    interface View {
        var title: String
        var noteText: String

        fun notifySnackBar(message: String)
    }

    interface Presenter {
        fun saveMemo(title: String, noteText: String):Long
    }
}