package jp.okmt.notepad.domain.editor

interface EditorActivityContract {
    interface View {
        var title: String
        var noteText: String

        fun notifySnackBar(message: String)
    }

    interface Presenter {
        val isCreateNew: Boolean
        fun saveMemo(title: String, noteText: String): Long
    }
}